package ro.eu.documentimporter;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import ro.eu.documentimporter.inputprocessor.CSVRowParserCallback;
import ro.eu.documentimporter.inputprocessor.RepositoryDocumentConvertor;
import ro.eu.documentimporter.repository.RepositoryDocumentDAO;
import ro.eu.documentimporter.repository.RepositoryDocumentService;

/**
 * Created by emilu on 5/22/2016.
 */
@Configuration
@ComponentScan("ro.eu.documentimporter")
@PropertySource("classpath:test-app.properties")
public class TestApplicationSpringConfiguration {
	@Autowired
	private Environment environment;

	@Mock
	private IDocumentImporterAppConfiguration applicationConfiguration;

	@Mock
	private RepositoryDocumentDAO repositoryDocumentDAO;
	
	@Mock
	private CSVRowParserCallback rowParserCallback;
	
	@Mock
	private RepositoryDocumentConvertor repositoryDocumentConvertor;

	// init mockito
	{
		MockitoAnnotations.initMocks(this);
	}

	@Bean(name = "repositoryDocumentDAO")
	public RepositoryDocumentDAO repositoryDocumentDAO() {
		return repositoryDocumentDAO;
	}

	@Bean(name = "applicationConfiguration")
	public IDocumentImporterAppConfiguration applicationConfiguration() {
		return applicationConfiguration;
	}

	@Bean(name = "repositoryDocumentService")
	public RepositoryDocumentService repositoryDocumentService() {
		return new RepositoryDocumentService();
	}
	
	@Bean(name = "rowParserCallback")
	public CSVRowParserCallback rowParserCallback() {
		return rowParserCallback;
	}
	
	@Bean(name = "repositoryDocumentConvertor")
	public RepositoryDocumentConvertor repositoryDocumentConvertor() {
		return repositoryDocumentConvertor;
	}
}