package ro.eu.dctm_documentimporter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by emilu on 5/22/2016.
 */
@Configuration
@Import({ DctmAppConfig.class })
@ComponentScan(basePackages = {"ro.eu.documentimporter"})
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
