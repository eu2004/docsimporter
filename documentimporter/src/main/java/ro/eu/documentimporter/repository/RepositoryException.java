package ro.eu.documentimporter.repository;

public class RepositoryException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RepositoryException(Exception e) {
		super(e);
	}

}
