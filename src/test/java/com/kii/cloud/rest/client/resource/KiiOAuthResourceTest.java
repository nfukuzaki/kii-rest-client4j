package com.kii.cloud.rest.client.resource;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestAppFilter;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.exception.KiiBadRequestException;
import com.kii.cloud.rest.client.exception.KiiForbiddenException;
import com.kii.cloud.rest.client.model.KiiCredentials;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiOAuthResourceTest {
	@Test
	public void getAccessTokenTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		String username = "test-" + System.currentTimeMillis();
		KiiNormalUser user = new KiiNormalUser().setUsername(username);
		user = rest.api().users().register(user, "password");
		
		try {
			rest.api().oauth().getAccessToken(username, "xxxxxxxxxxxx");
			fail("KiiBadRequestException should be thrown");
		} catch (KiiBadRequestException e) {
		}
		KiiCredentials credentials = rest.api().oauth().getAccessToken(username, "password");
		assertTrue(credentials.hasCredentials());
	}
	@Test
	public void getAdminAccessToken() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		try {
			rest.api().oauth().getAdminAccessToken(testApp.getClientID(), "xxxxxxxxxxxx");
			fail("KiiBadRequestException should be thrown");
		} catch (KiiBadRequestException e) {
		}
		KiiCredentials credentials = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		assertTrue(credentials.hasCredentials());
	}
	@Test
	public void refreshTokenTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().enableRefreshToken());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		String username = "test-" + System.currentTimeMillis();
		KiiNormalUser user = new KiiNormalUser().setUsername(username);
		user = rest.api().users().register(user, "password");
		String accessToken = user.getAccessToken();
		String refreshToken = user.getRefreshToken();
		assertNotNull(accessToken);
		assertNotNull(refreshToken);
		
		rest.setCredentials(new KiiCredentials(accessToken));
		rest.api().users(user).get();
		
		KiiCredentials newCredentials = rest.api().oauth().refreshAccessToken(refreshToken);
		Thread.sleep(3000);
		try {
			rest.api().users(user).get();
			fail("KiiForbiddenException should be thrown");
		} catch (KiiForbiddenException e) {
		}
		
		rest.setCredentials(newCredentials);
		rest.api().users(user).get();
	}
	@Test
	public void refreshTokenAdminTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials().enableRefreshToken());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		String username = "test-" + System.currentTimeMillis();
		KiiNormalUser user = new KiiNormalUser().setUsername(username);
		user = rest.api().users().register(user, "password");
		
		KiiCredentials credentials = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		String accessToken = credentials.getAccessToken();
		String refreshToken = credentials.getRefreshToken();
		assertNotNull(accessToken);
		assertNotNull(refreshToken);
		
		rest.setCredentials(credentials);
		rest.api().users(user).get();
		
		KiiCredentials newCredentials = rest.api().oauth().refreshAccessToken(refreshToken);
		
		try {
			rest.api().users(user).get();
			fail("KiiForbiddenException should be thrown");
		} catch (KiiForbiddenException e) {
		}
		
		rest.setCredentials(newCredentials);
		rest.api().users(user).get();
	}
}
