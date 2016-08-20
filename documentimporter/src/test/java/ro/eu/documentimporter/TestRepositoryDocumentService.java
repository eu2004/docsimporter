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
public class TestRepositoryDocumentService {
	private static final Logger logger = Logger.getLogger(TestRepositoryDocumentService.class);
	
	@Autowired
	private RepositoryDocumentService repositoryDocumentService;
	
	@Test
	public void testImportDocumentExistance() {
		try {
			Method method = repositoryDocumentService.getClass().getMethod("importDocument", Document.class);
			Assert.assertTrue(method != null);
			Assert.assertEquals(method.getReturnType(), RepositoryEntityIdAttribute.class);
		} catch (Exception e) {
			logger.error("Error checking method existance", e);
			Assert.fail(e.getMessage());
		}
	}
}
