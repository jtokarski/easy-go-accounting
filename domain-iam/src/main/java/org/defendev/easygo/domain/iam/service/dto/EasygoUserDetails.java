package org.defendev.easygo.domain.iam.service.dto;

import org.defendev.common.domain.iam.IDefendevUserDetails;
import org.defendev.common.domain.iam.Privilege;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.defendev.common.stream.Streams.stream;



public class EasygoUserDetails extends User implements IDefendevUserDetails {

    private long id;

    private Map<Privilege, Set<Long>> privilegeToOwnershipUnit;

    public EasygoUserDetails(String username, String password, Set<? extends GrantedAuthority> grantedAuthorities,
                             long id, Map<Privilege, Set<Long>> privilegeToOwnershipUnit) {
        super(username, password, true, true, true, true, grantedAuthorities);
        this.id = id;
        this.privilegeToOwnershipUnit = privilegeToOwnershipUnit;
    }

    public long getId() {
        return id;
    }

    @Override
    public Set<String> getRoles() {
        return stream(getAuthorities()).map(GrantedAuthority::toString).collect(Collectors.toSet());
    }

    @Override
    public Map<Privilege, Set<Long>> getPrivilegeToOwnershipUnit() {
        return privilegeToOwnershipUnit;
    }
}
