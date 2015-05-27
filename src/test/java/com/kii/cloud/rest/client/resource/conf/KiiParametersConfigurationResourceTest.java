package com.kii.cloud.rest.client.resource.conf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestAppFilter;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.KiiCredentials;
import com.kii.cloud.rest.client.model.conf.KiiAppConfigurationParameter;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiParametersConfigurationResourceTest {
	@Test
	public void getAllTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		KiiAppConfigurationParameter parameter = rest.api().configuration().parameters().get();
		assertTrue(parameter.has(KiiAppConfigurationParameter.PROPERTY_IS_MASTER_APP));
		assertTrue(parameter.has(KiiAppConfigurationParameter.PROPERTY_IS_THING_TYPE_CONFIGURATION_REQUIRED));
		assertTrue(parameter.has(KiiAppConfigurationParameter.PROPERTY_PHONE_NUMBER_VERIFICATION_REQUIRED));
		assertTrue(parameter.has(KiiAppConfigurationParameter.PROPERTY_GCM_COLLAPSE_KEY_DEFAULT_BEHAVIOR));
		assertTrue(parameter.has(KiiAppConfigurationParameter.PROPERTY_RESERVED_FIELDS_VALIDATION));
		assertTrue(parameter.has(KiiAppConfigurationParameter.PROPERTY_PASSWORD_RESET_METHOD));
		assertTrue(parameter.has(KiiAppConfigurationParameter.PROPERTY_REFRESH_TOKEN_ENABLED));
		assertTrue(parameter.has(KiiAppConfigurationParameter.PROPERTY_EMAIL_ADDRESS_VERIFICATION_REQUIRED));
		assertTrue(parameter.has(KiiAppConfigurationParameter.PROPERTY_DEFAULT_TOKEN_EXPIRATION_SECONDS));
		assertTrue(parameter.has(KiiAppConfigurationParameter.PROPERTY_EXPOSE_FULL_USER_DATA_TO_OTHERS));
		assertTrue(parameter.has(KiiAppConfigurationParameter.PROPERTY_MAX_TOKEN_EXPIRATION_SECONDS));
		assertTrue(parameter.has(KiiAppConfigurationParameter.PROPERTY_SEND_REFERRAL_FOR_LONG_APNS));
		assertTrue(parameter.has(KiiAppConfigurationParameter.PROPERTY_EMAIL_ADDRESS));
		assertTrue(parameter.has(KiiAppConfigurationParameter.PROPERTY_PASSWORD_RESET_TIMEOUT_SECONDS));
	}
	@Test
	public void setTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		KiiAppConfigurationParameter parameter = rest.api().configuration().parameters().get();
		boolean isMasterApp = parameter.isMasterApp();
		
		try {
			rest.api().configuration().parameters().set(KiiAppConfigurationParameter.PROPERTY_IS_MASTER_APP, !isMasterApp);
			parameter = rest.api().configuration().parameters().get();
			assertEquals(!isMasterApp, parameter.isMasterApp());
		} finally {
			rest.api().configuration().parameters().set(KiiAppConfigurationParameter.PROPERTY_IS_MASTER_APP, isMasterApp);
		}
	}
}
