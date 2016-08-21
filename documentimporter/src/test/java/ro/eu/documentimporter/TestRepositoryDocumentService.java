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

import ro.eu.documentimporter.DocumentImporterAppConfiguration.ExistingDocumentImporterActions;
import ro.eu.documentimporter.repository.RepositoryDocumentDAO;
import ro.eu.documentimporter.repository.RepositoryDocumentService;
import ro.eu.documentimporter.repository.model.Document;
import ro.eu.documentimporter.repository.model.RepositoryEntityIdAttribute;
import ro.eu.documentimporter.repository.model.RepositoryMetadata;
import ro.eu.documentimporter.repository.model.RepositoryMetadataType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationSpringConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class TestRepositoryDocumentService {

	@Autowired
	private RepositoryDocumentService mockedRepositoryDocumentService;

	@Before
	public void setup() {
		reset(mockedRepositoryDocumentService.getRepositoryDocumentDAO());
	}

	@Test
	public void testImportDocumentWithCreateNew() {
		// given
		final Document inputDoc = new Document();

		final Document expectedDoc = new Document();
		RepositoryEntityIdAttribute id = new RepositoryEntityIdAttribute();
		id.setValue("00000000000000000000");
		RepositoryMetadata metadata = new RepositoryMetadata();
		metadata.setName("r_object_id");
		metadata.setType(RepositoryMetadataType.STRING);
		id.setMetadata(metadata);
		expectedDoc.setId(id);

		RepositoryDocumentDAO repositoryDAO = mockedRepositoryDocumentService.getRepositoryDocumentDAO();
		when(repositoryDAO.getDocumentByCriteria(anyString())).thenReturn(null);
		when(repositoryDAO.createDocument(inputDoc)).thenReturn(expectedDoc);
		// when
		RepositoryEntityIdAttribute docId = mockedRepositoryDocumentService.importDocument(inputDoc);
		// then
		Assert.assertNotNull(docId);
		Assert.assertTrue(expectedDoc.getId().getValue().equals(docId.getValue()));
		verify(repositoryDAO, times(1)).createDocument(inputDoc);
		verify(repositoryDAO, times(1)).getDocumentByCriteria(anyString());
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), never())
				.createDocumentNewVersion(any(Document.class));
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), never())
				.replaceDocument(any(Document.class));
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), never()).updateDocument(any(Document.class));
	}

	@Test
	public void testImportDocumentWithIgnore() {
		// given
		when(mockedRepositoryDocumentService.getRepositoryDocumentDAO().getDocumentByCriteria(anyString()))
				.thenReturn(new Document());
		when(mockedRepositoryDocumentService.getApplicationConfiguration().getImporterActionInCaseExists())
				.thenReturn(ExistingDocumentImporterActions.IGNORE);
		// when
		RepositoryEntityIdAttribute docId = mockedRepositoryDocumentService.importDocument(new Document());
		// then
		Assert.assertNull(docId);
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), times(1)).getDocumentByCriteria(anyString());
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), never())
				.createDocumentNewVersion(any(Document.class));
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), never())
				.replaceDocument(any(Document.class));
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), never()).updateDocument(any(Document.class));
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), never()).createDocument(any(Document.class));
	}

	@Test
	public void testImportDocumentWithVersion() {
		// given
		final Document inputDoc = new Document();
		final Document expectedDoc = new Document();
		RepositoryEntityIdAttribute id = new RepositoryEntityIdAttribute();
		id.setValue("00000000000000000000");
		RepositoryMetadata metadata = new RepositoryMetadata();
		metadata.setName("r_object_id");
		metadata.setType(RepositoryMetadataType.STRING);
		id.setMetadata(metadata);
		expectedDoc.setId(id);

		when(mockedRepositoryDocumentService.getRepositoryDocumentDAO().getDocumentByCriteria(anyString()))
				.thenReturn(inputDoc);
		when(mockedRepositoryDocumentService.getRepositoryDocumentDAO().createDocumentNewVersion(inputDoc))
				.thenReturn(expectedDoc);
		when(mockedRepositoryDocumentService.getApplicationConfiguration().getImporterActionInCaseExists())
				.thenReturn(ExistingDocumentImporterActions.VERSION);
		// when
		RepositoryEntityIdAttribute docId = mockedRepositoryDocumentService.importDocument(inputDoc);
		// then
		Assert.assertNotNull(docId);
		Assert.assertTrue(expectedDoc.getId().getValue().equals(docId.getValue()));
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), times(1)).createDocumentNewVersion(inputDoc);
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), times(1)).getDocumentByCriteria(anyString());
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), never())
				.replaceDocument(any(Document.class));
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), never()).updateDocument(any(Document.class));
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), never()).createDocument(any(Document.class));
	}

	@Test
	public void testImportDocumentWithUpdate() {
		// given
		final Document inputDoc = new Document();
		final Document expectedDoc = new Document();
		RepositoryEntityIdAttribute id = new RepositoryEntityIdAttribute();
		id.setValue("00000000000000000000");
		RepositoryMetadata metadata = new RepositoryMetadata();
		metadata.setName("r_object_id");
		metadata.setType(RepositoryMetadataType.STRING);
		id.setMetadata(metadata);
		expectedDoc.setId(id);

		when(mockedRepositoryDocumentService.getRepositoryDocumentDAO().getDocumentByCriteria(anyString()))
				.thenReturn(inputDoc);
		when(mockedRepositoryDocumentService.getRepositoryDocumentDAO().updateDocument(inputDoc))
				.thenReturn(expectedDoc);
		when(mockedRepositoryDocumentService.getApplicationConfiguration().getImporterActionInCaseExists())
				.thenReturn(ExistingDocumentImporterActions.UPDATE);
		// when
		RepositoryEntityIdAttribute docId = mockedRepositoryDocumentService.importDocument(inputDoc);
		// then
		Assert.assertNotNull(docId);
		Assert.assertTrue(expectedDoc.getId().getValue().equals(docId.getValue()));
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), times(1)).getDocumentByCriteria(anyString());
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), times(1)).updateDocument(inputDoc);
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), never())
				.createDocumentNewVersion(any(Document.class));
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), never())
				.replaceDocument(any(Document.class));
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), never()).createDocument(any(Document.class));
	}

	@Test
	public void testImportDocumentWithReplace() {
		// given
		final Document inputDoc = new Document();
		final Document expectedDoc = new Document();
		RepositoryEntityIdAttribute id = new RepositoryEntityIdAttribute();
		id.setValue("00000000000000000000");
		RepositoryMetadata metadata = new RepositoryMetadata();
		metadata.setName("r_object_id");
		metadata.setType(RepositoryMetadataType.STRING);
		id.setMetadata(metadata);
		expectedDoc.setId(id);

		when(mockedRepositoryDocumentService.getRepositoryDocumentDAO().getDocumentByCriteria(anyString()))
				.thenReturn(inputDoc);
		when(mockedRepositoryDocumentService.getRepositoryDocumentDAO().replaceDocument(inputDoc))
				.thenReturn(expectedDoc);
		when(mockedRepositoryDocumentService.getApplicationConfiguration().getImporterActionInCaseExists())
				.thenReturn(ExistingDocumentImporterActions.REPLACE);
		// when
		RepositoryEntityIdAttribute docId = mockedRepositoryDocumentService.importDocument(inputDoc);
		// then
		Assert.assertNotNull(docId);
		Assert.assertTrue(expectedDoc.getId().getValue().equals(docId.getValue()));
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), times(1)).getDocumentByCriteria(anyString());
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), times(1)).replaceDocument(inputDoc);
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), never())
				.createDocumentNewVersion(any(Document.class));
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), never()).updateDocument(any(Document.class));
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), never()).createDocument(any(Document.class));
	}

	@Test
	public void testImportDocumentWithNoAction() {
		// given
		final Document inputDoc = new Document();
		final Document expectedDoc = new Document();
		RepositoryEntityIdAttribute id = new RepositoryEntityIdAttribute();
		id.setValue("00000000000000000000");
		RepositoryMetadata metadata = new RepositoryMetadata();
		metadata.setName("r_object_id");
		metadata.setType(RepositoryMetadataType.STRING);
		id.setMetadata(metadata);
		expectedDoc.setId(id);

		when(mockedRepositoryDocumentService.getRepositoryDocumentDAO().getDocumentByCriteria(anyString()))
				.thenReturn(inputDoc);
		when(mockedRepositoryDocumentService.getRepositoryDocumentDAO().updateDocument(inputDoc))
				.thenReturn(expectedDoc);
		when(mockedRepositoryDocumentService.getApplicationConfiguration().getImporterActionInCaseExists())
				.thenReturn(null);
		// when
		RepositoryEntityIdAttribute docId = mockedRepositoryDocumentService.importDocument(inputDoc);
		// then
		Assert.assertNull(docId);
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), times(1)).getDocumentByCriteria(anyString());
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), never())
				.createDocumentNewVersion(any(Document.class));
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), never())
				.replaceDocument(any(Document.class));
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), never()).updateDocument(any(Document.class));
		verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), never()).createDocument(any(Document.class));
	}
}
