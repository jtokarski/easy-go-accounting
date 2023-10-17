package org.defendev.easygo.domain.fa.api;

import java.time.ZonedDateTime;

public class DocumentFullDto implements IDocumentFullDto {

    private final String externalId;

    private final String controlNumber;

    private final ZonedDateTime documentIssueDateTime;

    private final String description;

    public DocumentFullDto(String externalId, String controlNumber, ZonedDateTime documentIssueDateTime,
                           String description) {
        this.externalId = externalId;
        this.controlNumber = controlNumber;
        this.documentIssueDateTime = documentIssueDateTime;
        this.description = description;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public ZonedDateTime getDocumentIssueDateTime() {
        return documentIssueDateTime;
    }

    public String getDescription() {
        return description;
    }
}
