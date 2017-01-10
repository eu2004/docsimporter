package ro.eu.dctm_documentimporter;

import org.springframework.stereotype.Component;

import com.documentum.fc.common.IDfAttr;

import ro.eu.documentimporter.repository.model.RepositoryMetadata;
import ro.eu.documentimporter.repository.model.RepositoryMetadataType;

@Component
public class RepositoryMetadataFactory {

	public RepositoryMetadata create(IDfAttr attribute) {
		RepositoryMetadata metadata = new RepositoryMetadata();
		metadata.setName(attribute.getName());
		RepositoryMetadataType type = getType(attribute);
		metadata.setType(type);
		metadata.setLength(attribute.getLength());
		metadata.setRepeating(attribute.isRepeating());
		return metadata;
	}

	private RepositoryMetadataType getType(IDfAttr attribute) {
		switch (attribute.getDataType()) {
		case IDfAttr.DM_BOOLEAN:
			return RepositoryMetadataType.BOOLEAN;
		case IDfAttr.DM_DOUBLE:
			return RepositoryMetadataType.DOUBLE;
		case IDfAttr.DM_ID:
			return RepositoryMetadataType.STRING;
		case IDfAttr.DM_INTEGER:
			return RepositoryMetadataType.INTEGER;
		case IDfAttr.DM_STRING:
			return RepositoryMetadataType.STRING;
		case IDfAttr.DM_TIME:
			return RepositoryMetadataType.DATE;
		default:
			return RepositoryMetadataType.STRING;
		}
	}
}
