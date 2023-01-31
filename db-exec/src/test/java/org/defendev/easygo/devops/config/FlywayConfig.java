package org.defendev.easygo.devops.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.MigrationVersion;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;



@Configuration
public class FlywayConfig {

    @Bean
    public Flyway flywaySetup(
        @Qualifier("dbaDataSource") DataSource dbaDataSource,
        @Qualifier("dbNamingReplacements") Map<String, String> replacements
    ) {
        final Flyway flyway = Flyway.configure()
            .dataSource(dbaDataSource)
            .schemas("EASYGO_SYS")
            .defaultSchema("EASYGO_SYS")
            .createSchemas(false)
            .table(replacements.get("flywaySchemaHistoryTableName"))
            .locations(new Location("classpath:db/oracle/migration"))
            .baselineVersion(MigrationVersion.fromVersion("0"))
            .placeholders(replacements)
            .load();
        return flyway;
    }


}
