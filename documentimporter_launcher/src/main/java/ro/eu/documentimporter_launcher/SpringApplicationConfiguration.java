package ro.eu.documentimporter_launcher;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import ro.eu.dctm_documentimporter.DctmApplicationConfiguration;

@Configuration
@ComponentScan
@Import({ DctmApplicationConfiguration.class })
@PropertySource("classpath:app.properties")
public class SpringApplicationConfiguration {
}
