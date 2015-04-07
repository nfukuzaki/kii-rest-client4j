package com.kii.cloud.bucket;

import org.junit.Test;

import com.kii.cloud.KiiRest;
import com.kii.cloud.TestApp;
import com.kii.cloud.TestEnvironments;
import com.kii.cloud.model.KiiNormalUser;

public class KiiBucketResourceTest {
	@Test
	public void test() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.AppID, testApp.AppKey, testApp.Site);
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test1-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		String appBucketName = "app_bucket" + System.currentTimeMillis();
		rest.api().buckets(appBucketName).create();
		rest.api().buckets(appBucketName).delete();
	}
}
