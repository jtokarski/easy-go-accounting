package org.defendev.easygo.web.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;


@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurity {

    private static final Logger log = LogManager.getLogger();

    public static final String SIGN_IN_PATH = "/sign-in";

    public static final String SIGN_OUT_PATH = "/sign-out";

    @Bean
    public SecurityFilterChain buildSecurityFilterChain(HttpSecurity http)
        throws Exception {

        final LoginUrlAuthenticationEntryPoint authnEntryPoint = new LoginUrlAuthenticationEntryPoint(SIGN_IN_PATH);
        final SimpleUrlAuthenticationFailureHandler authnFailureHandler = new SimpleUrlAuthenticationFailureHandler(
            SIGN_IN_PATH);
        authnFailureHandler.setUseForward(false);

        return http
            .securityMatcher(AntPathRequestMatcher.antMatcher("/**"))
            .authorizeHttpRequests(
                customizer -> customizer
                    .requestMatchers("/", SIGN_IN_PATH, "/api/security-context", "/api/document/_browse",
                        "/api/last-visited").permitAll()
                    .requestMatchers(RegexRequestMatcher.regexMatcher(
                        "/[a-zA-Z0-9-\\.]+\\.(png|svg|gif|ico|js|css|css\\.map|ttf|woff2)")).permitAll()
                    .requestMatchers("/actuator", "/actuator/beans", "/actuator/env", "/actuator/health",
                        "/actuator/info").permitAll()
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/**")).fullyAuthenticated()
            )
            .exceptionHandling(
                customizer -> customizer.authenticationEntryPoint(authnEntryPoint)
            )
            .formLogin(
                customizer -> customizer
                    .loginProcessingUrl(SIGN_IN_PATH)
                    .failureHandler(authnFailureHandler)
            )
            .logout(
                customizer -> customizer
                    .logoutRequestMatcher(AntPathRequestMatcher.antMatcher(HttpMethod.GET, SIGN_OUT_PATH))
                    .logoutSuccessUrl("/")
            )
            .build();
    }

}
