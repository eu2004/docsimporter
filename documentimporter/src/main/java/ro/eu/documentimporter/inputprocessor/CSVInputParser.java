package ro.eu.documentimporter.inputprocessor;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public abstract class CSVInputParser {

	/**
	 * Parse the csv file with the following settings: withDelimiter(',')
	 * withQuote('"') withRecordSeparator("\r\n") withIgnoreEmptyLines(true)
	 * withHeader()
	 * 
	 * @param csvReader
	 * @throws IOException
	 */
	public void parseCSV(Reader csvReader) throws IOException {
		try (CSVParser parser = new CSVParser(csvReader, CSVFormat.DEFAULT.withHeader())) {
			Iterator<CSVRecord> csvRecordIterator = parser.iterator();
			// parse content
			while (csvRecordIterator.hasNext()) {
				getRowParserCallback().processRepositoryDocument(
						getRepositoryDocumentConvertor().convert(csvRecordIterator.next().toMap()));
			}
		}
	}

	public abstract CSVRowParserCallback getRowParserCallback();

	public abstract RepositoryDocumentConvertor getRepositoryDocumentConvertor();
}