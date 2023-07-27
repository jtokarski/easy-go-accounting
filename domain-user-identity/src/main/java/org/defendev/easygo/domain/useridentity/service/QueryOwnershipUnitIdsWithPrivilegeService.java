package org.defendev.easygo.domain.useridentity.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.easygo.domain.useridentity.api.IQueryOwnershipUnitIdsWithPrivilegeService;
import org.defendev.easygo.domain.useridentity.api.OwnershipUnitIdsWithPrivilegeQuery;
import org.defendev.easygo.domain.useridentity.api.Privilege;
import org.defendev.easygo.domain.useridentity.service.dto.IEasygoUserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.nonNull;



@Service
public class QueryOwnershipUnitIdsWithPrivilegeService implements IQueryOwnershipUnitIdsWithPrivilegeService {

    private static final Logger log = LogManager.getLogger();

    private final QueryAnonymousPrivilegeService queryAnonymousPrivilegeService;

    private final ExtractLoggedInUserService extractLoggedInUserService;

    public QueryOwnershipUnitIdsWithPrivilegeService(QueryAnonymousPrivilegeService queryAnonymousPrivilegeService,
                                                     ExtractLoggedInUserService extractLoggedInUserService) {
        this.queryAnonymousPrivilegeService = queryAnonymousPrivilegeService;
        this.extractLoggedInUserService = extractLoggedInUserService;
    }

    @Override
    public Set<Long> query(OwnershipUnitIdsWithPrivilegeQuery query) {
        final Map<Privilege, Set<Long>> anonymousPrivilegeToOwnershipUnit = queryAnonymousPrivilegeService
            .queryPrivilegeToOwnershipUnit();
        final IEasygoUserDetails userDetails = extractLoggedInUserService.extract();
        final Map<Privilege, Set<Long>> privilegeToOwnershipUnit = nonNull(userDetails) ?
            userDetails.getPrivilegeToOwnershipUnit()
            : Map.of();

        final Privilege privilege = query.getPrivilege();

        final Set<Long> ownershipUnitIds = new HashSet<>();
        for (Privilege anyPrivilege : privilege.getContainedIn()) {
            ownershipUnitIds.addAll(anonymousPrivilegeToOwnershipUnit.getOrDefault(anyPrivilege, Set.of()));
            ownershipUnitIds.addAll(privilegeToOwnershipUnit.getOrDefault(anyPrivilege, Set.of()));
        }

        return ownershipUnitIds;
    }

}
