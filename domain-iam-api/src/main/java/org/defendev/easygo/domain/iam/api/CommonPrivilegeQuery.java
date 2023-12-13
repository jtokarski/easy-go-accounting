package org.defendev.easygo.domain.iam.api;

import org.defendev.common.domain.iam.IDefendevUserDetails;
import org.defendev.common.domain.query.Query;
import org.defendev.common.domain.query.QueryRequestedBy;



public class CommonPrivilegeQuery extends Query implements QueryRequestedBy {

    private final IDefendevUserDetails requestedBy;

    public CommonPrivilegeQuery(IDefendevUserDetails requestedBy) {
        this.requestedBy = requestedBy;
    }

    @Override
    public IDefendevUserDetails getRequestedBy() {
        return requestedBy;
    }

}
