package org.defendev.easygo.devops.dbfixture;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.xml.bind.JAXBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.fixture.loader.FixturesLoader;
import org.defendev.easygo.devops.config.DataSourceConfig;
import org.defendev.easygo.devops.dbfixture.loader.SourceDocumentFixturesLoader;
import org.defendev.easygo.domain.fa.config.FinancialAccountingProperties;
import org.defendev.easygo.domain.fa.config.JpaConfig;
import org.defendev.easygo.domain.fa.model.SourceDocument;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;




@TestPropertySource(properties = { DataSourceConfig.DB_PROPERTY_LOCATIONS })
@EnableConfigurationProperties(FinancialAccountingProperties.class)
@SpringJUnitConfig(
    classes = { JpaConfig.class },
    initializers = { ConfigDataApplicationContextInitializer.class }
)
public class LoadFinancialAccountingFixturesTest {

    private static final Logger log = LogManager.getLogger();

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
