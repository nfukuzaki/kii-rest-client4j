package com.kii.cloud.resource.storage;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.KiiRest;
import com.kii.cloud.SkipAcceptableTestRunner;
import com.kii.cloud.TestApp;
import com.kii.cloud.TestEnvironments;
import com.kii.cloud.model.storage.KiiNormalUser;
import com.kii.cloud.model.storage.KiiAcl.BucketAction;
import com.kii.cloud.model.storage.KiiAcl.Subject;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiAclResourceTest {
	@Test
	public void bucketAclTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketName = "user_bucket" + System.currentTimeMillis();
		
		// creating bucket
		rest.api().users(user).buckets(userBucketName).create();
		
		rest.api().users(user).buckets(userBucketName).acl().list();
		
		rest.api().users(user).buckets(userBucketName).acl().grant(BucketAction.CREATE_OBJECTS_IN_BUCKET, Subject.ANONYMOUS_USER);
		rest.api().users(user).buckets(userBucketName).acl().grant(BucketAction.CREATE_OBJECTS_IN_BUCKET, Subject.ANY_AUTHENTICATED_USER);
		
		rest.api().users(user).buckets(userBucketName).acl().list();
		rest.api().users(user).buckets(userBucketName).acl().list(BucketAction.CREATE_OBJECTS_IN_BUCKET);
		rest.api().users(user).buckets(userBucketName).acl().get(BucketAction.CREATE_OBJECTS_IN_BUCKET, Subject.ANONYMOUS_USER);
	}
	@Test
	public void objectAclTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		
	}
	@Test
	public void topicAclTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		
	}
}
