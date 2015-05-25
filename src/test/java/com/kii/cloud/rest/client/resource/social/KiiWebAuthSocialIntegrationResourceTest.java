package com.kii.cloud.rest.client.resource.social;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.social.KiiSocialProvider;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiWebAuthSocialIntegrationResourceTest {
	@Test
	public void test() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		String expectedBaseUrl = String.format("%s/apps/%s/integration/webauth/", testApp.getSite().getEndpoint(), testApp.getAppID());
		
		assertEquals(
				expectedBaseUrl + "connect?id=" + KiiSocialProvider.FACEBOOK.getID(),
				rest.api().webauth().getSocialIntegrationUrl(KiiSocialProvider.FACEBOOK));
		assertEquals(
				expectedBaseUrl + "link?id=" + KiiSocialProvider.FACEBOOK.getID(),
				rest.api().webauth().getSocialLinkUrl(KiiSocialProvider.FACEBOOK));
		assertEquals(
				expectedBaseUrl + "unlink?id=" + KiiSocialProvider.FACEBOOK.getID(),
				rest.api().webauth().getSocialUnLinkUrl(KiiSocialProvider.FACEBOOK));
		// TODO:send request to the URLs
	}
}
