package com.kii.cloud.rest.client.resource.social;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.OkHttpClientFactory;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestAppFilter;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.social.KiiSocialProvider;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiWebAuthSocialIntegrationResourceTest {
	@Test
	public void facebookTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().enableFacebook());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		String expectedBaseUrl = String.format("%s/apps/%s/integration/webauth/", testApp.getSite().getEndpoint(), testApp.getAppID());
		String webauthUrl = rest.api().webauth().getSocialIntegrationUrl(KiiSocialProvider.FACEBOOK);
		
		assertEquals(expectedBaseUrl + "connect?id=" + KiiSocialProvider.FACEBOOK.getID(), webauthUrl);
		
		OkHttpClient client = OkHttpClientFactory.newInstance();
		Request request = new Request.Builder().url(webauthUrl).get().build();
		Response response = client.newCall(request).execute();
		assertTrue(response.isSuccessful());
	}
}
