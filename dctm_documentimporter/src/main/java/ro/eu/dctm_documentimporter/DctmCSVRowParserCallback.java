package ro.eu.dctm_documentimporter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.eu.documentimporter.inputprocessor.CSVRowParserCallback;

import ro.eu.documentimporter.repository.RepositoryDocumentService;
import ro.eu.documentimporter.repository.RepositoryException;
import ro.eu.documentimporter.repository.model.RepositoryDocument;

@Component
public class DctmCSVRowParserCallback implements CSVRowParserCallback{
	@Autowired
	private RepositoryDocumentService dctmRepositoryDocumentService;
	
	@Override
	public void processRepositoryDocument(RepositoryDocument document) throws RepositoryException {
		dctmRepositoryDocumentService.importDocument(document);
	}

	public RepositoryDocumentService getDctmRepositoryDocumentService() {
		return dctmRepositoryDocumentService;
	}
}
