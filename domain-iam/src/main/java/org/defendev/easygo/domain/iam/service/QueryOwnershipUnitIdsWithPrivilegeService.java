package org.defendev.easygo.domain.iam.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.domain.iam.IDefendevUserDetails;
import org.defendev.common.domain.iam.Privilege;
import org.defendev.easygo.domain.iam.api.IQueryOwnershipUnitIdsWithPrivilegeService;
import org.defendev.easygo.domain.iam.api.OwnershipUnitIdsWithPrivilegeQuery;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.nonNull;



@Service
public class QueryOwnershipUnitIdsWithPrivilegeService implements IQueryOwnershipUnitIdsWithPrivilegeService {

    private static final Logger log = LogManager.getLogger();

    private final QueryAnonymousPrivilegeService queryAnonymousPrivilegeService;

    public QueryOwnershipUnitIdsWithPrivilegeService(QueryAnonymousPrivilegeService queryAnonymousPrivilegeService) {
        this.queryAnonymousPrivilegeService = queryAnonymousPrivilegeService;
    }

    @Override
    public Set<Long> query(OwnershipUnitIdsWithPrivilegeQuery query) {
        final Map<Privilege, Set<Long>> anonymousPrivilegeToOwnershipUnit = queryAnonymousPrivilegeService
            .queryPrivilegeToOwnershipUnit();
        final IDefendevUserDetails requestedBy = query.getRequestedBy();
        final Map<Privilege, Set<Long>> privilegeToOwnershipUnit = nonNull(requestedBy) ?
            requestedBy.getPrivilegeToOwnershipUnit()
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
