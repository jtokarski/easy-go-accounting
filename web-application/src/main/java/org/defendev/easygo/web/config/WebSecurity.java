package org.defendev.easygo.web.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;



@EnableWebSecurity
@Configuration
public class WebSecurity {

    private static final Logger log = LogManager.getLogger();

}
