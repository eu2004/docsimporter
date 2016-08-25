package ro.eu.alfresco_documentimporter;

import ro.eu.documentimporter.repository.RepositoryDocumentDAO;
import ro.eu.documentimporter.repository.model.RepositoryDocument;
import ro.eu.documentimporter.repository.model.RepositoryEntityIdAttribute;

public class AlfrescoRepositoryDocumentDAO implements RepositoryDocumentDAO{

	@Override
	public RepositoryDocument getDocumentByCriteria(String findCriteria) {
		return null;
	}

	@Override
	public RepositoryDocument getDocumentById(RepositoryEntityIdAttribute id) {
		return null;
	}

	@Override
	public RepositoryDocument createDocument(RepositoryDocument document) {
		return null;
	}

	@Override
	public RepositoryDocument createDocumentNewVersion(RepositoryDocument document) {
		return null;
	}

	@Override
	public RepositoryDocument updateDocument(RepositoryDocument document) {
		return null;
	}

	@Override
	public RepositoryDocument replaceDocument(RepositoryDocument document) {
		return null;
	}

}