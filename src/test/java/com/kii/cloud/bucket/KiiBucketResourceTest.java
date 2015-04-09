package com.kii.cloud.bucket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.KiiRest;
import com.kii.cloud.SkipAcceptableTestRunner;
import com.kii.cloud.TestApp;
import com.kii.cloud.TestEnvironments;
import com.kii.cloud.model.KiiBucket;
import com.kii.cloud.model.KiiClause;
import com.kii.cloud.model.KiiNormalUser;
import com.kii.cloud.model.KiiObject;
import com.kii.cloud.model.KiiQuery;
import com.kii.cloud.model.KiiQueryResult;
import com.kii.cloud.resource.KiiObjectsResource;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiBucketResourceTest {
	@Test
	public void test() throws Exception {
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
		KiiQuery query = new KiiQuery(KiiClause.lt("score", 9));
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
	}
}
