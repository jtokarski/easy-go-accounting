package org.defendev.easygo.domain.useridentity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlElement;
import org.defendev.common.domain.HasId;
import java.util.Set;


@XmlAccessorType(value = XmlAccessType.FIELD)
@Table(name = "UserIdentity")
@Entity
public class UserIdentity implements HasId<Long> {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @XmlElementWrapper(name = "ownershipUnits")
    @XmlElement(name = "ownershipUnit")
    @ManyToMany
    @JoinTable(
        name = "JoinUserIdentityOwnershipUnit",
        joinColumns = @JoinColumn(name = "userIdentityId", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "ownershipUnitId", referencedColumnName = "id")
    )
    private Set<OwnershipUnit> ownershipUnits;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<OwnershipUnit> getOwnershipUnits() {
        return ownershipUnits;
    }

    public void setOwnershipUnits(Set<OwnershipUnit> ownershipUnits) {
        this.ownershipUnits = ownershipUnits;
    }
}
