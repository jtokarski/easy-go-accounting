package org.defendev.easygo.domain.useridentity.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;



public class PasswordEncoderConfig {

    private static final Logger log = LogManager.getLogger();

    @Bean
    public PasswordEncoder passwordEncoder() {
        final String idBCrypt = "bcrypt";
        final PasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder(BCryptVersion.$2A, 10, null);
        final Map<String, PasswordEncoder> idToPasswordEncoder = new HashMap<String, PasswordEncoder>();
        idToPasswordEncoder.put(idBCrypt, bcryptPasswordEncoder);
        final DelegatingPasswordEncoder delegatingEncoder = new DelegatingPasswordEncoder(idBCrypt, idToPasswordEncoder);
        return delegatingEncoder;
    }

}
