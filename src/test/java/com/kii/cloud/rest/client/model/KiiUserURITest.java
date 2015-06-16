package com.kii.cloud.rest.client.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.storage.KiiAccountType;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiUser;
import com.kii.cloud.rest.client.model.uri.KiiURI;
import com.kii.cloud.rest.client.model.uri.KiiUserURI;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiUserURITest {
	@Test
	public void userIdTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user1 = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user1 = rest.api().users().register(user1, "password");
		rest.setCredentials(user1);
		
		String expectedUriString = String.format("kiicloud://%s/users/%s", testApp.getAppID(), user1.getUserID());
		assertEquals(expectedUriString, user1.getURI().toUriString());
		assertEquals(user1.getURI(), KiiURI.parse(expectedUriString));
		assertEquals(user1.getURI(), KiiUserURI.parse(expectedUriString));
		assertEquals(KiiAccountType.USER_ID, user1.getURI().getAccountType());
		
		KiiUser user2 = rest.api().users(user1.getURI()).get();
		assertEquals(user1.getUserID(), user2.getUserID());
		assertEquals(user1.getURI(), user2.getURI());
	}
	@Test
	public void accountTypeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		
		String email = "example@kii.com";
		String phone = "+089011112222";
		String name = "test_user";
		
		String emailURI = String.format("kiicloud://%s/users/%s", testApp.getAppID(), KiiAccountType.EMAIL.getFullyQualifiedIdentifier(email));
		String phoneURI = String.format("kiicloud://%s/users/%s", testApp.getAppID(), KiiAccountType.PHONE.getFullyQualifiedIdentifier(phone));
		String nameURI = String.format("kiicloud://%s/users/%s", testApp.getAppID(), KiiAccountType.LOGIN_NAME.getFullyQualifiedIdentifier(name));
		
		assertEquals(KiiAccountType.EMAIL, KiiUserURI.parse(emailURI).getAccountType());
		assertEquals(email, KiiUserURI.parse(emailURI).getIdentifier());
		assertEquals(KiiAccountType.PHONE, KiiUserURI.parse(phoneURI).getAccountType());
		assertEquals(phone, KiiUserURI.parse(phoneURI).getIdentifier());
		assertEquals(KiiAccountType.LOGIN_NAME, KiiUserURI.parse(nameURI).getAccountType());
		assertEquals(name, KiiUserURI.parse(nameURI).getIdentifier());
		assertEquals(KiiURI.parse(emailURI), KiiUserURI.parse(emailURI));
		assertEquals(KiiURI.parse(phoneURI), KiiUserURI.parse(phoneURI));
		assertEquals(KiiURI.parse(nameURI), KiiUserURI.parse(nameURI));
	}
}
