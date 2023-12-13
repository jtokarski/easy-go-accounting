package org.defendev.easygo.devops.dbfixture.loader;

import jakarta.persistence.EntityManager;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.fixture.loader.FixturesLoader;
import org.defendev.easygo.devops.dbfixture.wrapper.DocumentSet;
import org.defendev.easygo.domain.fa.model.Document;

import java.io.InputStream;
import java.util.List;



public class DocumentFixturesLoader extends FixturesLoader<Document, Long> {

    private static final Logger log = LogManager.getLogger();

    public DocumentFixturesLoader(String resourcePath, EntityManager entityManager)
        throws JAXBException {
        super(resourcePath, entityManager, Document.class);
    }

    @Override
    public Unmarshaller setUpUnmarshaller() throws JAXBException {
        final JAXBContext ctx = JAXBContext.newInstance(Document.class, DocumentSet.class);
        return ctx.createUnmarshaller();
    }

    @Override
    public void readFromResourceAndPersist() throws JAXBException {
        final InputStream resourceInputStream = getResourceInputStream(resourcePath);
        final DocumentSet documentSet = (DocumentSet) unmarshaller.unmarshal(resourceInputStream);
        final List<Document> documentList = documentSet.getDocuments();

        log.info("Parsed resource [{}] size [{}]", resourcePath, documentList.size());

        for(final Document document : documentList) {
            save(document);
        }
    }
}
