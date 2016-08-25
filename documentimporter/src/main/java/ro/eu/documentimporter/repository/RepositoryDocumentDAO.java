package ro.eu.documentimporter.repository;

import ro.eu.documentimporter.repository.model.RepositoryDocument;
import ro.eu.documentimporter.repository.model.RepositoryEntityIdAttribute;

public interface RepositoryDocumentDAO {
	public RepositoryDocument getDocumentByCriteria(String criteria);

	public RepositoryDocument getDocumentById(RepositoryEntityIdAttribute id);
	
	public RepositoryDocument createDocument(RepositoryDocument document);
	
	public RepositoryDocument createDocumentNewVersion(RepositoryDocument document);
	
	public RepositoryDocument updateDocument(RepositoryDocument document);

	public RepositoryDocument replaceDocument(RepositoryDocument document);
}
