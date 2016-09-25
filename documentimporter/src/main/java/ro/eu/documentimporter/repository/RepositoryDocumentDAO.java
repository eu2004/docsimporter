package ro.eu.documentimporter.repository;

import ro.eu.documentimporter.repository.model.RepositoryDocument;
import ro.eu.documentimporter.repository.model.RepositoryEntityIdAttribute;

public interface RepositoryDocumentDAO {
	public RepositoryDocument getDocumentByCriteria(String criteria) throws RepositoryException;

	public RepositoryDocument getDocumentById(RepositoryEntityIdAttribute id) throws RepositoryException;
	
	public RepositoryDocument createDocument(RepositoryDocument document) throws RepositoryException;
	
	public RepositoryDocument createDocumentNewVersion(RepositoryDocument document) throws RepositoryException;
	
	public RepositoryDocument updateDocument(RepositoryDocument document) throws RepositoryException;

	public RepositoryDocument replaceDocument(RepositoryDocument document) throws RepositoryException;
}
