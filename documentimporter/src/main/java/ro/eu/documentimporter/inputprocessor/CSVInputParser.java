package ro.eu.documentimporter.inputprocessor;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CSVInputParser {
	@Autowired
	private CSVRowParserCallback rowParserCallback;
	@Autowired
	private RepositoryDocumentConvertor repositoryDocumentConvertor;

	public void parseCSV(Reader csvReader) throws IOException {
		try (CSVParser parser = new CSVParser(csvReader, CSVFormat.DEFAULT)) {
			Iterator<CSVRecord> csvRecordIterator = parser.iterator();
			// skip header
			if (csvRecordIterator.hasNext()) {
				csvRecordIterator.next();
			}

			// parse content
			while (csvRecordIterator.hasNext()) {
				rowParserCallback.processRepositoryDocument(
						repositoryDocumentConvertor.convert(csvRecordIterator.next()));
			}
		}
	}
}