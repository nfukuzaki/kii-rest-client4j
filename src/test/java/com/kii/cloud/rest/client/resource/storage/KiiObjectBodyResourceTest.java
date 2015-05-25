package com.kii.cloud.rest.client.resource.storage;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.OkHttpClientFactory;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestAppFilter;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.exception.KiiNotFoundException;
import com.kii.cloud.rest.client.model.KiiAdminCredentials;
import com.kii.cloud.rest.client.model.storage.KiiChunkedDownloadContext;
import com.kii.cloud.rest.client.model.storage.KiiChunkedUploadContext;
import com.kii.cloud.rest.client.model.storage.KiiGroup;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiObject;
import com.kii.cloud.rest.client.model.storage.KiiThing;
import com.kii.cloud.rest.client.resource.storage.KiiObjectBodyResource;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiObjectBodyResourceTest {
	@Test
	public void normalTransferTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketName = "user_bucket" + System.currentTimeMillis();
		
		byte[] body = this.createObjectBody(1024);
		
		// creating object
		KiiObject object = new KiiObject().set("size", 1024);
		rest.api().users(user).buckets(userBucketName).objects().save(object);
		
		Thread.sleep(1000);
		
		// uploading object body
		ByteArrayInputStream is = new ByteArrayInputStream(body);
		rest.api().users(user).buckets(userBucketName).objects(object).body().upload("image/jpg", is);
		
		// publishing body
		String url = rest.api().users(user).buckets(userBucketName).objects(object).body().publish();
		assertTrue(url.startsWith("https://" + testApp.getAppID()));
		assertArrayEquals(body, getPublishedBody(url));
		
		// downloading object body
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		rest.api().users(user).buckets(userBucketName).objects(object).body().download(os);
		
		assertArrayEquals(body, os.toByteArray());
		
		// deleting object body
		rest.api().users(user).buckets(userBucketName).objects(object).body().delete();
	}
	@Test
	public void chunkedTransferTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketName = "user_bucket" + System.currentTimeMillis();
		
		byte[] body = this.createObjectBody(1024*3);
		
		// creating object
		KiiObject object = new KiiObject().set("size", 1024*3);
		rest.api().users(user).buckets(userBucketName).objects().save(object);
		
		// uploading object body by chunk
		KiiObjectBodyResource bodyResource =rest.api().users(user).buckets(userBucketName).objects(object).body();
		KiiChunkedUploadContext uploadContext = bodyResource.beginChunkedUpload("image/jpg", body.length);
		int offset = 0;
		int chunkSize = 1024;
		while (!uploadContext.isCompleted()) {
			uploadContext = bodyResource.uploadByChunk(uploadContext, Arrays.copyOfRange(body, offset, (offset + chunkSize)));
			offset += chunkSize;
		}
		bodyResource.commitChunkedUpload(uploadContext);
		
		// publishing body
		String url = rest.api().users(user).buckets(userBucketName).objects(object).body().publish();
		assertTrue(url.startsWith("https://" + testApp.getAppID()));
		assertArrayEquals(body, getPublishedBody(url));
		
		// downloading object body by chunk
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		KiiChunkedDownloadContext downloadContext = new KiiChunkedDownloadContext(1024);
		while (!downloadContext.isCompleted()) {
			downloadContext = bodyResource.downloadByChunk(downloadContext, os);
		}
		assertArrayEquals(body, os.toByteArray());
		
		// deleting object body
		rest.api().users(user).buckets(userBucketName).objects(object).body().delete();
	}
	@Test
	public void chunkedTransferCancelTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String userBucketName = "user_bucket" + System.currentTimeMillis();
		
		byte[] body = this.createObjectBody(1024*3);
		
		// creating object
		KiiObject object = new KiiObject().set("size", 1024*3);
		rest.api().users(user).buckets(userBucketName).objects().save(object);
		
		// uploading object body by chunk
		KiiObjectBodyResource bodyResource =rest.api().users(user).buckets(userBucketName).objects(object).body();
		KiiChunkedUploadContext uploadContext = bodyResource.beginChunkedUpload("image/jpg", body.length);
		int offset = 0;
		int chunkSize = 1024;
		while (!uploadContext.isCompleted()) {
			uploadContext = bodyResource.uploadByChunk(uploadContext, Arrays.copyOfRange(body, offset, (offset + chunkSize)));
			offset += chunkSize;
		}
		bodyResource.cancelChunkedUpload(uploadContext);
		
		// publishing body
		try {
			rest.api().users(user).buckets(userBucketName).objects(object).body().publish();
			fail("KiiNotFoundException should be thrown");
		} catch (KiiNotFoundException e) {
		}
	}
	@Test
	public void moveToAppScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		byte[] body = this.createObjectBody(1024);
		
		String userBucketName = "user_bucket" + System.currentTimeMillis();
		
		// creating object (source)
		KiiObject userObject = new KiiObject().set("size", 1024);
		rest.api().users(user).buckets(userBucketName).objects().save(userObject);
		
		Thread.sleep(1000);
		
		// uploading object body
		ByteArrayInputStream is = new ByteArrayInputStream(body);
		rest.api().users(user).buckets(userBucketName).objects(userObject).body().upload("image/jpg", is);
		
		KiiAdminCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		String appBucketName = "moved";
		
		// creating object (dest)
		KiiObject appObject = new KiiObject().set("size", 1024);
		rest.api().buckets(appBucketName).objects().save(appObject);
		rest.api().users(user).buckets(userBucketName).objects(userObject).body().moveToAppScope(appBucketName, appObject.getObjectID());
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		rest.api().buckets(appBucketName).objects(appObject).body().download(os);
		
		assertArrayEquals(body, os.toByteArray());
		assertFalse(rest.api().users(user).buckets(userBucketName).objects(userObject).body().exists());
	}
	@Test
	public void moveToGroupScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		byte[] body = this.createObjectBody(1024);
		
		String userBucketName = "user_bucket" + System.currentTimeMillis();
		
		// creating object (source)
		KiiObject userObject = new KiiObject().set("size", 1024);
		rest.api().users(user).buckets(userBucketName).objects().save(userObject);
		
		Thread.sleep(1000);
		
		// uploading object body
		ByteArrayInputStream is = new ByteArrayInputStream(body);
		rest.api().users(user).buckets(userBucketName).objects(userObject).body().upload("image/jpg", is);
		
		KiiAdminCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		String groupBucketName = "moved";
		
		// creating object (dest)
		KiiGroup group = new KiiGroup();
		group.setName("MyGroup");
		group.setOwner(user.getUserID());
		rest.api().groups().save(group);
		
		KiiObject groupObject = new KiiObject().set("size", 1024);
		rest.api().groups(group).buckets(groupBucketName).objects().save(groupObject);
		rest.api().users(user).buckets(userBucketName).objects(userObject).body().moveToGroupScope(group, groupBucketName, groupObject.getObjectID());
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		rest.api().groups(group).buckets(groupBucketName).objects(groupObject).body().download(os);
		
		assertArrayEquals(body, os.toByteArray());
		assertFalse(rest.api().users(user).buckets(userBucketName).objects(userObject).body().exists());
	}
	@Test
	public void moveToUserScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user1 = new KiiNormalUser().setUsername("test1-" + System.currentTimeMillis());
		user1 = rest.api().users().register(user1, "password");
		KiiNormalUser user2 = new KiiNormalUser().setUsername("test2-" + System.currentTimeMillis());
		user2 = rest.api().users().register(user2, "password");
		
		rest.setCredentials(user1);
		
		byte[] body = this.createObjectBody(1024);
		
		String userBucketName = "user_bucket" + System.currentTimeMillis();
		
		// creating object (source)
		KiiObject userObject1 = new KiiObject().set("size", 1024);
		rest.api().users(user1).buckets(userBucketName).objects().save(userObject1);
		
		Thread.sleep(1000);
		
		// uploading object body
		ByteArrayInputStream is = new ByteArrayInputStream(body);
		rest.api().users(user1).buckets(userBucketName).objects(userObject1).body().upload("image/jpg", is);
		
		KiiAdminCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		// creating object (dest)
		KiiObject userObject2 = new KiiObject().set("size", 1024);
		rest.api().users(user2).buckets(userBucketName).objects().save(userObject2);
		rest.api().users(user1).buckets(userBucketName).objects(userObject1).body().moveToUserScope(user2, userBucketName, userObject2.getObjectID());
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		rest.api().users(user2).buckets(userBucketName).objects(userObject2).body().download(os);
		
		assertArrayEquals(body, os.toByteArray());
		assertFalse(rest.api().users(user1).buckets(userBucketName).objects(userObject1).body().exists());
	}
	@Test
	public void moveToThingScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		
		// registering thing
		String vendorThingID1 = "thing-" + System.currentTimeMillis();
		KiiThing thing1 = new KiiThing()
			.setVendorThingID(vendorThingID1)
			.setProductName("KiiCloud")
			.setPassword("pa$$word");
		thing1 = rest.api().things().register(thing1);
		String vendorThingID2 = "thing-" + System.currentTimeMillis();
		KiiThing thing2 = new KiiThing()
			.setVendorThingID(vendorThingID2)
			.setProductName("KiiCloud")
			.setPassword("pa$$word");
		thing2 = rest.api().things().register(thing2);

		rest.setCredentials(thing1);
		
		byte[] body = this.createObjectBody(1024);
		
		String thingBucketName = "thing_bucket" + System.currentTimeMillis();
		
		// creating object (source)
		KiiObject thingObject1 = new KiiObject().set("size", 1024);
		rest.api().things(thing1).buckets(thingBucketName).objects().save(thingObject1);
		
		Thread.sleep(1000);
		
		// uploading object body
		ByteArrayInputStream is = new ByteArrayInputStream(body);
		rest.api().things(thing1).buckets(thingBucketName).objects(thingObject1).body().upload("image/jpg", is);
		
		KiiAdminCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		// creating object (dest)
		KiiObject thingObject2 = new KiiObject().set("size", 1024);
		rest.api().things(thing2).buckets(thingBucketName).objects().save(thingObject2);
		rest.api().things(thing1).buckets(thingBucketName).objects(thingObject1).body().moveToThingScope(thing2, thingBucketName, thingObject2.getObjectID());
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		rest.api().things(thing2).buckets(thingBucketName).objects(thingObject2).body().download(os);
		
		assertArrayEquals(body, os.toByteArray());
		assertFalse(rest.api().things(thing1).buckets(thingBucketName).objects(thingObject1).body().exists());
	}
	private byte[] createObjectBody(int length) {
		byte[] body = new byte[length];
		for (int i = 0; i < body.length; i++) {
			body[i] = (byte)(i % 128);
		}
		return body;
	}
	private byte[] getPublishedBody(String url) throws IOException {
		OkHttpClient client = OkHttpClientFactory.newInstance();
		Request request = new Request.Builder().url(url).get().build();
		Response response = client.newCall(request).execute();
		return response.body().bytes();
	}
}
