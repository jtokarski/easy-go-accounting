package org.defendev.easygo.domain.fa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.defendev.common.domain.HasId;
import org.defendev.common.domain.iam.IOwnedBy;
import org.defendev.common.xml.bind.LocalDateTimeXmlAdapter;

import java.time.LocalDateTime;



@XmlAccessorType(value = XmlAccessType.FIELD)
@Table(name = "Document")
@Entity
public class Document implements HasId<Long>, IOwnedBy<Long> {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "ownershipUnitId", nullable = false)
    private Long ownershipUnitId;

    @Column(name = "controlNumber")
    private String controlNumber;

    @Column(name = "typeKey")
    @Enumerated(EnumType.STRING)
    private DocumentType type;

    @XmlJavaTypeAdapter(LocalDateTimeXmlAdapter.class)
    @Column(name = "issueDateTimeZulu")
    private LocalDateTime issueDateTimeZulu;

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

    @Override
    public Long getOwnershipUnitId() {
        return ownershipUnitId;
    }

    public void setOwnershipUnitId(Long ownershipUnitId) {
        this.ownershipUnitId = ownershipUnitId;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    public LocalDateTime getIssueDateTimeZulu() {
        return issueDateTimeZulu;
    }

    public void setIssueDateTimeZulu(LocalDateTime issueDateTimeZulu) {
        this.issueDateTimeZulu = issueDateTimeZulu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

