package org.defendev.easygo.domain.useridentity.api;

import org.defendev.common.domain.query.Query;



public class OwnershipUnitIdsWithPrivilegeQuery extends Query {

    private final Privilege privilege;

    public OwnershipUnitIdsWithPrivilegeQuery(Privilege privilege) {
        this.privilege = privilege;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

}
