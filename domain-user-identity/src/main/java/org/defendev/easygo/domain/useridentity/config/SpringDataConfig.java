package org.defendev.easygo.domain.useridentity.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@EnableJpaRepositories(
    basePackages = { "org.defendev.easygo.domain.useridentity.repository" },
    entityManagerFactoryRef = "userIdentityEmf",
    transactionManagerRef = "userIdentityJpaTransactionManager"
)
public class SpringDataConfig {

    private static final Logger log = LogManager.getLogger();

}
