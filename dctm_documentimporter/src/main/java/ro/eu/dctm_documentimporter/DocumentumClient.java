package ro.eu.dctm_documentimporter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import ro.eu.documentimporter.inputprocessor.CSVInputParser;
import ro.eu.documentimporter.repository.RepositoryClient;
import ro.eu.documentimporter.repository.RepositoryDocumentDAO;

@Component
@PropertySource("classpath:app.properties")
public class DocumentumClient extends RepositoryClient{
	@Value("${repository.user}")
	private String user;
	@Value("${repository.pass}")
	private String pass;
	@Value("${repository.name}")
	private String repository;
	
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
