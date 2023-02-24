package org.defendev.easygo.devops.dbexec;

import org.apache.commons.text.StringSubstitutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.easygo.devops.config.DataSourceConfig;
import org.defendev.easygo.devops.config.DbObjectNamingConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;



@TestPropertySource(properties = { DataSourceConfig.DB_PROPERTY_LOCATIONS })
@SpringJUnitConfig(
    classes = { DataSourceConfig.class, DbObjectNamingConfig.class },
    initializers = { ConfigDataApplicationContextInitializer.class }
)
public class DatabaseMaintananceTest {

    private static final Logger log = LogManager.getLogger();

    @Qualifier("dbaDataSource")
    @Autowired
    private DataSource dbaDataSource;

    @Qualifier("dbNamingSubstitutor")
    @Autowired
    private StringSubstitutor substitutor;


    @Test
    public void createEasygoSysSchema() {
        classpathScriptExpandAndPopulateAsDba("db/oracle/setup/create-easygo-sys-schema.sql");
    }

    @Test
    public void dropEasygoSysSchema() {
        classpathScriptExpandAndPopulateAsDba("db/oracle/setup/drop-easygo-sys-schema.sql");
    }

    @Test
    public void dropTenantSchemas() {
        classpathScriptExpandAndPopulateAsDba("db/oracle/setup/drop-tenant-schemas.sql");
    }

    private void classpathScriptExpandAndPopulateAsDba(String scriptPath) {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        try {
            final Resource sqlTemplateResource = new ClassPathResource(scriptPath);
            final String sql = new String(sqlTemplateResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            final String sqlExpand = substitutor.replace(sql);
            final Resource sqlExpandResource = new InputStreamResource(
                    new ByteArrayInputStream(sqlExpand.getBytes(StandardCharsets.UTF_8))
            );
            populator.setSeparator(";;;");
            populator.addScript(sqlExpandResource);
            populator.execute(dbaDataSource);
        } catch (IOException ex) {
            throw new IllegalStateException("Exception occurred while ", ex);
        }
    }

}
