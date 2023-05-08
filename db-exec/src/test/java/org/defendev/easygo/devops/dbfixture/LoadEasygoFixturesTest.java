package org.defendev.easygo.devops.dbfixture;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.xml.bind.JAXBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.fixture.loader.FixturesLoader;
import org.defendev.easygo.devops.config.DataSourceConfig;
import org.defendev.easygo.devops.dbfixture.loader.OwnershipUnitFixturesLoader;
import org.defendev.easygo.devops.dbfixture.loader.SourceDocumentFixturesLoader;
import org.defendev.easygo.devops.dbfixture.loader.UserIdentityFixturesLoader;
import org.defendev.easygo.domain.fa.config.FinancialAccountingJpaConfig;
import org.defendev.easygo.domain.fa.config.FinancialAccountingProperties;
import org.defendev.easygo.domain.useridentity.config.PasswordEncoderConfig;
import org.defendev.easygo.domain.useridentity.config.UserIdentityJpaConfig;
import org.defendev.easygo.domain.useridentity.config.UserIdentityProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;




@TestPropertySource(properties = { DataSourceConfig.DB_PROPERTY_LOCATIONS })
@EnableConfigurationProperties({ UserIdentityProperties.class, FinancialAccountingProperties.class })
@SpringJUnitConfig(
    classes = { UserIdentityJpaConfig.class, PasswordEncoderConfig.class, FinancialAccountingJpaConfig.class },
    initializers = { ConfigDataApplicationContextInitializer.class }
)
public class LoadEasygoFixturesTest {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext(unitName = "userIdentityPersistenceUnit")
    private EntityManager userIdentityEm;

    @Qualifier("userIdentityTransactionTemplate")
    @Autowired
    private TransactionTemplate userIdentityTransactionTemplate;

    @PersistenceContext(unitName = "financialAccountingPersistenceUnit")
    private EntityManager financialAccountingEm;

    @Qualifier("financialAccountingTransactionTemplate")
    @Autowired
    private TransactionTemplate financialAccountingTransactionTemplate;

    @Test
    public void loadFixtures() throws JAXBException {

        final List<FixturesLoader<?, ?>> loaders = new ArrayList<>();

        final  OwnershipUnitFixturesLoader ownershipUnitFixturesLoader = new OwnershipUnitFixturesLoader(
            "fixture/user-identity/OwnershipUnitSet.xml", userIdentityEm);
        loaders.add(ownershipUnitFixturesLoader);

        final UserIdentityFixturesLoader userIdentityFixturesLoader = new UserIdentityFixturesLoader(
            "fixture/user-identity/UserIdentitySet.xml", userIdentityEm, passwordEncoder, ownershipUnitFixturesLoader);
        loaders.add(userIdentityFixturesLoader);

        final SourceDocumentFixturesLoader sourceDocumentFixturesLoader = new SourceDocumentFixturesLoader(
            "fixture/financial-accounting/SourceDocumentSet.xml", financialAccountingEm, ownershipUnitFixturesLoader);
        loaders.add(sourceDocumentFixturesLoader);

        userIdentityTransactionTemplate.execute(status -> {
            try {
                ownershipUnitFixturesLoader.readFromResourceAndPersist();
                userIdentityFixturesLoader.readFromResourceAndPersist();
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
            return "I should return something smarter";
        });

        financialAccountingTransactionTemplate.execute(status -> {
            try {
                sourceDocumentFixturesLoader.readFromResourceAndPersist();
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
            return "I should return something smarter";
        });
    }

}
