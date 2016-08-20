package ro.eu.documentimporter;

public interface RepositoryDocumentDAO {
	public Document documentExists(String findCriteria);

	public Document getDocumentById(RepositoryEntityIdAttribute id);
	
	public Document createDocument(Document document);
	
	public Document createDocumentNewVersion(Document document);
	
	public Document updateDocument(Document document);
}
