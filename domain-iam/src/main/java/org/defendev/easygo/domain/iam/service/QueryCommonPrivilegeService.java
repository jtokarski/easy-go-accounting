package org.defendev.easygo.domain.iam.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.domain.iam.Privilege;
import org.defendev.easygo.domain.iam.api.CommonPrivilegeQuery;
import org.defendev.easygo.domain.iam.model.CommonPrivilege;
import org.defendev.easygo.domain.iam.model.CommonPrivilegeSubject;
import org.defendev.easygo.domain.iam.repository.OwnershipUnitRepo;
import org.defendev.easygo.domain.iam.service.dto.EasygoRoles;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.defendev.common.stream.Streams.stream;



@Service
public class QueryCommonPrivilegeService {

    private static final Logger log = LogManager.getLogger();

    private final OwnershipUnitRepo ownershipUnitRepo;

    public QueryCommonPrivilegeService(OwnershipUnitRepo ownershipUnitRepo) {
        this.ownershipUnitRepo = ownershipUnitRepo;
    }

    public Map<Privilege, Set<Long>> execute(CommonPrivilegeQuery query) {
        final Predicate<CommonPrivilege> commonPrivilegePredicate;
        final boolean requestedByFullyAuthenticated = query.getRequestedBy().getRoles()
            .contains(EasygoRoles.ROLE_FULLY_AUTHENTICATED);
        if (requestedByFullyAuthenticated) {
            commonPrivilegePredicate = commonPrivilege -> (
                CommonPrivilegeSubject.fully_authenticated_user == commonPrivilege.getSubject() ||
                CommonPrivilegeSubject.anonymous_user == commonPrivilege.getSubject()
            );
        } else {
            commonPrivilegePredicate = commonPrivilege -> (
                CommonPrivilegeSubject.anonymous_user == commonPrivilege.getSubject()
            );
        }

        return ownershipUnitRepo.findWithAnyCommonPrivilege().stream().flatMap(ownershipUnit ->
            stream(ownershipUnit.getCommonPrivileges())
            .filter(commonPrivilegePredicate)
            .map(commonPrivilege -> Map.entry(commonPrivilege.getPrivilege(), ownershipUnit.getId()))
        ).collect(Collectors.groupingBy(
            entry -> entry.getKey(),
            Collectors.mapping(entry -> entry.getValue(), Collectors.toSet())
        ));
    }

}
