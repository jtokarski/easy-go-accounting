package org.defendev.easygo.domain.fa.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.easygo.domain.fa.model.SourceDocument;
import org.defendev.easygo.domain.fa.repository.SourceDocumentRepo;
import org.defendev.easygo.domain.fa.service.dto.SourceDocumentFullDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.defendev.common.time.TimeUtil.ZULU_ZONE_ID;




@Service
public class FindSourceDocumentService {

    private static final Logger log = LogManager.getLogger();

    private final SourceDocumentRepo sourceDocumentRepo;

    @Autowired
    public FindSourceDocumentService(SourceDocumentRepo sourceDocumentRepo) {
        this.sourceDocumentRepo = sourceDocumentRepo;
    }

    @Transactional(transactionManager = "financialAccountingJpaTransactionManager", readOnly = true)
    public Optional<SourceDocumentFullDto> findSourceDocument(String externalId) {
        final long id;

        try {
            id = Long.parseLong(externalId, 10);
        } catch (NumberFormatException nfe) {
            return Optional.empty();
        }
        final Optional<SourceDocument> sourceDocumentOptional = sourceDocumentRepo.findById(id);

        return sourceDocumentOptional.map(this::mapToFullDto);
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
