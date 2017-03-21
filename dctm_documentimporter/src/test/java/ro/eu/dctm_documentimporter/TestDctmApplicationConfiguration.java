package ro.eu.dctm_documentimporter;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import ro.eu.documentimporter.IDocumentImporterAppConfiguration;
import ro.eu.documentimporter.repository.RepositoryDocumentService;

/**
 * Created by emilu on 5/22/2016.
 */
@Configuration
@Import({ DctmApplicationConfiguration.class })
@PropertySource("classpath:test-app.properties")
public class TestDctmApplicationConfiguration {
	@Autowired
	private Environment env;
	
	@Mock
	private IDocumentImporterAppConfiguration applicationConfiguration;

	
	@Bean(name = "applicationConfiguration")
	public IDocumentImporterAppConfiguration applicationConfiguration() {
		return applicationConfiguration;
	}

	@Bean(name = "repositoryDocumentService")
	public RepositoryDocumentService repositoryDocumentService() {
		return new RepositoryDocumentService();
	}
}
