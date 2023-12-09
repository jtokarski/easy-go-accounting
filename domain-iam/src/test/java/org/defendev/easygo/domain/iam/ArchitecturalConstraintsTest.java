package org.defendev.easygo.domain.iam;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;



public class ArchitecturalConstraintsTest {

    private static JavaClasses classes;

    @BeforeAll
    public static void setUpClassFileImporter() {
        classes = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("org.defendev.easygo");
    }

    @Test
    public void prohibit_SecurityContextHolder_getContext() {
        final ArchRule archRule = ArchRuleDefinition.noClasses()
            .should().callMethod(SecurityContextHolder.class, "getContext");
        archRule.check(classes);
    }

}
