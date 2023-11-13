package org.defendev.easygo.domain.fa.api;

import java.time.ZonedDateTime;

public class DocumentFullDto implements IDocumentFullDto {

    private final String externalId;

    private final String controlNumber;

    private final ZonedDateTime issueDateTime;

    private final String description;

    public DocumentFullDto(String externalId, String controlNumber, ZonedDateTime issueDateTime,
                           String description) {
        this.externalId = externalId;
        this.controlNumber = controlNumber;
        this.issueDateTime = issueDateTime;
        this.description = description;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public ZonedDateTime getIssueDateTime() {
        return issueDateTime;
    }

    public String getDescription() {
        return description;
    }
}
