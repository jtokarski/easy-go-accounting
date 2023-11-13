package org.defendev.easygo.domain.fa.api;

import java.time.ZonedDateTime;


public class DocumentMinDto implements IDocumentMinDto {

    private final String externalId;

    private final String controlNumber;

    private final ZonedDateTime issueDateTime;

    public DocumentMinDto(String externalId, String controlNumber, ZonedDateTime issueDateTime) {
        this.externalId = externalId;
        this.controlNumber = controlNumber;
        this.issueDateTime = issueDateTime;
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
    public ZonedDateTime getIssueDateTime() {
        return issueDateTime;
    }
}
