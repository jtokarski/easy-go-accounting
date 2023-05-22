package org.defendev.easygo.domain.useridentity.api;

import org.defendev.common.domain.query.Query;



public class CheckObjectPrivilegeQuery extends Query {

    private final Privilege privilege;

    private final Long ownershipUnitId;

    public CheckObjectPrivilegeQuery(Privilege privilege, IOwnedBy object) {
        this.privilege = privilege;
        this.ownershipUnitId = object.getOwnershipUnitId();
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public Long getOwnershipUnitId() {
        return ownershipUnitId;
    }
}
