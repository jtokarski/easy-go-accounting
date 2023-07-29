package org.defendev.easygo.domain.iam.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import org.defendev.easygo.domain.iam.api.Privilege;

import java.util.Objects;



@Table(name = "JoinUserIdentityOwnershipUnit")
@Entity
public class JoinUserIdentityOwnershipUnit {

    @EmbeddedId
    private JoinUserIdentityOwnershipUnitId id;

    @MapsId("userIdentityId")
    @JoinColumn(name = "userIdentityId")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserIdentity userIdentity;

    @MapsId("ownershipUnitId")
    @JoinColumn(name = "ownershipUnitId")
    @ManyToOne(fetch = FetchType.LAZY)
    private OwnershipUnit ownershipUnit;

    @Enumerated(EnumType.STRING)
    private Privilege privilege;

    public JoinUserIdentityOwnershipUnit() {
        this.id = new JoinUserIdentityOwnershipUnitId(null, null);
    }

    public JoinUserIdentityOwnershipUnit(UserIdentity userIdentity, OwnershipUnit ownershipUnit) {
        this.userIdentity = userIdentity;
        this.ownershipUnit = ownershipUnit;
        this.id = new JoinUserIdentityOwnershipUnitId(userIdentity.getId(), ownershipUnit.getId());
    }

    public JoinUserIdentityOwnershipUnitId getId() {
        return id;
    }

    public void setId(JoinUserIdentityOwnershipUnitId id) {
        this.id = id;
    }

    public UserIdentity getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(UserIdentity userIdentity) {
        this.userIdentity = userIdentity;
    }

    public OwnershipUnit getOwnershipUnit() {
        return ownershipUnit;
    }

    public void setOwnershipUnit(OwnershipUnit ownershipUnit) {
        this.ownershipUnit = ownershipUnit;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinUserIdentityOwnershipUnit that = (JoinUserIdentityOwnershipUnit) o;
        return Objects.equals(userIdentity, that.userIdentity) && Objects.equals(ownershipUnit, that.ownershipUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userIdentity, ownershipUnit);
    }
}
