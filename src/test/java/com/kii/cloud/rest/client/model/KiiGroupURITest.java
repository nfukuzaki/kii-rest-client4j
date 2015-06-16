package com.kii.cloud.rest.client.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.storage.KiiGroup;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.uri.KiiGroupURI;
import com.kii.cloud.rest.client.model.uri.KiiURI;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiGroupURITest {
	@Test
	public void test() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		rest.setCredentials(user);
		
		KiiGroup group1 = new KiiGroup();
		group1.setName("MyGroup");
		group1.setOwner(user.getUserID());
		rest.api().groups().save(group1, null);
		
		String expectedUriString = String.format("kiicloud://%s/groups/%s", testApp.getAppID(), group1.getGroupID());
		assertEquals(expectedUriString, group1.getURI().toUriString());
		assertEquals(group1.getURI(), KiiURI.parse(expectedUriString));
		assertEquals(group1.getURI(), KiiGroupURI.parse(expectedUriString));
		
		KiiGroup group2 =rest.api().groups(group1.getURI()).get();
		assertEquals(group1.getGroupID(), group2.getGroupID());
		assertEquals(group1.getURI(), group2.getURI());
	}
}
