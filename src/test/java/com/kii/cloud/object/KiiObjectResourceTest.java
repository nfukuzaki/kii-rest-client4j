package com.kii.cloud.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.kii.cloud.KiiRest;
import com.kii.cloud.TestApp;
import com.kii.cloud.TestEnvironments;
import com.kii.cloud.model.KiiGroup;
import com.kii.cloud.model.KiiNormalUser;
import com.kii.cloud.model.KiiObject;


public class KiiObjectResourceTest {
	@Test
	public void appScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.AppID, testApp.AppKey, testApp.Site);
		
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
		assertEquals(200, object2.getInt("score"));
		
		// partial updating
		KiiObject object3 = new KiiObject().set("level", 1);
		rest.api().buckets(appBucketName).objects(object2).partialUpdate(object3);
		
		// getting object
		KiiObject object4 = rest.api().buckets(appBucketName).objects(object1).get();
		assertEquals(200, object4.getInt("score"));
		assertEquals(1, object4.getInt("level"));
		
		// deleting object
		rest.api().buckets(appBucketName).objects(object4).delete();
	}
	@Test
	public void groupScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.AppID, testApp.AppKey, testApp.Site);
		
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
		assertEquals(200, object2.getInt("score"));
		
		// partial updating
		KiiObject object3 = new KiiObject().set("level", 1);
		rest.api().groups(group).buckets(groupBucketName).objects(object2).partialUpdate(object3);
		
		// getting object
		KiiObject object4 = rest.api().groups(group).buckets(groupBucketName).objects(object1).get();
		assertEquals(200, object4.getInt("score"));
		assertEquals(1, object4.getInt("level"));
		
		// deleting object
		rest.api().groups(group).buckets(groupBucketName).objects(object4).delete();
	}
	@Test
	public void userScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.AppID, testApp.AppKey, testApp.Site);
		
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
		assertEquals(200, object2.getInt("score"));
		
		// partial updating
		KiiObject object3 = new KiiObject().set("level", 1);
		rest.api().users(user).buckets(userBucketName).objects(object2).partialUpdate(object3);
		
		// getting object
		KiiObject object4 = rest.api().users(user).buckets(userBucketName).objects(object1).get();
		assertEquals(200, object4.getInt("score"));
		assertEquals(1, object4.getInt("level"));
		
		// deleting object
		rest.api().users(user).buckets(userBucketName).objects(object4).delete();
	}
}
