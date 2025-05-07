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
            .sqlMigrationPrefix("V")
            .sqlMigrationSeparator("__")
            /*
             * The baselineVersion should correspond with versioning convention of migration files.
             * Given that this project uses scheme of:
             *   V0001__identity_access_management.sql
             * the baselineVersion that precedes first migration and is consistent with convention
             * is "0000", not "0"
             * Note that baselineVersion will be inserted in flyway_schema_history.version column.
             *
             * See:
             *   - https://documentation.red-gate.com/fd/flyway-baseline-version-setting-277578975.html
             */
            .baselineVersion(MigrationVersion.fromVersion("0000"))
            .placeholders(replacements)
            .load();

        /*
         * How does Flyway searches for migrations based on this configuration?
         *
         * The resulting Flyway instance will have CompositeMigrationResolver, that delegates to
         * other default resolvers. In this case SqlMigrationResolver.
         * Flyway also offers the possibility to implement and add custom MigrationResolver(s).
         *
         * See:
         *   - org.flywaydb.core.api.resolver.MigrationResolver
         *   - org.flywaydb.core.internal.resolver.CompositeMigrationResolver
         *   - org.flywaydb.core.internal.resolver.sql.SqlMigrationResolver
         */

        return flyway;
    }


}
