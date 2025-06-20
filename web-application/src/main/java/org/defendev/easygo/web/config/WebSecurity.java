package org.defendev.easygo.web.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.easygo.domain.iam.api.IEasygoOAuth2UserService;
import org.defendev.easygo.domain.iam.api.IEasygoOidcUserService;
import org.defendev.easygo.domain.iam.config.PasswordEncoderConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;



@Import({ PasswordEncoderConfig.class })
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurity {

    private static final Logger log = LogManager.getLogger();

    public static final String SIGN_IN_PATH = "/sign-in";

    public static final String SIGN_OUT_PATH = "/sign-out";

    public static final String CLIENT_NAME = "easy-go-accounting";

    public static final String OAUTH2_REGISTRATION_ID_GOOGLE = "google";

    public static final String OAUTH2_REGISTRATION_ID_AZURE = "azure";

    public static final String OAUTH2_REGISTRATION_ID_GITHUB = "github";

    public static final String OAUTH2_REGISTRATION_ID_SPRING_AUTHZ_SERVER = "sprin6authz";

    @Bean
    public AuthenticationManager globalAuthenticationManager(HttpSecurity http,
                                                             PasswordEncoder passwordEncoder,
                                                             UserDetailsService userDetailsService)
        throws Exception {
        final AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        builder.authenticationProvider(provider);
        return builder.build();
    }

    @Bean
    public SecurityFilterChain buildSecurityFilterChain(HttpSecurity http,
                                                        IEasygoOAuth2UserService easygoOAuth2UserService,
                                                        IEasygoOidcUserService easygoOidcUserService)
        throws Exception {

        final LoginUrlAuthenticationEntryPoint authnEntryPoint = new LoginUrlAuthenticationEntryPoint(SIGN_IN_PATH);
        final SimpleUrlAuthenticationSuccessHandler authnHandler = new SimpleUrlAuthenticationSuccessHandler("/");
        final SimpleUrlAuthenticationFailureHandler authnFailureHandler = new SimpleUrlAuthenticationFailureHandler(
            SIGN_IN_PATH);
        authnFailureHandler.setUseForward(false);

        return http
            .securityMatcher(PathPatternRequestMatcher.withDefaults().matcher("/**"))
            .authorizeHttpRequests(
                customizer -> customizer
                    .requestMatchers("/", SIGN_IN_PATH, "/api/security-context", "/api/document/_browse",
                        "/api/last-visited").permitAll()
                    .requestMatchers(RegexRequestMatcher.regexMatcher(
                        "/[a-zA-Z0-9-\\.]+\\.(png|svg|gif|ico|js|css|css\\.map|ttf|woff2)")).permitAll()
                    .requestMatchers("/actuator", "/actuator/beans", "/actuator/env", "/actuator/health",
                        "/actuator/info").permitAll()
                    .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/**")).fullyAuthenticated()
            )
            .exceptionHandling(
                customizer -> customizer.authenticationEntryPoint(authnEntryPoint)
            )
            .formLogin(
                customizer -> customizer
                    .loginProcessingUrl(SIGN_IN_PATH)
                    .successHandler(authnHandler)
                    .failureHandler(authnFailureHandler)
            )
            .oauth2Login(
                customizer -> customizer.userInfoEndpoint(
                    userInfoEndpointCustomizer -> userInfoEndpointCustomizer
                        .userService(easygoOAuth2UserService)
                        .oidcUserService(easygoOidcUserService)
                ).successHandler(authnHandler)
            )
            .logout(
                customizer -> customizer
                    .logoutRequestMatcher(
                        PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, SIGN_OUT_PATH)
                    )
                    .logoutSuccessUrl("/")
            )
            .build();
    }

    @Bean
    public ClientRegistrationRepository buildClientRegistrationRepository(WebApplicationProperties webProps) {
        final ClientRegistration azureRegistration = ClientRegistration
            .withRegistrationId(OAUTH2_REGISTRATION_ID_AZURE)
            .clientName(CLIENT_NAME)
            .clientId(webProps.getOidc().getAzure().getClientId())
            .clientSecret(webProps.getOidc().getAzure().getClientSecret())
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationUri(webProps.getOidc().getAzure().getAuthorizationUri())
            .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
            .scope(OidcScopes.OPENID, OidcScopes.PROFILE, OidcScopes.EMAIL, OidcScopes.PROFILE, OidcScopes.PHONE)
            .tokenUri(webProps.getOidc().getAzure().getTokenUri())
            .userInfoUri(webProps.getOidc().getAzure().getUserInfoUri())
            .userNameAttributeName(IdTokenClaimNames.SUB)
            .jwkSetUri(webProps.getOidc().getAzure().getJwkSetUri())
            .build();

        final ClientRegistration githubRegistration = CommonOAuth2Provider.GITHUB.getBuilder(OAUTH2_REGISTRATION_ID_GITHUB)
            .clientName(CLIENT_NAME)
            .clientId(webProps.getOidc().getGithub().getClientId())
            .clientSecret(webProps.getOidc().getGithub().getClientSecret())
            .build();

        final ClientRegistration googleRegistration = CommonOAuth2Provider.GOOGLE.getBuilder(OAUTH2_REGISTRATION_ID_GOOGLE)
            .clientName(CLIENT_NAME)
            .clientId(webProps.getOidc().getGoogle().getClientId())
            .clientSecret(webProps.getOidc().getGoogle().getClientSecret())
            .build();

        final ClientRegistration sprin6authzRegistration = ClientRegistration
            .withRegistrationId(OAUTH2_REGISTRATION_ID_SPRING_AUTHZ_SERVER)
            .clientName(CLIENT_NAME)
            .clientId(webProps.getOidc().getSpring().getClientId())
            .clientSecret(webProps.getOidc().getSpring().getClientSecret())
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
            .authorizationUri(webProps.getOidc().getSpring().getAuthorizationUri())
            .scope(OidcScopes.OPENID, OidcScopes.PROFILE)
            /*
             * Protocol Endpoint information is available on Authorization Server:
             *   http://localhost:8010/defendev-authz/.well-known/oauth-authorization-server
             *   http://localhost:8010/defendev-authz/.well-known/openid-configuration
             *
             */
            .tokenUri(webProps.getOidc().getSpring().getTokenUri())
            .userInfoUri(webProps.getOidc().getSpring().getUserInfoUri())
            .userNameAttributeName(IdTokenClaimNames.SUB)
            .jwkSetUri(webProps.getOidc().getSpring().getJwkSetUri())
            .build();

        return new InMemoryClientRegistrationRepository(azureRegistration, githubRegistration, googleRegistration,
            sprin6authzRegistration);
    }

}
