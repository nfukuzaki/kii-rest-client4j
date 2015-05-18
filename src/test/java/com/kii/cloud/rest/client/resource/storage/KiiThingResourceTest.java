package com.kii.cloud.rest.client.resource.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.storage.KiiGroup;
import com.kii.cloud.rest.client.model.storage.KiiGroupMembers;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiThing;
import com.kii.cloud.rest.client.model.storage.KiiThingOwner;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiThingResourceTest {
	@Test
	public void operateByOwnerTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
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
		
		// registering user
		KiiNormalUser user1 = new KiiNormalUser().setUsername("test1-" + System.currentTimeMillis());
		KiiNormalUser user2 = new KiiNormalUser().setUsername("test2-" + System.currentTimeMillis());
		user1 = rest.api().users().register(user1, "password");
		user2 = rest.api().users().register(user2, "password");
		rest.setCredentials(user1);
		
		// creating group
		KiiGroup group = new KiiGroup();
		group.setName("MyGroup");
		group.setOwner(user1);
		KiiGroupMembers members = new KiiGroupMembers();
		members.addMember(user2);
		rest.api().groups().save(group, members);

		// adding owner
		rest.api().things(thingID).owner().add(KiiThingOwner.user(user1));
		rest.api().things(vendorThingID).owner().add(KiiThingOwner.group(group));
		
		// checking existence
		assertTrue(rest.api().things(thingID).exists());
		
		// getting thing
		KiiThing th = rest.api().things(thingID).get();
		assertEquals(vendorThingID, th.getVendorThingID());
		assertEquals("KiiCloud", th.getProductName());
		
		// updating thing
		thing.setProductName("NewKiiCloud");
		rest.api().things(thingID).update(thing);
		th = rest.api().things(thingID).get();
		assertEquals(vendorThingID, th.getVendorThingID());
		assertEquals("NewKiiCloud", th.getProductName());
		
		// enable/disable
		assertFalse(rest.api().things(thingID).isDisabled());
		rest.api().things(thingID).disable();
		assertTrue(rest.api().things(thingID).isDisabled());
		rest.api().things(vendorThingID).enable();
		assertFalse(rest.api().things(thingID).isDisabled());
		
		// removing owner
		assertTrue(rest.api().things(thingID).owner().exists(KiiThingOwner.group(group)));
		rest.api().things(thingID).owner().delete(KiiThingOwner.group(group));
		assertFalse(rest.api().things(vendorThingID).owner().exists(KiiThingOwner.group(group)));
		
		rest.api().things(thingID).delete();
	}
	@Test
	public void operateByThingTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
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
		
		
		// adding owner
		KiiNormalUser user1 = new KiiNormalUser().setUsername("test1-" + System.currentTimeMillis());
		KiiNormalUser user2 = new KiiNormalUser().setUsername("test2-" + System.currentTimeMillis());
		user1 = rest.api().users().register(user1, "password");
		user2 = rest.api().users().register(user2, "password");
		rest.setCredentials(user1);
		rest.api().things(thingID).owner().add(KiiThingOwner.user(user1));
		rest.setCredentials(user2);
		rest.api().things(thingID).owner().add(KiiThingOwner.user(user2));
		
		rest.setCredentials(thing);
		
		// listing owner
		List<KiiThingOwner> owners = rest.api().things(thingID).owner().list();
		assertEquals(2, owners.size());
		
		// checking existence
		assertTrue(rest.api().things(thingID).exists());
		
		// getting thing
		KiiThing th = rest.api().things(thingID).get();
		assertEquals(vendorThingID, th.getVendorThingID());
		assertEquals("KiiCloud", th.getProductName());
		
		// updating thing
		thing.setProductName("NewKiiCloud");
		rest.api().things(thingID).update(thing);
		th = rest.api().things(thingID).get();
		assertEquals(vendorThingID, th.getVendorThingID());
		assertEquals("NewKiiCloud", th.getProductName());
		
		rest.api().things(thingID).delete();
	}
}
