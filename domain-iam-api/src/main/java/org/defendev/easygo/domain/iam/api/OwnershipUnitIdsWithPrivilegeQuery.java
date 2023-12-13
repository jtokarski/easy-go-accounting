package org.defendev.easygo.domain.iam.api;

import org.defendev.common.domain.iam.IDefendevUserDetails;
import org.defendev.common.domain.iam.Privilege;
import org.defendev.common.domain.query.Query;
import org.defendev.common.domain.query.QueryRequestedBy;



public class OwnershipUnitIdsWithPrivilegeQuery extends Query implements QueryRequestedBy {

    private final Privilege privilege;

    private final IDefendevUserDetails requestedBy;

    public OwnershipUnitIdsWithPrivilegeQuery(Privilege privilege, IDefendevUserDetails requestedBy) {
        this.privilege = privilege;
        this.requestedBy = requestedBy;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    @Override
    public IDefendevUserDetails getRequestedBy() {
        return requestedBy;
    }

}
