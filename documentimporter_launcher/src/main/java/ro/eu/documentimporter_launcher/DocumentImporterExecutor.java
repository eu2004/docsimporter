package ro.eu.documentimporter_launcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.eu.documentimporter.DocumentImporterAppConfiguration;
import ro.eu.documentimporter.repository.RepositoryDocumentService;

@Component
public class DocumentImporterExecutor {

	@Autowired
	private RepositoryDocumentService documentService;
	
	@Autowired
	private DocumentImporterAppConfiguration configuration;
	
	public void execute(){
		
	}
}
