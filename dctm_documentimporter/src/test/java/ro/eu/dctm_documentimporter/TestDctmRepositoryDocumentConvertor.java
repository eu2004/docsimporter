package ro.eu.dctm_documentimporter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import ro.eu.documentimporter.repository.model.RepositoryDocument;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestDctmApplicationConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class TestDctmRepositoryDocumentConvertor {
	@Autowired
	private DctmRepositoryDocumentConvertor dctmRepositoryDocumentConvertor;

	@Test
	public void testConvert() throws Exception {
		// given
		Map<String, String> csvRecord = new HashMap<>();
		csvRecord.put("r_object_id", "00000000000");
		csvRecord.put("r_object_type", "dm_document");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		Date r_creation_date = new Date();
		csvRecord.put("r_creation_date", simpleDateFormat.format(r_creation_date));

		// when
		RepositoryDocument document = dctmRepositoryDocumentConvertor.convert(csvRecord);

		// then
		Assert.assertNotNull(document);
		for (Entry<String, String> attribute : csvRecord.entrySet()) {
			if ("r_creation_date".equals(attribute.getKey())) {
				Assert.assertEquals(simpleDateFormat.format(r_creation_date),
						simpleDateFormat.format((Date) document.getValue(attribute.getKey())));
			} else {
				Assert.assertEquals(attribute.getValue(), document.getValue(attribute.getKey()).toString());
			}
		}
	}
}
