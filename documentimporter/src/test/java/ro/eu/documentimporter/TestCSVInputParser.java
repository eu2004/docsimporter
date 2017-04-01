package ro.eu.documentimporter;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import ro.eu.documentimporter.inputprocessor.CSVInputParser;
import ro.eu.documentimporter.repository.model.RepositoryDocument;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class TestCSVInputParser {
	@Autowired
	private CSVInputParser mockedCSVInputParser;
	
	@Test
	public void testParseCSV() throws IOException {
		String csvLines = "col1,col2,col3,col4,col5\n" +
						  "data1,data2,data3,data4,data5\n" +
						  "data1,data2,data3,data4,data5\n" +
						  "data1,data2,data3,data4,data5\n" +
						  "data1,data2,data3,data4,data5";
		Reader csvReader = new StringReader(csvLines);
		mockedCSVInputParser.parseCSV(csvReader);
		verify(mockedCSVInputParser.getRowParserCallback(), times(4)).processRepositoryDocument(Matchers.any(RepositoryDocument.class));
		verify(mockedCSVInputParser.getRepositoryDocumentConvertor(), times(4)).convert(Matchers.any(Map.class));
	}
	
}
