package org.defendev.easygo.domain.iam.api;

import java.util.Set;



public interface IQueryOwnershipUnitIdsWithPrivilegeService {

    Set<Long> query(OwnershipUnitIdsWithPrivilegeQuery query);

}
