package ro.eu.documentimporter_launcher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import ro.eu.dctm_documentimporter.DctmAppConfig;
import ro.eu.documentimporter.DocumentImporterAppConfig;
import ro.eu.documentimporter.repository.RepositoryClient;
import ro.eu.documentimporter.repository.RepositoryDocumentService;

@Configuration
@ComponentScan(basePackages = {"ro.eu.documentimporter_launcher"})
@Import({ DocumentImporterAppConfig.class, DctmAppConfig.class })
@PropertySource("classpath:app.properties")
public class DocumentImporterLauncherAppConfig {
	@Value("${importer.repository.connector}")
	private String repositoryConnector;

	@Value("${repository.user}")
	private String user;
	@Value("${repository.pass}")
	private String pass;
	@Value("${repository.name}")
	private String repository;

	@Bean
	public RepositoryClient getRepositoryConnector()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		RepositoryClient connector = (RepositoryClient) Class.forName(repositoryConnector).newInstance();
		connector.setPass(pass);
		connector.setRepository(repository);
		connector.setUser(user);
		return connector;
	}

	@Bean
	public RepositoryDocumentService getRepositoryDocumentService() {
		return new RepositoryDocumentService();
	}
}
