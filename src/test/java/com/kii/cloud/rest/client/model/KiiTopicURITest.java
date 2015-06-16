package com.kii.cloud.rest.client.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.push.KiiTopic;
import com.kii.cloud.rest.client.model.storage.KiiGroup;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiThing;
import com.kii.cloud.rest.client.model.storage.KiiThingOwner;
import com.kii.cloud.rest.client.model.uri.KiiTopicURI;
import com.kii.cloud.rest.client.model.uri.KiiURI;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiTopicURITest {
	@Test
	public void appScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);

		String appTopicName = "app_topic" + System.currentTimeMillis();
		
		KiiTopic topic1 = rest.api().topics(appTopicName).create();
		
		String expectedUriString = String.format("kiicloud://%s/topics/%s", testApp.getAppID(), topic1.getTopicID());
		assertEquals(expectedUriString, topic1.getURI().toUriString());
		assertEquals(topic1.getURI(), KiiURI.parse(expectedUriString));
		assertEquals(topic1.getURI(), KiiTopicURI.parse(expectedUriString));
		KiiTopic topic2 = rest.api().topics(topic1.getURI()).get();
		assertEquals(topic1.getTopicID(), topic2.getTopicID());
		assertEquals(topic1.getURI(), topic2.getURI());
		rest.api().topics(appTopicName).delete();
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
		
		KiiTopic topic1 = rest.api().groups(group).topics(groupTopicName).create();
		
		String expectedUriString = String.format("kiicloud://%s/groups/%s/topics/%s", testApp.getAppID(), group.getGroupID(), topic1.getTopicID());
		assertEquals(expectedUriString, topic1.getURI().toUriString());
		assertEquals(topic1.getURI(), KiiURI.parse(expectedUriString));
		assertEquals(topic1.getURI(), KiiTopicURI.parse(expectedUriString));
		KiiTopic topic2 = rest.api().topics(topic1.getURI()).get();
		assertEquals(topic1.getTopicID(), topic2.getTopicID());
		assertEquals(topic1.getURI(), topic2.getURI());
		rest.api().groups(group).topics(groupTopicName).delete();
	}
	@Test
	public void userScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userTopicName = "user_topic" + System.currentTimeMillis();
		
		KiiTopic topic1 = rest.api().users(user).topics(userTopicName).create();
		
		String expectedUriString = String.format("kiicloud://%s/users/%s/topics/%s", testApp.getAppID(), user.getUserID(), topic1.getTopicID());
		assertEquals(expectedUriString, topic1.getURI().toUriString());
		assertEquals(topic1.getURI(), KiiURI.parse(expectedUriString));
		assertEquals(topic1.getURI(), KiiTopicURI.parse(expectedUriString));
		KiiTopic topic2 = rest.api().topics(topic1.getURI()).get();
		assertEquals(topic1.getTopicID(), topic2.getTopicID());
		assertEquals(topic1.getURI(), topic2.getURI());
		rest.api().users(user).topics(userTopicName).delete();
	}
	@Test
	public void thingScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
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
		
		rest.setCredentials(user);
		
		// adding owner
		rest.api().things(thingID).owner().add(KiiThingOwner.user(user));

		String thingTopicName = "thing_topic" + System.currentTimeMillis();
		
		KiiTopic topic1 = rest.api().things(thing).topics(thingTopicName).create();
		
		String expectedUriString = String.format("kiicloud://%s/things/%s/topics/%s", testApp.getAppID(), thingID, topic1.getTopicID());
		assertEquals(expectedUriString, topic1.getURI().toUriString());
		assertEquals(topic1.getURI(), KiiURI.parse(expectedUriString));
		assertEquals(topic1.getURI(), KiiTopicURI.parse(expectedUriString));
		KiiTopic topic2 = rest.api().topics(topic1.getURI()).get();
		assertEquals(topic1.getTopicID(), topic2.getTopicID());
		assertEquals(topic1.getURI(), topic2.getURI());
		rest.api().things(thing).topics(thingTopicName).delete();
	}
}
