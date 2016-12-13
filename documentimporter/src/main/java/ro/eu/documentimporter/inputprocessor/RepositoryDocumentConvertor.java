package ro.eu.documentimporter.inputprocessor;

import java.util.Map;

import org.apache.commons.csv.CSVRecord;

import ro.eu.documentimporter.repository.RepositoryException;
import ro.eu.documentimporter.repository.model.RepositoryDocument;
import ro.eu.documentimporter.repository.model.RepositoryMetadata;

public interface RepositoryDocumentConvertor {

	public RepositoryDocument convert(Map<String, RepositoryMetadata> csvHeader, CSVRecord csvRecord) throws RepositoryException ;

}