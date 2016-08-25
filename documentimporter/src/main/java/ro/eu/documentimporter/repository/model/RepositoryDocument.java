package ro.eu.documentimporter.repository.model;

public class RepositoryDocument extends RepositoryEntity {

	private String findCriteria;

	public void setFindCriteria(String findCriteria) {
		this.findCriteria = findCriteria;
	}

	public String getFindCriteria() {
		return findCriteria;
	}
}