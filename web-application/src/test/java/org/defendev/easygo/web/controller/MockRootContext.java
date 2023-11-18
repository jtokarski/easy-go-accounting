package org.defendev.easygo.web.controller;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;



public class MockRootContext {

    @Bean
    public OidcUserService mockOidcUserService() {
        return Mockito.mock(OidcUserService.class);
    }

    @Bean
    public UserDetailsService mockUserDetailsService() {
        return Mockito.mock(UserDetailsService.class);
    }

}
