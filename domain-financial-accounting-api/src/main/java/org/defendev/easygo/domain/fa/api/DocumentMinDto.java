package org.defendev.easygo.domain.fa.api;

import java.time.ZonedDateTime;


public class DocumentMinDto implements IDocumentMinDto {

    private final String externalId;

    private final String controlNumber;

    private final ZonedDateTime documentIssueDateTime;

    public DocumentMinDto(String externalId, String controlNumber, ZonedDateTime documentIssueDateTime) {
        this.externalId = externalId;
        this.controlNumber = controlNumber;
        this.documentIssueDateTime = documentIssueDateTime;
    }

    @Override
    public String getExternalId() {
        return externalId;
    }

    @Override
    public String getControlNumber() {
        return controlNumber;
    }

    @Override
    public ZonedDateTime getDocumentIssueDateTime() {
        return documentIssueDateTime;
    }
}
