package com.kii.cloud.rest.client.resource.storage;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.AssertUtils;
import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.storage.KiiGroup;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiObject;
import com.kii.cloud.rest.client.model.storage.KiiAcl.Action;
import com.kii.cloud.rest.client.model.storage.KiiAcl.BucketAction;
import com.kii.cloud.rest.client.model.storage.KiiAcl.ObjectAction;
import com.kii.cloud.rest.client.model.storage.KiiAcl.Subject;
import com.kii.cloud.rest.client.model.storage.KiiAcl.TopicAction;
import com.kii.cloud.rest.client.model.storage.KiiThing;
import com.kii.cloud.rest.client.model.storage.KiiUser;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiAclResourceTest {
	
	private KiiUser subjectUser;
	private KiiGroup subjectGroup;
	private KiiThing subjectThing;
	
	@Test
	public void bucketAclTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		this.prepareTestData(rest);
		
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
		rest.api().users(user).buckets(userBucketName).acl().grant(BucketAction.CREATE_OBJECTS_IN_BUCKET, this.subjectUser);
		rest.api().users(user).buckets(userBucketName).acl().grant(BucketAction.CREATE_OBJECTS_IN_BUCKET, this.subjectGroup);
		rest.api().users(user).buckets(userBucketName).acl().grant(BucketAction.CREATE_OBJECTS_IN_BUCKET, this.subjectThing);
		// verify ACLs
		ACLs = rest.api().users(user).buckets(userBucketName).acl().list();
		assertEquals(3, ACLs.size());
		Subject[] expectedSubjects = new Subject[]{
				Subject.user(user),
				Subject.ANONYMOUS_USER,
				Subject.ANY_AUTHENTICATED_USER,
				Subject.user(this.subjectUser),
				Subject.group(this.subjectGroup),
				Subject.thing(this.subjectThing)};
		AssertUtils.assertEqualsIgnoreOrder(expectedSubjects, ACLs.get(BucketAction.CREATE_OBJECTS_IN_BUCKET));
		assertEquals(user.getUserID(), ACLs.get(BucketAction.QUERY_OBJECTS_IN_BUCKET).get(0).getID());
		assertEquals(user.getUserID(), ACLs.get(BucketAction.DROP_BUCKET_WITH_ALL_CONTENT).get(0).getID());
		
		// listing subject by action
		List<Subject> subjectCreateObjectsInBucket = rest.api().users(user).buckets(userBucketName).acl().list(BucketAction.CREATE_OBJECTS_IN_BUCKET);
		AssertUtils.assertEqualsIgnoreOrder(expectedSubjects, subjectCreateObjectsInBucket);
		// get subject
		Subject subject = rest.api().users(user).buckets(userBucketName).acl().get(BucketAction.CREATE_OBJECTS_IN_BUCKET, Subject.ANONYMOUS_USER);
		assertEquals("ANONYMOUS_USER", subject.getID());
		
		// revoking ACL
		rest.api().users(user).buckets(userBucketName).acl().revok(BucketAction.CREATE_OBJECTS_IN_BUCKET, Subject.ANY_AUTHENTICATED_USER);
		rest.api().users(user).buckets(userBucketName).acl().revok(BucketAction.CREATE_OBJECTS_IN_BUCKET, this.subjectUser);
		rest.api().users(user).buckets(userBucketName).acl().revok(BucketAction.CREATE_OBJECTS_IN_BUCKET, this.subjectGroup);
		rest.api().users(user).buckets(userBucketName).acl().revok(BucketAction.CREATE_OBJECTS_IN_BUCKET, this.subjectThing);
		subjectCreateObjectsInBucket = rest.api().users(user).buckets(userBucketName).acl().list(BucketAction.CREATE_OBJECTS_IN_BUCKET);
		
		expectedSubjects = new Subject[]{
				Subject.user(user),
				Subject.ANONYMOUS_USER};
		AssertUtils.assertEqualsIgnoreOrder(expectedSubjects, subjectCreateObjectsInBucket);
	}
	@Test
	public void objectAclTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		this.prepareTestData(rest);
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketName = "user_bucket" + System.currentTimeMillis();
		KiiObject object = new KiiObject().set("name", "TestObject");
		rest.api().users(user).buckets(userBucketName).objects().save(object);
		
		// listing ACL
		Map<Action,List<Subject>> ACLs = rest.api().users(user).buckets(userBucketName).objects(object).acl().list();
		// verify default ACLs
		assertEquals(2, ACLs.size());
		assertEquals(user.getUserID(), ACLs.get(ObjectAction.READ_EXISTING_OBJECT).get(0).getID());
		assertEquals(user.getUserID(), ACLs.get(ObjectAction.WRITE_EXISTING_OBJECT).get(0).getID());
		
		// granting ACL
		rest.api().users(user).buckets(userBucketName).objects(object).acl().grant(ObjectAction.READ_EXISTING_OBJECT, Subject.ANONYMOUS_USER);
		rest.api().users(user).buckets(userBucketName).objects(object).acl().grant(ObjectAction.READ_EXISTING_OBJECT, Subject.ANY_AUTHENTICATED_USER);
		rest.api().users(user).buckets(userBucketName).objects(object).acl().grant(ObjectAction.READ_EXISTING_OBJECT, this.subjectUser);
		rest.api().users(user).buckets(userBucketName).objects(object).acl().grant(ObjectAction.READ_EXISTING_OBJECT, this.subjectGroup);
		rest.api().users(user).buckets(userBucketName).objects(object).acl().grant(ObjectAction.READ_EXISTING_OBJECT, this.subjectThing);
		
		// verify ACLs
		ACLs = rest.api().users(user).buckets(userBucketName).objects(object).acl().list();
		assertEquals(2, ACLs.size());
		Subject[] expectedSubjects = new Subject[]{
				Subject.user(user),
				Subject.ANONYMOUS_USER,
				Subject.ANY_AUTHENTICATED_USER,
				Subject.user(this.subjectUser),
				Subject.group(this.subjectGroup),
				Subject.thing(this.subjectThing)};
		AssertUtils.assertEqualsIgnoreOrder(expectedSubjects, ACLs.get(ObjectAction.READ_EXISTING_OBJECT));
		assertEquals(user.getUserID(), ACLs.get(ObjectAction.WRITE_EXISTING_OBJECT).get(0).getID());
		
		// listing subject by action
		List<Subject> subjectReadExistingObject = rest.api().users(user).buckets(userBucketName).objects(object).acl().list(ObjectAction.READ_EXISTING_OBJECT);
		AssertUtils.assertEqualsIgnoreOrder(expectedSubjects, subjectReadExistingObject);
		// get subject
		Subject subject = rest.api().users(user).buckets(userBucketName).objects(object).acl().get(ObjectAction.READ_EXISTING_OBJECT, Subject.ANONYMOUS_USER);
		assertEquals("ANONYMOUS_USER", subject.getID());
		
		// revoking ACL
		rest.api().users(user).buckets(userBucketName).objects(object).acl().revok(ObjectAction.READ_EXISTING_OBJECT, Subject.ANY_AUTHENTICATED_USER);
		rest.api().users(user).buckets(userBucketName).objects(object).acl().revok(ObjectAction.READ_EXISTING_OBJECT, this.subjectUser);
		rest.api().users(user).buckets(userBucketName).objects(object).acl().revok(ObjectAction.READ_EXISTING_OBJECT, this.subjectGroup);
		rest.api().users(user).buckets(userBucketName).objects(object).acl().revok(ObjectAction.READ_EXISTING_OBJECT, this.subjectThing);
		subjectReadExistingObject = rest.api().users(user).buckets(userBucketName).objects(object).acl().list(ObjectAction.READ_EXISTING_OBJECT);
		assertEquals(2, subjectReadExistingObject.size());
		expectedSubjects = new Subject[]{
				Subject.user(user),
				Subject.ANONYMOUS_USER};
		AssertUtils.assertEqualsIgnoreOrder(expectedSubjects, subjectReadExistingObject);
	}
	@Test
	public void topicAclTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		this.prepareTestData(rest);
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userTopicName = "user_topic" + System.currentTimeMillis();
		
		// creating topic
		rest.api().users(user).topics(userTopicName).create();
		
		// listing ACL
		Map<Action,List<Subject>> ACLs = rest.api().users(user).topics(userTopicName).acl().list();
		// verify default ACLs
		assertEquals(2, ACLs.size());
		assertEquals(user.getUserID(), ACLs.get(TopicAction.SUBSCRIBE_TO_TOPIC).get(0).getID());
		assertEquals(user.getUserID(), ACLs.get(TopicAction.SEND_MESSAGE_TO_TOPIC).get(0).getID());
		
		// granting ACL
		rest.api().users(user).topics(userTopicName).acl().grant(TopicAction.SUBSCRIBE_TO_TOPIC, Subject.ANY_AUTHENTICATED_USER);
		rest.api().users(user).topics(userTopicName).acl().grant(TopicAction.SUBSCRIBE_TO_TOPIC, this.subjectUser);
		rest.api().users(user).topics(userTopicName).acl().grant(TopicAction.SUBSCRIBE_TO_TOPIC, this.subjectGroup);
		rest.api().users(user).topics(userTopicName).acl().grant(TopicAction.SUBSCRIBE_TO_TOPIC, this.subjectThing);
		// verify ACLs
		ACLs = rest.api().users(user).topics(userTopicName).acl().list();
		assertEquals(2, ACLs.size());
		Subject[] expectedSubjects = new Subject[]{
				Subject.user(user),
				Subject.ANY_AUTHENTICATED_USER,
				Subject.user(this.subjectUser),
				Subject.group(this.subjectGroup),
				Subject.thing(this.subjectThing)};
		AssertUtils.assertEqualsIgnoreOrder(expectedSubjects, ACLs.get(TopicAction.SUBSCRIBE_TO_TOPIC));
		assertEquals(user.getUserID(), ACLs.get(TopicAction.SEND_MESSAGE_TO_TOPIC).get(0).getID());
		
		// listing subject by action
		List<Subject> subjectSubscribeToTopic = rest.api().users(user).topics(userTopicName).acl().list(TopicAction.SUBSCRIBE_TO_TOPIC);
		AssertUtils.assertEqualsIgnoreOrder(expectedSubjects, subjectSubscribeToTopic);
		// get subject
		Subject subject = rest.api().users(user).topics(userTopicName).acl().get(TopicAction.SUBSCRIBE_TO_TOPIC, Subject.ANY_AUTHENTICATED_USER);
		assertEquals("ANY_AUTHENTICATED_USER", subject.getID());
		
		// revoking ACL
		rest.api().users(user).topics(userTopicName).acl().revok(TopicAction.SUBSCRIBE_TO_TOPIC, Subject.ANY_AUTHENTICATED_USER);
		rest.api().users(user).topics(userTopicName).acl().revok(TopicAction.SUBSCRIBE_TO_TOPIC, this.subjectUser);
		rest.api().users(user).topics(userTopicName).acl().revok(TopicAction.SUBSCRIBE_TO_TOPIC, this.subjectGroup);
		rest.api().users(user).topics(userTopicName).acl().revok(TopicAction.SUBSCRIBE_TO_TOPIC, this.subjectThing);
		// listing subject by action
		subjectSubscribeToTopic = rest.api().users(user).topics(userTopicName).acl().list(TopicAction.SUBSCRIBE_TO_TOPIC);
		assertEquals(1, subjectSubscribeToTopic.size());
		expectedSubjects = new Subject[]{Subject.user(user)};
		AssertUtils.assertEqualsIgnoreOrder(expectedSubjects, subjectSubscribeToTopic);
	}
	private void prepareTestData(KiiRest rest) throws Exception {
		// creating user for subject
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		this.subjectUser = rest.api().users().register(user, "password");
		rest.setCredentials(this.subjectUser);
		// creating group for subject
		KiiGroup group = new KiiGroup("MyGroup");
		group.setOwner(this.subjectUser);
		rest.api().groups().save(group);
		this.subjectGroup = group;
		rest.setCredentials(null);
		// creating thing for subject
		String vendorThingID = "thing-" + System.currentTimeMillis();
		String password = "pa$$word";
		KiiThing thing = new KiiThing()
			.setVendorThingID(vendorThingID)
			.setPassword(password);
		this.subjectThing = rest.api().things().register(thing);
	}
}
