package com.kii.cloud.rest.client.model;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiThing;
import com.kii.cloud.rest.client.model.storage.KiiThingIdentifierType;
import com.kii.cloud.rest.client.model.uri.KiiThingURI;
import com.kii.cloud.rest.client.model.uri.KiiURI;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiThingURITest {
	@Test
	public void thingIdTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String vendorThingID = "thing-" + System.currentTimeMillis();
		String password = "pa$$word";
		
		// registering thing
		KiiThing thing1 = new KiiThing()
			.setVendorThingID(vendorThingID)
			.setProductName("KiiCloud")
			.setPassword(password);
		thing1 = rest.api().things().register(thing1);
		rest.setCredentials(thing1);
		
		String expectedUriString = String.format("kiicloud://%s/things/%s", testApp.getAppID(), thing1.getThingID());
		assertEquals(expectedUriString, thing1.getURI().toUriString());
		assertEquals(thing1.getURI(), KiiURI.parse(expectedUriString));
		assertEquals(thing1.getURI(), KiiThingURI.parse(expectedUriString));
		assertEquals(KiiThingIdentifierType.THING_ID, thing1.getURI().getIdentifierType());
		
		KiiThing thing2 = rest.api().things(thing1.getURI()).get();
		assertEquals(thing1.getThingID(), thing2.getThingID());
		assertEquals(thing1.getURI(), thing2.getURI());
	}
	@Test
	public void thingIdentifierTypeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		
		String thingID = "th." + UUID.randomUUID().toString();
		String vendorThingID = UUID.randomUUID().toString();
		
		String idURI = String.format("kiicloud://%s/things/%s", testApp.getAppID(), KiiThingIdentifierType.THING_ID.getFullyQualifiedIdentifier(thingID));
		String vendorURI = String.format("kiicloud://%s/things/%s", testApp.getAppID(), KiiThingIdentifierType.VENDOR_THING_ID.getFullyQualifiedIdentifier(vendorThingID));
		
		assertEquals(KiiThingIdentifierType.THING_ID, KiiThingURI.parse(idURI).getIdentifierType());
		assertEquals(thingID, KiiThingURI.parse(idURI).getIdentifier());
		assertEquals(KiiThingIdentifierType.VENDOR_THING_ID, KiiThingURI.parse(vendorURI).getIdentifierType());
		assertEquals(vendorThingID, KiiThingURI.parse(vendorURI).getIdentifier());
		assertEquals(KiiURI.parse(idURI), KiiThingURI.parse(idURI));
		assertEquals(KiiURI.parse(vendorURI), KiiThingURI.parse(vendorURI));
	}
}
