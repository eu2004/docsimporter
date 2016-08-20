package ro.eu.documentimporter.repository;

import ro.eu.documentimporter.repository.model.Document;
import ro.eu.documentimporter.repository.model.RepositoryEntityIdAttribute;

public interface RepositoryDocumentDAO {
	public Document getDocumentByCriteria(String criteria);

	public Document getDocumentById(RepositoryEntityIdAttribute id);
	
	public Document createDocument(Document document);
	
	public Document createDocumentNewVersion(Document document);
	
	public Document updateDocument(Document document);

	public Document replaceDocument(Document document);
}
