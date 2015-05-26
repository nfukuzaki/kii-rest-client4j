package com.kii.cloud.rest.client.resource.social;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestAppFilter;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.social.KiiSocialAccountInfo;
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
		KiiUser user = rest.api().social().twitter().login(testApp.getTwitterAccessToken(), testApp.getTwitterAccessTokenSecret());
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
		
		// link with current user to twitter account.
		rest.api().users(user).twitter().link(testApp.getTwitterAccessToken(), testApp.getTwitterAccessTokenSecret());
		
		user = (KiiNormalUser)rest.api().users(user).get();
		List<KiiSocialAccountInfo> thirdPartyAccounts = user.getThirdPartyAccounts();
		assertEquals(1, thirdPartyAccounts.size());
		for (KiiSocialAccountInfo thirdPartyAccount : thirdPartyAccounts) {
			assertNotNull(thirdPartyAccount.getID());
			assertEquals(KiiSocialProvider.TWITTER, thirdPartyAccount.getType());
			assertNotNull(thirdPartyAccount.getCreatedAt());
		}
		
		// unlink with current user to twitter account.
		rest.api().users(user).twitter().unlink();
	}
}
