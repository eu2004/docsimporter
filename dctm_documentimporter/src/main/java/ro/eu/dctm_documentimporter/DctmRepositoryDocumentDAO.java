package ro.eu.dctm_documentimporter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;

import ro.eu.documentimporter.repository.RepositoryDocumentDAO;
import ro.eu.documentimporter.repository.model.RepositoryDocument;
import ro.eu.documentimporter.repository.model.RepositoryEntityIdAttribute;

@Component
@Scope("prototype")
public class DctmRepositoryDocumentDAO implements RepositoryDocumentDAO, IDctmSession {
	private static final Logger logger = Logger.getLogger(DctmRepositoryDocumentDAO.class);
	
	private IDfSession session;

	@Override
	public RepositoryDocument getDocumentByCriteria(String findCriteria) {
		System.out.println(session);
		return null;
	}

	@Override
	public RepositoryDocument getDocumentById(RepositoryEntityIdAttribute id) {
		return null;
	}

	@Override
	public RepositoryDocument createDocument(RepositoryDocument document) {
		return null;
	}

	@Override
	public RepositoryDocument createDocumentNewVersion(RepositoryDocument document) {
		return null;
	}

	@Override
	public RepositoryDocument updateDocument(RepositoryDocument document) {
		return null;
	}

	@Override
	public RepositoryDocument replaceDocument(RepositoryDocument document) {
		return null;
	}

	public IDfSession getSession() {
		return session;
	}

	public void setSession(IDfSession session) {
		this.session = session;
	}
}