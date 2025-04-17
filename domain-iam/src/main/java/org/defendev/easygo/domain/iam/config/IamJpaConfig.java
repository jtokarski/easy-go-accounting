package org.defendev.easygo.domain.iam.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Properties;


@EnableTransactionManagement
public class IamJpaConfig {

    private static final Logger log = LogManager.getLogger();

    @Bean
    public DataSource iamAppDataSource(IamProperties iamProperties) {
        final IamProperties.Db.Oracle oracleProps = iamProperties.getDb().getOracle().get(0);
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(oracleProps.getDriver());
        final String jdbcUrl = String.format("jdbc:oracle:thin:@//%s:%s/%s", oracleProps.getHost(),
            oracleProps.getPort(), oracleProps.getContainerName());
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(oracleProps.getAppUserName());
        hikariConfig.setPassword(oracleProps.getAppUserPassword());
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public FactoryBean<EntityManagerFactory> iamEmfFactoryBean(
        @Qualifier("iamAppDataSource") DataSource appDataSource
    ) {
        final LocalContainerEntityManagerFactoryBean emfFactoryBean = new LocalContainerEntityManagerFactoryBean();
        emfFactoryBean.setPersistenceUnitName("iamPersistenceUnit");
        emfFactoryBean.setDataSource(appDataSource);
        emfFactoryBean.setPackagesToScan("org.defendev.easygo.domain.iam.model");
        final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        emfFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        final Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", "none");
        jpaProperties.put("hibernate.globally_quoted_identifiers", Boolean.TRUE);
        jpaProperties.put("hibernate.show_sql", Boolean.FALSE);
        jpaProperties.put("hibernate.format_sql", Boolean.FALSE);
        jpaProperties.put("hibernate.use_sql_comments", Boolean.FALSE);
        emfFactoryBean.setJpaProperties(jpaProperties);
        return emfFactoryBean;
    }

    @Bean
    public EntityManagerFactory iamEmf(
        @Qualifier("iamEmfFactoryBean") FactoryBean<EntityManagerFactory> emfFactoryBean
    ) throws Exception {
        return emfFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager iamJpaTransactionManager(
        @Qualifier("iamEmf") EntityManagerFactory emf
    ) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public TransactionTemplate iamTransactionTemplate(
        @Qualifier("iamJpaTransactionManager") PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }
}
