//package com.kii.cloud.rest.client.model;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import com.kii.cloud.rest.client.KiiRest;
//import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
//import com.kii.cloud.rest.client.TestApp;
//import com.kii.cloud.rest.client.TestEnvironments;
//import com.kii.cloud.rest.client.model.storage.KiiGroup;
//import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
//import com.kii.cloud.rest.client.model.storage.KiiObject;
//import com.kii.cloud.rest.client.model.storage.KiiThing;
//import com.kii.cloud.rest.client.model.storage.KiiThingOwner;
//import com.kii.cloud.rest.client.util.KiiURIUtils;
//
//@RunWith(SkipAcceptableTestRunner.class)
//public class KiiObjectURITest {
//	@Test
//	public void appScopeTest() throws Exception {
//		TestApp testApp = TestEnvironments.random();
//		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
//		
//		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
//		user = rest.api().users().register(user, "password");
//		rest.setCredentials(user);
//		
//		String appBucketName = "app_bucket" + System.currentTimeMillis();
//		
//		// creating object
//		KiiObject object1 = new KiiObject().set("score", 100);
//		rest.api().buckets(appBucketName).objects().save(object1);
//		
//		KiiObjectURI uri = KiiURIUtils.createAppScopeObject(appBucketName, object1.getObjectID());
//		
//		String expectedUriString = String.format("kiicloud://buckets/%s/objects/%s", appBucketName, object1.getObjectID());
//		assertEquals(expectedUriString, uri.toString());
//		assertEquals(KiiURI.parse(expectedUriString), KiiObjectURI.create(expectedUriString));
//		
//		KiiObject object2 = rest.api().objects(uri).get();
//		assertEquals(object1.getObjectID(), object2.getObjectID());
//		assertEquals(object1.getInt("score"), object2.getInt("score"));
//		
//		rest.api().buckets(appBucketName).delete();
//	}
//	@Test
//	public void groupScopeTest() throws Exception {
//		TestApp testApp = TestEnvironments.random();
//		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
//		
//		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
//		user = rest.api().users().register(user, "password");
//		rest.setCredentials(user);
//		
//		KiiGroup group = new KiiGroup();
//		group.setName("MyGroup");
//		group.setOwner(user.getUserID());
//		rest.api().groups().save(group, null);
//		
//		String groupBucketName = "group_bucket" + System.currentTimeMillis();
//		
//		// creating object
//		KiiObject object1 = new KiiObject().set("score", 100);
//		rest.api().groups(group).buckets(groupBucketName).objects().save(object1);
//		
//		KiiObjectURI uri = KiiURIUtils.createGroupScopeObject(group.getGroupID(), groupBucketName, object1.getObjectID());
//		
//		String expectedUriString = String.format("kiicloud://groups/%s/buckets/%s/objects/%s", group.getGroupID(), groupBucketName, object1.getObjectID());
//		assertEquals(expectedUriString, uri.toString());
//		assertEquals(KiiURI.parse(expectedUriString), KiiObjectURI.create(expectedUriString));
//		
//		KiiObject object2 = rest.api().objects(uri).get();
//		assertEquals(object1.getObjectID(), object2.getObjectID());
//		assertEquals(object1.getInt("score"), object2.getInt("score"));
//		
//		rest.api().groups(group).buckets(groupBucketName).delete();
//	}
//	@Test
//	public void userScopeTest() throws Exception {
//		TestApp testApp = TestEnvironments.random();
//		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
//		
//		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
//		user = rest.api().users().register(user, "password");
//		rest.setCredentials(user);
//		
//		String userBucketName = "user_bucket" + System.currentTimeMillis();
//		
//		// creating object
//		KiiObject object1 = new KiiObject().set("score", 100);
//		rest.api().users(user).buckets(userBucketName).objects().save(object1);
//		
//		KiiObjectURI uri = KiiURIUtils.createUserScopeObject(user.getUserID(), userBucketName, object1.getObjectID());
//		String expectedUriString = String.format("kiicloud://users/%s/buckets/%s/objects/%s", user.getUserID(), userBucketName, object1.getObjectID());
//		assertEquals(expectedUriString, uri.toString());
//		assertEquals(KiiURI.parse(expectedUriString), KiiObjectURI.create(expectedUriString));
//		
//		KiiObject object2 = rest.api().objects(uri).get();
//		assertEquals(object1.getObjectID(), object2.getObjectID());
//		assertEquals(object1.getInt("score"), object2.getInt("score"));
//		
//		rest.api().users(user).buckets(userBucketName).delete();
//	}
//	@Test
//	public void thingScopeTest() throws Exception {
//		TestApp testApp = TestEnvironments.random();
//		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
//		
//		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
//		user = rest.api().users().register(user, "password");
//		rest.setCredentials(user);
//		
//		String vendorThingID = "thing-" + System.currentTimeMillis();
//		String password = "pa$$word";
//		
//		// registering thing
//		KiiThing thing = new KiiThing()
//			.setVendorThingID(vendorThingID)
//			.setProductName("KiiCloud")
//			.setPassword(password);
//		thing = rest.api().things().register(thing);
//		String thingID = thing.getThingID();
//		rest.setCredentials(thing);
//		
//		rest.setCredentials(user);
//		
//		// adding owner
//		rest.api().things(thingID).owner().add(KiiThingOwner.user(user));
//
//		String thingBucketName = "thing_bucket" + System.currentTimeMillis();
//		
//		// creating object
//		KiiObject object1 = new KiiObject().set("score", 100);
//		rest.api().things(thing).buckets(thingBucketName).objects().save(object1);
//		
//		KiiObjectURI uri = KiiURIUtils.createThingScopeObject(thing.getThingID(), thingBucketName, object1.getObjectID());
//		String expectedUriString = String.format("kiicloud://things/%s/buckets/%s/objects/%s", thing.getThingID(), thingBucketName, object1.getObjectID());
//		assertEquals(expectedUriString, uri.toString());
//		assertEquals(KiiURI.parse(expectedUriString), KiiObjectURI.create(expectedUriString));
//		KiiObject object2 = rest.api().objects(uri).get();
//		
//		assertEquals(object1.getObjectID(), object2.getObjectID());
//		assertEquals(object1.getInt("score"), object2.getInt("score"));
//		
//		rest.api().things(thing).buckets(thingBucketName).delete();
//	}
//}
