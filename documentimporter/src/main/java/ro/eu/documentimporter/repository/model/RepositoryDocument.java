package ro.eu.documentimporter.repository.model;

public class RepositoryDocument extends RepositoryEntity {

	private String findCriteria;
	private RepositoryEntityAttribute version;

	public void setFindCriteria(String findCriteria) {
		this.findCriteria = findCriteria;
	}

	public String getFindCriteria() {
		return findCriteria;
	}

	public String[] getVersionAsStrings() {
		return (String[]) version.getValue();
	}

	public RepositoryEntityAttribute getVersionAttributeValue() {
		return version;
	}

	public void setVersion(RepositoryEntityAttribute version) {
		this.version = version;
		this.setAttributeValue(version);
	}
}