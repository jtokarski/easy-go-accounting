package org.defendev.easygo.devops.dbmigration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.easygo.devops.config.DataSourceConfig;
import org.defendev.easygo.devops.config.DbObjectNamingConfig;
import org.defendev.easygo.devops.config.FlywayConfig;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;




@TestPropertySource(properties = { DataSourceConfig.DB_PROPERTY_LOCATIONS })
@SpringJUnitConfig(
    classes = { DataSourceConfig.class, DbObjectNamingConfig.class, FlywayConfig.class },
    initializers = { ConfigDataApplicationContextInitializer.class }
)
public class FlywayMigrationTest {

    private static final Logger log = LogManager.getLogger();

    @Test
    public void oracleBaseline(@Autowired Flyway flyway) {
        flyway.baseline();
    }

    @Test
    public void oracleMigrate(@Autowired Flyway flyway) {
        flyway.migrate();
    }

}
