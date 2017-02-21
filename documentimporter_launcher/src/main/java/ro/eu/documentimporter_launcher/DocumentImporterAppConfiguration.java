package ro.eu.documentimporter_launcher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import ro.eu.documentimporter.ExistingDocumentImporterActions;
import ro.eu.documentimporter.IDocumentImporterAppConfiguration;

@Component
@PropertySource("classpath:app.properties")
public class DocumentImporterAppConfiguration implements IDocumentImporterAppConfiguration{
	@Value("${importer.action.in.case.exists}")
	private String importerActionInCaseExists;

	@Value("${importer.input.doc.name}")
	private String importerInputDocName;

	public ExistingDocumentImporterActions getImporterActionInCaseExists() {
		return ExistingDocumentImporterActions.findByActionName(importerActionInCaseExists);
	}

	public String getImporterInputDocName() {
		return importerInputDocName;
	}
}
