package ro.eu.documentimporter.inputprocessor;

import java.util.Map;

import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import ro.eu.documentimporter.repository.model.RepositoryDocument;
import ro.eu.documentimporter.repository.model.RepositoryMetadata;

@Component
public class RepositoryDocumentConvertor {

	public RepositoryDocument convert(Map<String, RepositoryMetadata> csvHeader, CSVRecord csvRecord) {
		// TODO Auto-generated method stub
		return null;
	}

}