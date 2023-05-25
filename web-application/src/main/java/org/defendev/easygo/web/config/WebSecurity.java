package org.defendev.easygo.web.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.easygo.domain.useridentity.config.PasswordEncoderConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@EnableWebSecurity
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
    public SecurityFilterChain buildSecurityFilterChain(HttpSecurity http) throws Exception {

        final LoginUrlAuthenticationEntryPoint authenticationEntryPoint = new LoginUrlAuthenticationEntryPoint(SIGN_IN_PATH);

        return http
            .securityMatcher(AntPathRequestMatcher.antMatcher("/**"))
            .authorizeHttpRequests()
            .requestMatchers(AntPathRequestMatcher.antMatcher("/**")).permitAll()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .and()
            .formLogin().loginProcessingUrl(SIGN_IN_PATH)
            .and()
            .logout(logoutConfigurer -> logoutConfigurer
                .logoutUrl(SIGN_OUT_PATH)
                .logoutSuccessUrl("/")
            )
            .csrf().disable()
            .build();


    }

}
