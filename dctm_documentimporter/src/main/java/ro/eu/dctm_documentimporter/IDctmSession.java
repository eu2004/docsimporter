package ro.eu.dctm_documentimporter;

import com.documentum.fc.client.IDfSession;

public interface IDctmSession {
	public IDfSession getSession();

	public void setSession(IDfSession session);
}
