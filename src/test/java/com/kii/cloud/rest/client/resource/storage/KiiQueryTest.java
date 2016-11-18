package com.kii.cloud.rest.client.resource.storage;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.storage.KiiGeoPoint;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiObject;
import com.kii.cloud.rest.client.model.storage.KiiQuery;
import com.kii.cloud.rest.client.model.storage.KiiQueryClause;
import com.kii.cloud.rest.client.model.storage.KiiQueryResult;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiQueryTest {
	@Test
	public void notEqualsTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketID = "user_bucket" + System.currentTimeMillis();
		
		// creating object
		KiiObject object1 = new KiiObject().set("name", "foo");
		rest.api().users(user).buckets(userBucketID).objects().save(object1);
		KiiObject object2 = new KiiObject().set("name", "bar");
		rest.api().users(user).buckets(userBucketID).objects().save(object2);
		KiiObject object3 = new KiiObject().set("name", "hoge");
		rest.api().users(user).buckets(userBucketID).objects().save(object3);
		
		KiiQuery query = new KiiQuery(KiiQueryClause.neq("name", "foo")).sortByAsc("name");
		KiiQueryResult queryResult = rest.api().users(user).buckets(userBucketID).query(query);
		List<KiiObject> results = queryResult.getResults();
		
		assertEquals(2, results.size());
		assertEquals("bar", results.get(0).getString("name"));
		assertEquals("hoge", results.get(1).getString("name"));
	}
	@Test
	public void notNotEqualsTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketID = "user_bucket" + System.currentTimeMillis();
		
		// creating object
		KiiObject object1 = new KiiObject().set("name", "foo");
		rest.api().users(user).buckets(userBucketID).objects().save(object1);
		KiiObject object2 = new KiiObject().set("name", "bar");
		rest.api().users(user).buckets(userBucketID).objects().save(object2);
		KiiObject object3 = new KiiObject().set("name", "hoge");
		rest.api().users(user).buckets(userBucketID).objects().save(object3);
		
		KiiQuery query = new KiiQuery(KiiQueryClause.not(KiiQueryClause.neq("name", "foo")));
		KiiQueryResult queryResult = rest.api().users(user).buckets(userBucketID).query(query);
		List<KiiObject> results = queryResult.getResults();
		
		assertEquals(1, results.size());
		assertEquals("foo", results.get(0).getString("name"));
	}
	@Test
	public void notPrefixTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketID = "user_bucket" + System.currentTimeMillis();
		
		// creating object
		KiiObject object1 = new KiiObject().set("name", "foo");
		rest.api().users(user).buckets(userBucketID).objects().save(object1);
		KiiObject object2 = new KiiObject().set("name", "fool");
		rest.api().users(user).buckets(userBucketID).objects().save(object2);
		KiiObject object3 = new KiiObject().set("name", "bar");
		rest.api().users(user).buckets(userBucketID).objects().save(object3);
		
		KiiQuery query = new KiiQuery(KiiQueryClause.not(KiiQueryClause.prefix("name", "foo")));
		KiiQueryResult queryResult = rest.api().users(user).buckets(userBucketID).query(query);
		List<KiiObject> results = queryResult.getResults();
		
		assertEquals(1, results.size());
		assertEquals("bar", results.get(0).getString("name"));
	}
	@Test
	public void notInTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketID = "user_bucket" + System.currentTimeMillis();
		
		// creating object
		KiiObject object1 = new KiiObject().set("name", "foo");
		rest.api().users(user).buckets(userBucketID).objects().save(object1);
		KiiObject object2 = new KiiObject().set("name", "bar");
		rest.api().users(user).buckets(userBucketID).objects().save(object2);
		KiiObject object3 = new KiiObject().set("name", "hoge");
		rest.api().users(user).buckets(userBucketID).objects().save(object3);
		
		KiiQuery query = new KiiQuery(KiiQueryClause.not(KiiQueryClause.in("name", "foo", "bar")));
		KiiQueryResult queryResult = rest.api().users(user).buckets(userBucketID).query(query);
		List<KiiObject> results = queryResult.getResults();
		
		assertEquals(1, results.size());
		assertEquals("hoge", results.get(0).getString("name"));
	}
	@Test
	public void notRangeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketID = "user_bucket" + System.currentTimeMillis();
		
		// creating object
		KiiObject object1 = new KiiObject().set("age", 20);
		rest.api().users(user).buckets(userBucketID).objects().save(object1);
		KiiObject object2 = new KiiObject().set("age", 33);
		rest.api().users(user).buckets(userBucketID).objects().save(object2);
		KiiObject object3 = new KiiObject().set("age", 38);
		rest.api().users(user).buckets(userBucketID).objects().save(object3);
		
		KiiQuery query = new KiiQuery(KiiQueryClause.not(KiiQueryClause.gt("age", 30)));
		query.setLimit(5);
		KiiQueryResult queryResult = rest.api().users(user).buckets(userBucketID).query(query);
		List<KiiObject> results = queryResult.getResults();
		
		assertEquals(1, results.size());
		assertEquals(20, (int)results.get(0).getInt("age"));
	}
	@Test
	public void notHasFieldTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketID = "user_bucket" + System.currentTimeMillis();
		
		// creating object
		KiiObject object1 = new KiiObject().set("name", "foo").set("age", 20);
		rest.api().users(user).buckets(userBucketID).objects().save(object1);
		KiiObject object2 = new KiiObject().set("name", "bar").set("age", 33);
		rest.api().users(user).buckets(userBucketID).objects().save(object2);
		KiiObject object3 = new KiiObject().set("name", "hoge");
		rest.api().users(user).buckets(userBucketID).objects().save(object3);
		
		KiiQuery query = new KiiQuery(KiiQueryClause.not(KiiQueryClause.hasIntegerField("age")));
		KiiQueryResult queryResult = rest.api().users(user).buckets(userBucketID).query(query);
		List<KiiObject> results = queryResult.getResults();
		
		assertEquals(1, results.size());
		assertEquals("hoge", results.get(0).getString("name"));
	}
	@Test
	public void notGeoDistanceTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketID = "user_bucket" + System.currentTimeMillis();
		
		// creating object
		KiiObject object1 = new KiiObject().set("name", "kii").set("location", new KiiGeoPoint(35.668387, 139.739495));
		rest.api().users(user).buckets(userBucketID).objects().save(object1);
		KiiObject object2 = new KiiObject().set("name", "TDL").set("location", new KiiGeoPoint(35.633114, 139.880405));
		rest.api().users(user).buckets(userBucketID).objects().save(object2);
		
		KiiQuery query = new KiiQuery(KiiQueryClause.not(KiiQueryClause.geoDistance("location", new KiiGeoPoint(35.667384, 139.739994), 2000, null)));
		KiiQueryResult queryResult = rest.api().users(user).buckets(userBucketID).query(query);
		List<KiiObject> results = queryResult.getResults();
		
		assertEquals(1, results.size());
		assertEquals("TDL", results.get(0).getString("name"));
	}
	@Test
	public void notGeoBoxTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketID = "user_bucket" + System.currentTimeMillis();
		
		// creating object
		KiiObject object1 = new KiiObject().set("name", "kii").set("location", new KiiGeoPoint(35.668387, 139.739495));
		rest.api().users(user).buckets(userBucketID).objects().save(object1);
		KiiObject object2 = new KiiObject().set("name", "TDL").set("location", new KiiGeoPoint(35.633114, 139.880405));
		rest.api().users(user).buckets(userBucketID).objects().save(object2);
		
		KiiQuery query = new KiiQuery(KiiQueryClause.not(
				KiiQueryClause.geoBox("location", new KiiGeoPoint(35.669494, 139.741727), new KiiGeoPoint(35.660934, 139.734957))));
		KiiQueryResult queryResult = rest.api().users(user).buckets(userBucketID).query(query);
		List<KiiObject> results = queryResult.getResults();
		
		assertEquals(1, results.size());
		assertEquals("TDL", results.get(0).getString("name"));
	}
	@Test
	public void notOrTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketID = "user_bucket" + System.currentTimeMillis();
		
		// creating object
		KiiObject object1 = new KiiObject().set("name", "foo");
		rest.api().users(user).buckets(userBucketID).objects().save(object1);
		KiiObject object2 = new KiiObject().set("name", "bar");
		rest.api().users(user).buckets(userBucketID).objects().save(object2);
		KiiObject object3 = new KiiObject().set("name", "hoge");
		rest.api().users(user).buckets(userBucketID).objects().save(object3);
		
		KiiQuery query = new KiiQuery(KiiQueryClause.not(KiiQueryClause.or(KiiQueryClause.eq("name", "foo"), KiiQueryClause.eq("name", "bar"))));
		KiiQueryResult queryResult = rest.api().users(user).buckets(userBucketID).query(query);
		List<KiiObject> results = queryResult.getResults();
		
		assertEquals(1, results.size());
		assertEquals("hoge", results.get(0).getString("name"));
	}
	@Test
	public void notAndTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketID = "user_bucket" + System.currentTimeMillis();
		
		// creating object
		KiiObject object1 = new KiiObject().set("name", "foo").set("age", 30);
		rest.api().users(user).buckets(userBucketID).objects().save(object1);
		KiiObject object2 = new KiiObject().set("name", "foo").set("age", 20);
		rest.api().users(user).buckets(userBucketID).objects().save(object2);
		
		KiiQuery query = new KiiQuery(KiiQueryClause.not(KiiQueryClause.and(KiiQueryClause.eq("name", "foo"), KiiQueryClause.gt("age", 25))));
		KiiQueryResult queryResult = rest.api().users(user).buckets(userBucketID).query(query);
		List<KiiObject> results = queryResult.getResults();
		
		assertEquals(1, results.size());
		assertEquals("foo", results.get(0).getString("name"));
		assertEquals(20, (int)results.get(0).getInt("age"));
	}
}
