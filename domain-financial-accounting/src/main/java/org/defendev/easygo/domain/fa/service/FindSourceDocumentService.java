package org.defendev.easygo.domain.fa.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.domain.query.result.QueryResult;
import org.defendev.easygo.domain.fa.api.IFindSourceDocumentService;
import org.defendev.easygo.domain.fa.api.SourceDocumentFindQuery;
import org.defendev.easygo.domain.fa.api.SourceDocumentFullDto;
import org.defendev.easygo.domain.fa.model.SourceDocument;
import org.defendev.easygo.domain.fa.repository.SourceDocumentRepo;
import org.defendev.easygo.domain.iam.api.CheckObjectPrivilegeQuery;
import org.defendev.easygo.domain.iam.api.ICheckObjectPrivilegeService;
import org.defendev.easygo.domain.iam.api.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.defendev.common.time.TimeUtil.ZULU_ZONE_ID;



@Service
public class FindSourceDocumentService implements IFindSourceDocumentService {

    private static final Logger log = LogManager.getLogger();

    private final SourceDocumentRepo sourceDocumentRepo;

    private final ICheckObjectPrivilegeService checkObjectReadPrivilegeService;

    @Autowired
    public FindSourceDocumentService(SourceDocumentRepo sourceDocumentRepo,
                                     ICheckObjectPrivilegeService checkObjectReadPrivilegeService) {
        this.sourceDocumentRepo = sourceDocumentRepo;
        this.checkObjectReadPrivilegeService = checkObjectReadPrivilegeService;
    }

    @Transactional(transactionManager = "financialAccountingJpaTransactionManager", readOnly = true)
    public QueryResult<SourceDocumentFullDto> execute(SourceDocumentFindQuery query) {
        final long id;

        try {
            id = Long.parseLong(query.getExternalId(), 10);
        } catch (NumberFormatException nfe) {
            return QueryResult.notFound();
        }
        final Optional<SourceDocument> sourceDocumentOptional = sourceDocumentRepo.findById(id);

        sourceDocumentOptional.ifPresent(sourceDocument -> {
            checkObjectReadPrivilegeService.check(new CheckObjectPrivilegeQuery(Privilege.read, sourceDocument));
        });

        return sourceDocumentOptional.map(this::mapToFullDto)
            .map(dto -> QueryResult.success(dto))
            .orElseGet(() -> QueryResult.notFound());
    }

    private SourceDocumentFullDto mapToFullDto(SourceDocument entity) {
        return new SourceDocumentFullDto(
            String.valueOf(entity.getId()),
            entity.getControlNumber(),
            entity.getDocumentIssueDateTimeZulu().atZone(ZULU_ZONE_ID),
            entity.getDescription()
        );
    };

}
