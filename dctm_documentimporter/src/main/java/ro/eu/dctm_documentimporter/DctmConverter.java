package ro.eu.dctm_documentimporter;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.documentum.fc.client.IDfPersistentObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfAttr;

import ro.eu.documentimporter.repository.model.RepositoryDocument;
import ro.eu.documentimporter.repository.model.RepositoryEntityAttribute;
import ro.eu.documentimporter.repository.model.RepositoryEntityIdAttribute;
import ro.eu.documentimporter.repository.model.RepositoryMetadata;
import ro.eu.documentimporter.repository.model.RepositoryMetadataType;

@Component
public class DctmConverter {

	public void convert(RepositoryDocument source, IDfPersistentObject destination) {
		if (source == null || destination == null) {
			return;
		}

		Collection<RepositoryEntityAttribute> allAttributes = source.getAllAttributesValues();
		for (RepositoryEntityAttribute attribute : allAttributes) {
			if (attribute.getMetadata().getName().equals(source.getIdAttributeValue().getMetadata().getName())
					|| attribute.getMetadata().getName()
							.equals(source.getTypeAttributeValue().getMetadata().getName())) {
				continue;
			} else {

			}
		}
	}

	public void convert(IDfPersistentObject source, RepositoryDocument destination) {
		if (source == null || destination == null) {
			return;
		}

		try {
			// id
			RepositoryEntityIdAttribute id = new RepositoryEntityIdAttribute();
			id.setValue(source.getString("r_object_id"));
			RepositoryMetadata metadata = new RepositoryMetadata();
			metadata.setName("r_object_id");
			metadata.setType(RepositoryMetadataType.STRING);
			id.setMetadata(metadata);
			destination.setId(id);

			// r_version_label
			String[] versions = new String[source.getValueCount("r_version_label")];
			for (int i = 0; i < versions.length; i++) {
				versions[i] = source.getRepeatingString("r_version_label", i);
			}
			RepositoryEntityAttribute version = new RepositoryEntityAttribute();
			version.setValue(versions);
			metadata = new RepositoryMetadata();
			metadata.setName("r_version_label");
			metadata.setType(RepositoryMetadataType.STRING);
			version.setMetadata(metadata);
			destination.setVersion(version);

			// r_object_type
			RepositoryEntityAttribute type = new RepositoryEntityAttribute();
			type.setValue(source.getString("r_object_type"));
			metadata = new RepositoryMetadata();
			metadata.setName("r_object_type");
			metadata.setType(RepositoryMetadataType.STRING);
			type.setMetadata(metadata);
			destination.setType(type);

			// set the other attributes
			int attrCount = source.getAttrCount();
			for (int i = 0; i < attrCount; i++) {
				IDfAttr attribute = source.getAttr(i);
				if ("r_object_type".equals(attribute.getName()) || "r_version_label".equals(attribute.getName())
						|| "r_object_id".equals(attribute.getName())) {
					continue;
				}
				setValue(destination, source, attribute);
			}

		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	private void setValue(RepositoryDocument document, IDfPersistentObject source, IDfAttr attribute)
			throws DfException {
		Object value = null;
		RepositoryMetadataType type = null;
		switch (attribute.getDataType()) {
		case IDfAttr.DM_BOOLEAN:
			value = source.getBoolean(attribute.getName());
			type = RepositoryMetadataType.BOOLEAN;
			break;
		case IDfAttr.DM_DOUBLE:
			value = source.getDouble(attribute.getName());
			type = RepositoryMetadataType.DOUBLE;
			break;
		case IDfAttr.DM_INTEGER:
			value = source.getInt(attribute.getName());
			type = RepositoryMetadataType.INTEGER;
			break;
		case IDfAttr.DM_STRING:
			value = source.getString(attribute.getName());
			type = RepositoryMetadataType.STRING;
			break;
		case IDfAttr.DM_TIME:
			value = source.getTime(attribute.getName()).getDate();
			type = RepositoryMetadataType.DATE;
			break;
		case IDfAttr.DM_UNDEFINED:
			value = source.getString(attribute.getName());
			type = RepositoryMetadataType.STRING;
			break;
		}

		RepositoryEntityAttribute attributeValue = new RepositoryEntityAttribute();
		attributeValue.setValue(value);
		RepositoryMetadata metadata = new RepositoryMetadata();
		metadata.setName(attribute.getName());
		metadata.setType(type);
		attributeValue.setMetadata(metadata);
		document.setAttributeValue(attributeValue);
	}

}
