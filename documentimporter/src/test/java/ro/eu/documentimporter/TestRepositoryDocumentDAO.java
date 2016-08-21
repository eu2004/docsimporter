package ro.eu.documentimporter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

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
	@Autowired
	private RepositoryDocumentDAO mokedRepositoryDocumentDAO;

	@Test
	public void testMethodsNames() throws Exception {
		// given
		final List<String> expectedMethodsNames = Arrays.asList("getDocumentByCriteria", "getDocumentById",
				"createDocument", "createDocumentNewVersion", "updateDocument", "replaceDocument");
		final String expectedInterfaceName = "ro.eu.documentimporter.repository.RepositoryDocumentDAO";

		// when
		Class foundInterface = null;
		for (Class currentInterface : mokedRepositoryDocumentDAO.getClass().getInterfaces()) {
			if (currentInterface.getName().equals(expectedInterfaceName)) {
				foundInterface = currentInterface;
				break;
			}
		}

		// then
		Assert.assertTrue(foundInterface != null);
		Method[] declaredMethods = foundInterface.getDeclaredMethods();
		Assert.assertEquals(expectedMethodsNames.size(), declaredMethods.length);
		for (Method declaredMethod : declaredMethods) {
			Assert.assertTrue(expectedMethodsNames.contains(declaredMethod.getName()));
		}
	}

	@Test
	public void testGetDocumentByCriteria() throws Exception {
		Method method = mokedRepositoryDocumentDAO.getClass().getMethod("getDocumentByCriteria", String.class);
		Assert.assertTrue(method != null);
		Assert.assertEquals(Document.class, method.getReturnType());
	}

	@Test
	public void testGetDocumentById() throws Exception {
		Method methodToExecute = mokedRepositoryDocumentDAO.getClass().getMethod("getDocumentById",
				RepositoryEntityIdAttribute.class);
		Assert.assertTrue(methodToExecute != null);
		Assert.assertEquals(Document.class, methodToExecute.getReturnType());
	}

	@Test
	public void testCreateDocument() throws Exception {
		Method methodToExecute = mokedRepositoryDocumentDAO.getClass().getMethod("createDocument", Document.class);
		Assert.assertTrue(methodToExecute != null);
		Assert.assertEquals(Document.class, methodToExecute.getReturnType());
	}

	@Test
	public void testCreateDocumentNewVersion() throws Exception {
		Method methodToExecute = mokedRepositoryDocumentDAO.getClass().getMethod("createDocumentNewVersion",
				Document.class);
		Assert.assertTrue(methodToExecute != null);
		Assert.assertEquals(Document.class, methodToExecute.getReturnType());
	}

	@Test
	public void testUpdateDocument() throws Exception {
		Method methodToExecute = mokedRepositoryDocumentDAO.getClass().getMethod("updateDocument", Document.class);
		Assert.assertTrue(methodToExecute != null);
		Assert.assertEquals(Document.class, methodToExecute.getReturnType());
	}

	@Test
	public void testReplaceDocument() throws Exception {
		Method methodToExecute = mokedRepositoryDocumentDAO.getClass().getMethod("replaceDocument", Document.class);
		Assert.assertTrue(methodToExecute != null);
		Assert.assertEquals(Document.class, methodToExecute.getReturnType());
	}
}