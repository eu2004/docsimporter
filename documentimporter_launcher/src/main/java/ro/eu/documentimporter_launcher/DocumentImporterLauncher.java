package ro.eu.documentimporter_launcher;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DocumentImporterLauncher {

	public static void main(String... args) {
		// launch app
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "application_configuration.xml" });
		context.getBean(DocumentImporterExecutor.class).execute();
	}
}
