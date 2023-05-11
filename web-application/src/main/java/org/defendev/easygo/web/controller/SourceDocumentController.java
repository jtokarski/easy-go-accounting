package org.defendev.easygo.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.easygo.domain.fa.service.FindSourceDocumentService;
import org.defendev.easygo.domain.fa.service.QuerySourceDocumentService;
import org.defendev.easygo.domain.fa.service.dto.SourceDocumentCollectionResRep;
import org.defendev.easygo.domain.fa.service.dto.SourceDocumentFullDto;
import org.defendev.easygo.domain.fa.service.query.SourceDocumentQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;




@Controller
public class SourceDocumentController extends ApiBaseController {

    private static final Logger log = LogManager.getLogger();

    public static final String SOURCE_DOCUMENT_PATH_SEGMENT = "source-document";

    public static final String SOURCE_DOCUMENTS_BROWSE_PATH = SOURCE_DOCUMENT_PATH_SEGMENT + "/" +
        BROWSE_PATH_SEGMENT;

    public static final String SOURCE_DOCUMENT_PATH = SOURCE_DOCUMENT_PATH_SEGMENT + "/" + EXTERNAL_ID_PATH_SEGMENT;

    private final FindSourceDocumentService findSourceDocumentService;

    private final QuerySourceDocumentService querySourceDocumentService;

    public SourceDocumentController(FindSourceDocumentService findSourceDocumentService,
                                    QuerySourceDocumentService querySourceDocumentService) {
        this.findSourceDocumentService = findSourceDocumentService;
        this.querySourceDocumentService = querySourceDocumentService;
    }

    @PreAuthorize("isFullyAuthenticated()")
    @RequestMapping(path = SOURCE_DOCUMENT_PATH, method = RequestMethod.GET)
    public ResponseEntity<SourceDocumentFullDto> findSourceDocument(@PathVariable String externalId) {
        final Optional<SourceDocumentFullDto> sourceDocumentOptional = findSourceDocumentService
            .findSourceDocument(externalId);
        if (sourceDocumentOptional.isPresent()) {
            return ResponseEntity.ok(sourceDocumentOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(path = SOURCE_DOCUMENTS_BROWSE_PATH, method = RequestMethod.POST)
    public ResponseEntity<SourceDocumentCollectionResRep> browseSourceDocuments(@RequestBody SourceDocumentQuery query) {
        final SourceDocumentCollectionResRep collectionResRep = querySourceDocumentService.querySourceDocuments(query);
        return ResponseEntity.ok(collectionResRep);
    }

}
