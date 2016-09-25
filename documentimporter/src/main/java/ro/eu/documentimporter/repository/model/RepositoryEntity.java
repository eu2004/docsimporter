package ro.eu.documentimporter.repository.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RepositoryEntity {
	private RepositoryEntityIdAttribute id;
	private RepositoryEntityAttribute type;
	protected Map<String, RepositoryEntityAttribute> attributes = new HashMap<>(1);

	public void setAttributeValue(RepositoryEntityAttribute attributeValue) {
		attributes.put(attributeValue.getMetadata().getName(), attributeValue);
	}

	public boolean removeAttributeValue(String attributeName) {
		//do not remove the id attribute if is set
		if (id.getMetadata() != null && attributeName.equals(id.getMetadata().getName())) {
			return false;
		}
		
		//do not remove the type attribute if is set
		if (type.getMetadata() != null && attributeName.equals(type.getMetadata().getName())) {
			return false;
		}
		
		return attributes.remove(attributeName) != null;
	}

	public Object getValue(String attributeName) {
		RepositoryEntityAttribute attributeValue = attributes.get(attributeName);
		return attributeValue == null ? null : attributeValue.getValue();
	}

	public RepositoryEntityAttribute getAttributeValue(String attributeName) {
		return attributes.get(attributeName);
	}

	public Collection<RepositoryEntityAttribute> getAllAttributesValues() {
		return Collections.unmodifiableCollection(attributes.values());
	}

	public RepositoryEntityIdAttribute getIdAttributeValue() {
		return id;
	}
	
	public String getId() {
		return id.getValue();
	}

	public void setId(RepositoryEntityIdAttribute id) {
		this.id = id;
		this.setAttributeValue(id);
	}

	public String getType() {
		return (String) type.getValue();
	}
	
	public RepositoryEntityAttribute getTypeAttributeValue() {
		return type;
	}

	public void setType(RepositoryEntityAttribute type) {
		this.type = type;
		this.setAttributeValue(type);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RepositoryEntity other = (RepositoryEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
