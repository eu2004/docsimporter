package ro.eu.dctm_documentimporter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.documentum.fc.client.IDfPersistentObject;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfAttr;

import ro.eu.documentimporter.inputprocessor.RepositoryMetadataConvertor;
import ro.eu.documentimporter.repository.RepositoryException;
import ro.eu.documentimporter.repository.model.RepositoryMetadata;

@Component
public class DctmRepositoryMetadataConvertor implements RepositoryMetadataConvertor, IDctmSession {
	private static final Logger logger = Logger.getLogger(DctmRepositoryMetadataConvertor.class);

	private IDfSession session;

	@Autowired
	private RepositoryMetadataFactory metadataFactory;

	@Override
	public Map<String, RepositoryMetadata> convert(final Map<String, String> csvRow) {
		String type = getDctmTypeName(csvRow);
		if (type.length() == 0) {
			logger.warn("No r_object_type column found, use dm_sysobject as default type.");
			type = "dm_sysobject";
		}
		try {
			final Map<String, IDfAttr> typeAttributes = new HashMap<>();
			loadTypeAttributes(type, typeAttributes);
			Map<String, RepositoryMetadata> metadata = new LinkedHashMap<>();
			for (Entry<String, String> header : csvRow.entrySet()) {
				if ("$DUPLICATE_SEARCH_CRITERIA".equals(header.getKey())){
					continue;
				}
				metadata.put(header.getKey(), metadataFactory.create(typeAttributes.get(header.getKey())));
			}
			return metadata;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	private void loadTypeAttributes(String dctmTypeName, Map<String, IDfAttr> typeAttributes) throws DfException {
		IDfPersistentObject dmTypeInfo = session
				.getObjectByQualification(String.format("dm_type where name='%s'", dctmTypeName));
		// put r_object_id
		typeAttributes.put("r_object_id", new IDfAttr() {

			@Override
			public boolean isRepeating() {
				return false;
			}

			@Override
			public boolean isQualifiable() {
				return false;
			}

			@Override
			public String getName() {
				return "r_object_id";
			}

			@Override
			public int getLength() {
				return 16;
			}

			@Override
			public String getId() {
				return null;
			}

			@Override
			public int getDataType() {
				return IDfAttr.DM_STRING;
			}

			@Override
			public int getAllowedLength(String arg0) {
				return 16;
			}
		});

		int count = dmTypeInfo.getValueCount("attr_name");

		for (int i = 0; i < count; i++) {
			final String attrName = dmTypeInfo.getRepeatingString("attr_name", i);
			final int attrType = dmTypeInfo.getRepeatingInt("attr_type", i);
			final boolean isRepeating = dmTypeInfo.getRepeatingBoolean("attr_repeating", i);
			final int length = dmTypeInfo.getRepeatingInt("attr_length", i);
			IDfAttr attr = new IDfAttr() {

				@Override
				public int getAllowedLength(String arg0) {
					return length;
				}

				@Override
				public int getDataType() {
					return attrType;
				}

				@Override
				public String getId() {
					return null;
				}

				@Override
				public int getLength() {
					return length;
				}

				@Override
				public String getName() {
					return attrName;
				}

				@Override
				public boolean isQualifiable() {
					return false;
				}

				@Override
				public boolean isRepeating() {
					return isRepeating;
				}
			};
			typeAttributes.put(attrName, attr);
		}
		if (dmTypeInfo.getString("super_name") != null && dmTypeInfo.getString("super_name").length() > 0) {
			loadTypeAttributes(dmTypeInfo.getString("super_name"), typeAttributes);
		}
	}

	private String getDctmTypeName(Map<String, String> csvRow) {
		for (Entry<String, String> cell : csvRow.entrySet()) {
			if (cell.getKey().equals("r_object_type")) {
				return cell.getValue();
			}
		}
		return "";
	}

	@Override
	public IDfSession getSession() {
		return session;
	}

	@Override
	public void setSession(IDfSession session) {
		this.session = session;
	}
}
