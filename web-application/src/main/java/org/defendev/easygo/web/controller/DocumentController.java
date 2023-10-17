package org.defendev.easygo.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.domain.query.result.QueryResult;
import org.defendev.easygo.domain.fa.api.IFindDocumentService;
import org.defendev.easygo.domain.fa.api.IQueryDocumentService;
import org.defendev.easygo.domain.fa.api.DocumentCollectionResRep;
import org.defendev.easygo.domain.fa.api.DocumentFindQuery;
import org.defendev.easygo.domain.fa.api.DocumentFullDto;
import org.defendev.easygo.domain.fa.api.DocumentQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class DocumentController extends ApiBaseController {

    private static final Logger log = LogManager.getLogger();

    public static final String DOCUMENT_PATH_SEGMENT = "document";

    public static final String DOCUMENTS_BROWSE_PATH = DOCUMENT_PATH_SEGMENT + "/" +
        BROWSE_PATH_SEGMENT;

    public static final String DOCUMENT_PATH = DOCUMENT_PATH_SEGMENT + "/" + EXTERNAL_ID_PATH_SEGMENT;

    private final IFindDocumentService findDocumentService;

    private final IQueryDocumentService queryDocumentService;

    public DocumentController(IFindDocumentService findDocumentService,
                              IQueryDocumentService queryDocumentService) {
        this.findDocumentService = findDocumentService;
        this.queryDocumentService = queryDocumentService;
    }

    @PreAuthorize("isFullyAuthenticated()")
    @RequestMapping(path = DOCUMENT_PATH, method = RequestMethod.GET)
    public ResponseEntity<DocumentFullDto> findDocument(@PathVariable String externalId) {
        final QueryResult<DocumentFullDto> result = findDocumentService
            .execute(new DocumentFindQuery(externalId));
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.getData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(path = DOCUMENTS_BROWSE_PATH, method = RequestMethod.POST)
    public ResponseEntity<DocumentCollectionResRep> browseDocuments(@RequestBody DocumentQuery query) {
        final DocumentCollectionResRep collectionResRep = queryDocumentService.execute(query).getData();
        return ResponseEntity.ok(collectionResRep);
    }


}
