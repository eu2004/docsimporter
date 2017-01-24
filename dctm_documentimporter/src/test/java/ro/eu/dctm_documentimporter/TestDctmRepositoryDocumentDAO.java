package ro.eu.dctm_documentimporter;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.documentum.com.DfClientX;

import ro.eu.documentimporter.repository.RepositoryDocumentDAO;
import ro.eu.documentimporter.repository.model.RepositoryEntityIdAttribute;
import ro.eu.documentimporter.repository.model.RepositoryMetadata;
import ro.eu.documentimporter.repository.model.RepositoryMetadataType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestDctmApplicationConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class TestDctmRepositoryDocumentDAO {

	@Autowired
	private RepositoryDocumentDAO dctmRepositoryDocumentDAO;

	@Test
	public void testGetDocumentByCriteria() throws Exception {
		String expectedName = "config_5.1.dtd";
		String criteria = "dm_document where object_name='" + expectedName + "'";
		DctmDocument document = (DctmDocument) dctmRepositoryDocumentDAO.getDocumentByCriteria(criteria);
		Assert.assertTrue(document != null);
		Assert.assertTrue(expectedName.equals(document.getObjectName()));
	}

	@Test
	public void testGetDocumentById() throws Exception {
		DctmDocument newDocument = new DctmDocument();
		newDocument.setObjectName("test_" + System.currentTimeMillis());
		newDocument.setType("dm_document");
		DctmDocument document = (DctmDocument) dctmRepositoryDocumentDAO.createDocument(newDocument);
		String expectedId = document.getId();
		
		RepositoryEntityIdAttribute id = new RepositoryEntityIdAttribute();
		id.setValue(expectedId);
		RepositoryMetadata metadata = new RepositoryMetadata();
		metadata.setName("r_object_id");
		metadata.setType(RepositoryMetadataType.STRING);
		id.setMetadata(metadata);
		document = (DctmDocument) dctmRepositoryDocumentDAO.getDocumentById(id);
		Assert.assertTrue(document != null);
		Assert.assertTrue(expectedId.equals(document.getId()));
	}

	@Test
	public void testCreateDocument() throws Exception {
		DctmDocument newDocument = new DctmDocument();
		newDocument.setObjectName("test_" + System.currentTimeMillis());
		newDocument.setType("dm_document");
		DctmDocument document = (DctmDocument) dctmRepositoryDocumentDAO.createDocument(newDocument);
		Assert.assertTrue(document != null);
		Assert.assertTrue(document.getObjectName().equals(newDocument.getObjectName()));
		Assert.assertTrue(new DfClientX().getId(document.getId()).isObjectId());
	}

	@Test
	public void testCreateDocumentNewVersion() throws Exception {
		DctmDocument newDocument = new DctmDocument();
		newDocument.setObjectName("test_" + System.currentTimeMillis());
		newDocument.setType("dm_document");
		DctmDocument document = (DctmDocument) dctmRepositoryDocumentDAO.createDocument(newDocument);
		
		document.setObjectName("new version of " + document.getObjectName());
		DctmDocument documentNewVersion = (DctmDocument) dctmRepositoryDocumentDAO.createDocumentNewVersion(document);
		Assert.assertTrue(documentNewVersion != null);
		Assert.assertTrue(documentNewVersion.getObjectName().equals(document.getObjectName()));
		Assert.assertFalse(documentNewVersion.getId().equals(document.getId()));
		Assert.assertFalse(Arrays.equals(documentNewVersion.getVersionAsStrings(), document.getVersionAsStrings()));
	}

	@Test
	public void testReplaceDocument() throws Exception {
		DctmDocument newDocument = new DctmDocument();
		newDocument.setObjectName("test_" + System.currentTimeMillis());
		newDocument.setType("dm_document");
		DctmDocument document = (DctmDocument) dctmRepositoryDocumentDAO.createDocument(newDocument);
		
		document.setFindCriteria("dm_document where object_name='" + newDocument.getObjectName() + "'");
		document.setObjectName("replacement document " + document.getObjectName());
		DctmDocument replacementDocument = (DctmDocument) dctmRepositoryDocumentDAO.replaceDocument(document);
		Assert.assertTrue(replacementDocument != null);
		Assert.assertTrue(replacementDocument.getObjectName().equals(document.getObjectName()));
		Assert.assertTrue(dctmRepositoryDocumentDAO.getDocumentById(document.getIdAttributeValue()) == null);
	}
	
	@Test
	public void testUpdateDocument() throws Exception {
		DctmDocument newDocument = new DctmDocument();
		newDocument.setObjectName("test_" + System.currentTimeMillis());
		newDocument.setType("dm_document");
		DctmDocument document = (DctmDocument) dctmRepositoryDocumentDAO.createDocument(newDocument);
		
		document.setObjectName("updated document " + document.getObjectName());
		DctmDocument updatedDocument = (DctmDocument) dctmRepositoryDocumentDAO.updateDocument(document);
		Assert.assertTrue(updatedDocument != null);
		Assert.assertTrue(updatedDocument.getObjectName().equals(document.getObjectName()));
	}
}
