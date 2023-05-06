package org.defendev.easygo.devops.dbfixture;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.xml.bind.JAXBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.fixture.loader.FixturesLoader;
import org.defendev.easygo.devops.config.DataSourceConfig;
import org.defendev.easygo.devops.dbfixture.loader.UserIdentityFixturesLoader;
import org.defendev.easygo.domain.useridentity.config.JpaConfig;
import org.defendev.easygo.domain.useridentity.config.PasswordEncoderConfig;
import org.defendev.easygo.domain.useridentity.config.UserIdentityProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;



@TestPropertySource(properties = { DataSourceConfig.DB_PROPERTY_LOCATIONS })
@EnableConfigurationProperties(UserIdentityProperties.class)
@SpringJUnitConfig(
    classes = { JpaConfig.class, PasswordEncoderConfig.class },
    initializers = { ConfigDataApplicationContextInitializer.class }
)
public class LoadUserIdentityFixturesTest {

    private static final Logger log = LogManager.getLogger();

    @PersistenceContext(unitName = "userIdentityPersistenceUnit")
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Commit
    @Transactional
    @Test
    public void loadFixtures() throws JAXBException {

        final List<FixturesLoader<?, ?>> loaders = new ArrayList<>();

        final UserIdentityFixturesLoader userIdentityFixturesLoader = new UserIdentityFixturesLoader(
            "fixture/user-identity/UserIdentitySet.xml", entityManager, passwordEncoder);
        loaders.add(userIdentityFixturesLoader);

        for (final FixturesLoader<?, ?> fixturesLoader : loaders) {
            fixturesLoader.readFromResourceAndPersist();
        }
    }

}


