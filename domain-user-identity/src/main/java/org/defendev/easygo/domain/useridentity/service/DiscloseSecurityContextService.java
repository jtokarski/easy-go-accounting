package org.defendev.easygo.domain.useridentity.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.domain.useridentity.service.api.IDiscloseSecurityContextService;
import org.defendev.common.domain.useridentity.service.dto.*;
import org.defendev.easygo.domain.useridentity.service.dto.EasygoUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.defendev.common.stream.Streams.stream;



@Service
public class DiscloseSecurityContextService implements IDiscloseSecurityContextService {

    private static final Logger log = LogManager.getLogger();

    @Override
    public ISecurityContextDto getSecurityContext() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final Authentication authentication = securityContext.getAuthentication();
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        final Object principal = authentication.getPrincipal();

        final IUserDetailsDto userDetailsDto;
        if (principal instanceof String) {
            userDetailsDto = new UserDetailsDto((String) principal);
        } else if (principal instanceof EasygoUserDetails) {
            userDetailsDto = new UserDetailsDto(((EasygoUserDetails) principal).getUsername());
        } else {
            userDetailsDto = new UserDetailsDto("unknown");
        }
        final Set<IGrantedAuthorityDto> grantedAuthorities = stream(authentication.getAuthorities()).map(
                grantedAuthority -> new GrantedAuthorityDto(grantedAuthority.getAuthority())
        ).collect(Collectors.toSet());
        final IAuthenticationDto authenticationDto = new AuthenticationDto(grantedAuthorities, userDetailsDto);
        final ISecurityContextDto securityContextDto = new SecurityContextDto(authenticationDto);

        return securityContextDto;
    }

}
