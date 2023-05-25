package org.defendev.easygo.domain.fa.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.domain.resrep.CollectionMeta;
import org.defendev.common.domain.resrep.ICollectionMeta;
import org.defendev.easygo.domain.fa.model.SourceDocument;
import org.defendev.easygo.domain.fa.repository.SourceDocumentPageableSpec;
import org.defendev.easygo.domain.fa.repository.SourceDocumentPredicateSpec;
import org.defendev.easygo.domain.fa.repository.SourceDocumentRepo;
import org.defendev.easygo.domain.fa.service.dto.SourceDocumentCollectionResRep;
import org.defendev.easygo.domain.fa.service.dto.SourceDocumentMinDto;
import org.defendev.easygo.domain.fa.service.query.SourceDocumentQuery;
import org.defendev.easygo.domain.useridentity.api.IQueryOwnershipUnitIdsWithPrivilegeService;
import org.defendev.easygo.domain.useridentity.api.OwnershipUnitIdsWithPrivilegeQuery;
import org.defendev.easygo.domain.useridentity.api.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.defendev.common.stream.Streams.stream;
import static org.defendev.common.time.TimeUtil.ZULU_ZONE_ID;



@Service
public class QuerySourceDocumentService {

    private static final Logger log = LogManager.getLogger();

    private final SourceDocumentRepo sourceDocumentRepo;

    private final IQueryOwnershipUnitIdsWithPrivilegeService privilegeService;

    @Autowired
    public QuerySourceDocumentService(SourceDocumentRepo sourceDocumentRepo,
                                      IQueryOwnershipUnitIdsWithPrivilegeService privilegeService) {
        this.sourceDocumentRepo = sourceDocumentRepo;
        this.privilegeService = privilegeService;
    }

    @Transactional(transactionManager = "financialAccountingJpaTransactionManager", readOnly = true)
    public SourceDocumentCollectionResRep querySourceDocuments(SourceDocumentQuery query) {
        if (!query.getResolveOwnershipUnitsForRequestingUser()) {
            throw new UnsupportedOperationException("Not implemented");
        }

        final SourceDocumentQuery queryWithOwnership = query.withOwnershipUnitIds(
                privilegeService.query(new OwnershipUnitIdsWithPrivilegeQuery(Privilege.preview)));

        final Pageable pageable = (new SourceDocumentPageableSpec(queryWithOwnership)).toPageable();
        final SourceDocumentPredicateSpec predicateSpec = new SourceDocumentPredicateSpec(queryWithOwnership);
        final Page<SourceDocument> sourceDocumentsPage = sourceDocumentRepo.findAll(predicateSpec, pageable);
        return mapToCollectionResRep(pageable, sourceDocumentsPage);
    }

    private SourceDocumentCollectionResRep mapToCollectionResRep(Pageable pageable,
                                                                 Page<SourceDocument> sourceDocumentsPage) {

        final List<SourceDocument> sourceDocumentsList = sourceDocumentsPage.getContent();

        final List<SourceDocumentMinDto> dtos = stream(sourceDocumentsList).map(
            (SourceDocument sourceDocument) -> new SourceDocumentMinDto(
                String.valueOf(sourceDocument.getId()),
                sourceDocument.getControlNumber(),
                sourceDocument.getDocumentIssueDateTimeZulu().atZone(ZULU_ZONE_ID)
            )
        ).collect(Collectors.toList());

        final CollectionMeta collectionMeta;
        if (0 < sourceDocumentsList.size()) {
            collectionMeta = new CollectionMeta(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                (int) sourceDocumentsPage.getTotalElements()
            );
        } else {
            collectionMeta = new CollectionMeta(
                ICollectionMeta.NO_SUCH_PAGE,
                0,
                ICollectionMeta.TOTAL_ELEMENTS_UNKNOWN
            );
        }

        return new SourceDocumentCollectionResRep(collectionMeta, dtos);
    }

}
