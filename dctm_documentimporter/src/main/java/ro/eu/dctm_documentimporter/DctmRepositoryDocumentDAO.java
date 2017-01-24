package ro.eu.dctm_documentimporter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.documentum.com.DfClientX;
import com.documentum.com.IDfClientX;
import com.documentum.fc.client.DfIdNotFoundException;
import com.documentum.fc.client.IDfPersistentObject;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfId;

import ro.eu.documentimporter.repository.RepositoryDocumentDAO;
import ro.eu.documentimporter.repository.RepositoryException;
import ro.eu.documentimporter.repository.model.RepositoryDocument;
import ro.eu.documentimporter.repository.model.RepositoryEntityIdAttribute;

@Component
@Scope("prototype")
public class DctmRepositoryDocumentDAO implements RepositoryDocumentDAO, IDctmSession {
	private static final Logger logger = Logger.getLogger(DctmRepositoryDocumentDAO.class);
	private static final IDfClientX dctmClientX = new DfClientX();
	
	private IDfSession session;
	
	@Autowired
	private DctmConverter dctmConverter;

	@Override
	public RepositoryDocument getDocumentByCriteria(String findCriteria) throws RepositoryException {
		if (findCriteria == null) {
			return null;//TODO made use of optional
		}
		IDfPersistentObject object;
		try {
			object = session.getObjectByQualification(findCriteria);
		} catch(DfIdNotFoundException e) {
			return null;
		} catch (DfException e) {
			logger.error("Error getting object " + e.getMessage(), e);
			throw new RepositoryException(e);
		}
		return dctmConverter.convert(object, new DctmDocument());
	}

	@Override
	public RepositoryDocument getDocumentById(RepositoryEntityIdAttribute id) {
		IDfPersistentObject object;
		try {
			object = session.getObject(dctmClientX.getId(id.getValue()));
		} catch(DfIdNotFoundException e) {
			return null;
		} catch (DfException e) {
			logger.error("Error getting object " + e.getMessage(), e);
			throw new RepositoryException(e);
		}
		return dctmConverter.convert(object, new DctmDocument());
	}

	@Override
	public RepositoryDocument createDocument(RepositoryDocument document) {
		IDfPersistentObject object;
		try {
			object = session.newObject(document.getType());
			object = dctmConverter.convert((DctmDocument) document, object);
			object.save();
		} catch (DfException e) {
			logger.error("Error getting object " + e.getMessage(), e);
			throw new RepositoryException(e);
		}
		return dctmConverter.convert(object, new DctmDocument());
	}

	@Override
	public RepositoryDocument createDocumentNewVersion(RepositoryDocument document) {
		IDfSysObject object;
		try {
			object = (IDfSysObject) session.getObject(dctmClientX.getId(document.getId()));
			object.checkout();
			object = (IDfSysObject) dctmConverter.convert((DctmDocument) document, object);
			IDfId newId = object.checkin(false, null);
			object = (IDfSysObject) session.getObject(newId);
		} catch(DfIdNotFoundException e) {
			throw new RepositoryException(e);
		} catch (DfException e) {
			logger.error("Error createDocumentNewVersion " + e.getMessage(), e);
			throw new RepositoryException(e);
		}
		return dctmConverter.convert(object, new DctmDocument());
	}

	@Override
	public RepositoryDocument updateDocument(RepositoryDocument document) {
		IDfSysObject object;
		try {
			object = (IDfSysObject) session.getObject(dctmClientX.getId(document.getId()));
			object = (IDfSysObject) dctmConverter.convert((DctmDocument) document, object);
			object.save();
		} catch(DfIdNotFoundException e) {
			throw new RepositoryException(e);
		} catch (DfException e) {
			logger.error("Error createDocumentNewVersion " + e.getMessage(), e);
			throw new RepositoryException(e);
		}
		return dctmConverter.convert(object, new DctmDocument());
	}

	@Override
	public RepositoryDocument replaceDocument(RepositoryDocument document) {
		IDfSysObject object;
		try {
			object = (IDfSysObject) session.getObjectByQualification(document.getFindCriteria());
			if (object == null) {
				logger.info("No object found for criteria " + document.getFindCriteria());
				return null;
			}
			object.destroyAllVersions();
			return createDocument(document);
		} catch (DfException e) {
			logger.error("Error createDocumentNewVersion " + e.getMessage(), e);
			throw new RepositoryException(e);
		}
	}

	public IDfSession getSession() {
		logger.debug("getSession " + session);
		return session;
	}

	public void setSession(IDfSession session) {
		logger.debug("setSession " + session);
		this.session = session;
	}
}