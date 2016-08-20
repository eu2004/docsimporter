package ro.eu.documentimporter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepositoryDocumentService {
	private static final Logger logger = Logger.getLogger(RepositoryDocumentService.class);

	@Autowired
	private DocumentImporterAppConfiguration applicationConfiguration;

	@Autowired
	private RepositoryDocumentDAO repositoryDocumentDAO;

	public RepositoryEntityIdAttribute importDocument(Document document) throws RepositoryDocumentServiceException {
		if (document == null) {
			throw new RepositoryDocumentServiceException("document cannot be NULL!");
		}

		try {
			// check if document already exists
			Document existingDocument = repositoryDocumentDAO.documentExists(document.getFindCriteria());
			if (existingDocument == null) {
				// if objects not exists create a new one
				return repositoryDocumentDAO.createDocument(document).getId();
			} else {
				if (applicationConfiguration.getImporterActionInCaseExists() == null) {
					logger.error(document
							+ " already exists but no action found for it. Please check application configuration file.");
					return null;
				}

				switch (applicationConfiguration.getImporterActionInCaseExists()) {
				case NEW:
					logger.info(document + " already exists but create a new document");
					return repositoryDocumentDAO.createDocument(document).getId();
				case UPDATE:
					logger.info(document + " already exists but update it");
					return repositoryDocumentDAO.updateDocument(document).getId();
				case VERSION:
					logger.info(document + " already exists but create a new version of it");
					return repositoryDocumentDAO.createDocumentNewVersion(document).getId();
				default:
					logger.info(document + " was not created because already exists");
					return null;
				}
			}
		} catch (Exception ex) {
			logger.error("Error importing object " + ex.getMessage(), ex);
			throw new RepositoryDocumentServiceException(ex);
		}
	}

	public RepositoryDocumentDAO getRepositoryDocumentDAO() {
		return repositoryDocumentDAO;
	}

	public DocumentImporterAppConfiguration getApplicationConfiguration() {
		return applicationConfiguration;
	}

	public void setApplicationConfiguration(DocumentImporterAppConfiguration applicationConfiguration) {
		this.applicationConfiguration = applicationConfiguration;
	}

	public void setRepositoryDocumentDAO(RepositoryDocumentDAO repositoryDocumentDAO) {
		this.repositoryDocumentDAO = repositoryDocumentDAO;
	}

}
