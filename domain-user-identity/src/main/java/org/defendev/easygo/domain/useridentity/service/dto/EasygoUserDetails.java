package org.defendev.easygo.domain.useridentity.service.dto;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Set;



public class EasygoUserDetails extends User {

    private long id;

    private Set<Long> ownershipUnitIds;

    private Set<Long> readOnlyOwnershipUnitIds;

    public EasygoUserDetails(long id, String username, String password, Set<Long> ownershipUnitIds,
                             Set<Long> readOnlyOwnershipUnitIds) {
        super(username, password, true, true, true, true, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.id = id;
        this.ownershipUnitIds = ownershipUnitIds;
        this.readOnlyOwnershipUnitIds = readOnlyOwnershipUnitIds;
    }

    public long getId() {
        return id;
    }

    public Set<Long> getOwnershipUnitIds() {
        return ownershipUnitIds;
    }

    public Set<Long> getReadOnlyOwnershipUnitIds() {
        return readOnlyOwnershipUnitIds;
    }
}
