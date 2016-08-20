package ro.eu.documentimporter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DocumentImporterLauncher {

	public static void main(String... args) {
		// launch app
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringApplicationConfiguration.class);
		context.getBean(DocumentImporterExecutor.class).execute();
	}
}
