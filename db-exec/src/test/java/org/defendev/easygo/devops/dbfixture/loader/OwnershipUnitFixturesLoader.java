package org.defendev.easygo.devops.dbfixture.loader;

import jakarta.persistence.EntityManager;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.fixture.loader.FixturesLoader;
import org.defendev.easygo.devops.dbfixture.wrapper.OwnershipUnitSet;
import org.defendev.easygo.domain.useridentity.model.OwnershipUnit;

import java.io.InputStream;
import java.util.List;



public class OwnershipUnitFixturesLoader extends FixturesLoader<OwnershipUnit, Long> {

    private static final Logger log = LogManager.getLogger();

    public OwnershipUnitFixturesLoader(String resourcePath, EntityManager entityManager) throws JAXBException {
        super(resourcePath, entityManager, OwnershipUnit.class);
    }

    @Override
    public Unmarshaller setUpUnmarshaller() throws JAXBException {
        final JAXBContext ctx = JAXBContext.newInstance(OwnershipUnit.class, OwnershipUnitSet.class);
        return ctx.createUnmarshaller();
    }

    @Override
    public void readFromResourceAndPersist() throws JAXBException {
        final InputStream resourceInputStream = getResourceInputStream(resourcePath);
        final OwnershipUnitSet ownershipUnitSet = (OwnershipUnitSet) unmarshaller.unmarshal(resourceInputStream);
        final List<OwnershipUnit> ownershipUnitList = ownershipUnitSet.getOwnershipUnits();

        log.info("Parsed resource [{}] size [{}]", resourcePath, ownershipUnitList.size());

        for (final OwnershipUnit ownershipUnit : ownershipUnitList) {
            save(ownershipUnit);
        }
    }
}
