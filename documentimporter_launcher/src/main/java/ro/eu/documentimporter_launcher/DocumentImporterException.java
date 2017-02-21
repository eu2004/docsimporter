package ro.eu.documentimporter_launcher;

public class DocumentImporterException extends RuntimeException {

	public DocumentImporterException(Throwable throwable) {
		super(throwable);
	}

	public DocumentImporterException(String error) {
		super(error);
	}

}
