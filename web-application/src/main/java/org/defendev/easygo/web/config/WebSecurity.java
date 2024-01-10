package org.defendev.easygo.web.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;



@Import({ PasswordEncoderConfig.class })
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurity {

    private static final Logger log = LogManager.getLogger();

    public static final String SIGN_IN_PATH = "/sign-in";

    public static final String SIGN_OUT_PATH = "/sign-out";

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
    public SecurityFilterChain buildSecurityFilterChain(HttpSecurity http)
        throws Exception {

        final LoginUrlAuthenticationEntryPoint authnEntryPoint = new LoginUrlAuthenticationEntryPoint(SIGN_IN_PATH);
        final SimpleUrlAuthenticationSuccessHandler authnHandler = new SimpleUrlAuthenticationSuccessHandler("/");
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
                    .successHandler(authnHandler)
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
