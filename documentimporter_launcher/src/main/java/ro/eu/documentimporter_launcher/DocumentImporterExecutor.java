package ro.eu.documentimporter_launcher;

import java.io.IOException;
import java.io.Reader;

import org.apache.log4j.Logger;

import ro.eu.documentimporter.repository.RepositoryClient;
import ro.eu.documentimporter.repository.model.RepositoryDocument;

public class DocumentImporterExecutor {
	private static final Logger logger = Logger.getLogger(DocumentImporterExecutor.class);

	private RepositoryClient repositoryClient;

	public RepositoryClient getRepositoryClient() {
		return repositoryClient;
	}

	public void setRepositoryClient(RepositoryClient repositoryClient) {
		this.repositoryClient = repositoryClient;
	}

	public void execute() {
		RepositoryDocument document = new RepositoryDocument();
		Reader csvReader = getCsvReader();
		try {
			repositoryClient.getCSVInputParser().parseCSV(csvReader);
		} catch (IOException e) {
			logger.error("Error executing importing " + e.getMessage(), e);
		}
	}

	private Reader getCsvReader() {
		// TODO Auto-generated method stub
		return null;
	}
}
