package ro.eu.documentimporter_launcher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import ro.eu.dctm_documentimporter.DctmApplicationConfiguration;
import ro.eu.dctm_documentimporter.DctmRepositoryDocumentDAO;
import ro.eu.documentimporter.repository.RepositoryDocumentDAO;
import ro.eu.documentimporter.repository.RepositoryDocumentService;

@Configuration
@ComponentScan
@Import({ DctmApplicationConfiguration.class })
@PropertySource("classpath:app.properties")
public class SpringApplicationConfiguration {
	@Value("${importer.repository}")
	private String repositoryBrand;

	@Bean
	public RepositoryDocumentDAO getRepositoryDocumentDAO() {
		switch (this.repositoryBrand) {
		case "DOCUMENTUM":
			return new DctmRepositoryDocumentDAO();
		default:
			throw new DocumentImporterException("No repository brand specified!");
		}
	}
	
	@Bean
	public RepositoryDocumentService getRepositoryDocumentService() {
		return new RepositoryDocumentService();
	}
}
