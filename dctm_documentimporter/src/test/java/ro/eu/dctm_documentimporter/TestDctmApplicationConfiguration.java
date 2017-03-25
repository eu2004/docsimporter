package ro.eu.dctm_documentimporter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import ro.eu.documentimporter.DocumentImporterAppConfig;

/**
 * Created by emilu on 5/22/2016.
 */
@Configuration
@Import({ DocumentImporterAppConfig.class, DctmAppConfig.class })
@PropertySource("classpath:app.properties")
public class TestDctmApplicationConfiguration {
	
	
	public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        try {
            ctx.register(DctmAppConfig.class);
            ctx.refresh();
           
            System.out.println("DctmRepositoryDocumentDAO: "
                    + ctx.getBean("dctmRepositoryDocumentDAO"));
        } finally {
            ctx.close();
        }
    }
}
