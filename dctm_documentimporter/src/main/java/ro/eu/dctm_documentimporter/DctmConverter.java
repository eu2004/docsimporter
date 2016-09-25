package ro.eu.dctm_documentimporter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.documentum.fc.client.IDfPersistentObject;

import ro.eu.documentimporter.repository.model.RepositoryDocument;
import ro.eu.documentimporter.repository.model.RepositoryEntityIdAttribute;
import ro.eu.documentimporter.repository.model.RepositoryMetadata;
import ro.eu.documentimporter.repository.model.RepositoryMetadataType;

@Component
public class DctmConverter implements Converter<IDfPersistentObject, RepositoryDocument> {

	@Override
	public RepositoryDocument convert(IDfPersistentObject source) {
		if (source == null) {
			return null;
		}

		RepositoryDocument document = new RepositoryDocument();
		try {
			// id
			RepositoryEntityIdAttribute id = new RepositoryEntityIdAttribute();
			id.setValue(source.getString("r_object_id"));
			RepositoryMetadata metadata = new RepositoryMetadata();
			metadata.setName("r_object_id");
			metadata.setType(RepositoryMetadataType.STRING);
			id.setMetadata(metadata);
			document.setId(id);

			// object name
			document.setObjectName(source.getString("object_name"));

			// r_version_label
			String[] versions = new String[source.getValueCount("r_version_label")];
			for (int i = 0; i < versions.length; i++) {
				versions[i] = source.getRepeatingString("r_version_label", i);
			}
			document.setVersions(versions);
			
			//r_object_type
			document.setType(source.getString("r_object_type"));
			//...
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return document;
	}

}
