package ro.eu.documentimporter.repository;

public class RepositoryDocumentServiceException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RepositoryDocumentServiceException(String message) {
		super(message);
	}

	public RepositoryDocumentServiceException(Exception ex) {
		super(ex);
	}
}
