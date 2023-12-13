package org.defendev.easygo.domain.iam.service.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


public class EasygoRoles {

    public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String ROLE_FULLY_AUTHENTICATED = "ROLE_FULLY_AUTHENTICATED";

    private EasygoRoles() { }

    public static GrantedAuthority roleAnonymous() {
        return new SimpleGrantedAuthority(ROLE_ANONYMOUS);
    }

    public static GrantedAuthority fullyAuthenticated() {
        return new SimpleGrantedAuthority(ROLE_FULLY_AUTHENTICATED);
    }

}
