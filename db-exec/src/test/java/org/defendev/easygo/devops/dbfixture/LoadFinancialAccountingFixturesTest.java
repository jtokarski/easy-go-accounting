package org.defendev.easygo.devops.dbfixture;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.xml.bind.JAXBException;
import org.defendev.common.fixture.loader.FixturesLoader;
import org.defendev.easygo.devops.config.DataSourceConfig;
import org.defendev.easygo.devops.config.DbObjectNamingConfig;
import org.defendev.easygo.devops.config.JpaFinancialAccountingConfig;
import org.defendev.easygo.devops.dbfixture.loader.SourceDocumentFixturesLoader;
import org.defendev.easygo.domain.fa.SourceDocument;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;



@TestPropertySource(properties = { DataSourceConfig.DB_PROPERTY_LOCATIONS })
@SpringJUnitConfig(
    classes = { DataSourceConfig.class, DbObjectNamingConfig.class, JpaFinancialAccountingConfig.class },
    initializers = { ConfigDataApplicationContextInitializer.class }
)
public class LoadFinancialAccountingFixturesTest {

    @PersistenceContext(unitName = "easygoPersistenceUnit")
    private EntityManager entityManager;

    @Commit
    @Transactional
    @Test
    public void loadFixtures() throws JAXBException {

        final List<FixturesLoader<?, ?>> loaders = new ArrayList<>();

        final SourceDocumentFixturesLoader sourceDocumentFixturesLoader = new SourceDocumentFixturesLoader(
            "fixture/financial-accounting/SourceDocumentSet.xml", entityManager, SourceDocument.class
        );
        loaders.add(sourceDocumentFixturesLoader);

        for (final FixturesLoader<?, ?> fixturesLoader : loaders) {
            fixturesLoader.readFromResourceAndPersist();
        }
    }

}
