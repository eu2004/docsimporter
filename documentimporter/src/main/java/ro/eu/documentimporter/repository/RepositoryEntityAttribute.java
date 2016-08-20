package ro.eu.documentimporter.repository;

public class RepositoryEntityAttribute {
	private RepositoryMetadata metadata;
	private Object value;

	public RepositoryMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(RepositoryMetadata metadata) {
		this.metadata = metadata;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}