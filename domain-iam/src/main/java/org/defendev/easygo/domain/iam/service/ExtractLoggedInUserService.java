package org.defendev.easygo.domain.iam.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.easygo.domain.iam.service.dto.IEasygoUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public class ExtractLoggedInUserService {

    private static final Logger log = LogManager.getLogger();

    public IEasygoUserDetails extract() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final Authentication authentication = securityContext.getAuthentication();
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        final Object principal = authentication.getPrincipal();
        if (principal instanceof IEasygoUserDetails) {
            return (IEasygoUserDetails) principal;
        } else {
            return null;
        }
    }


}
