package org.defendev.easygo.devops.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dbaDataSource() {
        return null;
    }

    @Bean
    public DataSource financialAccountingApplicationDataSource() {
        return null;
    }


}
