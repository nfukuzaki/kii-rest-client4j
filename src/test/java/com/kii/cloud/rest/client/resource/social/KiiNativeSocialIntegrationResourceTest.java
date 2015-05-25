package com.kii.cloud.rest.client.resource.social;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestAppFilter;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.social.KiiNativeSocialCredentials;
import com.kii.cloud.rest.client.model.social.KiiSocialProvider;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiUser;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiNativeSocialIntegrationResourceTest {
	@Test
	public void twitterLoginTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().enableTwitterNative().hasTwitterAccessToken().hasTwitterAccessTokenSecret());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		// login with Twitter account
		KiiNativeSocialCredentials credentials = KiiNativeSocialCredentials.twitter(testApp.getTwitterAccessToken(), testApp.getTwitterAccessTokenSecret());
		KiiUser user = rest.api().social().login(credentials);
		rest.setCredentials(user);
		
		// deleting user
		rest.api().users(user).delete();
	}
	@Test
	public void twitterLoginLink() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().enableTwitterNative().hasTwitterAccessToken().hasTwitterAccessTokenSecret());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		KiiNativeSocialCredentials credentials = KiiNativeSocialCredentials.twitter(testApp.getTwitterAccessToken(), testApp.getTwitterAccessTokenSecret());
		rest.api().users(user).socialIntegration().link(credentials);
		
		rest.api().users(user).socialIntegration().unlink(KiiSocialProvider.TWITTER);
	}
}
