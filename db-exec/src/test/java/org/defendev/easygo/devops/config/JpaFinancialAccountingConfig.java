package org.defendev.easygo.devops.config;

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

import javax.sql.DataSource;
import java.util.Properties;



public class JpaFinancialAccountingConfig {

    private static final Logger log = LogManager.getLogger();

    @Bean
    public FactoryBean<EntityManagerFactory> easygoEmfFactoryBean(
        @Qualifier("financialAccountingAppDataSource") DataSource appDataSource
    ) {
        final LocalContainerEntityManagerFactoryBean emfFactoryBean = new LocalContainerEntityManagerFactoryBean();
        emfFactoryBean.setPersistenceUnitName("easygoPersistenceUnit");
        emfFactoryBean.setDataSource(appDataSource);
        emfFactoryBean.setPackagesToScan("org.defendev.easygo.domain.fa");
        final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        emfFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        final Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.Oracle12cDialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", "none");
        jpaProperties.put("hibernate.globally_quoted_identifiers", Boolean.TRUE);
        jpaProperties.put("hibernate.show_sql", Boolean.FALSE);
        jpaProperties.put("hibernate.format_sql", Boolean.FALSE);
        jpaProperties.put("hibernate.use_sql_comments", Boolean.FALSE);
        emfFactoryBean.setJpaProperties(jpaProperties);
        return emfFactoryBean;
    }

    @Bean
    public EntityManagerFactory easygoEmf(
        @Qualifier("easygoEmfFactoryBean") FactoryBean<EntityManagerFactory> emfFactoryBean
    ) throws Exception {
        return emfFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager easygoJpaTransactionManager(
        @Qualifier("easygoEmf") EntityManagerFactory emf
    ) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

}
