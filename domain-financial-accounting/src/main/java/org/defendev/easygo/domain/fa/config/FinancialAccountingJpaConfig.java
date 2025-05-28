package org.defendev.easygo.domain.fa.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import static org.hibernate.cfg.JdbcSettings.DIALECT;
import static org.hibernate.cfg.JdbcSettings.FORMAT_SQL;
import static org.hibernate.cfg.JdbcSettings.SHOW_SQL;
import static org.hibernate.cfg.JdbcSettings.USE_SQL_COMMENTS;
import static org.hibernate.cfg.MappingSettings.GLOBALLY_QUOTED_IDENTIFIERS;
import static org.hibernate.cfg.SchemaToolingSettings.JAKARTA_HBM2DDL_DATABASE_ACTION;
import static org.hibernate.tool.schema.Action.ACTION_NONE;



@EnableTransactionManagement
public class FinancialAccountingJpaConfig {

    private static final Logger log = LogManager.getLogger();

    @Bean
    public DataSource financialAccountingAppDataSource(FinancialAccountingProperties faProps) {
        final FinancialAccountingProperties.Db.Oracle oracleProps = faProps.getDb().getOracle().get(0);
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
    public EntityManagerFactory financialAccountingEmf(
        @Qualifier("financialAccountingAppDataSource") DataSource appDataSource
    ) {
        final LocalContainerEntityManagerFactoryBean emfFactoryBean = new LocalContainerEntityManagerFactoryBean();
        emfFactoryBean.setPersistenceUnitName("financialAccountingPersistenceUnit");
        emfFactoryBean.setDataSource(appDataSource);
        emfFactoryBean.setPackagesToScan("org.defendev.easygo.domain.fa.model");
        final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        emfFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        final Properties jpaProperties = new Properties();
        jpaProperties.put(DIALECT, "org.hibernate.dialect.OracleDialect");
        jpaProperties.put(JAKARTA_HBM2DDL_DATABASE_ACTION, ACTION_NONE);
        jpaProperties.put(GLOBALLY_QUOTED_IDENTIFIERS, Boolean.TRUE);
        jpaProperties.put(SHOW_SQL, Boolean.FALSE);
        jpaProperties.put(FORMAT_SQL, Boolean.FALSE);
        jpaProperties.put(USE_SQL_COMMENTS, Boolean.FALSE);
        emfFactoryBean.setJpaProperties(jpaProperties);
        emfFactoryBean.afterPropertiesSet();
        return emfFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager financialAccountingJpaTransactionManager(
        @Qualifier("financialAccountingEmf") EntityManagerFactory emf
    ) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public TransactionTemplate financialAccountingTransactionTemplate(
        @Qualifier("financialAccountingJpaTransactionManager") PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }
}
