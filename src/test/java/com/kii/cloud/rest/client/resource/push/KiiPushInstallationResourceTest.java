package com.kii.cloud.rest.client.resource.push;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestAppFilter;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.exception.KiiNotFoundException;
import com.kii.cloud.rest.client.model.KiiCredentials;
import com.kii.cloud.rest.client.model.push.KiiMqttEndpoint;
import com.kii.cloud.rest.client.model.push.KiiPushInstallation;
import com.kii.cloud.rest.client.model.push.KiiPushInstallation.InstallationType;
import com.kii.cloud.rest.client.model.push.KiiPushMessage;
import com.kii.cloud.rest.client.model.push.KiiMqttMessage;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiThing;
import com.kii.cloud.rest.client.model.storage.KiiThingOwner;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiPushInstallationResourceTest {
	@Test
	public void mqttTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiCredentials adminCredentials = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		
		// registering user
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		// registering thing
		String vendorThingID = "thing-" + System.currentTimeMillis();
		String password = "pa$$word";
		KiiThing thing = new KiiThing()
			.setVendorThingID(vendorThingID)
			.setProductName("KiiCloud")
			.setPassword(password);
		thing = rest.api().things().register(thing);
		String thingID = thing.getThingID();
		// adding owner to thing
		rest.api().things(thingID).owner().add(KiiThingOwner.user(user));
		rest.setCredentials(thing);
		
		// installing MQTT
		KiiPushInstallation pushInstallation1 = new KiiPushInstallation()
			.setDevelopment(true)
			.setInstallationType(InstallationType.MQTT);
		rest.api().installations().register(pushInstallation1);
		// getting push installation
		KiiPushInstallation pushInstallation2 = rest.api().installations(pushInstallation1).get();
		assertEquals(pushInstallation1.getInstallationID(), pushInstallation2.getInstallationID());
		assertEquals(pushInstallation1.getInstallationRegistrationID(), pushInstallation2.getInstallationRegistrationID());
		assertEquals(InstallationType.MQTT, pushInstallation1.getInstallationType());
		assertEquals(InstallationType.MQTT, pushInstallation2.getInstallationType());
		assertEquals(pushInstallation1.getInstallationID(), pushInstallation2.getInstallationID());
		assertEquals(thingID, pushInstallation2.getThingID());
		// listing push installations
		rest.setCredentials(adminCredentials);
		List<KiiPushInstallation> pushInstallationList = rest.api().installations().list();
		rest.setCredentials(thing);
		List<KiiPushInstallation> pushInstallationListByThing = rest.api().installations().list(thing);
		rest.setCredentials(user);
		List<KiiPushInstallation> pushInstallationListByUser = rest.api().installations().list(user);
		assertTrue(pushInstallationList.size() >= 1);
		assertEquals(1, pushInstallationListByThing.size());
		assertEquals(0, pushInstallationListByUser.size());
		
		// creating and subscribing a topic
		rest.setCredentials(thing);
		rest.api().things(thing).topics("thing-topic").create();
		rest.api().things(thing).topics("thing-topic").subscribe(thing);
		
		// getting MQTT Endpoint
		KiiMqttEndpoint mqttEndpoint = rest.api().installations(pushInstallation2).getMqttEndpoint(true);
		assertEquals(pushInstallation2.getInstallationID(), mqttEndpoint.getInstallationID());
		
		// receiving MQTT
		final CountDownLatch latch = new CountDownLatch(1);
		final AtomicReference<Throwable> throwable = new AtomicReference<Throwable>();
		final AtomicReference<MqttMessage> message = new AtomicReference<MqttMessage>();
		MemoryPersistence persistence = new MemoryPersistence();
		MqttClient mqttClient = new MqttClient(mqttEndpoint.getTcpServerURI(), mqttEndpoint.getMqttTopic(), persistence);
		MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(true);
		options.setConnectionTimeout(60 * 10);
		options.setKeepAliveInterval(60 * 5); 
		options.setUserName(mqttEndpoint.getUsername());
		options.setPassword(mqttEndpoint.getPassword().toCharArray());
		mqttClient.setCallback(new MqttCallback() {
			@Override
			public void messageArrived(String topic, MqttMessage msg) throws Exception {
				message.set(msg);
				latch.countDown();
			}
			@Override
			public void deliveryComplete(IMqttDeliveryToken token) {
			}
			@Override
			public void connectionLost(Throwable cause) {
				throwable.set(cause);
				latch.countDown();
			}
		});
		mqttClient.connect(options);
		mqttClient.subscribe(mqttEndpoint.getMqttTopic());
		
		// sending message to the topic
		JsonObject messageBody = new JsonObject();
		messageBody.addProperty("msg", "mqtt test");
		KiiPushMessage pushMessage = new KiiPushMessage(messageBody).setSendToDevelopment(true);
		JsonObject mqttMessageBody = new JsonObject();
		mqttMessageBody.addProperty("msg", "mqtt body");
		pushMessage.setMqtt(new KiiMqttMessage(mqttMessageBody));
		rest.api().things(thing).topics("thing-topic").send(pushMessage);
		
		if (!latch.await(20, TimeUnit.SECONDS)) {
			fail("Test timeouts");
		}
		mqttClient.disconnect();
		mqttClient.close();
		assertNull(throwable.get());
		JsonObject payload = (JsonObject)new JsonParser().parse(new String(message.get().getPayload()));
		assertEquals("mqtt body", payload.get("msg").getAsString());
		
		// deleting push installation
		rest.api().installations(pushInstallation2).delete();
		
		try {
			rest.api().installations(pushInstallation2).get();
			fail("KiiNotFoundException should be thrown");
		} catch (KiiNotFoundException e) {
		}
	}
}
