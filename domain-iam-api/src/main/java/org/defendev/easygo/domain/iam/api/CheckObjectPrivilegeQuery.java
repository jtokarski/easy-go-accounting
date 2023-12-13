package org.defendev.easygo.domain.iam.api;

import org.defendev.common.domain.iam.IDefendevUserDetails;
import org.defendev.common.domain.iam.IOwnedBy;
import org.defendev.common.domain.iam.Privilege;
import org.defendev.common.domain.query.Query;
import org.defendev.common.domain.query.QueryRequestedBy;



public class CheckObjectPrivilegeQuery extends Query implements QueryRequestedBy {

    private final Privilege privilege;

    private final Long ownershipUnitId;

    private final IDefendevUserDetails requestedBy;

    public CheckObjectPrivilegeQuery(Privilege privilege, IOwnedBy<Long> object, IDefendevUserDetails requestedBy) {
        this.privilege = privilege;
        this.ownershipUnitId = object.getOwnershipUnitId();
        this.requestedBy = requestedBy;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public Long getOwnershipUnitId() {
        return ownershipUnitId;
    }

    @Override
    public IDefendevUserDetails getRequestedBy() {
        return requestedBy;
    }

}
