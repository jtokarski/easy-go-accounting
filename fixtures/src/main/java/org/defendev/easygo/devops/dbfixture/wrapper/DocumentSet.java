package org.defendev.easygo.devops.dbfixture.wrapper;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.defendev.easygo.domain.fa.model.Document;

import java.util.List;



@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "documentSet")
public class DocumentSet {

    @XmlElement(name = "document")
    private List<Document> documents;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}

