package ro.eu.documentimporter;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import ro.eu.documentimporter.inputprocessor.CSVInputParser;
import ro.eu.documentimporter.inputprocessor.CSVRowParserCallback;
import ro.eu.documentimporter.inputprocessor.RepositoryDocumentConvertor;
import ro.eu.documentimporter.repository.RepositoryDocumentDAO;
import ro.eu.documentimporter.repository.RepositoryDocumentService;

/**
 * Created by emilu on 5/22/2016.
 */
@Configuration
@ComponentScan(basePackages = {"ro.eu.documentimporter"})
@PropertySource("classpath:app.properties")
public class TestApplicationConfiguration {
	@Autowired
	private Environment environment;

	@Mock
	private RepositoryDocumentDAO repositoryDocumentDAO;
	
	@Mock
	private CSVRowParserCallback rowParserCallback;
	
	@Mock
	private RepositoryDocumentConvertor repositoryDocumentConvertor;
	
	@Mock
	private CSVRowParserCallback mockedCsvRowParserCallback;
	
	@Mock
	private RepositoryDocumentConvertor mockedRepositoryDocumentConvertor;
	
	private RepositoryDocumentService repositoryDocumentService = new RepositoryDocumentService(){

		@Override
		public RepositoryDocumentDAO getRepositoryDocumentDAO() {
			return repositoryDocumentDAO;
		}
		
	};

	// init mockito
	{
		MockitoAnnotations.initMocks(this);
	}

	@Bean(name = "mockedRepositoryDocumentConvertor")
	public RepositoryDocumentConvertor mockedRepositoryDocumentConvertor() {
		return mockedRepositoryDocumentConvertor;
	}
	
	@Bean(name = "mockedCsvRowParserCallback")
	public CSVRowParserCallback mockedCsvRowParserCallback() {
		return mockedCsvRowParserCallback;
	}
	
	@Bean(name = "mockedCSVInputParser")
	public CSVInputParser mockedCSVInputParser() {
		return new CSVInputParser() {
			
			@Override
			public CSVRowParserCallback getRowParserCallback() {
				return mockedCsvRowParserCallback;
			}
			
			@Override
			public RepositoryDocumentConvertor getRepositoryDocumentConvertor() {
				return mockedRepositoryDocumentConvertor;
			}
		};
	}
	
	@Bean(name = "repositoryDocumentDAO")
	public RepositoryDocumentDAO repositoryDocumentDAO() {
		return repositoryDocumentDAO;
	}
	
	@Bean(name = "repositoryDocumentService")
	public RepositoryDocumentService repositoryDocumentService() {
		return repositoryDocumentService;
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