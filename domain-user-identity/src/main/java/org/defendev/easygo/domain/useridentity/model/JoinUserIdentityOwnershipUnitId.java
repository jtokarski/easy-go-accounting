package org.defendev.easygo.domain.useridentity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;



@Embeddable
public class JoinUserIdentityOwnershipUnitId implements Serializable {

    @Column(name = "userIdentityId")
    private Long userIdentityId;

    @Column(name = "ownershipUnitId")
    private Long ownershipUnitId;

    public JoinUserIdentityOwnershipUnitId() { }

    public JoinUserIdentityOwnershipUnitId(Long userIdentityId, Long ownershipUnitId) {
        this.userIdentityId = userIdentityId;
        this.ownershipUnitId = ownershipUnitId;
    }

    public Long getUserIdentityId() {
        return userIdentityId;
    }

    public void setUserIdentityId(Long userIdentityId) {
        this.userIdentityId = userIdentityId;
    }

    public Long getOwnershipUnitId() {
        return ownershipUnitId;
    }

    public void setOwnershipUnitId(Long ownershipUnitId) {
        this.ownershipUnitId = ownershipUnitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinUserIdentityOwnershipUnitId that = (JoinUserIdentityOwnershipUnitId) o;
        return Objects.equals(userIdentityId, that.userIdentityId) && Objects.equals(ownershipUnitId, that.ownershipUnitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userIdentityId, ownershipUnitId);
    }
}
