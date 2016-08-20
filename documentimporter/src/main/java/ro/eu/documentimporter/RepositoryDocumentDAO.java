package docimporter.documentimporter;

public interface RepositoryDocumentDAO {
	public boolean documentExists(String findCriteria);

	public Document getDocumentById(RepositoryEntityIdAttribute id);
	
	public Document createDocument(Document document);
	
	public Document createDocumentNewVersion(Document document);
	
	public Document updateDocument(Document document);
}
