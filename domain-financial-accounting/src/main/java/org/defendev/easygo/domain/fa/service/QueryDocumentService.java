package org.defendev.easygo.domain.fa.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.domain.query.result.QueryResult;
import org.defendev.common.domain.resrep.CollectionMeta;
import org.defendev.common.domain.resrep.ICollectionMeta;
import org.defendev.easygo.domain.fa.api.IQueryDocumentService;
import org.defendev.easygo.domain.fa.api.DocumentCollectionResRep;
import org.defendev.easygo.domain.fa.api.DocumentMinDto;
import org.defendev.easygo.domain.fa.api.DocumentQuery;
import org.defendev.easygo.domain.fa.model.Document;
import org.defendev.easygo.domain.fa.repository.DocumentPageableSpec;
import org.defendev.easygo.domain.fa.repository.DocumentPredicateSpec;
import org.defendev.easygo.domain.fa.repository.DocumentRepo;
import org.defendev.easygo.domain.iam.api.IQueryOwnershipUnitIdsWithPrivilegeService;
import org.defendev.easygo.domain.iam.api.OwnershipUnitIdsWithPrivilegeQuery;
import org.defendev.easygo.domain.iam.api.Privilege;
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
public class QueryDocumentService implements IQueryDocumentService {

    private static final Logger log = LogManager.getLogger();

    private final DocumentRepo documentRepo;

    private final IQueryOwnershipUnitIdsWithPrivilegeService privilegeService;

    @Autowired
    public QueryDocumentService(DocumentRepo documentRepo,
                                IQueryOwnershipUnitIdsWithPrivilegeService privilegeService) {
        this.documentRepo = documentRepo;
        this.privilegeService = privilegeService;
    }

    @Transactional(transactionManager = "financialAccountingJpaTransactionManager", readOnly = true)
    public QueryResult<DocumentCollectionResRep> execute(DocumentQuery query) {
        if (!query.getResolveOwnershipUnitsForRequestingUser()) {
            throw new UnsupportedOperationException("Not implemented");
        }

        final DocumentQuery queryWithOwnership = query.withOwnershipUnitIds(
                privilegeService.query(new OwnershipUnitIdsWithPrivilegeQuery(Privilege.preview)));

        final Pageable pageable = (new DocumentPageableSpec(queryWithOwnership)).toPageable();
        final DocumentPredicateSpec predicateSpec = new DocumentPredicateSpec(queryWithOwnership);
        final Page<Document> documentsPage = documentRepo.findAll(predicateSpec, pageable);
        final DocumentCollectionResRep resRep = mapToCollectionResRep(pageable, documentsPage);
        return QueryResult.success(resRep);
    }

    private DocumentCollectionResRep mapToCollectionResRep(Pageable pageable,
                                                           Page<Document> documentsPage) {

        final List<Document> documentsList = documentsPage.getContent();

        final List<DocumentMinDto> dtos = stream(documentsList).map(
            (Document document) -> new DocumentMinDto(
                String.valueOf(document.getId()),
                document.getControlNumber(),
                document.getDocumentIssueDateTimeZulu().atZone(ZULU_ZONE_ID)
            )
        ).collect(Collectors.toList());

        final CollectionMeta collectionMeta;
        if (0 < documentsList.size()) {
            collectionMeta = new CollectionMeta(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                (int) documentsPage.getTotalElements()
            );
        } else {
            collectionMeta = new CollectionMeta(
                ICollectionMeta.NO_SUCH_PAGE,
                0,
                ICollectionMeta.TOTAL_ELEMENTS_UNKNOWN
            );
        }

        return new DocumentCollectionResRep(collectionMeta, dtos);
    }

}
