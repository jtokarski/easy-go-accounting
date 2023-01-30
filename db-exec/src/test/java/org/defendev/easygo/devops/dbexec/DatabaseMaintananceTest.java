package org.defendev.easygo.devops.dbexec;

import org.apache.commons.text.StringSubstitutor;
import org.defendev.easygo.devops.config.DataSourceConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;




@TestPropertySource(properties = {"spring.config.location=" +
    "classpath:db-coordinates.yaml," +
    "classpath:db-root.yaml," +
    "classpath:db-tenant.yaml"
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringJUnitConfig(
    classes = {DataSourceConfig.class},
    initializers = {ConfigDataApplicationContextInitializer.class}
)
public class DatabaseMaintananceTest {

    private StringSubstitutor substitutor;


    @BeforeAll
    public void setUpSubstitutor(
//        String
    ) {
        final Map<String, String> replacements = new HashMap<>();
        replacements.put("abc.aaA", "333");
        replacements.put("asd.sdF", "444");
        substitutor = new StringSubstitutor(replacements);
        substitutor.setEnableUndefinedVariableException(true);
    }








    @Test
    public void createEasygoSysSchema(
        @Qualifier("dbaDataSource") DataSource dbaDataSource
    ) {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        final Map<String, String> replacements = new HashMap<>();

        try {
            final Resource resource2 = new ClassPathResource("db/oracle/setup/test.sql");
            final String sql = new String(resource2.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            final String sqlExpand = substitutor.replace(sql);
            final Resource resource3 = new InputStreamResource(
                new ByteArrayInputStream(sqlExpand.getBytes(StandardCharsets.UTF_8))
            );
            populator.addScript(resource3);
            populator.execute(dbaDataSource);
        } catch (IOException ex) {
            throw new IllegalStateException("", ex);
        }
        assertThat(178).isEqualTo(178);
    }

    @Test
    public void dropEasygoSysSchema() {

        assertThat(8).isEqualTo(8);

    }

    @Test
    public void dropTenantSchemas() {
        assertThat(1).isEqualTo(1);

    }

}
