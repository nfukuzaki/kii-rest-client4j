package com.kii.cloud.rest.client.resource.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestAppFilter;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.TestUtils;
import com.kii.cloud.rest.client.exception.KiiForbiddenException;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.exception.KiiUnauthorizedException;
import com.kii.cloud.rest.client.model.KiiCredentials;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiObject;
import com.kii.cloud.rest.client.model.storage.KiiPseudoUser;
import com.kii.cloud.rest.client.model.storage.KiiUser;
import com.kii.cloud.rest.client.resource.storage.KiiUserResource.NotificationMethod;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiUserResourceTest {
	@Test
	public void normalUserTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().disableEmailVerification());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
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
		KiiUser registeredUser = rest.api().users().register(testUser, password);
		rest.setCredentials(registeredUser);
		// getting user
		KiiUser user1 = rest.api().users(registeredUser.getUserID()).get();
		assertEquals(registeredUser.getJsonObject(), user1.getJsonObject());
		// updating user
		user1.set("age", "30");
		rest.api().users(user1).update(user1);
		// getting user
		KiiUser user2 = rest.api().users(user1.getUserID()).get();
		assertEquals(user1.getJsonObject(), user2.getJsonObject());
		// changing password
		String newPassword = "newPa$$word";
		rest.api().users(user2).changePassword(password, newPassword);
		try {
			rest.api().oauth().getAccessToken(username, password);
			Assert.fail("KiiRestException should be thrown.");
		} catch (KiiRestException expected) {
		}
		KiiCredentials credentials = rest.api().oauth().getAccessToken(username, newPassword);
		rest.setCredentials(credentials);
		// reseting password
		rest.api().users(user2).resetPassword(NotificationMethod.EMAIL);
		// deleting user
		rest.api().users(user2).delete();
	}
	@Test
	public void pseudoUserTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		String currentTime = String.valueOf(System.currentTimeMillis());
		String username = "test-" + currentTime;
		String displayname =  username.toUpperCase();
		String country = "JP";
		String password = "pa$$word";

		KiiPseudoUser testUser = new KiiPseudoUser()
			.setDisplayName(displayname)
			.setCountry(country);
		
		// register pseudo user
		KiiUser registeredUser = rest.api().users().register(testUser);
		rest.setCredentials(registeredUser);
		// getting user
		KiiUser user1 = rest.api().users(registeredUser.getUserID()).get();
		assertEquals(registeredUser.getJsonObject(), user1.getJsonObject());
		// updating user
		user1.set("age", "30");
		rest.api().users(user1).update(user1);
		// getting user
		KiiUser user2 = rest.api().users(user1.getUserID()).get();
		assertEquals(user1.getJsonObject(), user2.getJsonObject());
		// becoming normal users
		rest.api().users(user2).updateToNormal((KiiPseudoUser)user2, username, null, null, password);
		// getting normal user
		KiiUser user3 = rest.api().users(user2.getUserID()).get();
		Assert.assertFalse(user3.isPseudo());
	}
	@Test
	public void emailVerificationTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials().enableEmailVerification());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		String currentTime = String.valueOf(System.currentTimeMillis());
		String username = "test-" + currentTime;
		String email = username + "@example.com";
		String password = "pa$$word";
		
		KiiNormalUser user = new KiiNormalUser()
			.setUsername(username)
			.setEmail(email);
		
		// register new user
		KiiUser registeredUser = rest.api().users().register(user, password);
		rest.setCredentials(registeredUser);
		
		// check the emailAddressVerified
		user = (KiiNormalUser)rest.api().users(registeredUser).get();
		assertFalse(user.isEmailAddressVerified());
		
		// switch admin context
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		String verificationCode = rest.api().users(registeredUser).getEmailVerificationCode();
		assertNotNull(verificationCode);
		
		// switch user context
		rest.setCredentials(registeredUser);
		
		// verify email
		rest.api().users(registeredUser).verifyEmail(verificationCode);
		
		// check the emailAddressVerified
		user = (KiiNormalUser)rest.api().users(registeredUser).get();
		assertTrue(user.isEmailAddressVerified());
	}
	@Test
	public void phoneVerificationTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials().enablePhoneVerification());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		String currentTime = String.valueOf(System.currentTimeMillis());
		String username = "test-" + currentTime;
		String phone = TestUtils.getRandomGlobalJpPhoneNumber();
		String password = "pa$$word";
		
		KiiNormalUser user = new KiiNormalUser()
			.setUsername(username)
			.setPhone(phone);
		
		// register new user
		KiiUser registeredUser = rest.api().users().register(user, password);
		rest.setCredentials(registeredUser);
		
		// check the phoneNumberVerified
		user = (KiiNormalUser)rest.api().users(registeredUser).get();
		assertFalse(user.isPhoneNumberVerified());
		
		// switch admin context
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		String verificationCode = rest.api().users(registeredUser).getPhoneVerificationCode();
		assertNotNull(verificationCode);
		
		// switch user context
		rest.setCredentials(registeredUser);
		
		// verify phone
		rest.api().users(registeredUser).verifyPhone(verificationCode);
		
		// check the phoneNumberVerified
		user = (KiiNormalUser)rest.api().users(registeredUser).get();
		assertTrue(user.isPhoneNumberVerified());
	}
	@Test
	public void emailVerificationForAdminTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials().enableEmailVerification());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		String currentTime = String.valueOf(System.currentTimeMillis());
		String username = "test-" + currentTime;
		String email = username + "@example.com";
		String password = "pa$$word";
		
		KiiNormalUser user = new KiiNormalUser()
			.setUsername(username)
			.setEmail(email);
		
		// register new user
		KiiUser registeredUser = rest.api().users().register(user, password);
		rest.setCredentials(registeredUser);
		
		// check the emailAddressVerified
		user = (KiiNormalUser)rest.api().users(registeredUser).get();
		assertFalse(user.isEmailAddressVerified());
		
		// switch admin context
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		String verificationCode1 = rest.api().users(registeredUser).getEmailVerificationCode();
		assertNotNull(verificationCode1);
		
		// re-sending email verification
		rest.api().users(registeredUser).resendEmailVerification();
		String verificationCode2 = rest.api().users(registeredUser).getEmailVerificationCode();
		assertEquals(verificationCode1, verificationCode2);
	}
	@Test
	public void phoneVerificationForAdminTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials().enablePhoneVerification());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		String currentTime = String.valueOf(System.currentTimeMillis());
		String username = "test-" + currentTime;
		String phone = TestUtils.getRandomGlobalJpPhoneNumber();
		String password = "pa$$word";
		
		KiiNormalUser user = new KiiNormalUser()
			.setUsername(username)
			.setPhone(phone);
		
		// register new user
		KiiUser registeredUser = rest.api().users().register(user, password);
		rest.setCredentials(registeredUser);
		
		// check the phoneNumberVerified
		user = (KiiNormalUser)rest.api().users(registeredUser).get();
		assertFalse(user.isPhoneNumberVerified());
		
		// switch admin context
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		String verificationCode1 = rest.api().users(registeredUser).getPhoneVerificationCode();
		assertNotNull(verificationCode1);
		
		// re-sending phone verification
		rest.api().users(registeredUser).resendPhoneVerification();
		String verificationCode2 = rest.api().users(registeredUser).getPhoneVerificationCode();
		assertEquals(verificationCode1, verificationCode2);
		
		// reseting phone verification
		rest.api().users(registeredUser).resetPhoneVerification();
		String verificationCode3 = rest.api().users(registeredUser).getPhoneVerificationCode();
		assertNotEquals(verificationCode2, verificationCode3);
		
		
		// switch user context
		rest.setCredentials(registeredUser);
		
		// verify phone by old verificationCode
		try {
			rest.api().users(registeredUser).verifyPhone(verificationCode2);
			fail("KiiRestException should be thrown");
		} catch (KiiRestException e) {
		}
		// verify phone by new verificationCode
		rest.api().users(registeredUser).verifyPhone(verificationCode3);
		
		// check the phoneNumberVerified
		user = (KiiNormalUser)rest.api().users(registeredUser).get();
		assertTrue(user.isPhoneNumberVerified());
	}
	@Test
	public void allUserFieldsTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().disableEmailVerification().disablePhoneVerification());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		String currentTime = String.valueOf(System.currentTimeMillis());
		String username = "test-" + currentTime;
		String email = username + "@example.com";
		String phone = TestUtils.getRandomGlobalJpPhoneNumber();
		String displayname =  username.toUpperCase();
		String country = "JP";
		String password = "pa$$word";
		
		KiiNormalUser testUser = (KiiNormalUser)new KiiNormalUser()
			.setUsername(username)
			.setEmail(email)
			.setPhone(phone)
			.setDisplayName(displayname)
			.setCountry(country)
			.set("string_field", "abc")
			.set("int_field", 10)
			.set("long_field", 100L)
			.set("bool_field", false)
			.set("bigdecimal_field", new BigDecimal("3.14"))
			.set("json_field", (JsonObject)new JsonParser().parse("{\"json\":123}"))
			.set("json_array_field", (JsonArray)new JsonParser().parse("[1,2,3]"))
			;
		
		// register new user
		KiiUser registeredUser = rest.api().users().register(testUser, password);
		rest.setCredentials(registeredUser);
		
		KiiNormalUser actual = (KiiNormalUser)rest.api().users(registeredUser).get();
		
		assertTrue(actual.hasPassword());
		assertTrue(actual.getInternalUserID() > 0);
		assertEquals(username, actual.getUsername());
		assertEquals(phone, actual.getPhone());
		assertEquals(email, actual.getEmail());
		assertEquals(country, actual.getCountry());
		assertTrue(actual.isEmailAddressVerified());
		assertTrue(actual.isPhoneNumberVerified());
		assertFalse(actual.isDisabled());
		assertEquals("abc", actual.getString("string_field"));
		assertEquals(10, (int)actual.getInt("int_field"));
		assertEquals(100, (long)actual.getLong("long_field"));
		assertEquals(false, (boolean)actual.getBoolean("bool_field"));
		assertEquals(new BigDecimal("3.14"), actual.getBigDecimal("bigdecimal_field"));
		assertEquals(new JsonParser().parse("{\"json\":123}"), actual.getJsonObject("json_field"));
		assertEquals(new JsonParser().parse("[1,2,3]"), actual.getJsonArray("json_array_field"));
	}
	@Test
	public void unauthorizedExceptionTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketName = "user_bucket" + System.currentTimeMillis();
		
		// creating object
		KiiObject object = new KiiObject().set("size", 1024);
		rest.api().users(user).buckets(userBucketName).objects().save(object);
		
		rest.setCredentials(null);
		
		try {
			rest.api().users(user).buckets(userBucketName).objects(object).get();
			fail("KiiUnauthorizedException should be thrown");
		} catch (KiiUnauthorizedException e) {
		}
	}
	@Test
	public void forbiddenExceptionTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketName = "user_bucket" + System.currentTimeMillis();
		
		// creating object
		KiiObject object = new KiiObject().set("size", 1024);
		rest.api().users(user).buckets(userBucketName).objects().save(object);
		
		KiiCredentials credentials = new KiiCredentials("xxxxxxxxxxxxxxxxxxxxxxxxxx");
		rest.setCredentials(credentials);
		
		try {
			rest.api().users(user).buckets(userBucketName).objects(object).get();
			fail("KiiForbiddenException should be thrown");
		} catch (KiiForbiddenException e) {
		}
	}
}
