package ro.eu.documentimporter.repository.model;

public class RepositoryMetadata {
	private String name;
	private RepositoryMetadataType type;
	private boolean isRepeating;
	private int length;

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RepositoryMetadataType getType() {
		return type;
	}

	public void setType(RepositoryMetadataType type) {
		this.type = type;
	}

	public boolean isRepeating() {
		return isRepeating;
	}

	public void setRepeating(boolean isRepeating) {
		this.isRepeating = isRepeating;
	}

}