package ro.eu.documentimporter.repository;

import org.springframework.beans.factory.annotation.Autowired;

import ro.eu.documentimporter.inputprocessor.CSVInputParser;

public abstract class RepositoryClient {
	@Autowired
	private RepositoryDocumentService repositoryDocumentService;
	
	private String user;
	
	private String pass;
	
	private String repository;

	public abstract RepositoryDocumentDAO getRepositoryDocumentDAO();
	
	public abstract CSVInputParser getCSVInputParser();
	
	public RepositoryDocumentService getRepositoryDocumentService() {
		return repositoryDocumentService;
	}

	public void setRepositoryDocumentService(RepositoryDocumentService repositoryDocumentService) {
		this.repositoryDocumentService = repositoryDocumentService;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}
}
