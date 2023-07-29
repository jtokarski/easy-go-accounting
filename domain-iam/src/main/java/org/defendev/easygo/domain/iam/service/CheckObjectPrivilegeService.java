package org.defendev.easygo.domain.iam.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.easygo.domain.iam.api.CheckObjectPrivilegeQuery;
import org.defendev.easygo.domain.iam.api.ICheckObjectPrivilegeService;
import org.defendev.easygo.domain.iam.api.Privilege;
import org.defendev.easygo.domain.iam.service.dto.IEasygoUserDetails;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;



@Service
public class CheckObjectPrivilegeService implements ICheckObjectPrivilegeService {

    private static final Logger log = LogManager.getLogger();

    private final QueryAnonymousPrivilegeService queryAnonymousPrivilegeService;

    private final ExtractLoggedInUserService extractLoggedInUserService;

    public CheckObjectPrivilegeService(QueryAnonymousPrivilegeService queryAnonymousPrivilegeService,
                                       ExtractLoggedInUserService extractLoggedInUserService) {
        this.queryAnonymousPrivilegeService = queryAnonymousPrivilegeService;
        this.extractLoggedInUserService = extractLoggedInUserService;
    }

    @Override
    public void check(CheckObjectPrivilegeQuery query) {
        final Map<Privilege, Set<Long>> anonymousPrivilegeToOwnershipUnit = queryAnonymousPrivilegeService
            .queryPrivilegeToOwnershipUnit();
        final IEasygoUserDetails userDetails = extractLoggedInUserService.extract();
        final Map<Privilege, Set<Long>> privilegeToOwnershipUnit = userDetails.getPrivilegeToOwnershipUnit();

        final Privilege privilege = query.getPrivilege();
        final Long ownershipUnitId = query.getOwnershipUnitId();

        for (Privilege anyPrivilege : privilege.getContainedIn()) {
            if (privilegeToOwnershipUnit.containsKey(anyPrivilege) &&
                    privilegeToOwnershipUnit.get(anyPrivilege).contains(ownershipUnitId)) {
                return;
            }
            if (anonymousPrivilegeToOwnershipUnit.containsKey(anyPrivilege) &&
                    anonymousPrivilegeToOwnershipUnit.get(anyPrivilege).contains(ownershipUnitId)) {
                return;
            }
        }

        throw new AccessDeniedException("Access denied");
    }

}
