package docimporter.documentimporter;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created by emilu on 5/22/2016.
 */
@Configuration
@PropertySource("classpath:test-app.properties")
public class TestApplicationConfiguration {
	@Autowired
	private Environment env;

	@Mock
	private RepositoryDocumentDAO repositoryDocumentDAO;

	@Mock
	private RepositoryDocumentService repositoryDocumentService;

	// init mockito
	{
		MockitoAnnotations.initMocks(this);
	}

	@Bean(name = "repositoryDocumentDAO")
	public RepositoryDocumentDAO repositoryDocumentDAO() {
		return repositoryDocumentDAO;
	}

	@Bean(name = "repositoryDocumentService")
	public RepositoryDocumentService repositoryDocumentService() {
		return repositoryDocumentService;
	}
}