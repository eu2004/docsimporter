package docimporter.documentimporter;

public class RepositoryMetadata {
	private String name;
	private RepositoryMetadataType type;
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
}