package ro.eu.dctm_documentimporter;

import java.util.Collection;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.documentum.fc.client.IDfPersistentObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfTime;
import com.documentum.fc.common.IDfAttr;

import ro.eu.documentimporter.repository.model.RepositoryDocument;
import ro.eu.documentimporter.repository.model.RepositoryEntityAttribute;
import ro.eu.documentimporter.repository.model.RepositoryEntityIdAttribute;
import ro.eu.documentimporter.repository.model.RepositoryMetadata;
import ro.eu.documentimporter.repository.model.RepositoryMetadataType;

@Component
public class DctmConverter {

	public IDfPersistentObject convert(DctmDocument source, IDfPersistentObject destination) throws DfException {
		if (source == null || destination == null) {
			return destination;
		}

		Collection<RepositoryEntityAttribute> allAttributes = source.getAllAttributesValues();
		for (RepositoryEntityAttribute attribute : allAttributes) {
			if ("r_version_label".equals(attribute.getMetadata().getName())) {
				//set r_version_label attribute
				setValue(source, destination, attribute);
			}else {
				//skip other internal and read-only attributes
				if (attribute.getMetadata().getName().startsWith("r_") || attribute.getMetadata().getName().startsWith("i_")) {
					continue;
				}
				setValue(source, destination, attribute);
			}
		}
		return destination;
	}

	public DctmDocument convert(IDfPersistentObject source, DctmDocument destination) {
		if (source == null || destination == null) {
			return destination;
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
			if (source.hasAttr("r_version_label")) {
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
			}

			// r_object_type
			RepositoryEntityAttribute type = new RepositoryEntityAttribute();
			type.setValue(source.getString("r_object_type"));
			metadata = new RepositoryMetadata();
			metadata.setName("r_object_type");
			metadata.setType(RepositoryMetadataType.STRING);
			type.setMetadata(metadata);
			destination.setType(type);

			// set other attributes
			int attrCount = source.getAttrCount();
			for (int i = 0; i < attrCount; i++) {
				IDfAttr attribute = source.getAttr(i);
				setValue(source, destination, attribute);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return destination;
	}

	private void setValue(RepositoryDocument source, IDfPersistentObject destination,
			RepositoryEntityAttribute attribute) throws DfException {
		switch (attribute.getMetadata().getType()) {
		case BOOLEAN:
			if (attribute.getValue() != null) {
				setDctmObjectAttributeBoolValue(destination, attribute);
			}
			break;
		case DATE:
			if (attribute.getValue() != null) {
				setDctmObjectAttributeDateTimeValue(destination, attribute);
			}
			break;
		case DOUBLE:
			if (attribute.getValue() != null) {
				setDctmObjectAttributeDoubleValue(destination, attribute);
			}
			break;
		case INTEGER:
			if (attribute.getValue() != null) {
				setDctmObjectAttributeIntegerValue(destination, attribute);
			}
			break;
		default:
			if (attribute.getValue() != null) {
				setDctmObjectAttributeStringValue(destination, attribute);
			}
		}
	}

	private void setDctmObjectAttributeStringValue(IDfPersistentObject destination, RepositoryEntityAttribute attrValue)
			throws DfException {
		if (attrValue.getValue() instanceof String[]) {
			String[] attrValues = (String[]) attrValue.getValue();
			int count = attrValues.length;
			for (int i = 0; i < count; i++) {
				destination.setRepeatingString(attrValue.getMetadata().getName(), i, attrValues[i]);
			}
		} else {
			destination.setString(attrValue.getMetadata().getName(), (String) attrValue.getValue());
		}
	}

	private void setDctmObjectAttributeIntegerValue(IDfPersistentObject destination,
			RepositoryEntityAttribute attrValue) throws DfException {
		if (attrValue.getValue() instanceof Integer[]) {
			Integer[] attrValues = (Integer[]) attrValue.getValue();
			int count = attrValues.length;
			for (int i = 0; i < count; i++) {
				destination.setRepeatingInt(attrValue.getMetadata().getName(), i, attrValues[i]);
			}
		} else {
			destination.setInt(attrValue.getMetadata().getName(), (Integer) attrValue.getValue());
		}
	}

	private void setDctmObjectAttributeDoubleValue(IDfPersistentObject destination, RepositoryEntityAttribute attrValue)
			throws DfException {
		if (attrValue.getValue() instanceof Double[]) {
			Double[] attrValues = (Double[]) attrValue.getValue();
			int count = attrValues.length;
			for (int i = 0; i < count; i++) {
				destination.setRepeatingDouble(attrValue.getMetadata().getName(), i, attrValues[i]);
			}
		} else {
			destination.setDouble(attrValue.getMetadata().getName(), (Double) attrValue.getValue());
		}
	}

	private void setDctmObjectAttributeDateTimeValue(IDfPersistentObject destination,
			RepositoryEntityAttribute attrValue) throws DfException {
		if (attrValue.getValue() instanceof Date[]) {
			Date[] attrValues = (Date[]) attrValue.getValue();
			int count = attrValues.length;
			for (int i = 0; i < count; i++) {
				destination.setRepeatingTime(attrValue.getMetadata().getName(), i, new DfTime(attrValues[i]));
			}
		} else {
			destination.setTime(attrValue.getMetadata().getName(), new DfTime((Date) attrValue.getValue()));
		}
	}

	private void setDctmObjectAttributeBoolValue(IDfPersistentObject destination, RepositoryEntityAttribute attrValue)
			throws DfException {
		if (attrValue.getValue() instanceof Boolean[]) {
			Boolean[] attrValues = (Boolean[]) attrValue.getValue();
			int count = attrValues.length;
			for (int i = 0; i < count; i++) {
				destination.setRepeatingBoolean(attrValue.getMetadata().getName(), i, attrValues[i]);
			}
		} else {
			destination.setBoolean(attrValue.getMetadata().getName(), (boolean) attrValue.getValue());
		}
	}

	private void setValue(IDfPersistentObject source, RepositoryDocument destination, IDfAttr attribute)
			throws DfException {
		Object value = null;
		RepositoryMetadataType type = null;
		switch (attribute.getDataType()) {
		case IDfAttr.DM_BOOLEAN:
			value = getDctmObjectAttributeBoolValue(source, attribute.getName());
			type = RepositoryMetadataType.BOOLEAN;
			break;
		case IDfAttr.DM_DOUBLE:
			value = getDctmObjectAttributeDoubleValue(source, attribute.getName());
			type = RepositoryMetadataType.DOUBLE;
			break;
		case IDfAttr.DM_INTEGER:
			value = getDctmObjectAttributeIntegerValue(source, attribute.getName());
			type = RepositoryMetadataType.INTEGER;
			break;
		case IDfAttr.DM_ID:
		case IDfAttr.DM_STRING:
			value = getDctmObjectAttributeStringValue(source, attribute.getName());
			type = RepositoryMetadataType.STRING;
			break;
		case IDfAttr.DM_TIME:
			value = getDctmObjectAttributeDateTimeValue(source, attribute.getName());
			type = RepositoryMetadataType.DATE;
			break;
		case IDfAttr.DM_UNDEFINED:
			value = getDctmObjectAttributeStringValue(source, attribute.getName());
			type = RepositoryMetadataType.STRING;
			break;
		}

		RepositoryEntityAttribute attributeValue = new RepositoryEntityAttribute();
		attributeValue.setValue(value);
		RepositoryMetadata metadata = new RepositoryMetadata();
		metadata.setName(attribute.getName());
		metadata.setType(type);
		attributeValue.setMetadata(metadata);
		destination.setAttributeValue(attributeValue);
	}

	private Object getDctmObjectAttributeDateTimeValue(IDfPersistentObject source, String attributeName)
			throws DfException {
		int count = source.getValueCount(attributeName);
		if (count == 1) {
			return source.getTime(attributeName).getDate();
		} else {
			Date[] values = new Date[count];
			for (int i = 0; i < count; i++) {
				values[i] = source.getRepeatingTime(attributeName, i).getDate();
			}
			return values;
		}
	}

	private Object getDctmObjectAttributeStringValue(IDfPersistentObject source, String attributeName)
			throws DfException {
		int count = source.getValueCount(attributeName);
		if (count == 1) {
			return source.getString(attributeName);
		} else {
			String[] values = new String[count];
			for (int i = 0; i < count; i++) {
				values[i] = source.getRepeatingString(attributeName, i);
			}
			return values;
		}
	}

	private Object getDctmObjectAttributeIntegerValue(IDfPersistentObject source, String attributeName)
			throws DfException {
		int count = source.getValueCount(attributeName);
		if (count == 1) {
			return source.getInt(attributeName);
		} else {
			Integer[] values = new Integer[count];
			for (int i = 0; i < count; i++) {
				values[i] = source.getRepeatingInt(attributeName, i);
			}
			return values;
		}
	}

	private Object getDctmObjectAttributeDoubleValue(IDfPersistentObject source, String attributeName)
			throws DfException {
		int count = source.getValueCount(attributeName);
		if (count == 1) {
			return source.getDouble(attributeName);
		} else {
			Double[] values = new Double[count];
			for (int i = 0; i < count; i++) {
				values[i] = source.getRepeatingDouble(attributeName, i);
			}
			return values;
		}
	}

	private Object getDctmObjectAttributeBoolValue(IDfPersistentObject source, String attributeName)
			throws DfException {
		int count = source.getValueCount(attributeName);
		if (count == 1) {
			return source.getBoolean(attributeName);
		} else {
			Boolean[] values = new Boolean[count];
			for (int i = 0; i < count; i++) {
				values[i] = source.getRepeatingBoolean(attributeName, i);
			}
			return values;
		}
	}

}
