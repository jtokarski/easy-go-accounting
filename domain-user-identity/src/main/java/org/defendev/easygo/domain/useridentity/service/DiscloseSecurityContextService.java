package org.defendev.easygo.domain.useridentity.service;

import org.defendev.common.domain.useridentity.service.api.IDiscloseSecurityContextService;
import org.defendev.common.domain.useridentity.service.dto.AuthenticationDto;
import org.defendev.common.domain.useridentity.service.dto.IAuthenticationDto;
import org.defendev.common.domain.useridentity.service.dto.ISecurityContextDto;
import org.defendev.common.domain.useridentity.service.dto.IUserDetailsDto;
import org.defendev.common.domain.useridentity.service.dto.SecurityContextDto;
import org.defendev.common.domain.useridentity.service.dto.UserDetailsDto;
import org.springframework.stereotype.Service;



@Service
public class DiscloseSecurityContextService implements IDiscloseSecurityContextService {

    @Override
    public ISecurityContextDto getSecurityContext() {
        /*
         *
         * _( temporarily mocked ... )_
         *
         */
        final IUserDetailsDto userDetailsDto = new UserDetailsDto("mockus3r");
        final IAuthenticationDto authenticationDto = new AuthenticationDto(userDetailsDto);
        final ISecurityContextDto securityContextDto = new SecurityContextDto(authenticationDto);
        return securityContextDto;
    }

}
