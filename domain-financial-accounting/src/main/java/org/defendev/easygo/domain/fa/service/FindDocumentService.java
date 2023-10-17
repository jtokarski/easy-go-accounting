package org.defendev.easygo.domain.fa.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.domain.query.result.QueryResult;
import org.defendev.easygo.domain.fa.api.IFindDocumentService;
import org.defendev.easygo.domain.fa.api.DocumentFindQuery;
import org.defendev.easygo.domain.fa.api.DocumentFullDto;
import org.defendev.easygo.domain.fa.model.Document;
import org.defendev.easygo.domain.fa.repository.DocumentRepo;
import org.defendev.easygo.domain.iam.api.CheckObjectPrivilegeQuery;
import org.defendev.easygo.domain.iam.api.ICheckObjectPrivilegeService;
import org.defendev.easygo.domain.iam.api.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.defendev.common.time.TimeUtil.ZULU_ZONE_ID;



@Service
public class FindDocumentService implements IFindDocumentService {

    private static final Logger log = LogManager.getLogger();

    private final DocumentRepo documentRepo;

    private final ICheckObjectPrivilegeService checkObjectReadPrivilegeService;

    @Autowired
    public FindDocumentService(DocumentRepo documentRepo,
                               ICheckObjectPrivilegeService checkObjectReadPrivilegeService) {
        this.documentRepo = documentRepo;
        this.checkObjectReadPrivilegeService = checkObjectReadPrivilegeService;
    }

    @Transactional(transactionManager = "financialAccountingJpaTransactionManager", readOnly = true)
    public QueryResult<DocumentFullDto> execute(DocumentFindQuery query) {
        final long id;

        try {
            id = Long.parseLong(query.getExternalId(), 10);
        } catch (NumberFormatException nfe) {
            return QueryResult.notFound();
        }
        final Optional<Document> documentOptional = documentRepo.findById(id);

        documentOptional.ifPresent(document -> {
            checkObjectReadPrivilegeService.check(new CheckObjectPrivilegeQuery(Privilege.read, document));
        });

        return documentOptional.map(this::mapToFullDto)
            .map(dto -> QueryResult.success(dto))
            .orElseGet(() -> QueryResult.notFound());
    }

    private DocumentFullDto mapToFullDto(Document entity) {
        return new DocumentFullDto(
            String.valueOf(entity.getId()),
            entity.getControlNumber(),
            entity.getDocumentIssueDateTimeZulu().atZone(ZULU_ZONE_ID),
            entity.getDescription()
        );
    };

}
