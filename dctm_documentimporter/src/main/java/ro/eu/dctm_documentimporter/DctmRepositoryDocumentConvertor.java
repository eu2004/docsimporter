package ro.eu.dctm_documentimporter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ro.eu.documentimporter.inputprocessor.RepositoryDocumentConvertor;
import ro.eu.documentimporter.inputprocessor.RepositoryMetadataConvertor;
import ro.eu.documentimporter.repository.RepositoryException;
import ro.eu.documentimporter.repository.model.RepositoryDocument;
import ro.eu.documentimporter.repository.model.RepositoryEntityAttribute;
import ro.eu.documentimporter.repository.model.RepositoryMetadata;

@Component
@Scope("prototype")
public class DctmRepositoryDocumentConvertor implements RepositoryDocumentConvertor {

	@Autowired
	private RepositoryMetadataConvertor repositoryMetadataConvertor;

	private Map<String, Map<String, RepositoryMetadata>> dctmTypesCache = new HashMap<>();

	@Override
	public RepositoryDocument convert(Map<String, String> csvRecord) throws RepositoryException {
		final RepositoryDocument document = new RepositoryDocument();
		if (dctmTypesCache.get(csvRecord.get("r_object_type")) == null) {
			Map<String, RepositoryMetadata> attributesDefinition = repositoryMetadataConvertor.convert(csvRecord);
			dctmTypesCache.put(csvRecord.get("r_object_type"), attributesDefinition);
		}

		for (Entry<String, String> cell : csvRecord.entrySet()) {
			RepositoryEntityAttribute attributeValue = new RepositoryEntityAttribute();
			attributeValue.setMetadata(dctmTypesCache.get(csvRecord.get("r_object_type")).get(cell.getKey()));
			attributeValue.setValue(cell.getValue());
			document.setAttributeValue(attributeValue);
		}
		return document;
	}
}