package org.defendev.easygo.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.domain.useridentity.service.dto.AuthenticationDto;
import org.defendev.common.domain.useridentity.service.dto.IAuthenticationDto;
import org.defendev.common.domain.useridentity.service.dto.ISecurityContextDto;
import org.defendev.common.domain.useridentity.service.dto.IUserDetailsDto;
import org.defendev.common.domain.useridentity.service.dto.SecurityContextDto;
import org.defendev.common.domain.useridentity.service.dto.UserDetailsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.Set;


@Controller
public class SecurityContextController extends ApiBaseController {

    private static final Logger log = LogManager.getLogger();

    @RequestMapping(path = {"security-context"}, method = RequestMethod.GET)
    public ResponseEntity<ISecurityContextDto> getSecurityContext() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final Authentication authentication = securityContext.getAuthentication();
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        final Object principal = authentication.getPrincipal();

        final IUserDetailsDto userDetailsDto = new UserDetailsDto(principal.toString());
        final IAuthenticationDto authenticationDto = new AuthenticationDto(Set.of(), userDetailsDto,
            !(authentication instanceof AnonymousAuthenticationToken));
        final ISecurityContextDto securityContextDto = new SecurityContextDto(authenticationDto);

        return ResponseEntity.ok(securityContextDto);
    }

}