package com.kii.cloud.rest.client.resource.servercode;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestAppFilter;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.KiiAdminCredentials;
import com.kii.cloud.rest.client.model.servercode.KiiServerHookConfiguration;
import com.kii.cloud.rest.client.model.servercode.KiiServerHookConfiguration.Path;
import com.kii.cloud.rest.client.model.servercode.KiiServerHookConfiguration.SchedulerConfiguration;
import com.kii.cloud.rest.client.model.servercode.KiiServerHookConfiguration.TriggerAction;
import com.kii.cloud.rest.client.model.servercode.KiiServerHookConfiguration.TriggerConfiguration;
import com.kii.cloud.rest.client.model.servercode.KiiServerHookConfiguration.When;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiServerCodeHookResourceTest {
	@Test
	public void test() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiAdminCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		String versionID = rest.api().servercode().deploy(new File(getClass().getResource("get_server_time.js").getPath()));
		rest.api().servercode().setCurrentVersion(versionID);
		
		KiiServerHookConfiguration hookConfig = new KiiServerHookConfiguration();
		hookConfig.addTriggerConfiguration(
				new TriggerConfiguration(Path.user())
				.addTriggerAction(
						new TriggerAction(When.USER_CREATED, "user_created")
				)
		);
		hookConfig.addSchedulerConfiguration(new SchedulerConfiguration("1DayBatch", "1 0 * * *", "daily_batch", null));
		rest.api().hooks(versionID).deploy(hookConfig);
	}
}
