package org.defendev.easygo.devops.config;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;



@Component
public class SubstitutingSqlScriptExecutor {

    private final StringSubstitutor substitutor;

    private final DataSource dbaDataSource;

    @Autowired
    public SubstitutingSqlScriptExecutor(
        @Qualifier("dbNamingSubstitutor") StringSubstitutor substitutor,
        @Qualifier("dbaDataSource") DataSource dbaDataSource
    ) {
        this.substitutor = substitutor;
        this.dbaDataSource = dbaDataSource;
    }

    public void classpathScriptExpandAndPopulateAsDba(String scriptPath) {
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
