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
		String appBucketID = "app_bucket" + System.currentTimeMillis();
		rest.api().buckets(appBucketID).create();
		
		KiiBucket bucket = rest.api().buckets(appBucketID).get();
		assertEquals("rw", bucket.getBucketType());
		assertEquals(0, bucket.getSize());
		
		// creating objects
		KiiObjectsResource objectsResource = rest.api().buckets(appBucketID).objects();
		for (int i = 0; i < 15; i++) {
			KiiObject obj = new KiiObject().set("score", i);
			objectsResource.save(obj);
		}
		
		// counting object
		int count  = rest.api().buckets(appBucketID).count();
		assertEquals(15, count);
		
		// querying object
		KiiQuery query = new KiiQuery(KiiQueryClause.lt("score", 9));
		query.setLimit(5);
		KiiQueryResult queryResult = rest.api().buckets(appBucketID).query(query);
		List<KiiObject> results = queryResult.getResults();
		assertEquals(5, results.size());
		for (KiiObject obj : results) {
			assertTrue(obj.getInt("score") < 9);
		}
		assertTrue(queryResult.hasNext());
		queryResult = rest.api().buckets(appBucketID).query(queryResult.getNextQuery());
		results = queryResult.getResults();
		assertEquals(4, results.size());
		for (KiiObject obj : results) {
			assertTrue(obj.getInt("score") < 9);
		}
		assertFalse(queryResult.hasNext());
		
		// counting object
		count  = rest.api().buckets(appBucketID).count(query);
		assertEquals(9, count);
		
		// deleting bucket
		rest.api().buckets(appBucketID).delete();
		
		try {
			rest.api().buckets(appBucketID).get();
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
		String userBucketID = "user_bucket" + System.currentTimeMillis();
		rest.api().users(user).buckets(userBucketID).create();
		
		KiiBucket bucket = rest.api().users(user).buckets(userBucketID).get();
		assertEquals("rw", bucket.getBucketType());
		assertEquals(0, bucket.getSize());
		
		// creating objects
		KiiObjectsResource objectsResource = rest.api().users(user).buckets(userBucketID).objects();
		for (int i = 0; i < 15; i++) {
			KiiObject obj = new KiiObject().set("score", i);
			objectsResource.save(obj);
		}
		
		// counting object
		int count  = rest.api().users(user).buckets(userBucketID).count();
		assertEquals(15, count);
		
		// querying object
		KiiQuery query = new KiiQuery(KiiQueryClause.lt("score", 9));
		query.setLimit(5);
		KiiQueryResult queryResult = rest.api().users(user).buckets(userBucketID).query(query);
		List<KiiObject> results = queryResult.getResults();
		assertEquals(5, results.size());
		for (KiiObject obj : results) {
			assertTrue(obj.getInt("score") < 9);
		}
		assertTrue(queryResult.hasNext());
		queryResult = rest.api().users(user).buckets(userBucketID).query(queryResult.getNextQuery());
		results = queryResult.getResults();
		assertEquals(4, results.size());
		for (KiiObject obj : results) {
			assertTrue(obj.getInt("score") < 9);
		}
		assertFalse(queryResult.hasNext());
		
		// counting object
		count  = rest.api().users(user).buckets(userBucketID).count(query);
		assertEquals(9, count);
		
		// deleting bucket
		rest.api().users(user).buckets(userBucketID).delete();
		
		try {
			rest.api().users(user).buckets(userBucketID).get();
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
		String groupBucketID = "group_bucket" + System.currentTimeMillis();
		rest.api().groups(group).buckets(groupBucketID).create();
		
		KiiBucket bucket = rest.api().groups(group).buckets(groupBucketID).get();
		assertEquals("rw", bucket.getBucketType());
		assertEquals(0, bucket.getSize());
		
		// creating objects
		KiiObjectsResource objectsResource = rest.api().groups(group).buckets(groupBucketID).objects();
		for (int i = 0; i < 15; i++) {
			KiiObject obj = new KiiObject().set("score", i);
			objectsResource.save(obj);
		}
		
		// counting object
		int count  = rest.api().groups(group).buckets(groupBucketID).count();
		assertEquals(15, count);
		
		// querying object
		KiiQuery query = new KiiQuery(KiiQueryClause.lt("score", 9));
		query.setLimit(5);
		KiiQueryResult queryResult = rest.api().groups(group).buckets(groupBucketID).query(query);
		List<KiiObject> results = queryResult.getResults();
		assertEquals(5, results.size());
		for (KiiObject obj : results) {
			assertTrue(obj.getInt("score") < 9);
		}
		assertTrue(queryResult.hasNext());
		queryResult = rest.api().groups(group).buckets(groupBucketID).query(queryResult.getNextQuery());
		results = queryResult.getResults();
		assertEquals(4, results.size());
		for (KiiObject obj : results) {
			assertTrue(obj.getInt("score") < 9);
		}
		assertFalse(queryResult.hasNext());
		
		// counting object
		count  = rest.api().groups(group).buckets(groupBucketID).count(query);
		assertEquals(9, count);
		
		// deleting bucket
		rest.api().groups(group).buckets(groupBucketID).delete();
		
		try {
			rest.api().groups(group).buckets(groupBucketID).get();
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
		String thingBucketID = "thing_bucket" + System.currentTimeMillis();
		rest.api().things(thingID).buckets(thingBucketID).create();
		
		KiiBucket bucket = rest.api().things(thingID).buckets(thingBucketID).get();
		assertEquals("rw", bucket.getBucketType());
		assertEquals(0, bucket.getSize());
		
		// creating objects
		KiiObjectsResource objectsResource = rest.api().things(thingID).buckets(thingBucketID).objects();
		for (int i = 0; i < 15; i++) {
			KiiObject obj = new KiiObject().set("score", i);
			objectsResource.save(obj);
		}
		
		// counting object
		int count  = rest.api().things(thingID).buckets(thingBucketID).count();
		assertEquals(15, count);
		
		// querying object
		KiiQuery query = new KiiQuery(KiiQueryClause.lt("score", 9));
		query.setLimit(5);
		KiiQueryResult queryResult = rest.api().things(thingID).buckets(thingBucketID).query(query);
		List<KiiObject> results = queryResult.getResults();
		assertEquals(5, results.size());
		for (KiiObject obj : results) {
			assertTrue(obj.getInt("score") < 9);
		}
		assertTrue(queryResult.hasNext());
		queryResult = rest.api().things(thingID).buckets(thingBucketID).query(queryResult.getNextQuery());
		results = queryResult.getResults();
		assertEquals(4, results.size());
		for (KiiObject obj : results) {
			assertTrue(obj.getInt("score") < 9);
		}
		assertFalse(queryResult.hasNext());
		
		// counting object
		count  = rest.api().things(thingID).buckets(thingBucketID).count(query);
		assertEquals(9, count);
		
		// deleting bucket
		rest.api().things(thingID).buckets(thingBucketID).delete();
		
		try {
			rest.api().things(thingID).buckets(thingBucketID).get();
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
		String appBucketID = "app_bucket" + System.currentTimeMillis();
		rest.api().buckets(appBucketID).create();
		
		KiiBucket bucket = rest.api().buckets(appBucketID).get();
		assertEquals("rw", bucket.getBucketType());
		assertEquals(0, bucket.getSize());
		
		// subscribe/unsubscribe
		assertFalse(rest.api().buckets(appBucketID).isSubscribed(user));
		rest.api().buckets(appBucketID).subscribe(user);
		assertTrue(rest.api().buckets(appBucketID).isSubscribed(user));
		rest.api().buckets(appBucketID).unsubscribe(user);
		assertFalse(rest.api().buckets(appBucketID).isSubscribed(user));
	}

}
