package org.defendev.easygo.domain.useridentity.service.dto;

import org.defendev.easygo.domain.useridentity.api.Privilege;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Map;
import java.util.Set;



public class EasygoUserDetails extends User implements IEasygoUserDetails {

    private long id;

    private Map<Privilege, Set<Long>> privilegeToOwnershipUnit;

    public EasygoUserDetails(long id, String username, String password,
                             Map<Privilege, Set<Long>> privilegeToOwnershipUnit) {
        super(username, password, true, true, true, true, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.id = id;
        this.privilegeToOwnershipUnit = privilegeToOwnershipUnit;
    }

    public long getId() {
        return id;
    }

    @Override
    public Map<Privilege, Set<Long>> getPrivilegeToOwnershipUnit() {
        return privilegeToOwnershipUnit;
    }
}
