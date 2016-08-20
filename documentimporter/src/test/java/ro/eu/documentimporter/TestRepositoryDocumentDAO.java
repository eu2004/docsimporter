package docimporter.documentimporter;

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
public class TestRepositoryDocumentDAO {
	private static final Logger logger = Logger.getLogger(TestRepositoryDocumentDAO.class);

	@Autowired
	private RepositoryDocumentDAO mokedRepositoryDocumentDAO;

	@Test
	public void testDocumentExists() {
		try {
			Assert.assertTrue(mokedRepositoryDocumentDAO.getClass().getMethod("documentExists", String.class) != null);
		} catch (Exception e) {
			logger.error("Error checking method existance", e);
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testGetDocumentById() {
		try {
			Method getDocumentByIdMethod = mokedRepositoryDocumentDAO.getClass().getMethod("getDocumentById",
					RepositoryEntityIdAttribute.class);
			Assert.assertTrue(getDocumentByIdMethod != null);
			Assert.assertEquals(Document.class, getDocumentByIdMethod.getReturnType());
		} catch (Exception e) {
			logger.error("Error checking method existance", e);
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testCreateDocument() {
		try {
			Method getDocumentByIdMethod = mokedRepositoryDocumentDAO.getClass().getMethod("createDocument",
					Document.class);
			Assert.assertTrue(getDocumentByIdMethod != null);
			Assert.assertEquals(Document.class, getDocumentByIdMethod.getReturnType());
		} catch (Exception e) {
			logger.error("Error checking method existance", e);
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testCreateDocumentNewVersion() {
		try {
			Method getDocumentByIdMethod = mokedRepositoryDocumentDAO.getClass().getMethod("createDocumentNewVersion",
					Document.class);
			Assert.assertTrue(getDocumentByIdMethod != null);
			Assert.assertEquals(Document.class, getDocumentByIdMethod.getReturnType());
		} catch (Exception e) {
			logger.error("Error checking method existance", e);
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testUpdateDocument() {
		try {
			Method getDocumentByIdMethod = mokedRepositoryDocumentDAO.getClass().getMethod("updateDocument",
					Document.class);
			Assert.assertTrue(getDocumentByIdMethod != null);
			Assert.assertEquals(Document.class, getDocumentByIdMethod.getReturnType());
		} catch (Exception e) {
			logger.error("Error checking method existance", e);
			Assert.fail(e.getMessage());
		}
	}
}
