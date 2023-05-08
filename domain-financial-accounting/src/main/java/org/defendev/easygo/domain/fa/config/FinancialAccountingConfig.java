package org.defendev.easygo.domain.fa.config;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ComponentScan;



@Import({FinancialAccountingJpaConfig.class, SpringDataConfig.class})
@ComponentScan(basePackages = "org.defendev.easygo.domain.fa.service")
public class FinancialAccountingConfig {
}
