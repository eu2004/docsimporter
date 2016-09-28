package ro.eu.dctm_documentimporter;

import ro.eu.documentimporter.repository.model.RepositoryDocument;
import ro.eu.documentimporter.repository.model.RepositoryEntityAttribute;
import ro.eu.documentimporter.repository.model.RepositoryMetadata;
import ro.eu.documentimporter.repository.model.RepositoryMetadataType;

public class DctmDocument extends RepositoryDocument {
	public String getObjectName() {
		return (String) getAttributeValue("object_name").getValue();
	}

	public void setObjectName(String name) {
		RepositoryEntityAttribute nameValue = new RepositoryEntityAttribute();
		nameValue.setValue(name);
		RepositoryMetadata nameMetadata = new RepositoryMetadata();
		nameMetadata.setName("object_name");
		nameMetadata.setType(RepositoryMetadataType.STRING);
		nameValue.setMetadata(nameMetadata);
		setAttributeValue(nameValue);
	}

	public void setType(String type) {
		RepositoryEntityAttribute typeAttr = new RepositoryEntityAttribute();
		typeAttr.setValue(type);
		RepositoryMetadata typeMetadata = new RepositoryMetadata();
		typeMetadata.setName("r_object_type");
		typeAttr.setMetadata(typeMetadata);
		this.setType(typeAttr);
		
	}
	
	public String getType() {
		return (String) getAttributeValue("r_object_type").getValue();
	}
}
