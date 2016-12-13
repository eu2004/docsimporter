package ro.eu.dctm_documentimporter;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import ro.eu.documentimporter.inputprocessor.RepositoryMetadataConvertor;
import ro.eu.documentimporter.repository.model.RepositoryMetadata;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestDctmApplicationConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class TestDctmRepositoryMetadataConvertor {
	@Autowired
	private RepositoryMetadataConvertor dctmRepositoryMetadataConvertor;

	@Test
	public void testConvert() throws Exception {
		Map<String, Integer> headerMap = new HashMap<String, Integer>();
		headerMap.put("r_object_id", 1);
		Map<String, RepositoryMetadata> dctmHeader = dctmRepositoryMetadataConvertor.convert(headerMap);
		Assert.assertTrue(dctmHeader != null);
		Assert.assertEquals(headerMap.size(), dctmHeader.size());
	}
}
