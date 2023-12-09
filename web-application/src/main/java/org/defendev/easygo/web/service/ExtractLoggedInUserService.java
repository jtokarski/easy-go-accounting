package org.defendev.easygo.web.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.domain.iam.DefendevUserDetailsDto;
import org.defendev.common.domain.iam.IDefendevUserDetails;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;


@Service
public class ExtractLoggedInUserService {

    private static final Logger log = LogManager.getLogger();

    private static String ANONYMOUS_USER = "anonymousUser";

    public IDefendevUserDetails extract() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final Authentication authentication = securityContext.getAuthentication();
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        final Object principal = authentication.getPrincipal();
        if (principal instanceof IDefendevUserDetails) {
            return (IDefendevUserDetails) principal;
        } else if (ANONYMOUS_USER.equals(principal)) {
            return new DefendevUserDetailsDto(ANONYMOUS_USER, Map.of());
        } else {
            throw new AccessDeniedException("Unrecognized authentication principal");
        }
    }

}
