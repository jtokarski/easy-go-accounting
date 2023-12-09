package org.defendev.easygo.domain.fa.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.defendev.common.domain.iam.IDefendevUserDetails;
import org.defendev.common.domain.query.Query;
import org.defendev.common.domain.query.QueryExternalId;
import org.defendev.common.domain.query.QueryRequestedBy;


public class DocumentFindQuery extends Query implements QueryExternalId, QueryRequestedBy {

    private final String externalId;

    private final IDefendevUserDetails requestedBy;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DocumentFindQuery(@JsonProperty("externalId") String externalId) {
        this.externalId = externalId;
        this.requestedBy = null;
    }

    public DocumentFindQuery(String externalId, IDefendevUserDetails requestedBy) {
        this.requestedBy = requestedBy;
        this.externalId = externalId;
    }

    @Override
    public String getExternalId() {
        return externalId;
    }

    @Override
    public IDefendevUserDetails getRequestedBy() {
        return requestedBy;
    }

    public DocumentFindQuery withRequestedBy(IDefendevUserDetails requestedBy) {
        return new DocumentFindQuery(externalId, requestedBy);
    }

}
