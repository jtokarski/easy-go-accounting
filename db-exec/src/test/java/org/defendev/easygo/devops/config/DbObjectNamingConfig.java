package org.defendev.easygo.devops.config;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;




@Configuration
public class DbObjectNamingConfig {

    @Bean
    public Map<String, String> dbNamingReplacements(
        @Value("${db.oracle.tenant.id}") String tenantId,
        @Value("${db.oracle.tenant.roUserPasswordUI}") String roUserPasswordUI,
        @Value("${db.oracle.tenant.roUserPasswordSE}") String roUserPasswordSE,
        @Value("${db.oracle.tenant.roUserPasswordFA}") String roUserPasswordFA,
        @Value("${db.oracle.tenant.appUserPasswordUI}") String appUserPasswordUI,
        @Value("${db.oracle.tenant.appUserPasswordSE}") String appUserPasswordSE,
        @Value("${db.oracle.tenant.appUserPasswordFA}") String appUserPasswordFA
    ) {
        final Map<String, String> replacements = new HashMap<>();
        replacements.put("flywaySchemaHistoryTableName", String.format("%s_flyway_schema_history", tenantId));
        replacements.put("schemaNameUI", String.format("EASYGO_%s_UI", tenantId));
        replacements.put("schemaNameSE", String.format("EASYGO_%s_SE", tenantId));
        replacements.put("schemaNameFA", String.format("EASYGO_%s_FA", tenantId));
        replacements.put("appUserNameUI", String.format("EASYGO_%s_UI_APPU", tenantId));
        replacements.put("appUserNameSE", String.format("EASYGO_%s_SE_APPU", tenantId));
        replacements.put("appUserNameFA", String.format("EASYGO_%s_FA_APPU", tenantId));
        replacements.put("appUserPasswordUI", appUserPasswordUI);
        replacements.put("appUserPasswordSE", appUserPasswordSE);
        replacements.put("appUserPasswordFA", appUserPasswordFA);
        replacements.put("roUserNameUI", String.format("EASYGO_%s_UI_ROU", tenantId));
        replacements.put("roUserNameSE", String.format("EASYGO_%s_SE_ROU", tenantId));
        replacements.put("roUserNameFA", String.format("EASYGO_%s_FA_ROU", tenantId));
        replacements.put("roUserPasswordUI", roUserPasswordUI);
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
