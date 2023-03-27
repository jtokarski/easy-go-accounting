package org.defendev.easygo.domain.fa.service;

import org.defendev.easygo.domain.fa.model.SourceDocument;
import org.defendev.easygo.domain.fa.repository.SourceDocumentRepo;
import org.defendev.easygo.domain.fa.service.dto.SourceDocumentFullDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.defendev.common.time.TimeUtil.ZULU_ZONE_ID;




@Service
public class FindSourceDocumentService {

    private final SourceDocumentRepo sourceDocumentRepo;

    @Autowired
    public FindSourceDocumentService(SourceDocumentRepo sourceDocumentRepo) {
        this.sourceDocumentRepo = sourceDocumentRepo;
    }

    public Optional<SourceDocumentFullDto> findSourceDocument(String externalId) {
        final long id;
        try {
            id = Long.parseLong(externalId, 10);
        } catch (NumberFormatException nfe) {
            return Optional.empty();
        }
        return sourceDocumentRepo.findById(id).map(this::mapToFullDto);
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
