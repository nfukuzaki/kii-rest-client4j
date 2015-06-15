package com.kii.cloud.rest.client.resource.push;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

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
import com.kii.cloud.rest.client.model.push.KiiGCMMessage;
import com.kii.cloud.rest.client.model.push.KiiPushMessage;
import com.kii.cloud.rest.client.model.push.KiiTopic;
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
		
		String appTopicID = "app_topic" + System.currentTimeMillis();
		
		// listing topics
		KiiListResult<KiiTopic> existingTopics = rest.api().topics().list();
		
		// creating topic
		rest.api().topics().create(appTopicID);
		// checking topic
		boolean exists = rest.api().topics(appTopicID).exists();
		assertTrue(exists);
		
		// listing topics
		KiiListResult<KiiTopic> topics = rest.api().topics().list();
		assertTrue(topics.size() - existingTopics.size() == 1);
		assertTrue(containsKiiTopic(topics.getResult(), appTopicID));
		
		// subscribing topic
		rest.api().topics(appTopicID).subscribe(user);
		// checking subscription status
		boolean isSubscribed = rest.api().topics(appTopicID).isSubscribed(user);
		assertTrue(isSubscribed);
		
		// Sending message
		JsonObject messageBody = new JsonObject();
		messageBody.addProperty("msg", "test");
		KiiPushMessage message = new KiiPushMessage(messageBody);
		message.setGCM(new KiiGCMMessage(new JsonObject()));
		String pushMessageID = rest.api().topics(appTopicID).send(message);
		assertNotNull(pushMessageID);
		
		// unsubscribing topic
		rest.api().topics(appTopicID).unsubscribe(user);
		// checking subscription status
		isSubscribed = rest.api().topics(appTopicID).isSubscribed(user);
		assertFalse(isSubscribed);
		
		// deleting topic
		rest.api().topics(appTopicID).delete();
	}
	@Test
	public void userScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userTopicID = "user_topic" + System.currentTimeMillis();
		
		// listing topics
		KiiListResult<KiiTopic> existingTopics = rest.api().users(user).topics().list();
		
		// creating topic
		rest.api().users(user).topics().create(userTopicID);
		// checking topic
		boolean exists = rest.api().users(user).topics(userTopicID).exists();
		assertTrue(exists);
		
		// listing topics
		KiiListResult<KiiTopic> topics = rest.api().users(user).topics().list();
		assertTrue(topics.size() - existingTopics.size() == 1);
		assertTrue(containsKiiTopic(topics.getResult(), userTopicID));
		
		// subscribing topic
		rest.api().users(user).topics(userTopicID).subscribe(user);
		// checking subscription status
		boolean isSubscribed = rest.api().users(user).topics(userTopicID).isSubscribed(user);
		assertTrue(isSubscribed);
		
		// Sending message
		JsonObject messageBody = new JsonObject();
		messageBody.addProperty("msg", "test");
		KiiPushMessage message = new KiiPushMessage(messageBody);
		message.setGCM(new KiiGCMMessage(new JsonObject()));
		String pushMessageID = rest.api().users(user).topics(userTopicID).send(message);
		assertNotNull(pushMessageID);
		
		// unsubscribing topic
		rest.api().users(user).topics(userTopicID).unsubscribe(user);
		// checking subscription status
		isSubscribed = rest.api().users(user).topics(userTopicID).isSubscribed(user);
		assertFalse(isSubscribed);
		
		// deleting topic
		rest.api().users(user).topics(userTopicID).delete();
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
		
		String groupTopicID = "group_topic" + System.currentTimeMillis();
		
		// listing topics
		KiiListResult<KiiTopic> existingTopics = rest.api().groups(group).topics().list();
		
		// creating topic
		rest.api().groups(group).topics().create(groupTopicID);
		// checking topic
		boolean exists = rest.api().groups(group).topics(groupTopicID).exists();
		assertTrue(exists);
		
		// listing topics
		KiiListResult<KiiTopic> topics = rest.api().groups(group).topics().list();
		assertTrue(topics.size() - existingTopics.size() == 1);
		assertTrue(containsKiiTopic(topics.getResult(), groupTopicID));
		
		// subscribing topic
		rest.api().groups(group).topics(groupTopicID).subscribeByUser(user.getUserID());
		// checking subscription status
		boolean isSubscribed = rest.api().groups(group).topics(groupTopicID).isSubscribedByUser(user.getUserID());
		assertTrue(isSubscribed);
		
		// Sending message
		JsonObject messageBody = new JsonObject();
		messageBody.addProperty("msg", "test");
		KiiPushMessage message = new KiiPushMessage(messageBody);
		message.setGCM(new KiiGCMMessage(new JsonObject()));
		String pushMessageID = rest.api().groups(group).topics(groupTopicID).send(message);
		assertNotNull(pushMessageID);
		
		// unsubscribing topic
		rest.api().groups(group).topics(groupTopicID).unsubscribeByUser(user.getUserID());
		// checking subscription status
		isSubscribed = rest.api().groups(group).topics(groupTopicID).isSubscribedByUser(user.getUserID());
		assertFalse(isSubscribed);
		
		// deleting topic
		rest.api().groups(group).topics(groupTopicID).delete();
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
		
		String thingTopicID = "thing_topic" + System.currentTimeMillis();
		
		// listing topics
		KiiListResult<KiiTopic> existingTopics = rest.api().things(thingID).topics().list();
		
		// creating topic
		rest.api().things(thingID).topics().create(thingTopicID);
		// checking topic
		boolean exists = rest.api().things(thingID).topics(thingTopicID).exists();
		assertTrue(exists);
		
		// listing topics
		KiiListResult<KiiTopic> topics = rest.api().things(thingID).topics().list();
		assertTrue(topics.size() - existingTopics.size() == 1);
		assertTrue(containsKiiTopic(topics.getResult(), thingTopicID));
		
		// subscribing topic
		rest.api().things(thingID).topics(thingTopicID).subscribe(thing);
		// checking subscription status
		assertTrue(rest.api().things(thingID).topics(thingTopicID).isSubscribed(thing));
		
		// Sending message
		JsonObject messageBody = new JsonObject();
		messageBody.addProperty("msg", "test");
		KiiPushMessage message = new KiiPushMessage(messageBody);
		message.setGCM(new KiiGCMMessage(new JsonObject()));
		String pushMessageID = rest.api().things(thingID).topics(thingTopicID).send(message);
		assertNotNull(pushMessageID);
		
		// unsubscribing topic
		rest.api().things(thingID).topics(thingTopicID).unsubscribe(thing);
		// checking subscription status
		assertFalse(rest.api().things(thingID).topics(thingTopicID).isSubscribed(thing));
		
		// deleting topic
		rest.api().things(thingID).topics(thingTopicID).delete();
	}
	private boolean containsKiiTopic(List<KiiTopic> topics, String topicID) {
		for (KiiTopic topic : topics) {
			if (topic.getTopicID().equals(topicID)) {
				return true;
			}
		}
		return false;
	}
}
