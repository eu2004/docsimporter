package ro.eu.dctm_documentimporter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

import com.documentum.com.DfClientX;
import com.documentum.com.IDfClientX;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfLoginInfo;
import com.documentum.fc.common.IDfLoginInfo;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan
public class DctmApplicationConfiguration {
	@Autowired
	private Environment env;

	@Bean
	public IDfSessionManager getDocbaseAccessor() throws DfException {
		IDfClientX clientx = new DfClientX();
		IDfClient client = clientx.getLocalClient();
		IDfSessionManager sessionManager = client.newSessionManager();
		IDfLoginInfo loginInfo = new DfLoginInfo();
		loginInfo.setUser(env.getProperty("dctm.user"));
		loginInfo.setPassword(env.getProperty("dctm.pass"));
		sessionManager.setIdentity(env.getProperty("dctm.docbase"), loginInfo);
		return sessionManager;
	}

}
