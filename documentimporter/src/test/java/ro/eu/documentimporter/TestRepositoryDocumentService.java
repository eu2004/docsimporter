package ro.eu.documentimporter;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class TestRepositoryDocumentService {
	private static final Logger logger = Logger.getLogger(TestRepositoryDocumentService.class);

	@Autowired
	private RepositoryDocumentService mockedRepositoryDocumentService;

	@Test
	public void testImportDocumentExistance() {
		try {
			Method method = mockedRepositoryDocumentService.getClass().getMethod("importDocument", Document.class);
			Assert.assertTrue(method != null);
			Assert.assertEquals(method.getReturnType(), RepositoryEntityIdAttribute.class);
		} catch (Exception e) {
			logger.error("Error checking method existance", e);
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testImportDocument() {
		try {
			// given
			final Document inputDoc = new Document();

			final Document expectedDoc = new Document();
			RepositoryEntityIdAttribute id = new RepositoryEntityIdAttribute();
			id.setValue("00000000000000000000");
			RepositoryMetadata metadata = new RepositoryMetadata();
			metadata.setName("r_object_id");
			metadata.setType(RepositoryMetadataType.INTEGER);
			id.setMetadata(metadata);
			expectedDoc.setId(id);

			when(mockedRepositoryDocumentService.getRepositoryDocumentDAO().documentExists(anyString())).thenReturn(null);
			when(mockedRepositoryDocumentService.getRepositoryDocumentDAO().createDocument(inputDoc)).thenReturn(expectedDoc);
			// when
			RepositoryEntityIdAttribute docId = mockedRepositoryDocumentService.importDocument(inputDoc);
			// then
			Assert.assertNotNull(docId);
			Assert.assertTrue(expectedDoc.getId().getValue().equals(docId.getValue()));
			verify(mockedRepositoryDocumentService.getRepositoryDocumentDAO(), times(1)).createDocument(inputDoc);
		} catch (Exception e) {
			logger.error("Error checking method existance", e);
			Assert.fail(e.getMessage());
		}
	}
}
