package org.defendev.easygo.domain.useridentity.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;



@Import({ JpaConfig.class, SpringDataConfig.class, PasswordEncoderConfig.class })
@ComponentScan(basePackages = "org.defendev.easygo.domain.useridentity.service")
public class UserIdentityConfig {

    private static final Logger log = LogManager.getLogger();

}
