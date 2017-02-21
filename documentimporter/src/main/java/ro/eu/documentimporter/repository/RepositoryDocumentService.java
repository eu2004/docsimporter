package ro.eu.documentimporter.repository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.eu.documentimporter.IDocumentImporterAppConfiguration;
import ro.eu.documentimporter.repository.model.RepositoryDocument;
import ro.eu.documentimporter.repository.model.RepositoryEntityIdAttribute;

@Service
public class RepositoryDocumentService {
	private static final Logger logger = Logger.getLogger(RepositoryDocumentService.class);

	@Autowired
	private IDocumentImporterAppConfiguration applicationConfiguration;

	@Autowired
	private RepositoryDocumentDAO repositoryDocumentDAO;

	public RepositoryEntityIdAttribute importDocument(RepositoryDocument document) throws RepositoryDocumentServiceException {
		if (document == null) {
			throw new RepositoryDocumentServiceException("Document cannot be NULL!");
		}

		try {
			// check if document already exists
			RepositoryDocument existingDocument = repositoryDocumentDAO.getDocumentByCriteria(document.getFindCriteria());
			if (existingDocument == null) {
				// if objects not exists create a new one
				logger.debug(document + " not exists create a new document");
				return repositoryDocumentDAO.createDocument(document).getIdAttributeValue();
			} else {
				if (applicationConfiguration.getImporterActionInCaseExists() == null) {
					logger.error(document
							+ " already exists but no action found for it. Please check application configuration file.");
					return null;
				}

				switch (applicationConfiguration.getImporterActionInCaseExists()) {
				case NEW:
					logger.debug(document + " already exists but create a new document");
					return repositoryDocumentDAO.createDocument(document).getIdAttributeValue();
				case UPDATE:
					logger.debug(document + " already exists but update it");
					return repositoryDocumentDAO.updateDocument(document).getIdAttributeValue();
				case REPLACE:
					logger.debug(document + " already exists but replace it");
					return repositoryDocumentDAO.replaceDocument(document).getIdAttributeValue();
				case VERSION:
					logger.debug(document + " already exists but create a new version of it");
					return repositoryDocumentDAO.createDocumentNewVersion(document).getIdAttributeValue();
				default:
					logger.debug(document + " was not created because already exists");
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

	public IDocumentImporterAppConfiguration getApplicationConfiguration() {
		return applicationConfiguration;
	}

	public void setApplicationConfiguration(IDocumentImporterAppConfiguration applicationConfiguration) {
		this.applicationConfiguration = applicationConfiguration;
	}

	public void setRepositoryDocumentDAO(RepositoryDocumentDAO repositoryDocumentDAO) {
		this.repositoryDocumentDAO = repositoryDocumentDAO;
	}

}
