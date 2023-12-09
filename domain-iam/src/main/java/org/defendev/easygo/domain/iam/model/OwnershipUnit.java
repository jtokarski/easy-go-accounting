package org.defendev.easygo.domain.iam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import org.defendev.common.domain.HasId;
import org.defendev.common.domain.iam.Privilege;

import java.util.Set;


@XmlAccessorType(value = XmlAccessType.FIELD)
@Table(name = "OwnershipUnit")
@Entity
public class OwnershipUnit implements HasId<Long> {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @XmlElementWrapper(name = "anonymousPrivilege")
    @XmlElement(name = "privilege")
    @CollectionTable(
        name = "OwnershipUnitAnonymousPrivilege",
        joinColumns = { @JoinColumn(name = "ownershipUnitId", referencedColumnName = "id") })
    @Column(name = "privilegeKey", nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Privilege> anonymousPrivilege;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Privilege> getAnonymousPrivilege() {
        return anonymousPrivilege;
    }

    public void setAnonymousPrivilege(Set<Privilege> anonymousPrivilege) {
        this.anonymousPrivilege = anonymousPrivilege;
    }
}
