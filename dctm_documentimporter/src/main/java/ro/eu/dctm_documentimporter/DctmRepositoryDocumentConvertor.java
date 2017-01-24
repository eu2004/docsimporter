package ro.eu.dctm_documentimporter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	private ThreadLocal<SimpleDateFormat> simpleDateFormat = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		}
	};

	@Override
	public RepositoryDocument convert(Map<String, String> csvRecord) throws RepositoryException {
		final RepositoryDocument document = new RepositoryDocument();
		if (dctmTypesCache.get(csvRecord.get("r_object_type")) == null) {
			Map<String, RepositoryMetadata> attributesDefinition = repositoryMetadataConvertor.convert(csvRecord);
			dctmTypesCache.put(csvRecord.get("r_object_type"), attributesDefinition);
		}

		for (Entry<String, String> cell : csvRecord.entrySet()) {
			RepositoryEntityAttribute attributeValue = new RepositoryEntityAttribute();
			RepositoryMetadata attributeDefinition = dctmTypesCache.get(csvRecord.get("r_object_type"))
					.get(cell.getKey());
			attributeValue.setMetadata(attributeDefinition);
			try {
				attributeValue.setValue(getCellValue(attributeDefinition, cell.getValue()));
			} catch (ParseException e) {
				throw new RepositoryException(e);
			}
			document.setAttributeValue(attributeValue);
		}
		return document;
	}

	private Object getCellValue(RepositoryMetadata attributeDefinition, String value) throws ParseException {
		switch (attributeDefinition.getType()) {
		case BOOLEAN:
			return Boolean.valueOf(value);
		case DOUBLE:
			return Double.valueOf(value);
		case INTEGER:
			return Integer.valueOf(value);
		case DATE:
			return simpleDateFormat.get().parse(value);
		default:
			return value;
		}
	}
}