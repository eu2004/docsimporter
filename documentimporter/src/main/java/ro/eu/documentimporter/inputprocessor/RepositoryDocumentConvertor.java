package ro.eu.documentimporter.inputprocessor;

import java.util.Map;

import ro.eu.documentimporter.repository.RepositoryException;
import ro.eu.documentimporter.repository.model.RepositoryDocument;

public interface RepositoryDocumentConvertor {

	public RepositoryDocument convert(Map<String, String> csvRecord) throws RepositoryException ;

}