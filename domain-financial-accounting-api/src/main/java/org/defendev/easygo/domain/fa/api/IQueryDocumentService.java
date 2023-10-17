package org.defendev.easygo.domain.fa.api;

import org.defendev.common.domain.query.result.QueryResult;



public interface IQueryDocumentService {

    QueryResult<DocumentCollectionResRep> execute(DocumentQuery query);

}
