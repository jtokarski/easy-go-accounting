package org.defendev.easygo.devops.dbfixture.loader;

import jakarta.persistence.EntityManager;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.fixture.loader.FixturesLoader;
import org.defendev.easygo.devops.dbfixture.wrapper.UserIdentitySet;
import org.defendev.easygo.domain.useridentity.model.UserIdentity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.InputStream;
import java.util.List;



public class UserIdentityFixturesLoader extends FixturesLoader<UserIdentity, Long> {

    private static final Logger log = LogManager.getLogger();

    private final PasswordEncoder passwordEncoder;

    public UserIdentityFixturesLoader(String resourcePath, EntityManager entityManager,
                                      PasswordEncoder passwordEncoder)
        throws JAXBException {
        super(resourcePath, entityManager, UserIdentity.class);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Unmarshaller setUpUnmarshaller() throws JAXBException {
        final JAXBContext ctx = JAXBContext.newInstance(UserIdentity.class, UserIdentitySet.class);
        return ctx.createUnmarshaller();
    }

    @Override
    public void readFromResourceAndPersist() throws JAXBException {
        final InputStream resourceInputStream = getResourceInputStream(resourcePath);
        final UserIdentitySet userIdentitySet = (UserIdentitySet) unmarshaller.unmarshal(resourceInputStream);
        final List<UserIdentity> userIdentityList = userIdentitySet.getUserIdentities();

        log.info("Parsed resource [{}] size [{}]", resourcePath, userIdentityList.size());

        for (final UserIdentity userIdentity : userIdentityList) {
            final String hardcodedPassword = userIdentity.getPassword();
            final String encodedPassword = passwordEncoder.encode(hardcodedPassword);
            userIdentity.setPassword(encodedPassword);
            save(userIdentity);
        }
    }
}
