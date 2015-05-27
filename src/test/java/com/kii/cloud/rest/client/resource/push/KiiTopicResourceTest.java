package com.kii.cloud.rest.client.resource.push;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestAppFilter;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.KiiCredentials;
import com.kii.cloud.rest.client.model.KiiListResult;
import com.kii.cloud.rest.client.model.push.GCMMessage;
import com.kii.cloud.rest.client.model.push.KiiPushMessage;
import com.kii.cloud.rest.client.model.storage.KiiGroup;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiThing;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiTopicResourceTest {
	@Test
	public void appScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		String appTopicName = "app_topic" + System.currentTimeMillis();
		
		// listing topics
		KiiListResult<String> existingTopicNames = rest.api().topics().list();
		
		// creating topic
		rest.api().topics(appTopicName).create();
		// checking topic
		boolean exists = rest.api().topics(appTopicName).exists();
		assertTrue(exists);
		
		// listing topics
		KiiListResult<String> topicNames = rest.api().topics().list();
		assertTrue(topicNames.size() - existingTopicNames.size() == 1);
		assertTrue(topicNames.getResult().contains(appTopicName));
		
		// subscribing topic
		rest.api().topics(appTopicName).subscribe(user);
		// checking subscription status
		boolean isSubscribed = rest.api().topics(appTopicName).isSubscribed(user);
		assertTrue(isSubscribed);
		
		// Sending message
		JsonObject messageBody = new JsonObject();
		messageBody.addProperty("msg", "test");
		KiiPushMessage message = new KiiPushMessage(messageBody);
		message.setGCM(new GCMMessage(new JsonObject()));
		String pushMessageID = rest.api().topics(appTopicName).send(message);
		assertNotNull(pushMessageID);
		
		// unsubscribing topic
		rest.api().topics(appTopicName).unsubscribe(user);
		// checking subscription status
		isSubscribed = rest.api().topics(appTopicName).isSubscribed(user);
		assertFalse(isSubscribed);
		
		// deleting topic
		rest.api().topics(appTopicName).delete();
	}
	@Test
	public void userScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userTopicName = "user_topic" + System.currentTimeMillis();
		
		// listing topics
		KiiListResult<String> existingTopicNames = rest.api().users(user).topics().list();
		
		// creating topic
		rest.api().users(user).topics(userTopicName).create();
		// checking topic
		boolean exists = rest.api().users(user).topics(userTopicName).exists();
		assertTrue(exists);
		
		// listing topics
		KiiListResult<String> topicNames = rest.api().users(user).topics().list();
		assertTrue(topicNames.size() - existingTopicNames.size() == 1);
		assertTrue(topicNames.getResult().contains(userTopicName));
		
		// subscribing topic
		rest.api().users(user).topics(userTopicName).subscribe(user);
		// checking subscription status
		boolean isSubscribed = rest.api().users(user).topics(userTopicName).isSubscribed(user);
		assertTrue(isSubscribed);
		
		// Sending message
		JsonObject messageBody = new JsonObject();
		messageBody.addProperty("msg", "test");
		KiiPushMessage message = new KiiPushMessage(messageBody);
		message.setGCM(new GCMMessage(new JsonObject()));
		String pushMessageID = rest.api().users(user).topics(userTopicName).send(message);
		assertNotNull(pushMessageID);
		
		// unsubscribing topic
		rest.api().users(user).topics(userTopicName).unsubscribe(user);
		// checking subscription status
		isSubscribed = rest.api().users(user).topics(userTopicName).isSubscribed(user);
		assertFalse(isSubscribed);
		
		// deleting topic
		rest.api().users(user).topics(userTopicName).delete();
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
		rest.api().groups().save(group, null);
		
		String groupTopicName = "group_topic" + System.currentTimeMillis();
		
		// listing topics
		KiiListResult<String> existingTopicNames = rest.api().groups(group).topics().list();
		
		// creating topic
		rest.api().groups(group).topics(groupTopicName).create();
		// checking topic
		boolean exists = rest.api().groups(group).topics(groupTopicName).exists();
		assertTrue(exists);
		
		// listing topics
		KiiListResult<String> topicNames = rest.api().groups(group).topics().list();
		assertTrue(topicNames.size() - existingTopicNames.size() == 1);
		assertTrue(topicNames.getResult().contains(groupTopicName));
		
		// subscribing topic
		rest.api().groups(group).topics(groupTopicName).subscribeByUser(user.getUserID());
		// checking subscription status
		boolean isSubscribed = rest.api().groups(group).topics(groupTopicName).isSubscribedByUser(user.getUserID());
		assertTrue(isSubscribed);
		
		// Sending message
		JsonObject messageBody = new JsonObject();
		messageBody.addProperty("msg", "test");
		KiiPushMessage message = new KiiPushMessage(messageBody);
		message.setGCM(new GCMMessage(new JsonObject()));
		String pushMessageID = rest.api().groups(group).topics(groupTopicName).send(message);
		assertNotNull(pushMessageID);
		
		// unsubscribing topic
		rest.api().groups(group).topics(groupTopicName).unsubscribeByUser(user.getUserID());
		// checking subscription status
		isSubscribed = rest.api().groups(group).topics(groupTopicName).isSubscribedByUser(user.getUserID());
		assertFalse(isSubscribed);
		
		// deleting topic
		rest.api().groups(group).topics(groupTopicName).delete();
	}
	
	@Test
	public void thingScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
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
		
		String thingTopicName = "thing_topic" + System.currentTimeMillis();
		
		// listing topics
		KiiListResult<String> existingTopicNames = rest.api().things(thingID).topics().list();
		
		// creating topic
		rest.api().things(thingID).topics(thingTopicName).create();
		// checking topic
		boolean exists = rest.api().things(thingID).topics(thingTopicName).exists();
		assertTrue(exists);
		
		// listing topics
		KiiListResult<String> topicNames = rest.api().things(thingID).topics().list();
		assertTrue(topicNames.size() - existingTopicNames.size() == 1);
		assertTrue(topicNames.getResult().contains(thingTopicName));
		
		// subscribing topic
		rest.api().things(thingID).topics(thingTopicName).subscribe(thing);
		// checking subscription status
		assertTrue(rest.api().things(thingID).topics(thingTopicName).isSubscribed(thing));
		
		// Sending message
		JsonObject messageBody = new JsonObject();
		messageBody.addProperty("msg", "test");
		KiiPushMessage message = new KiiPushMessage(messageBody);
		message.setGCM(new GCMMessage(new JsonObject()));
		String pushMessageID = rest.api().things(thingID).topics(thingTopicName).send(message);
		assertNotNull(pushMessageID);
		
		// unsubscribing topic
		rest.api().things(thingID).topics(thingTopicName).unsubscribe(thing);
		// checking subscription status
		assertFalse(rest.api().things(thingID).topics(thingTopicName).isSubscribed(thing));
		
		// deleting topic
		rest.api().things(thingID).topics(thingTopicName).delete();
	}

}
