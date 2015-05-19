package com.kii.cloud.rest.client.resource.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.exception.KiiNotFoundException;
import com.kii.cloud.rest.client.model.storage.KiiBucket;
import com.kii.cloud.rest.client.model.storage.KiiGroup;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiObject;
import com.kii.cloud.rest.client.model.storage.KiiQuery;
import com.kii.cloud.rest.client.model.storage.KiiQueryClause;
import com.kii.cloud.rest.client.model.storage.KiiQueryResult;
import com.kii.cloud.rest.client.model.storage.KiiThing;
import com.kii.cloud.rest.client.model.storage.KiiThingOwner;
import com.kii.cloud.rest.client.resource.storage.KiiObjectsResource;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiBucketResourceTest {
	@Test
	public void appScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		// creating bucket
		String appBucketName = "app_bucket" + System.currentTimeMillis();
		rest.api().buckets(appBucketName).create();
		
		KiiBucket bucket = rest.api().buckets(appBucketName).get();
		assertEquals("rw", bucket.getBucketType());
		assertEquals(0, bucket.getSize());
		
		// creating objects
		KiiObjectsResource objectsResource = rest.api().buckets(appBucketName).objects();
		for (int i = 0; i < 15; i++) {
			KiiObject obj = new KiiObject().set("score", i);
			objectsResource.save(obj);
		}
		
		// counting object
		int count  = rest.api().buckets(appBucketName).count();
		assertEquals(15, count);
		
		// querying object
		KiiQuery query = new KiiQuery(KiiQueryClause.lt("score", 9));
		query.setLimit(5);
		KiiQueryResult queryResult = rest.api().buckets(appBucketName).query(query);
		List<KiiObject> results = queryResult.getResults();
		assertEquals(5, results.size());
		for (KiiObject obj : results) {
			assertTrue(obj.getInt("score") < 9);
		}
		assertTrue(queryResult.hasNext());
		queryResult = rest.api().buckets(appBucketName).query(queryResult.getNextQuery());
		results = queryResult.getResults();
		assertEquals(4, results.size());
		for (KiiObject obj : results) {
			assertTrue(obj.getInt("score") < 9);
		}
		assertFalse(queryResult.hasNext());
		
		// counting object
		count  = rest.api().buckets(appBucketName).count(query);
		assertEquals(9, count);
		
		// deleting bucket
		rest.api().buckets(appBucketName).delete();
		
		try {
			rest.api().buckets(appBucketName).get();
			fail("KiiRestException must be thrown.");
		} catch (KiiNotFoundException e) {
			assertEquals(404, e.getStatus());
		}
	}
	@Test
	public void userScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		// creating bucket
		String userBucketName = "user_bucket" + System.currentTimeMillis();
		rest.api().users(user).buckets(userBucketName).create();
		
		KiiBucket bucket = rest.api().users(user).buckets(userBucketName).get();
		assertEquals("rw", bucket.getBucketType());
		assertEquals(0, bucket.getSize());
		
		// creating objects
		KiiObjectsResource objectsResource = rest.api().users(user).buckets(userBucketName).objects();
		for (int i = 0; i < 15; i++) {
			KiiObject obj = new KiiObject().set("score", i);
			objectsResource.save(obj);
		}
		
		// counting object
		int count  = rest.api().users(user).buckets(userBucketName).count();
		assertEquals(15, count);
		
		// querying object
		KiiQuery query = new KiiQuery(KiiQueryClause.lt("score", 9));
		query.setLimit(5);
		KiiQueryResult queryResult = rest.api().users(user).buckets(userBucketName).query(query);
		List<KiiObject> results = queryResult.getResults();
		assertEquals(5, results.size());
		for (KiiObject obj : results) {
			assertTrue(obj.getInt("score") < 9);
		}
		assertTrue(queryResult.hasNext());
		queryResult = rest.api().users(user).buckets(userBucketName).query(queryResult.getNextQuery());
		results = queryResult.getResults();
		assertEquals(4, results.size());
		for (KiiObject obj : results) {
			assertTrue(obj.getInt("score") < 9);
		}
		assertFalse(queryResult.hasNext());
		
		// counting object
		count  = rest.api().users(user).buckets(userBucketName).count(query);
		assertEquals(9, count);
		
		// deleting bucket
		rest.api().users(user).buckets(userBucketName).delete();
		
		try {
			rest.api().users(user).buckets(userBucketName).get();
			fail("KiiRestException must be thrown.");
		} catch (KiiNotFoundException e) {
			assertEquals(404, e.getStatus());
		}
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
		rest.api().groups().save(group);
		
		// creating bucket
		String groupBucketName = "group_bucket" + System.currentTimeMillis();
		rest.api().groups(group).buckets(groupBucketName).create();
		
		KiiBucket bucket = rest.api().groups(group).buckets(groupBucketName).get();
		assertEquals("rw", bucket.getBucketType());
		assertEquals(0, bucket.getSize());
		
		// creating objects
		KiiObjectsResource objectsResource = rest.api().groups(group).buckets(groupBucketName).objects();
		for (int i = 0; i < 15; i++) {
			KiiObject obj = new KiiObject().set("score", i);
			objectsResource.save(obj);
		}
		
		// counting object
		int count  = rest.api().groups(group).buckets(groupBucketName).count();
		assertEquals(15, count);
		
		// querying object
		KiiQuery query = new KiiQuery(KiiQueryClause.lt("score", 9));
		query.setLimit(5);
		KiiQueryResult queryResult = rest.api().groups(group).buckets(groupBucketName).query(query);
		List<KiiObject> results = queryResult.getResults();
		assertEquals(5, results.size());
		for (KiiObject obj : results) {
			assertTrue(obj.getInt("score") < 9);
		}
		assertTrue(queryResult.hasNext());
		queryResult = rest.api().groups(group).buckets(groupBucketName).query(queryResult.getNextQuery());
		results = queryResult.getResults();
		assertEquals(4, results.size());
		for (KiiObject obj : results) {
			assertTrue(obj.getInt("score") < 9);
		}
		assertFalse(queryResult.hasNext());
		
		// counting object
		count  = rest.api().groups(group).buckets(groupBucketName).count(query);
		assertEquals(9, count);
		
		// deleting bucket
		rest.api().groups(group).buckets(groupBucketName).delete();
		
		try {
			rest.api().groups(group).buckets(groupBucketName).get();
			fail("KiiRestException must be thrown.");
		} catch (KiiNotFoundException e) {
			assertEquals(404, e.getStatus());
		}
	}
	@Test
	public void thingScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		
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
		
		// creating bucket
		String thingBucketName = "thing_bucket" + System.currentTimeMillis();
		rest.api().things(thingID).buckets(thingBucketName).create();
		
		KiiBucket bucket = rest.api().things(thingID).buckets(thingBucketName).get();
		assertEquals("rw", bucket.getBucketType());
		assertEquals(0, bucket.getSize());
		
		// creating objects
		KiiObjectsResource objectsResource = rest.api().things(thingID).buckets(thingBucketName).objects();
		for (int i = 0; i < 15; i++) {
			KiiObject obj = new KiiObject().set("score", i);
			objectsResource.save(obj);
		}
		
		// counting object
		int count  = rest.api().things(thingID).buckets(thingBucketName).count();
		assertEquals(15, count);
		
		// querying object
		KiiQuery query = new KiiQuery(KiiQueryClause.lt("score", 9));
		query.setLimit(5);
		KiiQueryResult queryResult = rest.api().things(thingID).buckets(thingBucketName).query(query);
		List<KiiObject> results = queryResult.getResults();
		assertEquals(5, results.size());
		for (KiiObject obj : results) {
			assertTrue(obj.getInt("score") < 9);
		}
		assertTrue(queryResult.hasNext());
		queryResult = rest.api().things(thingID).buckets(thingBucketName).query(queryResult.getNextQuery());
		results = queryResult.getResults();
		assertEquals(4, results.size());
		for (KiiObject obj : results) {
			assertTrue(obj.getInt("score") < 9);
		}
		assertFalse(queryResult.hasNext());
		
		// counting object
		count  = rest.api().things(thingID).buckets(thingBucketName).count(query);
		assertEquals(9, count);
		
		// deleting bucket
		rest.api().things(thingID).buckets(thingBucketName).delete();
		
		try {
			rest.api().things(thingID).buckets(thingBucketName).get();
			fail("KiiRestException must be thrown.");
		} catch (KiiNotFoundException e) {
			assertEquals(404, e.getStatus());
		}
	}
	@Test
	public void subscribeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		// creating bucket
		String appBucketName = "app_bucket" + System.currentTimeMillis();
		rest.api().buckets(appBucketName).create();
		
		KiiBucket bucket = rest.api().buckets(appBucketName).get();
		assertEquals("rw", bucket.getBucketType());
		assertEquals(0, bucket.getSize());
		
		// subscribe/unsubscribe
		assertFalse(rest.api().buckets(appBucketName).isSubscribed(user));
		rest.api().buckets(appBucketName).subscribe(user);
		assertTrue(rest.api().buckets(appBucketName).isSubscribed(user));
		rest.api().buckets(appBucketName).unsubscribe(user);
		assertFalse(rest.api().buckets(appBucketName).isSubscribed(user));
	}

}
