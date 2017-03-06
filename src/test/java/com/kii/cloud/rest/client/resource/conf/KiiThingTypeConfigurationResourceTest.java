package com.kii.cloud.rest.client.resource.conf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestAppFilter;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.KiiCredentials;
import com.kii.cloud.rest.client.model.conf.KiiThingTypeConfiguration;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiThingTypeConfigurationResourceTest {
	@Test
	public void test() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		// listing existing configurations
		List<KiiThingTypeConfiguration> configurations = rest.api().configuration().thingTypes().list();
		int existingCount = configurations.size();
		if (existingCount == 0) {
			existingCount = 1;
		}
		
		// adding new thing type configuration
		KiiThingTypeConfiguration conf = new KiiThingTypeConfiguration()
			.setSimpleFlow(true)
			.setVerificationCodeFlowStartedByThing(true)
			.setVerificationCodeFlowStartedByUser(false)
			.setVerificationCodeTimeout(2000L)
			.setVerificationCodeLength(10L);
		
		rest.api().configuration().thingTypes("kii-test-type").save(conf);
		configurations = rest.api().configuration().thingTypes().list();
		
		assertEquals(existingCount, configurations.size());
		
		KiiThingTypeConfiguration actualConf = rest.api().configuration().thingTypes("kii-test-type").get();
		assertTrue(actualConf.isSimpleFlow());
		assertTrue(actualConf.isVerificationCodeFlowStartedByThing());
		assertFalse(actualConf.isVerificationCodeFlowStartedByUser());
		assertEquals(2000, (long)actualConf.getVerificationCodeTimeout());
		assertEquals(10, (long)actualConf.getVerificationCodeLength());
		
//		rest.api().configuration().thingTypes("kii-test-type").remove();
	}
}
