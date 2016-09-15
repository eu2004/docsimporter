package ro.eu.dctm_documentimporter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created by emilu on 5/22/2016.
 */
@Configuration
@Import({ DctmApplicationConfiguration.class })
@PropertySource("classpath:test-app.properties")
public class TestDctmApplicationConfiguration {
	@Autowired
	private Environment env;
}
