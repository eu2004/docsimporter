package ro.eu.documentimporter.repository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import ro.eu.documentimporter.ExistingDocumentImporterActions;
import ro.eu.documentimporter.repository.model.RepositoryDocument;
import ro.eu.documentimporter.repository.model.RepositoryEntityIdAttribute;

@Service
@PropertySource("classpath:app.properties")
public abstract class RepositoryDocumentService {
	private static final Logger logger = Logger.getLogger(RepositoryDocumentService.class);
	@Value("${importer.action.in.case.exists}")
	private String importerActionInCaseExists;
	
	public RepositoryEntityIdAttribute importDocument(RepositoryDocument document) throws RepositoryDocumentServiceException {
		if (document == null) {
			throw new RepositoryDocumentServiceException("Document cannot be NULL!");
		}

		try {
			// check if document already exists
			RepositoryDocument existingDocument = getRepositoryDocumentDAO().getDocumentByCriteria(document.getFindCriteria());
			if (existingDocument == null) {
				// if objects not exists create a new one
				logger.debug(document + " not exists create a new document");
				return getRepositoryDocumentDAO().createDocument(document).getIdAttributeValue();
			} else {
				if (getImporterActionInCaseExists() == null) {
					logger.error(document
							+ " already exists but no action found for it. Please check application configuration file.");
					return null;
				}

				switch (getImporterActionInCaseExists()) {
				case NEW:
					logger.debug(document + " already exists but create a new document");
					return getRepositoryDocumentDAO().createDocument(document).getIdAttributeValue();
				case UPDATE:
					logger.debug(document + " already exists but update it");
					document.setId(existingDocument.getIdAttributeValue());
					return getRepositoryDocumentDAO().updateDocument(document).getIdAttributeValue();
				case REPLACE:
					logger.debug(document + " already exists but replace it");
					return getRepositoryDocumentDAO().replaceDocument(document).getIdAttributeValue();
				case VERSION:
					logger.debug(document + " already exists but create a new version of it");
					document.setId(existingDocument.getIdAttributeValue());
					return getRepositoryDocumentDAO().createDocumentNewVersion(document).getIdAttributeValue();
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

	public abstract RepositoryDocumentDAO getRepositoryDocumentDAO();

	public ExistingDocumentImporterActions getImporterActionInCaseExists() {
		return ExistingDocumentImporterActions.findByActionName(importerActionInCaseExists);
	}

	public void setImporterActionInCaseExistsStr(String importerActionInCaseExists) {
		this.importerActionInCaseExists = importerActionInCaseExists;
	}
}
