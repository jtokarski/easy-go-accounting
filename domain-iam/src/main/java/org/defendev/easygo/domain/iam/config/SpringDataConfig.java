package org.defendev.easygo.domain.iam.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@EnableJpaRepositories(
    basePackages = {"org.defendev.easygo.domain.iam.repository"},
    entityManagerFactoryRef = "iamEmf",
    transactionManagerRef = "iamJpaTransactionManager"
)
public class SpringDataConfig {

    private static final Logger log = LogManager.getLogger();

}
