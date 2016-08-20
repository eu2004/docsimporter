package ro.eu.documentimporter;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:app.properties")
public class DocumentImporterAppConfiguration {
	@Value("${importer.action.in.case.exists}")
	private String importerActionInCaseExists;

	public ExistingDocumentImporterActions getImporterActionInCaseExists() {
		return ExistingDocumentImporterActions.findByActionName(importerActionInCaseExists);
	}

	public static enum ExistingDocumentImporterActions {
		VERSION("VERSION"), UPDATE("REPLACE"), NEW("CREATE"), IGNORE("SKIP");

		private static Map<String, ExistingDocumentImporterActions> actionsMap = new LinkedHashMap<>(1);
		private String actionName;

		private ExistingDocumentImporterActions(String actionName) {
			this.actionName = actionName;
		}

		public String getActionName() {
			return actionName;
		}

		static {
			for (ExistingDocumentImporterActions value : ExistingDocumentImporterActions.values()) {
				actionsMap.put(value.actionName, value);
			}
		}

		public static ExistingDocumentImporterActions findByActionName(String actionName) {
			return actionsMap.get(actionName);
		}
	}
}
