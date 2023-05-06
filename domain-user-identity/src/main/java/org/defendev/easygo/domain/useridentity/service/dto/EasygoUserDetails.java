package org.defendev.easygo.domain.useridentity.service.dto;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;



public class EasygoUserDetails extends User {

    private long id;

    public EasygoUserDetails(long id, String username, String password) {
        super(username, password, true, true, true, true, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
