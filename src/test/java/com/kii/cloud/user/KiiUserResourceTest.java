package com.kii.cloud.user;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import com.kii.cloud.KiiRest;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.TestApp;
import com.kii.cloud.TestEnvironments;
import com.kii.cloud.TestUtils;
import com.kii.cloud.model.KiiNormalUser;
import com.kii.cloud.model.KiiPseudoUser;
import com.kii.cloud.model.KiiUser;
import com.kii.cloud.model.KiiUserCredentials;
import com.kii.cloud.resource.KiiUserResource.NotificationMethod;

public class KiiUserResourceTest {
	
	@Test
	public void normalUserTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.AppID, testApp.AppKey, testApp.Site);
		
		String currentTime = String.valueOf(System.currentTimeMillis());
		String username = "test-" + currentTime;
		String email = username + "@example.com";
		String phone = TestUtils.getRandomGlobalJpPhoneNumber();
		String displayname =  username.toUpperCase();
		String country = "JP";
		String password = "pa$$word";
		
		KiiNormalUser testUser = new KiiNormalUser()
			.setUsername(username)
			.setEmail(email)
			.setPhone(phone)
			.setDisplayName(displayname)
			.setCountry(country);
		
		// register new user
		KiiUser registeredUser = rest.api().user().register(testUser, password);
		rest.setCredentials(registeredUser);
		// getting user
		KiiUser user1 = rest.api().user(registeredUser.getUserID()).get();
		assertEquals(registeredUser.getJsonObject(), user1.getJsonObject());
		// updating user
		user1.set("age", "30");
		rest.api().user(user1).update(user1);
		// getting user
		KiiUser user2 = rest.api().user(user1.getUserID()).get();
		assertEquals(user1.getJsonObject(), user2.getJsonObject());
		// changing password
		String newPassword = "newPa$$word";
		rest.api().user(user2).changePassword(password, newPassword);
		try {
			rest.api().oauth().getAccessToken(username, password);
			Assert.fail("KiiRestException should be thrown.");
		} catch (KiiRestException expected) {
		}
		KiiUserCredentials credentials = rest.api().oauth().getAccessToken(username, newPassword);
		rest.setCredentials(credentials);
		// reseting password
		rest.api().user(user2).resetPassword(NotificationMethod.EMAIL);
		// deleting user
		rest.api().user(user2).delete();
	}
	@Test
	public void pseudoUserTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.AppID, testApp.AppKey, testApp.Site);
		
		String currentTime = String.valueOf(System.currentTimeMillis());
		String username = "test-" + currentTime;
		String displayname =  username.toUpperCase();
		String country = "JP";
		String password = "pa$$word";

		KiiPseudoUser testUser = new KiiPseudoUser()
			.setDisplayName(displayname)
			.setCountry(country);
		
		// register pseudo user
		KiiUser registeredUser = rest.api().user().register(testUser);
		rest.setCredentials(registeredUser);
		// getting user
		KiiUser user1 = rest.api().user(registeredUser.getUserID()).get();
		assertEquals(registeredUser.getJsonObject(), user1.getJsonObject());
		// updating user
		user1.set("age", "30");
		rest.api().user(user1).update(user1);
		// getting user
		KiiUser user2 = rest.api().user(user1.getUserID()).get();
		assertEquals(user1.getJsonObject(), user2.getJsonObject());
		// becoming normal users
		rest.api().user(user2).updateToNormal((KiiPseudoUser)user2, username, null, null, password);
		// getting normal user
		KiiUser user3 = rest.api().user(user2.getUserID()).get();
		Assert.assertFalse(user3.isPseudo());
	}
}
