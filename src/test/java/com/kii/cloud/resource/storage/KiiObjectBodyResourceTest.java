package com.kii.cloud.resource.storage;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.KiiRest;
import com.kii.cloud.OkHttpClientFactory;
import com.kii.cloud.SkipAcceptableTestRunner;
import com.kii.cloud.TestApp;
import com.kii.cloud.TestEnvironments;
import com.kii.cloud.model.storage.KiiChunkedDownloadContext;
import com.kii.cloud.model.storage.KiiChunkedUploadContext;
import com.kii.cloud.model.storage.KiiNormalUser;
import com.kii.cloud.model.storage.KiiObject;
import com.kii.cloud.resource.storage.KiiObjectBodyResource;
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
