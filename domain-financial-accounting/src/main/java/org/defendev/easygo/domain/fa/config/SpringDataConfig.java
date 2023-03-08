package org.defendev.easygo.domain.fa.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@EnableJpaRepositories(
    basePackages = { "org.defendev.easygo.domain.fa.repository" },
    entityManagerFactoryRef = "easygoEmf",
    transactionManagerRef = "easygoJpaTransactionManager"
)
public class SpringDataConfig {

    private static final Logger log = LogManager.getLogger();


}
