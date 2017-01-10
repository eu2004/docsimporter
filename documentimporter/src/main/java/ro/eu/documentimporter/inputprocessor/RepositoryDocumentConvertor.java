package ro.eu.documentimporter.inputprocessor;

import org.apache.commons.csv.CSVRecord;

import ro.eu.documentimporter.repository.RepositoryException;
import ro.eu.documentimporter.repository.model.RepositoryDocument;

public interface RepositoryDocumentConvertor {

	public RepositoryDocument convert(CSVRecord csvRecord) throws RepositoryException ;

}