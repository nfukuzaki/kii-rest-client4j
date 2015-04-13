package com.kii.cloud.thing;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.KiiRest;
import com.kii.cloud.SkipAcceptableTestRunner;
import com.kii.cloud.TestApp;
import com.kii.cloud.TestEnvironments;
import com.kii.cloud.model.storage.KiiNormalUser;
import com.kii.cloud.model.storage.KiiThing;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiThingResourceTest {
	@Test
	public void operateByUserTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		String vendorThingID = "thing-" + System.currentTimeMillis();
		String password = "pa$$word";
		
		KiiThing thing = new KiiThing()
			.setVendorThingID(vendorThingID)
			.setPassword(password);
		
		rest.api().things().register(thing);
		
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);

	}
	@Test
	public void operateByThingTest() throws Exception {
	}
}
