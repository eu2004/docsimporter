package ro.eu.dctm_documentimporter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.eu.documentimporter.repository.RepositoryDocumentDAO;
import ro.eu.documentimporter.repository.RepositoryDocumentService;

@Component
public class DctmRepositoryDocumentService extends RepositoryDocumentService{
	@Autowired
	private RepositoryDocumentDAO dctmRepositoryDocumentDAO;
	
	@Override
	public RepositoryDocumentDAO getRepositoryDocumentDAO() {
		return dctmRepositoryDocumentDAO;
	}

}
