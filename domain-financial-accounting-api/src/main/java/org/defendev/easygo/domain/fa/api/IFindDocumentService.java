package org.defendev.easygo.domain.fa.api;

import org.defendev.common.domain.query.result.QueryResult;



public interface IFindDocumentService {

    QueryResult<DocumentFullDto> execute(DocumentFindQuery query);

}
