package org.defendev.easygo.devops.dbfixture.loader;

import jakarta.persistence.EntityManager;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.fixture.loader.FixturesLoader;
import org.defendev.easygo.devops.dbfixture.wrapper.SourceDocumentSet;
import org.defendev.easygo.domain.fa.SourceDocument;

import java.io.InputStream;
import java.util.List;



public class SourceDocumentFixturesLoader extends FixturesLoader<SourceDocument, Long> {

    private static final Logger log = LogManager.getLogger();

    public SourceDocumentFixturesLoader(String resourcePath, EntityManager entityManager,
                                        Class<SourceDocument> entityClazz)
        throws JAXBException {
        super(resourcePath, entityManager, SourceDocument.class);
    }

    @Override
    public Unmarshaller setUpUnmarshaller() throws JAXBException {
        final JAXBContext ctx = JAXBContext.newInstance(SourceDocument.class, SourceDocumentSet.class);
        return ctx.createUnmarshaller();
    }

    @Override
    public void readFromResourceAndPersist() throws JAXBException {
        final InputStream resourceInputStream = getResourceInputStream(resourcePath);
        final SourceDocumentSet sourceDocumentSet = (SourceDocumentSet) unmarshaller.unmarshal(resourceInputStream);
        final List<SourceDocument> sourceDocumentList = sourceDocumentSet.getSourceDocuments();

        log.info("Parsed resource [{}] size [{}]", resourcePath, sourceDocumentList.size());

        for(final SourceDocument sourceDocument : sourceDocumentList) {
            save(sourceDocument);
        }
    }
}
