package org.defendev.easygo.domain.iam.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.domain.iam.IDefendevUserDetails;
import org.defendev.common.domain.iam.Privilege;
import org.defendev.easygo.domain.iam.api.CommonPrivilegeQuery;
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

    private final QueryCommonPrivilegeService queryCommonPrivilegeService;

    public QueryOwnershipUnitIdsWithPrivilegeService(QueryCommonPrivilegeService queryCommonPrivilegeService) {
        this.queryCommonPrivilegeService = queryCommonPrivilegeService;
    }

    @Override
    public Set<Long> query(OwnershipUnitIdsWithPrivilegeQuery query) {
        final IDefendevUserDetails requestedBy = query.getRequestedBy();
        final Map<Privilege, Set<Long>> commonPrivilegeToOwnershipUnit = queryCommonPrivilegeService
            .execute(new CommonPrivilegeQuery(query.getRequestedBy()));
        final Map<Privilege, Set<Long>> privilegeToOwnershipUnit = nonNull(requestedBy) ?
            requestedBy.getPrivilegeToOwnershipUnit()
            : Map.of();

        final Privilege privilege = query.getPrivilege();

        final Set<Long> ownershipUnitIds = new HashSet<>();
        for (Privilege anyPrivilege : privilege.getContainedIn()) {
            ownershipUnitIds.addAll(commonPrivilegeToOwnershipUnit.getOrDefault(anyPrivilege, Set.of()));
            ownershipUnitIds.addAll(privilegeToOwnershipUnit.getOrDefault(anyPrivilege, Set.of()));
        }

        return ownershipUnitIds;
    }

}
