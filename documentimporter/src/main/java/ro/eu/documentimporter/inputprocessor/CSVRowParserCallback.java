package ro.eu.documentimporter.inputprocessor;

import ro.eu.documentimporter.repository.RepositoryException;
import ro.eu.documentimporter.repository.model.RepositoryDocument;

public interface CSVRowParserCallback {
	public void processRepositoryDocument(RepositoryDocument document) throws RepositoryException ;
}
