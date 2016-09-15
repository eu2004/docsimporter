package ro.eu.dctm_documentimporter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import ro.eu.documentimporter.repository.RepositoryDocumentDAO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestDctmApplicationConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class TestDctmRepositoryDocumentDAO {
	
	@Autowired
	private RepositoryDocumentDAO dctmRepositoryDocumentDAO;

	@Test
	public void testLoad() throws Exception {
		dctmRepositoryDocumentDAO.getDocumentByCriteria(null);
		Assert.assertTrue(true);
	}
}
