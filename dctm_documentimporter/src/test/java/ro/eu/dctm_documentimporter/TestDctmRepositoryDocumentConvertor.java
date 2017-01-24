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
import ro.eu.documentimporter.repository.model.RepositoryMetadataType;

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
		csvRecord.put("r_object_type", "dm_document");//string
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		Date r_creation_date = new Date();
		csvRecord.put("r_creation_date", simpleDateFormat.format(r_creation_date));//date
		csvRecord.put("group_permit", "1");//integer
		csvRecord.put("r_immutable_flag", "true");//boolean
		csvRecord.put("r_full_content_size", "1000456890");//double

		// when
		RepositoryDocument document = dctmRepositoryDocumentConvertor.convert(csvRecord);

		// then
		Assert.assertNotNull(document);
		for (Entry<String, String> attribute : csvRecord.entrySet()) {
			if ("r_object_type".equals(attribute.getKey())) {
				Assert.assertEquals(attribute.getValue(), document.getValue(attribute.getKey()).toString());
				Assert.assertTrue(document.getAttributeValue(attribute.getKey()).getMetadata().getType()
						.equals(RepositoryMetadataType.STRING));
			}else if ("r_creation_date".equals(attribute.getKey())) {
				Assert.assertEquals(simpleDateFormat.format(r_creation_date),
						simpleDateFormat.format((Date) document.getValue(attribute.getKey())));
				Assert.assertTrue(document.getAttributeValue(attribute.getKey()).getMetadata().getType()
						.equals(RepositoryMetadataType.DATE));
			}else if ("group_permit".equals(attribute.getKey())) {
				Assert.assertEquals(1, document.getValue(attribute.getKey()));
				Assert.assertTrue(document.getAttributeValue(attribute.getKey()).getMetadata().getType()
						.equals(RepositoryMetadataType.INTEGER));
			}else if ("r_immutable_flag".equals(attribute.getKey())) {
				Assert.assertEquals(true, document.getValue(attribute.getKey()));
				Assert.assertTrue(document.getAttributeValue(attribute.getKey()).getMetadata().getType()
						.equals(RepositoryMetadataType.BOOLEAN));
			}else if ("r_full_content_size".equals(attribute.getKey())) {
				Assert.assertEquals(new Double(1000456890), (Double)document.getValue(attribute.getKey()));
				Assert.assertTrue(document.getAttributeValue(attribute.getKey()).getMetadata().getType()
						.equals(RepositoryMetadataType.DOUBLE));
			}else {
				Assert.assertEquals(attribute.getValue(), document.getValue(attribute.getKey()).toString());
			}
		}
	}
}
