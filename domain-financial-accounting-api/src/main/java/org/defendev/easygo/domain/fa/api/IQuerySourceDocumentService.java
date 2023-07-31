package org.defendev.easygo.domain.fa.api;

import org.defendev.common.domain.query.result.QueryResult;



public interface IQuerySourceDocumentService {

    QueryResult<SourceDocumentCollectionResRep> execute(SourceDocumentQuery query);

}
