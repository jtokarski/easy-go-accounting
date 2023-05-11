package org.defendev.easygo.domain.fa.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.easygo.domain.fa.model.SourceDocument;
import org.defendev.easygo.domain.fa.repository.SourceDocumentRepo;
import org.defendev.easygo.domain.fa.service.dto.SourceDocumentFullDto;
import org.defendev.easygo.domain.useridentity.service.ExtractLoggedInUserService;
import org.defendev.easygo.domain.useridentity.service.dto.EasygoUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.defendev.common.time.TimeUtil.ZULU_ZONE_ID;




@Service
public class FindSourceDocumentService {

    private static final Logger log = LogManager.getLogger();

    private final SourceDocumentRepo sourceDocumentRepo;

    private final ExtractLoggedInUserService extractLoggedInUserService;

    @Autowired
    public FindSourceDocumentService(SourceDocumentRepo sourceDocumentRepo,
                                     ExtractLoggedInUserService extractLoggedInUserService) {
        this.sourceDocumentRepo = sourceDocumentRepo;
        this.extractLoggedInUserService = extractLoggedInUserService;
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

        sourceDocumentOptional.ifPresent(sourceDocument -> {
            final Long ownershipUnitId = sourceDocument.getOwnershipUnitId();
            final EasygoUserDetails loggedInUser = extractLoggedInUserService.extract();
            if (!loggedInUser.getOwnershipUnitIds().contains(ownershipUnitId) &&
                !loggedInUser.getReadOnlyOwnershipUnitIds().contains(ownershipUnitId)) {
                throw new AccessDeniedException("Not authorized to view this document");
            }
        });

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
