package ro.eu.dctm_documentimporter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import ro.eu.documentimporter.inputprocessor.CSVInputParser;
import ro.eu.documentimporter.repository.RepositoryDocumentDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestDctmApplicationConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class TestDctmCSVInputParser {

	@Autowired
	private CSVInputParser dctmCSVInputParser;

	@Autowired
	private RepositoryDocumentDAO dctmRepositoryDocumentDAO;

	@Test
	public void testParseCSV_createNewObject() throws IOException {
		//given
		long now = System.currentTimeMillis();
		String objectName = "test_parser_" + now;
		String title = now + "";
		String input = "object_name,title,r_object_type,$DUPLICATE_SEARCH_CRITERIA\n" + objectName + "," + title + ",dm_document,dm_document where object_name='$object_name'";
		Reader csvReader = new StringReader(input);
		
		//when
		dctmCSVInputParser.parseCSV(csvReader);
		
		//then
		DctmDocument document = (DctmDocument) dctmRepositoryDocumentDAO
				.getDocumentByCriteria("dm_document where object_name='" + objectName + "' and title='" + title + "'");
		Assert.assertTrue(document != null);
		Assert.assertTrue(document.getId() != null);
		Assert.assertTrue(objectName.equals(document.getObjectName()));
		Assert.assertTrue(title.equals(document.getValue("title")));

	}

}
