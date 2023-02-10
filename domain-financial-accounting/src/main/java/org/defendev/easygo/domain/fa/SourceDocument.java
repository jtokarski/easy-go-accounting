package org.defendev.easygo.domain.fa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.defendev.common.domain.HasId;
import org.defendev.common.xml.bind.LocalDateTimeXmlAdapter;

import java.time.LocalDateTime;



@XmlAccessorType(value = XmlAccessType.FIELD)
@Table(name = "SourceDocument")
@Entity
public class SourceDocument implements HasId<Long> {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "controlNumber")
    private String controlNumber;

    @XmlJavaTypeAdapter(LocalDateTimeXmlAdapter.class)
    @Column(name = "documentDateTimeZulu")
    private LocalDateTime documentDateTimeZulu;

    @Column(name = "description")
    private String description;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
    }

    public LocalDateTime getDocumentDateTimeZulu() {
        return documentDateTimeZulu;
    }

    public void setDocumentDateTimeZulu(LocalDateTime documentDateTimeZulu) {
        this.documentDateTimeZulu = documentDateTimeZulu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

