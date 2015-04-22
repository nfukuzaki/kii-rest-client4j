package com.kii.cloud.resource.servercode;

import java.text.DateFormat;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.KiiRest;
import com.kii.cloud.SkipAcceptableTestRunner;
import com.kii.cloud.TestApp;
import com.kii.cloud.TestAppFilter;
import com.kii.cloud.TestEnvironments;
import com.kii.cloud.model.KiiAdminCredentials;
import com.kii.cloud.model.servercode.KiiDevlog;
import com.kii.cloud.model.servercode.KiiDevlogFilter;
import com.kii.cloud.model.servercode.KiiDevlog.LogLevel;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiDevlogResourceTest {
	@Test
	public void catTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiAdminCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		List<KiiDevlog> logs = rest.logs().cat(
				new KiiDevlogFilter()
				.level(LogLevel.INFO)
				.from(DateFormat.getDateInstance().parse("2015/4/20"))
				.to(DateFormat.getDateInstance().parse("2015/4/22")));
		Collections.sort(logs);
		for (KiiDevlog log : logs) {
			System.out.println(log);
		}
	}
	@Test
	public void tailTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiAdminCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
	}
}
