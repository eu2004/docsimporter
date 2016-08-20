package ro.eu.documentimporter;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.reset;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import ro.eu.documentimporter.ApplicationConfiguration.ExistingDocumentImporterActions;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class TestRepositoryDocumentService {
	private static final Logger logger = Logger.getLogger(TestRepositoryDocumentService.class);

	@Autowired
	private RepositoryDocumentService mockedRepositoryDocumentService;

	@Before
	public void setup() {
		reset(mockedRepositoryDocumentService.getRepositoryDocumentDAO());
	}

	@Test
	public void testImportDocumentWithCreateNew() {
		try {
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
			when(repositoryDAO.documentExists(anyString())).thenReturn(null);
			when(repositoryDAO.createDocument(inputDoc)).thenReturn(expectedDoc);
			// when
			RepositoryEntityIdAttribute docId = mockedRepositoryDocumentService.importDocument(inputDoc);
			// then
			Assert.assertNotNull(docId);
			Assert.assertTrue(expectedDoc.getId().getValue().equals(docId.getValue()));
			verify(repositoryDAO, times(1)).createDocument(inputDoc);
			verify(repositoryDAO, times(1)).documentExists(anyString());
		} catch (Exception e) {
			logger.error("Error checking method existance", e);
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testImportDocumentWithIgnore() {
		try {
			// given
			when(mockedRepositoryDocumentService.getRepositoryDocumentDAO().documentExists(anyString()))
					.thenReturn(new Document());
			when(mockedRepositoryDocumentService.getApplicationConfiguration().getImporterActionInCaseExists())
					.thenReturn(ExistingDocumentImporterActions.IGNORE);
			// when
			RepositoryEntityIdAttribute docId = mockedRepositoryDocumentService.importDocument(new Document());
			// then
			Assert.assertNull(docId);
			verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), times(1)).documentExists(anyString());
		} catch (Exception e) {
			logger.error("Error checking method existance", e);
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testImportDocumentWithVersion() {
		try {
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

			when(mockedRepositoryDocumentService.getRepositoryDocumentDAO().documentExists(anyString()))
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
			verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), times(1))
					.createDocumentNewVersion(inputDoc);
			verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), times(1)).documentExists(anyString());
		} catch (Exception e) {
			logger.error("Error checking method existance", e);
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testImportDocumentWithUpdate() {
		try {
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

			when(mockedRepositoryDocumentService.getRepositoryDocumentDAO().documentExists(anyString()))
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
			verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), times(1)).documentExists(anyString());
			verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), times(1)).updateDocument(inputDoc);
		} catch (Exception e) {
			logger.error("Error checking method existance", e);
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testImportDocumentWithNoAction() {
		try {
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
		
			when(mockedRepositoryDocumentService.getRepositoryDocumentDAO().documentExists(anyString()))
					.thenReturn(inputDoc);
			when(mockedRepositoryDocumentService.getRepositoryDocumentDAO().updateDocument(inputDoc))
					.thenReturn(expectedDoc);
			when(mockedRepositoryDocumentService.getApplicationConfiguration().getImporterActionInCaseExists())
					.thenReturn(null);
			// when
			RepositoryEntityIdAttribute docId = mockedRepositoryDocumentService.importDocument(inputDoc);
			// then
			Assert.assertNull(docId);
			verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), times(1)).documentExists(anyString());
		} catch (Exception e) {
			logger.error("Error checking method existance", e);
			Assert.fail(e.getMessage());
		}
	}
}
