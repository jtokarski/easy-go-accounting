package org.defendev.easygo.devops.dbfixture.wrapper;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.defendev.easygo.domain.fa.SourceDocument;

import java.util.List;



@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "sourceDocumentSet")
public class SourceDocumentSet {

    @XmlElement(name = "sourceDocument")
    private List<SourceDocument> sourceDocuments;

    public List<SourceDocument> getSourceDocuments() {
        return sourceDocuments;
    }

    public void setSourceDocuments(List<SourceDocument> sourceDocuments) {
        this.sourceDocuments = sourceDocuments;
    }

}

