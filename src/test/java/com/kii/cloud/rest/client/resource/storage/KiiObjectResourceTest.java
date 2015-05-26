package com.kii.cloud.rest.client.resource.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.exception.KiiConflictException;
import com.kii.cloud.rest.client.model.storage.KiiGroup;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiObject;
import com.kii.cloud.rest.client.model.storage.KiiThing;
import com.kii.cloud.rest.client.model.storage.KiiThingOwner;


@RunWith(SkipAcceptableTestRunner.class)
public class KiiObjectResourceTest {
	@Test
	public void appScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String appBucketName = "app_bucket" + System.currentTimeMillis();
		
		// creating object
		KiiObject object1 = new KiiObject().set("score", 100);
		rest.api().buckets(appBucketName).objects().save(object1);
		
		// check object
		boolean exists = rest.api().buckets(appBucketName).objects(object1.getObjectID()).exists();
		assertTrue(exists);
		
		// updating object
		object1.set("score", 200);
		rest.api().buckets(appBucketName).objects(object1).update(object1);
		
		// getting object
		KiiObject object2 = rest.api().buckets(appBucketName).objects(object1).get();
		assertEquals(200, (int)object2.getInt("score"));
		
		// partial updating
		KiiObject object3 = new KiiObject().set("level", 1);
		rest.api().buckets(appBucketName).objects(object2).partialUpdate(object3);
		
		// getting object
		KiiObject object4 = rest.api().buckets(appBucketName).objects(object1).get();
		assertEquals(200, (int)object4.getInt("score"));
		assertEquals(1, (int)object4.getInt("level"));
		
		// deleting object
		rest.api().buckets(appBucketName).objects(object4).delete();
		
		// deleting bucket
		rest.api().buckets(appBucketName).delete();
	}
	@Test
	public void groupScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		KiiGroup group = new KiiGroup();
		group.setName("MyGroup");
		group.setOwner(user.getUserID());
		rest.api().groups().save(group, null);
		
		String groupBucketName = "group_bucket" + System.currentTimeMillis();
		
		// creating object
		KiiObject object1 = new KiiObject().set("score", 100);
		rest.api().groups(group).buckets(groupBucketName).objects().save(object1);
		
		// check object
		boolean exists = rest.api().groups(group).buckets(groupBucketName).objects(object1.getObjectID()).exists();
		assertTrue(exists);
		
		// updating object
		object1.set("score", 200);
		rest.api().groups(group).buckets(groupBucketName).objects(object1).update(object1);
		
		// getting object
		KiiObject object2 = rest.api().groups(group).buckets(groupBucketName).objects(object1).get();
		assertEquals(200, (int)object2.getInt("score"));
		
		// partial updating
		KiiObject object3 = new KiiObject().set("level", 1);
		rest.api().groups(group).buckets(groupBucketName).objects(object2).partialUpdate(object3);
		
		// getting object
		KiiObject object4 = rest.api().groups(group).buckets(groupBucketName).objects(object1).get();
		assertEquals(200, (int)object4.getInt("score"));
		assertEquals(1, (int)object4.getInt("level"));
		
		// deleting object
		rest.api().groups(group).buckets(groupBucketName).objects(object4).delete();
		
		// deleting bucket
		rest.api().groups(group).buckets(groupBucketName).delete();
	}
	@Test
	public void userScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketName = "user_bucket" + System.currentTimeMillis();
		
		// creating object
		KiiObject object1 = new KiiObject().set("score", 100);
		rest.api().users(user).buckets(userBucketName).objects().save(object1);
		
		// check object
		boolean exists = rest.api().users(user).buckets(userBucketName).objects(object1.getObjectID()).exists();
		assertTrue(exists);
		
		// updating object
		object1.set("score", 200);
		rest.api().users(user).buckets(userBucketName).objects(object1).update(object1);
		
		// getting object
		KiiObject object2 = rest.api().users(user).buckets(userBucketName).objects(object1).get();
		assertEquals(200, (int)object2.getInt("score"));
		
		// partial updating
		KiiObject object3 = new KiiObject().set("level", 1);
		rest.api().users(user).buckets(userBucketName).objects(object2).partialUpdate(object3);
		
		// getting object
		KiiObject object4 = rest.api().users(user).buckets(userBucketName).objects(object1).get();
		assertEquals(200, (int)object4.getInt("score"));
		assertEquals(1, (int)object4.getInt("level"));
		
		// deleting object
		rest.api().users(user).buckets(userBucketName).objects(object4).delete();
		
		// deleting bucket
		rest.api().users(user).buckets(userBucketName).delete();
	}
	@Test
	public void thingScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
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
		
		rest.setCredentials(user);
		
		// adding owner
		rest.api().things(thingID).owner().add(KiiThingOwner.user(user));

		String thingBucketName = "thing_bucket" + System.currentTimeMillis();
		
		// creating object
		KiiObject object1 = new KiiObject().set("score", 100);
		rest.api().things(thing).buckets(thingBucketName).objects().save(object1);
		
		// check object
		boolean exists = rest.api().things(thing).buckets(thingBucketName).objects(object1.getObjectID()).exists();
		assertTrue(exists);
		
		// updating object
		object1.set("score", 200);
		rest.api().things(thing).buckets(thingBucketName).objects(object1).update(object1);
		
		// getting object
		KiiObject object2 = rest.api().things(thing).buckets(thingBucketName).objects(object1).get();
		assertEquals(200, (int)object2.getInt("score"));
		
		// partial updating
		KiiObject object3 = new KiiObject().set("level", 1);
		rest.api().things(thing).buckets(thingBucketName).objects(object2).partialUpdate(object3);
		
		// getting object
		KiiObject object4 = rest.api().things(thing).buckets(thingBucketName).objects(object1).get();
		assertEquals(200, (int)object4.getInt("score"));
		assertEquals(1, (int)object4.getInt("level"));
		
		// deleting object
		rest.api().things(thing).buckets(thingBucketName).objects(object4).delete();
		
		// deleting bucket
		rest.api().things(thing).buckets(thingBucketName).delete();
	}
	@Test
	public void updateWithOptimisticLockTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketName = "user_bucket" + System.currentTimeMillis();
		
		// creating object
		KiiObject object1 = new KiiObject().set("score", 100);
		rest.api().users(user).buckets(userBucketName).objects().save(object1);
		
		// partial updating
		KiiObject partialObject = new KiiObject().set("level", 2);
		rest.api().users(user).buckets(userBucketName).objects(object1.getObjectID()).partialUpdate(partialObject);
		
		// updating object with optimistic lock
		try {
			rest.api().users(user).buckets(userBucketName).objects(object1.getObjectID()).updateWithOptimisticLock(object1);
			fail("KiiConflictException should be thrown");
		} catch (KiiConflictException e) {
		}
		
		// getting object
		KiiObject object2 = rest.api().users(user).buckets(userBucketName).objects(object1.getObjectID()).get();
		
		assertEquals(100, (int)object2.getInt("score"));
		assertEquals(2, (int)object2.getInt("level"));
		
		// updating object with optimistic lock
		object2.set("score", 200);
		object2.set("level", 3);
		rest.api().users(user).buckets(userBucketName).objects(object2.getObjectID()).updateWithOptimisticLock(object2);
		
		// getting object
		KiiObject object3 = rest.api().users(user).buckets(userBucketName).objects(object2.getObjectID()).get();
		
		assertEquals(200, (int)object3.getInt("score"));
		assertEquals(3, (int)object3.getInt("level"));
	}
	@Test
	public void partialUupdateWithOptimisticLockTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketName = "user_bucket" + System.currentTimeMillis();
		
		// creating object
		KiiObject object1 = new KiiObject().set("score", 100);
		rest.api().users(user).buckets(userBucketName).objects().save(object1);
		String version = object1.getVersion();
		
		// updating object
		object1.set("level", 1);
		rest.api().users(user).buckets(userBucketName).objects(object1.getObjectID()).partialUpdate(object1);
		
		// partial updating object with optimistic lock
		try {
			KiiObject partialObject = new KiiObject().set("level", 2).setVersion(version);
			rest.api().users(user).buckets(userBucketName).objects(object1.getObjectID()).partialUpdateWithOptimisticLock(partialObject);
			fail("KiiConflictException should be thrown");
		} catch (KiiConflictException e) {
		}
		
		// getting object
		KiiObject object2 = rest.api().users(user).buckets(userBucketName).objects(object1.getObjectID()).get();
		
		assertEquals(100, (int)object2.getInt("score"));
		assertEquals(1, (int)object2.getInt("level"));
		
		// partial updating object with optimistic lock
		KiiObject partialObject = new KiiObject().set("level", 3).setVersion(object2.getVersion());
		rest.api().users(user).buckets(userBucketName).objects(object1.getObjectID()).partialUpdateWithOptimisticLock(partialObject);
		
		// getting object
		KiiObject object3 = rest.api().users(user).buckets(userBucketName).objects(object2.getObjectID()).get();
		
		assertEquals(100, (int)object3.getInt("score"));
		assertEquals(3, (int)object3.getInt("level"));
	}
}
