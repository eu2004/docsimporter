package ro.eu.documentimporter;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created by emilu on 5/22/2016.
 */
@Configuration
@ComponentScan
@PropertySource("classpath:test-app.properties")
public class TestApplicationConfiguration {
	@Autowired
	private Environment environment;

	@Mock
	private ApplicationConfiguration applicationConfiguration;

	@Mock
	private RepositoryDocumentDAO repositoryDocumentDAO;

	// init mockito
	{
		MockitoAnnotations.initMocks(this);
	}

	@Bean(name = "repositoryDocumentDAO")
	public RepositoryDocumentDAO repositoryDocumentDAO() {
		return repositoryDocumentDAO;
	}

	@Bean(name = "applicationConfiguration")
	public ApplicationConfiguration applicationConfiguration() {
		return applicationConfiguration;
	}

	@Bean(name = "repositoryDocumentService")
	public RepositoryDocumentService repositoryDocumentService() {
		return new RepositoryDocumentService();
	}
}