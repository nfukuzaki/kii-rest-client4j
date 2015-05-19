package com.kii.cloud.rest.client.resource.servercode;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestAppFilter;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.KiiAdminCredentials;
import com.kii.cloud.rest.client.model.servercode.KiiDevlog;
import com.kii.cloud.rest.client.model.servercode.KiiDevlogFilter;
import com.kii.cloud.rest.client.model.servercode.KiiDevlog.LogLevel;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiObject;
import com.kii.cloud.rest.client.resource.servercode.KiiDevlogResource.TailContext;
import com.kii.cloud.rest.client.resource.servercode.KiiDevlogResource.TailListener;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiDevlogResourceTest {
	@Test
	public void catTest() throws Exception {
		final TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		final KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
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
		final TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiAdminCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		final CountDownLatch latch = new CountDownLatch(3);
		final List<KiiDevlog> results = new ArrayList<KiiDevlog>();
		final AtomicReference<Exception> exception = new AtomicReference<Exception>();
		
		rest.logs().tail(new TailListener() {
			@Override
			public void onTail(List<KiiDevlog> logs, TailContext context) {
				results.addAll(logs);
				latch.countDown();
			}
			
			@Override
			public void onFailure(Exception e) {
				exception.set(e);
			}
		});
		
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
					KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
					user = rest.api().users().register(user, "password");
					rest.setCredentials(user);
					for (int i = 0; i < 100; i++) {
						rest.api().users(user).buckets("my_bucket").objects().save(new KiiObject().set("no", i));
						Thread.sleep(100);
					}
				} catch (Exception ignore) {
				}
			}
		});
		th.start();
		
		try {
			if (!latch.await(60, TimeUnit.SECONDS)) {
				fail("Test timeouts");
			}
			assertTrue(results.size() > 10);
		} finally {
			th.interrupt();
		}
	}
}
