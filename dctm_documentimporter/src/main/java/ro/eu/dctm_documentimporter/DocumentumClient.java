package ro.eu.dctm_documentimporter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.eu.documentimporter.inputprocessor.CSVInputParser;
import ro.eu.documentimporter.repository.RepositoryClient;
import ro.eu.documentimporter.repository.RepositoryDocumentDAO;

@Component
public class DocumentumClient extends RepositoryClient{
	
	@Autowired
	private RepositoryDocumentDAO dctmRepositoryDocumentDAO;
	
	@Autowired
	private DctmCSVInputParser csvInputParser;

	@Override
	public RepositoryDocumentDAO getRepositoryDocumentDAO() {
		return dctmRepositoryDocumentDAO;
	}

	@Override
	public CSVInputParser getCSVInputParser() {
		return csvInputParser;
	}

}
