package org.defendev.easygo.devops.config;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.HashMap;
import java.util.Map;



@Import({ SubstitutingSqlScriptExecutor.class })
@Configuration
public class DbObjectNamingConfig {

    @Bean
    public Map<String, String> dbNamingReplacements(
        @Value("${easygo.dbExec.oracle.tenant.id}") String tenantId,
        @Value("${easygo.dbExec.oracle.tenant.roUserPasswordIAM}") String roUserPasswordIAM,
        @Value("${easygo.dbExec.oracle.tenant.roUserPasswordSE}") String roUserPasswordSE,
        @Value("${easygo.dbExec.oracle.tenant.roUserPasswordFA}") String roUserPasswordFA,
        @Value("${easygo.dbExec.oracle.tenant.appUserPasswordIAM}") String appUserPasswordIAM,
        @Value("${easygo.dbExec.oracle.tenant.appUserPasswordSE}") String appUserPasswordSE,
        @Value("${easygo.dbExec.oracle.tenant.appUserPasswordFA}") String appUserPasswordFA
    ) {
        final Map<String, String> replacements = new HashMap<>();
        replacements.put("flywaySchemaHistoryTableName", String.format("%s_flyway_schema_history", tenantId));
        replacements.put("schemaNameIAM", String.format("EASYGO_%s_IAM", tenantId));
        replacements.put("schemaNameSE", String.format("EASYGO_%s_SE", tenantId));
        replacements.put("schemaNameFA", String.format("EASYGO_%s_FA", tenantId));
        replacements.put("appUserNameIAM", String.format("EASYGO_%s_IAM_APPU", tenantId));
        replacements.put("appUserNameSE", String.format("EASYGO_%s_SE_APPU", tenantId));
        replacements.put("appUserNameFA", String.format("EASYGO_%s_FA_APPU", tenantId));
        replacements.put("appUserPasswordIAM", appUserPasswordIAM);
        replacements.put("appUserPasswordSE", appUserPasswordSE);
        replacements.put("appUserPasswordFA", appUserPasswordFA);
        replacements.put("roUserNameIAM", String.format("EASYGO_%s_IAM_ROU", tenantId));
        replacements.put("roUserNameSE", String.format("EASYGO_%s_SE_ROU", tenantId));
        replacements.put("roUserNameFA", String.format("EASYGO_%s_FA_ROU", tenantId));
        replacements.put("roUserPasswordIAM", roUserPasswordIAM);
        replacements.put("roUserPasswordSE", roUserPasswordSE);
        replacements.put("roUserPasswordFA", roUserPasswordFA);
        return replacements;
    }

    @Bean
    public StringSubstitutor dbNamingSubstitutor(
        @Qualifier("dbNamingReplacements") Map<String, String> replacements
    ) {
        final StringSubstitutor substitutor = new StringSubstitutor(replacements);
        substitutor.setEnableUndefinedVariableException(true);
        return substitutor;
    }

}
