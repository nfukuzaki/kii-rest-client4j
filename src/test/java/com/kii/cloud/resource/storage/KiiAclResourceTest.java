package com.kii.cloud.resource.storage;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.KiiRest;
import com.kii.cloud.SkipAcceptableTestRunner;
import com.kii.cloud.TestApp;
import com.kii.cloud.TestEnvironments;
import com.kii.cloud.model.storage.KiiNormalUser;
import com.kii.cloud.model.storage.KiiAcl.Action;
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
		
		// listing ACL
		Map<Action,List<Subject>> ACLs = rest.api().users(user).buckets(userBucketName).acl().list();
		// verify default ACLs
		assertEquals(3, ACLs.size());
		assertEquals(user.getUserID(), ACLs.get(BucketAction.CREATE_OBJECTS_IN_BUCKET).get(0).getID());
		assertEquals(user.getUserID(), ACLs.get(BucketAction.QUERY_OBJECTS_IN_BUCKET).get(0).getID());
		assertEquals(user.getUserID(), ACLs.get(BucketAction.DROP_BUCKET_WITH_ALL_CONTENT).get(0).getID());
		
		// granting ACL
		rest.api().users(user).buckets(userBucketName).acl().grant(BucketAction.CREATE_OBJECTS_IN_BUCKET, Subject.ANONYMOUS_USER);
		rest.api().users(user).buckets(userBucketName).acl().grant(BucketAction.CREATE_OBJECTS_IN_BUCKET, Subject.ANY_AUTHENTICATED_USER);
		// verify ACLs
		ACLs = rest.api().users(user).buckets(userBucketName).acl().list();
		assertEquals(3, ACLs.size());
		assertEquals(user.getUserID(), ACLs.get(BucketAction.CREATE_OBJECTS_IN_BUCKET).get(0).getID());
		assertEquals("ANONYMOUS_USER", ACLs.get(BucketAction.CREATE_OBJECTS_IN_BUCKET).get(1).getID());
		assertEquals("ANY_AUTHENTICATED_USER", ACLs.get(BucketAction.CREATE_OBJECTS_IN_BUCKET).get(2).getID());
		assertEquals(user.getUserID(), ACLs.get(BucketAction.QUERY_OBJECTS_IN_BUCKET).get(0).getID());
		assertEquals(user.getUserID(), ACLs.get(BucketAction.DROP_BUCKET_WITH_ALL_CONTENT).get(0).getID());
		
		// listing subject by action
		List<Subject> subjectCreateObjectsInBucket = rest.api().users(user).buckets(userBucketName).acl().list(BucketAction.CREATE_OBJECTS_IN_BUCKET);
		assertEquals(user.getUserID(), subjectCreateObjectsInBucket.get(0).getID());
		assertEquals("ANONYMOUS_USER", subjectCreateObjectsInBucket.get(1).getID());
		assertEquals("ANY_AUTHENTICATED_USER", subjectCreateObjectsInBucket.get(2).getID());
		// get subject
		Subject subject = rest.api().users(user).buckets(userBucketName).acl().get(BucketAction.CREATE_OBJECTS_IN_BUCKET, Subject.ANONYMOUS_USER);
		assertEquals("ANONYMOUS_USER", subject.getID());
		
		// revoking ACL
		rest.api().users(user).buckets(userBucketName).acl().revok(BucketAction.CREATE_OBJECTS_IN_BUCKET, Subject.ANY_AUTHENTICATED_USER);
		subjectCreateObjectsInBucket = rest.api().users(user).buckets(userBucketName).acl().list(BucketAction.CREATE_OBJECTS_IN_BUCKET);
		assertEquals(user.getUserID(), subjectCreateObjectsInBucket.get(0).getID());
		assertEquals("ANONYMOUS_USER", subjectCreateObjectsInBucket.get(1).getID());
	}
	@Test
	public void objectAclTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketName = "user_bucket" + System.currentTimeMillis();
		
		
		
		
	}
	@Test
	public void topicAclTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		
	}
}
