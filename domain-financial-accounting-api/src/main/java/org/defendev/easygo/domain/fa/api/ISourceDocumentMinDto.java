package org.defendev.easygo.domain.fa.api;

import java.time.ZonedDateTime;

import org.defendev.common.domain.resrep.IBaseDto;



public interface ISourceDocumentMinDto extends IBaseDto {

    String getControlNumber();

    ZonedDateTime getDocumentIssueDateTime();

}
