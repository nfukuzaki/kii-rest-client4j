package com.kii.cloud.rest.client.resource.social;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestAppFilter;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.social.KiiSocialProvider;
import com.kii.cloud.rest.client.model.storage.KiiUser;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiNativeSocialIntegrationResourceTest {
	@Test
	public void twitterTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().enableTwitterNative().hasTwitterAccessToken().hasTwitterAccessTokenSecret());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		// login with Twitter account
		KiiUser user = rest.api().social(KiiSocialProvider.TWITTER).loginWithTwitter(testApp.getTwitterAccessToken(), testApp.getTwitterAccessTokenSecret());
		rest.setCredentials(user);
		
		// deleting user
		rest.api().users(user).delete();
	}
}
