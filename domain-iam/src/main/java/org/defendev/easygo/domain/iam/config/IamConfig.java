package org.defendev.easygo.domain.iam.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;



@Import({ IamJpaConfig.class, SpringDataConfig.class, PasswordEncoderConfig.class })
@ComponentScan(basePackages = "org.defendev.easygo.domain.iam.service")
public class IamConfig {

    private static final Logger log = LogManager.getLogger();

}
