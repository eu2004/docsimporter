package ro.eu.documentimporter;

import java.util.LinkedHashMap;
import java.util.Map;

public enum ExistingDocumentImporterActions {
	VERSION("VERSION"), UPDATE("UPDATE"), NEW("CREATE"), IGNORE("SKIP"), REPLACE("REPLACE");

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
