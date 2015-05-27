package com.kii.cloud.rest.client.resource.push;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestAppFilter;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.exception.KiiNotFoundException;
import com.kii.cloud.rest.client.model.push.KiiMqttEndpoint;
import com.kii.cloud.rest.client.model.push.KiiPushInstallation;
import com.kii.cloud.rest.client.model.push.KiiPushInstallation.InstallationType;
import com.kii.cloud.rest.client.model.storage.KiiThing;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiPushInstallationResourceTest {
	@Test
	public void mqttTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		String vendorThingID = "thing-" + System.currentTimeMillis();
		String password = "pa$$word";
		
		// registering thing
		KiiThing thing = new KiiThing()
			.setVendorThingID(vendorThingID)
			.setProductName("KiiCloud")
			.setPassword(password);
		thing = rest.api().things().register(thing);
		String thingID = thing.getThingID();
		rest.setCredentials(thing);
		
		// installing MQTT
		KiiPushInstallation pushInstallation1 = new KiiPushInstallation()
			.setDevelopment(true)
			.setInstallationType(InstallationType.MQTT);
		rest.api().installations().register(pushInstallation1);
		
		KiiPushInstallation pushInstallation2 = rest.api().installations(pushInstallation1).get();
		assertEquals(pushInstallation1.getInstallationID(), pushInstallation2.getInstallationID());
		assertEquals(pushInstallation1.getInstallationRegistrationID(), pushInstallation2.getInstallationRegistrationID());
		assertEquals(InstallationType.MQTT, pushInstallation1.getInstallationType());
		assertEquals(InstallationType.MQTT, pushInstallation2.getInstallationType());
		assertEquals(pushInstallation1.getInstallationID(), pushInstallation2.getInstallationID());
		assertEquals(thingID, pushInstallation2.getThingID());
		
		KiiMqttEndpoint mqttEndpoint = rest.api().installations(pushInstallation2).getMqttEndpoint();
		assertEquals(pushInstallation2.getInstallationID(), mqttEndpoint.getInstallationID());
		
		rest.api().installations(pushInstallation2).delete();
		
		try {
			rest.api().installations(pushInstallation2).get();
			fail("KiiNotFoundException should be thrown");
		} catch (KiiNotFoundException e) {
		}
	}
}
