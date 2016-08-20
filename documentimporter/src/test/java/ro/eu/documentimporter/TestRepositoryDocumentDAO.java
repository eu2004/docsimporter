package ro.eu.documentimporter;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import ro.eu.documentimporter.repository.RepositoryDocumentDAO;
import ro.eu.documentimporter.repository.model.Document;
import ro.eu.documentimporter.repository.model.RepositoryEntityIdAttribute;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationSpringConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class TestRepositoryDocumentDAO {
	private static final Logger logger = Logger.getLogger(TestRepositoryDocumentDAO.class);

	@Autowired
	private RepositoryDocumentDAO mokedRepositoryDocumentDAO;

	@Test
	public void testDocumentExists() {
		try {
			Method method = mokedRepositoryDocumentDAO.getClass().getMethod("documentExists", String.class);
			Assert.assertTrue(method != null);
			Assert.assertEquals(Document.class, method.getReturnType());
		} catch (Exception e) {
			logger.error("Error checking method existance", e);
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testGetDocumentById() {
		try {
			Method methodToExecute = mokedRepositoryDocumentDAO.getClass().getMethod("getDocumentById",
					RepositoryEntityIdAttribute.class);
			Assert.assertTrue(methodToExecute != null);
			Assert.assertEquals(Document.class, methodToExecute.getReturnType());
		} catch (Exception e) {
			logger.error("Error checking method existance", e);
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testCreateDocument() {
		try {
			Method methodToExecute = mokedRepositoryDocumentDAO.getClass().getMethod("createDocument",
					Document.class);
			Assert.assertTrue(methodToExecute != null);
			Assert.assertEquals(Document.class, methodToExecute.getReturnType());
		} catch (Exception e) {
			logger.error("Error checking method existance", e);
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testCreateDocumentNewVersion() {
		try {
			Method methodToExecute = mokedRepositoryDocumentDAO.getClass().getMethod("createDocumentNewVersion",
					Document.class);
			Assert.assertTrue(methodToExecute != null);
			Assert.assertEquals(Document.class, methodToExecute.getReturnType());
		} catch (Exception e) {
			logger.error("Error checking method existance", e);
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testUpdateDocument() {
		try {
			Method methodToExecute = mokedRepositoryDocumentDAO.getClass().getMethod("updateDocument",
					Document.class);
			Assert.assertTrue(methodToExecute != null);
			Assert.assertEquals(Document.class, methodToExecute.getReturnType());
		} catch (Exception e) {
			logger.error("Error checking method existance", e);
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testReplaceDocument() {
		try {
			Method methodToExecute = mokedRepositoryDocumentDAO.getClass().getMethod("replaceDocument",
					Document.class);
			Assert.assertTrue(methodToExecute != null);
			Assert.assertEquals(Document.class, methodToExecute.getReturnType());
		} catch (Exception e) {
			logger.error("Error checking method existance", e);
			Assert.fail(e.getMessage());
		}
	}
}
