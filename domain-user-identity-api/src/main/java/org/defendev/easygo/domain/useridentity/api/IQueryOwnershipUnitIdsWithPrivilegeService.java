package org.defendev.easygo.domain.useridentity.api;

import java.util.Set;



public interface IQueryOwnershipUnitIdsWithPrivilegeService {

    Set<Long> query(OwnershipUnitIdsWithPrivilegeQuery query);

}
