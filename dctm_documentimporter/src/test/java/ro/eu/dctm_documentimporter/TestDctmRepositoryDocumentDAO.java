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
import ro.eu.documentimporter.repository.model.RepositoryDocument;
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
		RepositoryDocument document = dctmRepositoryDocumentDAO.getDocumentByCriteria(criteria);
		Assert.assertTrue(document != null);
		Assert.assertTrue(expectedName.equals(document.getObjectName()));
	}

	@Test
	public void testGetDocumentById() throws Exception {
		String expectedId = "0900000c80000430";
		RepositoryEntityIdAttribute id = new RepositoryEntityIdAttribute();
		id.setValue(expectedId);
		RepositoryMetadata metadata = new RepositoryMetadata();
		metadata.setName("r_object_id");
		metadata.setType(RepositoryMetadataType.STRING);
		id.setMetadata(metadata);
		RepositoryDocument document = dctmRepositoryDocumentDAO.getDocumentById(id);
		Assert.assertTrue(document != null);
		Assert.assertTrue(expectedId.equals(document.getId().getValue()));
	}

	@Test
	public void testCreateDocument() throws Exception {
		RepositoryDocument newDocument = new RepositoryDocument();
		newDocument.setObjectName("test_" + System.currentTimeMillis());
		newDocument.setType("dm_document");
		RepositoryDocument document = dctmRepositoryDocumentDAO.createDocument(newDocument);
		Assert.assertTrue(document != null);
		Assert.assertTrue(document.getObjectName().equals(newDocument.getObjectName()));
		Assert.assertTrue(new DfClientX().getId(document.getId().getValue()).isObjectId());
	}

	@Test
	public void testCreateDocumentNewVersion() throws Exception {
		RepositoryDocument newDocument = new RepositoryDocument();
		newDocument.setObjectName("test_" + System.currentTimeMillis());
		newDocument.setType("dm_document");
		RepositoryDocument document = dctmRepositoryDocumentDAO.createDocument(newDocument);
		
		document.setObjectName("new version of " + document.getObjectName());
		RepositoryDocument documentNewVersion = dctmRepositoryDocumentDAO.createDocumentNewVersion(document);
		Assert.assertTrue(documentNewVersion != null);
		Assert.assertTrue(documentNewVersion.getObjectName().equals(document.getObjectName()));
		Assert.assertFalse(documentNewVersion.getId().equals(document.getId()));
	}

	@Test
	public void testReplaceDocument() throws Exception {
		RepositoryDocument newDocument = new RepositoryDocument();
		newDocument.setObjectName("test_" + System.currentTimeMillis());
		newDocument.setType("dm_document");
		RepositoryDocument document = dctmRepositoryDocumentDAO.createDocument(newDocument);
		
		document.setFindCriteria("dm_document where object_name='" + newDocument.getObjectName() + "'");
		document.setObjectName("replacement document " + document.getObjectName());
		RepositoryDocument replacementDocument = dctmRepositoryDocumentDAO.replaceDocument(document);
		Assert.assertTrue(replacementDocument != null);
		Assert.assertTrue(replacementDocument.getObjectName().equals(document.getObjectName()));
		Assert.assertTrue(dctmRepositoryDocumentDAO.getDocumentById(document.getId()) == null);
	}
	
	@Test
	public void testUpdateDocument() throws Exception {
		RepositoryDocument newDocument = new RepositoryDocument();
		newDocument.setObjectName("test_" + System.currentTimeMillis());
		newDocument.setType("dm_document");
		RepositoryDocument document = dctmRepositoryDocumentDAO.createDocument(newDocument);
		
		document.setObjectName("updated document " + document.getObjectName());
		RepositoryDocument updatedDocument = dctmRepositoryDocumentDAO.updateDocument(document);
		Assert.assertTrue(updatedDocument != null);
		Assert.assertTrue(updatedDocument.getObjectName().equals(document.getObjectName()));
	}
}
