package org.defendev.easygo.domain.fa.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.defendev.common.domain.query.Query;
import org.defendev.common.domain.query.QueryExternalId;



public class SourceDocumentFindQuery extends Query implements QueryExternalId {

    private final String externalId;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public SourceDocumentFindQuery(@JsonProperty("externalId") String externalId) {
        this.externalId = externalId;
    }

    @Override
    public String getExternalId() {
        return externalId;
    }
}
