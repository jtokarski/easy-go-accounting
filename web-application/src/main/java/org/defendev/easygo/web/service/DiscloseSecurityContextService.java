package org.defendev.easygo.web.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.domain.iam.IDefendevUserDetails;
import org.defendev.common.domain.iam.service.api.IDiscloseSecurityContextService;
import org.defendev.common.domain.iam.service.dto.AuthenticationDto;
import org.defendev.common.domain.iam.service.dto.GrantedAuthorityDto;
import org.defendev.common.domain.iam.service.dto.IAuthenticationDto;
import org.defendev.common.domain.iam.service.dto.IGrantedAuthorityDto;
import org.defendev.common.domain.iam.service.dto.ISecurityContextDto;
import org.defendev.common.domain.iam.service.dto.IUserDetailsDto;
import org.defendev.common.domain.iam.service.dto.SecurityContextDto;
import org.defendev.common.domain.iam.service.dto.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.defendev.common.stream.Streams.stream;



@Service
public class DiscloseSecurityContextService implements IDiscloseSecurityContextService {

    private static final Logger log = LogManager.getLogger();

    private final AuthenticationTrustResolver authenticationTrustResolver;

    @Autowired
    public DiscloseSecurityContextService() {
        this.authenticationTrustResolver = new AuthenticationTrustResolverImpl();
    }

    @Override
    public ISecurityContextDto getSecurityContext() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final Authentication authentication = securityContext.getAuthentication();
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        final Object principal = authentication.getPrincipal();

        final IUserDetailsDto userDetailsDto;
        final boolean isUsernameKnown;
        if (principal instanceof String) {
            userDetailsDto = new UserDetailsDto((String) principal);
            isUsernameKnown = true;
        } else if (principal instanceof IDefendevUserDetails) {
            userDetailsDto = new UserDetailsDto(((IDefendevUserDetails) principal).getUsername());
            isUsernameKnown = true;
        } else if (principal instanceof User) {
            userDetailsDto = new UserDetailsDto(((User) principal).getUsername());
            isUsernameKnown = true;
        } else {
            userDetailsDto = new UserDetailsDto("unknown");
            isUsernameKnown = false;
        }
        final Set<IGrantedAuthorityDto> grantedAuthorities = stream(authorities).map(
            grantedAuthority -> new GrantedAuthorityDto(grantedAuthority.getAuthority())
        ).collect(Collectors.toSet());
        final IAuthenticationDto authenticationDto = new AuthenticationDto(grantedAuthorities, userDetailsDto,
            isAuthenticated(authentication, isUsernameKnown));
        final ISecurityContextDto securityContextDto = new SecurityContextDto(authenticationDto);

        return securityContextDto;
    }

    private boolean isAuthenticated(Authentication authentication, boolean isUsernameKnown) {
        return isUsernameKnown && !authenticationTrustResolver.isAnonymous(authentication);
    }

}
