package ro.eu.dctm_documentimporter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import ro.eu.documentimporter.repository.RepositoryDocumentDAO;
import ro.eu.documentimporter.repository.model.RepositoryEntityAttribute;
import ro.eu.documentimporter.repository.model.RepositoryMetadata;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestDctmApplicationConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class TestDctmCSVRowParserCallback {
	@Autowired
	private DctmCSVRowParserCallback callback;

	@Autowired
	private RepositoryDocumentDAO dctmRepositoryDocumentDAO;
	
	@Test
	public void testProcessRepositoryDocumentCreateNew() {
		// given
		DctmDocument document = new DctmDocument();
		document.setObjectName("test_" + System.currentTimeMillis());
		document.setType("dm_document");

		// when
		callback.processRepositoryDocument(document);

		// then
		String findCriteria = "dm_document where object_name='" + document.getObjectName() + "'";
		DctmDocument savedDocument = (DctmDocument) dctmRepositoryDocumentDAO.getDocumentByCriteria(findCriteria);
		Assert.assertTrue(savedDocument != null);
		Assert.assertEquals(document.getObjectName(), savedDocument.getObjectName());
	}
	
	@Test
	public void testProcessRepositoryDocumentAlreadyExistsButWithNoAction() {
		// given
		DctmDocument document = new DctmDocument();
		document.setObjectName("test_" + System.currentTimeMillis());
		document.setType("dm_document");
		String findCriteria = "dm_document where object_name='" + document.getObjectName() + "'";
		document.setFindCriteria(findCriteria);

		// when
		callback.processRepositoryDocument(document);
		RepositoryEntityAttribute subjectAttribute = new RepositoryEntityAttribute();
		RepositoryMetadata subjectAttributeDefinition = new RepositoryMetadata();
		subjectAttributeDefinition.setName("subject");
		subjectAttribute.setMetadata(subjectAttributeDefinition );
		subjectAttribute.setValue("subject_" + document.getObjectName());
		document.setAttributeValue(subjectAttribute );
		callback.processRepositoryDocument(document);

		// then
		findCriteria = "dm_document where object_name='" + document.getObjectName() + "' and subject='" + subjectAttribute.getValue() + "'";
		DctmDocument savedDocument = (DctmDocument) dctmRepositoryDocumentDAO.getDocumentByCriteria(findCriteria);
		Assert.assertTrue(savedDocument == null);
	}
}
