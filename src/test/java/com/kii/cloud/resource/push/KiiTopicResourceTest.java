package com.kii.cloud.resource.push;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRest;
import com.kii.cloud.SkipAcceptableTestRunner;
import com.kii.cloud.TestApp;
import com.kii.cloud.TestAppFilter;
import com.kii.cloud.TestEnvironments;
import com.kii.cloud.model.KiiAdminCredentials;
import com.kii.cloud.model.push.GCMMessage;
import com.kii.cloud.model.push.KiiPushMessage;
import com.kii.cloud.model.storage.KiiGroup;
import com.kii.cloud.model.storage.KiiNormalUser;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiTopicResourceTest {
	@Test
	public void appScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		
		KiiAdminCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		String appTopicName = "app_topic" + System.currentTimeMillis();
		
		// creating topic
		rest.api().topics(appTopicName).create();
		// checking topic
		boolean exists = rest.api().topics(appTopicName).exists();
		assertTrue(exists);
		
		// subscribing topic
		rest.api().topics(appTopicName).subscribe(user.getUserID());
		// checking subscription status
		boolean isSubscribed = rest.api().topics(appTopicName).isSubscribed(user.getUserID());
		assertTrue(isSubscribed);
		
		// Sending message
		JsonObject messageBody = new JsonObject();
		messageBody.addProperty("msg", "test");
		KiiPushMessage message = new KiiPushMessage(messageBody);
		message.setGCM(new GCMMessage(new JsonObject()));
		String pushMessageID = rest.api().topics(appTopicName).send(message);
		assertNotNull(pushMessageID);
		
		// unsubscribing topic
		rest.api().topics(appTopicName).unsubscribe(user.getUserID());
		// checking subscription status
		isSubscribed = rest.api().topics(appTopicName).isSubscribed(user.getUserID());
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
		
		// creating topic
		rest.api().users(user).topics(userTopicName).create();
		// checking topic
		boolean exists = rest.api().users(user).topics(userTopicName).exists();
		assertTrue(exists);
		
		// subscribing topic
		rest.api().users(user).topics(userTopicName).subscribe(user.getUserID());
		// checking subscription status
		boolean isSubscribed = rest.api().users(user).topics(userTopicName).isSubscribed(user.getUserID());
		assertTrue(isSubscribed);
		
		// Sending message
		JsonObject messageBody = new JsonObject();
		messageBody.addProperty("msg", "test");
		KiiPushMessage message = new KiiPushMessage(messageBody);
		message.setGCM(new GCMMessage(new JsonObject()));
		String pushMessageID = rest.api().users(user).topics(userTopicName).send(message);
		assertNotNull(pushMessageID);
		
		// unsubscribing topic
		rest.api().users(user).topics(userTopicName).unsubscribe(user.getUserID());
		// checking subscription status
		isSubscribed = rest.api().users(user).topics(userTopicName).isSubscribed(user.getUserID());
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
		
		// creating topic
		rest.api().groups(group).topics(groupTopicName).create();
		// checking topic
		boolean exists = rest.api().groups(group).topics(groupTopicName).exists();
		assertTrue(exists);
		
		// subscribing topic
		rest.api().groups(group).topics(groupTopicName).subscribe(user.getUserID());
		// checking subscription status
		boolean isSubscribed = rest.api().groups(group).topics(groupTopicName).isSubscribed(user.getUserID());
		assertTrue(isSubscribed);
		
		// Sending message
		JsonObject messageBody = new JsonObject();
		messageBody.addProperty("msg", "test");
		KiiPushMessage message = new KiiPushMessage(messageBody);
		message.setGCM(new GCMMessage(new JsonObject()));
		String pushMessageID = rest.api().groups(group).topics(groupTopicName).send(message);
		assertNotNull(pushMessageID);
		
		// unsubscribing topic
		rest.api().groups(group).topics(groupTopicName).unsubscribe(user.getUserID());
		// checking subscription status
		isSubscribed = rest.api().groups(group).topics(groupTopicName).isSubscribed(user.getUserID());
		assertFalse(isSubscribed);
		
		// deleting topic
		rest.api().groups(group).topics(groupTopicName).delete();
	}
}
