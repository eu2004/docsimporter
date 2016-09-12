package ro.eu.dctm_documentimporter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.documentum.fc.client.DfAuthenticationException;
import com.documentum.fc.client.DfIdentityException;
import com.documentum.fc.client.DfPrincipalException;
import com.documentum.fc.client.DfServiceException;
import com.documentum.fc.client.IDfSessionManager;

@Aspect
public class DctmSessionAspect {
	@Autowired
	private IDfSessionManager sessionManager;

	@Autowired
	private Environment env;

	@Before("execution(* ro.eu.dctm_documentimporter.DctmRepositoryDocumentDAO.*(..))")
	public void setSession(JoinPoint joinPoint)
			throws DfIdentityException, DfAuthenticationException, DfPrincipalException, DfServiceException {
		IDctmSession dctmSession = (IDctmSession) joinPoint.getTarget();
		dctmSession.setSession(sessionManager.getSession(env.getProperty("dctm.docbase")));
	}

	@After("execution(* ro.eu.dctm_documentimporter.DctmRepositoryDocumentDAO.*(..))")
	public void releaseSession(JoinPoint joinPoint)
			throws DfIdentityException, DfAuthenticationException, DfPrincipalException, DfServiceException {
		IDctmSession dctmSession = (IDctmSession) joinPoint.getTarget();
		sessionManager.release(dctmSession.getSession());
	}
}
