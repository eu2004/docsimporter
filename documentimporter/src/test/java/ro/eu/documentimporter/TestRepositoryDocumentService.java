package ro.eu.documentimporter;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import ro.eu.documentimporter.repository.RepositoryDocumentDAO;
import ro.eu.documentimporter.repository.RepositoryDocumentService;
import ro.eu.documentimporter.repository.RepositoryDocumentServiceException;
import ro.eu.documentimporter.repository.RepositoryException;
import ro.eu.documentimporter.repository.model.RepositoryDocument;
import ro.eu.documentimporter.repository.model.RepositoryEntityIdAttribute;
import ro.eu.documentimporter.repository.model.RepositoryMetadata;
import ro.eu.documentimporter.repository.model.RepositoryMetadataType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class TestRepositoryDocumentService {

	@Autowired
	private RepositoryDocumentService repositoryDocumentService;

	@Before
	public void setup() {
		reset(repositoryDocumentService.getRepositoryDocumentDAO());
	}

	@Test
	public void testImportDocumentWithCreateNew() throws RepositoryException {
		// given
		final RepositoryDocument inputDoc = new RepositoryDocument();
		inputDoc.setFindCriteria("find criteria");
		final RepositoryDocument expectedDoc = new RepositoryDocument();
		RepositoryEntityIdAttribute id = new RepositoryEntityIdAttribute();
		id.setValue("00000000000000000000");
		RepositoryMetadata metadata = new RepositoryMetadata();
		metadata.setName("r_object_id");
		metadata.setType(RepositoryMetadataType.STRING);
		id.setMetadata(metadata);
		expectedDoc.setId(id);

		RepositoryDocumentDAO repositoryDAO = repositoryDocumentService.getRepositoryDocumentDAO();
		when(repositoryDAO.getDocumentByCriteria(anyString())).thenReturn(null);
		when(repositoryDAO.createDocument(inputDoc)).thenReturn(expectedDoc);
		// when
		RepositoryEntityIdAttribute docId = repositoryDocumentService.importDocument(inputDoc);
		// then
		Assert.assertNotNull(docId);
		Assert.assertTrue(expectedDoc.getIdAttributeValue().getValue().equals(docId.getValue()));
		verify(repositoryDAO, times(1)).createDocument(inputDoc);
		verify(repositoryDAO, times(1)).getDocumentByCriteria(anyString());
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
				.createDocumentNewVersion(any(RepositoryDocument.class));
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
				.replaceDocument(any(RepositoryDocument.class));
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
				.updateDocument(any(RepositoryDocument.class));
	}

	@Test
	public void testImportDocumentWithIgnore() throws RepositoryException {
		// given
		final RepositoryDocument inputDoc = new RepositoryDocument();
		inputDoc.setFindCriteria("find criteria");
		when(repositoryDocumentService.getRepositoryDocumentDAO().getDocumentByCriteria(anyString()))
				.thenReturn(inputDoc);
		repositoryDocumentService.setImporterActionInCaseExistsStr("IGNORE");
		// when
		RepositoryEntityIdAttribute docId = repositoryDocumentService.importDocument(inputDoc);
		// then
		Assert.assertNull(docId);
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), times(1)).getDocumentByCriteria(anyString());
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
				.createDocumentNewVersion(any(RepositoryDocument.class));
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
				.replaceDocument(any(RepositoryDocument.class));
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
				.updateDocument(any(RepositoryDocument.class));
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
				.createDocument(any(RepositoryDocument.class));
	}

	@Test
	public void testImportDocumentWithVersion() throws RepositoryException {
		// given
		final RepositoryDocument inputDoc = new RepositoryDocument();
		inputDoc.setFindCriteria("find criteria");
		final RepositoryDocument expectedDoc = new RepositoryDocument();
		RepositoryEntityIdAttribute id = new RepositoryEntityIdAttribute();
		id.setValue("00000000000000000000");
		RepositoryMetadata metadata = new RepositoryMetadata();
		metadata.setName("r_object_id");
		metadata.setType(RepositoryMetadataType.STRING);
		id.setMetadata(metadata);
		expectedDoc.setId(id);

		when(repositoryDocumentService.getRepositoryDocumentDAO().getDocumentByCriteria(anyString()))
				.thenReturn(inputDoc);
		when(repositoryDocumentService.getRepositoryDocumentDAO().createDocumentNewVersion(inputDoc))
				.thenReturn(expectedDoc);
		repositoryDocumentService.setImporterActionInCaseExistsStr("VERSION");
		// when
		RepositoryEntityIdAttribute docId = repositoryDocumentService.importDocument(inputDoc);
		// then
		Assert.assertNotNull(docId);
		Assert.assertTrue(expectedDoc.getIdAttributeValue().getValue().equals(docId.getValue()));
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), times(1)).createDocumentNewVersion(inputDoc);
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), times(1)).getDocumentByCriteria(anyString());
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
				.replaceDocument(any(RepositoryDocument.class));
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
				.updateDocument(any(RepositoryDocument.class));
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
				.createDocument(any(RepositoryDocument.class));
	}

	@Test
	public void testImportDocumentWithUpdate() {
		// given
		final RepositoryDocument inputDoc = new RepositoryDocument();
		inputDoc.setFindCriteria("find criteria");
		final RepositoryDocument expectedDoc = new RepositoryDocument();
		RepositoryEntityIdAttribute id = new RepositoryEntityIdAttribute();
		id.setValue("00000000000000000000");
		RepositoryMetadata metadata = new RepositoryMetadata();
		metadata.setName("r_object_id");
		metadata.setType(RepositoryMetadataType.STRING);
		id.setMetadata(metadata);
		expectedDoc.setId(id);

		when(repositoryDocumentService.getRepositoryDocumentDAO().getDocumentByCriteria(anyString()))
				.thenReturn(inputDoc);
		when(repositoryDocumentService.getRepositoryDocumentDAO().updateDocument(inputDoc))
				.thenReturn(expectedDoc);
		repositoryDocumentService.setImporterActionInCaseExistsStr("UPDATE");
		// when
		RepositoryEntityIdAttribute docId = repositoryDocumentService.importDocument(inputDoc);
		// then
		Assert.assertNotNull(docId);
		Assert.assertTrue(expectedDoc.getIdAttributeValue().getValue().equals(docId.getValue()));
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), times(1)).getDocumentByCriteria(anyString());
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), times(1)).updateDocument(inputDoc);
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
				.createDocumentNewVersion(any(RepositoryDocument.class));
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
				.replaceDocument(any(RepositoryDocument.class));
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
				.createDocument(any(RepositoryDocument.class));
	}

	@Test
	public void testImportDocumentWithReplace() {
		// given
		final RepositoryDocument inputDoc = new RepositoryDocument();
		inputDoc.setFindCriteria("find criteria");
		final RepositoryDocument expectedDoc = new RepositoryDocument();
		RepositoryEntityIdAttribute id = new RepositoryEntityIdAttribute();
		id.setValue("00000000000000000000");
		RepositoryMetadata metadata = new RepositoryMetadata();
		metadata.setName("r_object_id");
		metadata.setType(RepositoryMetadataType.STRING);
		id.setMetadata(metadata);
		expectedDoc.setId(id);

		when(repositoryDocumentService.getRepositoryDocumentDAO().getDocumentByCriteria(anyString()))
				.thenReturn(inputDoc);
		when(repositoryDocumentService.getRepositoryDocumentDAO().replaceDocument(inputDoc))
				.thenReturn(expectedDoc);
		repositoryDocumentService.setImporterActionInCaseExistsStr("REPLACE");
		// when
		RepositoryEntityIdAttribute docId = repositoryDocumentService.importDocument(inputDoc);
		// then
		Assert.assertNotNull(docId);
		Assert.assertTrue(expectedDoc.getIdAttributeValue().getValue().equals(docId.getValue()));
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), times(1)).getDocumentByCriteria(anyString());
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), times(1)).replaceDocument(inputDoc);
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
				.createDocumentNewVersion(any(RepositoryDocument.class));
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
				.updateDocument(any(RepositoryDocument.class));
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
				.createDocument(any(RepositoryDocument.class));
	}

	@Test
	public void testImportDocumentWithNoAction() {
		// given
		final RepositoryDocument inputDoc = new RepositoryDocument();
		inputDoc.setFindCriteria("find criteria");
		final RepositoryDocument expectedDoc = new RepositoryDocument();
		RepositoryEntityIdAttribute id = new RepositoryEntityIdAttribute();
		id.setValue("00000000000000000000");
		RepositoryMetadata metadata = new RepositoryMetadata();
		metadata.setName("r_object_id");
		metadata.setType(RepositoryMetadataType.STRING);
		id.setMetadata(metadata);
		expectedDoc.setId(id);

		when(repositoryDocumentService.getRepositoryDocumentDAO().getDocumentByCriteria(anyString()))
				.thenReturn(inputDoc);
		when(repositoryDocumentService.getRepositoryDocumentDAO().updateDocument(inputDoc))
				.thenReturn(expectedDoc);
		repositoryDocumentService.setImporterActionInCaseExistsStr("");
		
		// when
		RepositoryEntityIdAttribute docId = repositoryDocumentService.importDocument(inputDoc);
		// then
		Assert.assertNull(docId);
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), times(1)).getDocumentByCriteria(anyString());
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
				.createDocumentNewVersion(any(RepositoryDocument.class));
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
				.replaceDocument(any(RepositoryDocument.class));
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
				.updateDocument(any(RepositoryDocument.class));
		verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
				.createDocument(any(RepositoryDocument.class));
	}

	@Test
	public void testImportDocumentWithError() {
		// given
		final RepositoryDocument inputDoc = new RepositoryDocument();
		inputDoc.setFindCriteria("find criteria");
		final RepositoryDocument expectedDoc = new RepositoryDocument();
		RepositoryEntityIdAttribute id = new RepositoryEntityIdAttribute();
		id.setValue("00000000000000000000");
		RepositoryMetadata metadata = new RepositoryMetadata();
		metadata.setName("r_object_id");
		metadata.setType(RepositoryMetadataType.STRING);
		id.setMetadata(metadata);
		expectedDoc.setId(id);

		when(repositoryDocumentService.getRepositoryDocumentDAO().getDocumentByCriteria(anyString()))
				.thenThrow(new RuntimeException("Known exception"));
		// when
		try {
			repositoryDocumentService.importDocument(inputDoc);
			Assert.fail("Test should have failed with a \"Known exception\"");
			// then
		} catch (Exception ex) {
			Assert.assertTrue(ex instanceof RepositoryDocumentServiceException);
			Assert.assertEquals("java.lang.RuntimeException: Known exception", ex.getMessage());

			verify(repositoryDocumentService.getRepositoryDocumentDAO(), times(1))
					.getDocumentByCriteria(anyString());
			verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
					.createDocumentNewVersion(any(RepositoryDocument.class));
			verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
					.replaceDocument(any(RepositoryDocument.class));
			verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
					.updateDocument(any(RepositoryDocument.class));
			verify(repositoryDocumentService.getRepositoryDocumentDAO(), never())
					.createDocument(any(RepositoryDocument.class));
		}

	}
}
