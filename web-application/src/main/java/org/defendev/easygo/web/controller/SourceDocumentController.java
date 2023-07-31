package org.defendev.easygo.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.domain.query.result.QueryResult;
import org.defendev.easygo.domain.fa.api.IFindSourceDocumentService;
import org.defendev.easygo.domain.fa.api.IQuerySourceDocumentService;
import org.defendev.easygo.domain.fa.api.SourceDocumentCollectionResRep;
import org.defendev.easygo.domain.fa.api.SourceDocumentFindQuery;
import org.defendev.easygo.domain.fa.api.SourceDocumentFullDto;
import org.defendev.easygo.domain.fa.api.SourceDocumentQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class SourceDocumentController extends ApiBaseController {

    private static final Logger log = LogManager.getLogger();

    public static final String SOURCE_DOCUMENT_PATH_SEGMENT = "source-document";

    public static final String SOURCE_DOCUMENTS_BROWSE_PATH = SOURCE_DOCUMENT_PATH_SEGMENT + "/" +
        BROWSE_PATH_SEGMENT;

    public static final String SOURCE_DOCUMENT_PATH = SOURCE_DOCUMENT_PATH_SEGMENT + "/" + EXTERNAL_ID_PATH_SEGMENT;

    private final IFindSourceDocumentService findSourceDocumentService;

    private final IQuerySourceDocumentService querySourceDocumentService;

    public SourceDocumentController(IFindSourceDocumentService findSourceDocumentService,
                                    IQuerySourceDocumentService querySourceDocumentService) {
        this.findSourceDocumentService = findSourceDocumentService;
        this.querySourceDocumentService = querySourceDocumentService;
    }

    @PreAuthorize("isFullyAuthenticated()")
    @RequestMapping(path = SOURCE_DOCUMENT_PATH, method = RequestMethod.GET)
    public ResponseEntity<SourceDocumentFullDto> findSourceDocument(@PathVariable String externalId) {
        final QueryResult<SourceDocumentFullDto> result = findSourceDocumentService
            .execute(new SourceDocumentFindQuery(externalId));
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.getData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(path = SOURCE_DOCUMENTS_BROWSE_PATH, method = RequestMethod.POST)
    public ResponseEntity<SourceDocumentCollectionResRep> browseSourceDocuments(@RequestBody SourceDocumentQuery query) {
        final SourceDocumentCollectionResRep collectionResRep = querySourceDocumentService.execute(query).getData();
        return ResponseEntity.ok(collectionResRep);
    }

}
