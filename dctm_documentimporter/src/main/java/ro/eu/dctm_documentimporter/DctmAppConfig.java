package ro.eu.dctm_documentimporter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.documentum.com.DfClientX;
import com.documentum.com.IDfClientX;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfLoginInfo;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"ro.eu.dctm_documentimporter"})
@PropertySource("classpath:app.properties")
public class DctmAppConfig {
	@Autowired
	private Environment env;

	@Bean
	public IDfSessionManager getIDfSessionManager() throws DfException {
		IDfClientX clientx = new DfClientX();
		IDfClient client = clientx.getLocalClient();
		IDfSessionManager sessionManager = client.newSessionManager();
		IDfLoginInfo loginInfo = clientx.getLoginInfo();
		loginInfo.setUser(env.getProperty("repository.user"));
		loginInfo.setPassword(env.getProperty("repository.pass"));
		sessionManager.setIdentity(env.getProperty("repository.name"), loginInfo);
		return sessionManager;
	}
}
