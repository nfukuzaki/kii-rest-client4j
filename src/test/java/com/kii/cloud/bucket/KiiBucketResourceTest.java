package com.kii.cloud.bucket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;

import com.kii.cloud.KiiRest;
import com.kii.cloud.TestApp;
import com.kii.cloud.TestEnvironments;
import com.kii.cloud.model.KiiClause;
import com.kii.cloud.model.KiiNormalUser;
import com.kii.cloud.model.KiiObject;
import com.kii.cloud.model.KiiQuery;
import com.kii.cloud.model.KiiQueryResult;
import com.kii.cloud.resource.KiiObjectsResource;

public class KiiBucketResourceTest {
	@Test
	public void test() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.AppID, testApp.AppKey, testApp.Site);
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test1-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		// creating bucket
		String appBucketName = "app_bucket" + System.currentTimeMillis();
		rest.api().buckets(appBucketName).create();
		
		// creating objects
		KiiObjectsResource objectsResource = rest.api().buckets(appBucketName).objects();
		for (int i = 0; i < 15; i++) {
			KiiObject obj = new KiiObject().set("score", i);
			objectsResource.save(obj);
		}
		
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
		
		rest.api().buckets(appBucketName).delete();
	}
}
