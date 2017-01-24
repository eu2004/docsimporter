package ro.eu.documentimporter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ro.eu.documentimporter.inputprocessor.CSVInputParser;

@Configuration
@ComponentScan
@PropertySource("classpath:test-app.properties")
public class TestCSVInputParser {
	@Autowired
	private CSVInputParser csvInputParser;
	
	public void testParseCSV() throws IOException{
		String csvInput = "";
		Reader csvReader = new StringReader(csvInput);
		csvInputParser.parseCSV(csvReader);
		//TODO mock it
	}
}
