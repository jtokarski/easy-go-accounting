package org.defendev.easygo.devops.dbexec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.easygo.devops.config.DataSourceConfig;
import org.defendev.easygo.devops.config.DbObjectNamingConfig;
import org.defendev.easygo.devops.config.SubstitutingSqlScriptExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;



@TestPropertySource(properties = { DataSourceConfig.DB_PROPERTY_LOCATIONS })
@SpringJUnitConfig(
    classes = { DataSourceConfig.class, DbObjectNamingConfig.class },
    initializers = { ConfigDataApplicationContextInitializer.class }
)
public class DatabaseMaintananceTest {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private SubstitutingSqlScriptExecutor executor;

    @Test
    public void createEasygoSysSchema() {
        executor.classpathScriptExpandAndPopulateAsDba("db/oracle/setup/create-easygo-sys-schema.sql");
    }

    @Test
    public void dropEasygoSysSchema() {
        executor.classpathScriptExpandAndPopulateAsDba("db/oracle/setup/drop-easygo-sys-schema.sql");
    }

    @Test
    public void dropTenantSchemas() {
        executor.classpathScriptExpandAndPopulateAsDba("db/oracle/setup/drop-tenant-schemas.sql");
    }

}
