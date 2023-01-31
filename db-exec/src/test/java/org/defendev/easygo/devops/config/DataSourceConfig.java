package org.defendev.easygo.devops.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;



@Configuration
public class DataSourceConfig {

    public static final String DB_PROPERTY_LOCATIONS = "spring.config.location=" +
        "classpath:db-coordinates.yaml" + "," +
        "classpath:db-root.yaml" + "," +
        "classpath:db-tenant.yaml";

    @Bean
    public DataSource dbaDataSource(
        @Value("${db.oracle.coordinate.driver}") String driverClassName,
        @Value("${db.oracle.coordinate.host}") String host,
        @Value("${db.oracle.coordinate.port}") int port,
        @Value("${db.oracle.coordinate.containerName}") String containerName,
        @Value("${db.oracle.root.userId}") String userId,
        @Value("${db.oracle.root.password}")String password
    ) {
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);
        final String jdbcUrl = String.format("jdbc:oracle:thin:@//%s:%s/%s", host, port, containerName);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(userId);
        hikariConfig.setPassword(password);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public DataSource financialAccountingApplicationDataSource() {
        return null;
    }


}
