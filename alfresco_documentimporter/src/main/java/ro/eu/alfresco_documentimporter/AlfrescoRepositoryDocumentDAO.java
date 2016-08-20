package ro.eu.alfresco_documentimporter;

import ro.eu.documentimporter.repository.RepositoryDocumentDAO;
import ro.eu.documentimporter.repository.model.Document;
import ro.eu.documentimporter.repository.model.RepositoryEntityIdAttribute;

public class AlfrescoRepositoryDocumentDAO implements RepositoryDocumentDAO{

	@Override
	public Document documentExists(String findCriteria) {
		return null;
	}

	@Override
	public Document getDocumentById(RepositoryEntityIdAttribute id) {
		return null;
	}

	@Override
	public Document createDocument(Document document) {
		return null;
	}

	@Override
	public Document createDocumentNewVersion(Document document) {
		return null;
	}

	@Override
	public Document updateDocument(Document document) {
		return null;
	}

	@Override
	public Document replaceDocument(Document document) {
		return null;
	}

}