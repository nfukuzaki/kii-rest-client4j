package com.kii.cloud.rest.client.resource.storage;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.storage.KiiGroup;
import com.kii.cloud.rest.client.model.storage.KiiGroupMembers;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiPseudoUser;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiGroupResourceTest {
	@Test
	public void test() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiPseudoUser user1 = new KiiPseudoUser();
		KiiPseudoUser user2 = new KiiPseudoUser();
		KiiNormalUser user3 = new KiiNormalUser().setUsername("test1-" + System.currentTimeMillis());
		KiiNormalUser user4 = new KiiNormalUser().setUsername("test2-" + System.currentTimeMillis());
		
		user1 = rest.api().users().register(user1);
		user2 = rest.api().users().register(user2);
		user3 = rest.api().users().register(user3, "password");
		user4 = rest.api().users().register(user4, "password");
		
		rest.setCredentials(user1);
		
		// creating new group
		KiiGroup group1 = new KiiGroup();
		group1.setName("MyGroup");
		group1.setOwner(user1.getUserID());
		
		KiiGroupMembers members = new KiiGroupMembers();
		members.addMember(user2.getUserID());
		members.addMember(user3.getUserID());
		rest.api().groups().save(group1, members);
		
		// changing group name
		rest.api().groups(group1).changeName("NewMyGroup");
		KiiGroup group2 = rest.api().groups(group1).get();
		assertEquals("NewMyGroup", group2.getName());
		
		// remove/add member
		rest.api().groups(group1).members(user3.getUserID()).remove();
		rest.api().groups(group1).members(user4.getUserID()).add();
		// getting list of members
		members = rest.api().groups(group1).members().list();
		assertEquals(3, members.getMembers().size());
		assertTrue(members.getMembers().contains(user1.getUserID()));
		assertTrue(members.getMembers().contains(user2.getUserID()));
		assertTrue(members.getMembers().contains(user4.getUserID()));
		
		// changing owner
		assertEquals(user1.getUserID(), group2.getOwner());
		rest.api().groups(group2.getGroupID()).changeOwner(user2.getUserID());
		KiiGroup group3 = rest.api().groups(group1).get();
		assertEquals(user2.getUserID(), group3.getOwner());
		rest.setCredentials(user2);
		
		rest.api().groups(group2.getGroupID()).delete();
	}
}
