package ro.eu.documentimporter_launcher;

import java.io.IOException;
import java.io.Reader;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.eu.documentimporter.repository.RepositoryClient;
import ro.eu.documentimporter.repository.model.RepositoryDocument;

@Component
public class DocumentImporterExecutor {
	private static final Logger logger = Logger.getLogger(DocumentImporterExecutor.class);

	@Autowired
	private RepositoryClient repositoryConnector;
	
	public void execute(){
		RepositoryDocument document = new RepositoryDocument();
		Reader csvReader = getCsvReader();
		try {
			repositoryConnector.getCSVInputParser().parseCSV(csvReader );
		} catch (IOException e) {
			logger.error("Error executing importing " + e.getMessage(), e);
		}
	}

	private Reader getCsvReader() {
		// TODO Auto-generated method stub
		return null;
	}
}
