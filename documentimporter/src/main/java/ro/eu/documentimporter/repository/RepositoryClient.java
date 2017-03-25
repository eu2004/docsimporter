package ro.eu.documentimporter.repository;

import org.springframework.beans.factory.annotation.Autowired;

import ro.eu.documentimporter.inputprocessor.CSVInputParser;

public abstract class RepositoryClient {
	@Autowired
	private RepositoryDocumentService repositoryDocumentService;
	
	public abstract RepositoryDocumentDAO getRepositoryDocumentDAO();
	
	public abstract CSVInputParser getCSVInputParser();
	
	public RepositoryDocumentService getRepositoryDocumentService() {
		return repositoryDocumentService;
	}

	public void setRepositoryDocumentService(RepositoryDocumentService repositoryDocumentService) {
		this.repositoryDocumentService = repositoryDocumentService;
	}
}
