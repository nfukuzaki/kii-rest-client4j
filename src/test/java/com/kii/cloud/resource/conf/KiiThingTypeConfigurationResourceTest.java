package com.kii.cloud.resource.conf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.KiiRest;
import com.kii.cloud.SkipAcceptableTestRunner;
import com.kii.cloud.TestApp;
import com.kii.cloud.TestAppFilter;
import com.kii.cloud.TestEnvironments;
import com.kii.cloud.model.KiiAdminCredentials;
import com.kii.cloud.model.conf.KiiThingTypeConfiguration;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiThingTypeConfigurationResourceTest {
	@Test
	public void test() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiAdminCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		// listing existing configurations
		List<KiiThingTypeConfiguration> configurations = rest.api().configuration().thingTypes().list();
		int existingCount = configurations.size();
		
		// adding new thing type configuration
		KiiThingTypeConfiguration conf = new KiiThingTypeConfiguration()
			.setSimpleFlow(true)
			.setVerificationCodeFlowStartedByThing(true)
			.setVerificationCodeFlowStartedByUser(false)
			.setVerificationCodeTimeout(2000L)
			.setVerificationCodeLength(10L);
		
		rest.api().configuration().thingTypes("kii-test-type").save(conf);
		configurations = rest.api().configuration().thingTypes().list();
		
		assertEquals(existingCount + 1, configurations.size());
		
		KiiThingTypeConfiguration actualConf = rest.api().configuration().thingTypes("kii-test-type").get();
		assertTrue(actualConf.isSimpleFlow());
		assertTrue(actualConf.isVerificationCodeFlowStartedByThing());
		assertFalse(actualConf.isVerificationCodeFlowStartedByUser());
		assertEquals(2000, (long)actualConf.getVerificationCodeTimeout());
		assertEquals(10, (long)actualConf.getVerificationCodeLength());
		
		rest.api().configuration().thingTypes("kii-test-type").remove();
	}
}
