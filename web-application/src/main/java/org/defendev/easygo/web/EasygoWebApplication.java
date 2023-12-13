package org.defendev.easygo.web;

import org.defendev.easygo.domain.fa.config.FinancialAccountingConfig;
import org.defendev.easygo.domain.fa.config.FinancialAccountingProperties;
import org.defendev.easygo.web.config.WebApplicationProperties;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;


@EnableConfigurationProperties({FinancialAccountingProperties.class,
    WebApplicationProperties.class})
@Import({FinancialAccountingConfig.class})
@SpringBootApplication
public class EasygoWebApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EasygoWebApplication.class)
            .web(WebApplicationType.SERVLET)
            .profiles("runJar")
            .run(args);
    }

}
