package com.kii.cloud.rest.client.resource.storage;

import java.util.List;

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
		group1.setName("MyGroup1");
		group1.setOwner(user1.getUserID());
		
		KiiGroupMembers members = new KiiGroupMembers();
		members.addMember(user2.getUserID());
		members.addMember(user3.getUserID());
		rest.api().groups().save(group1, members);
		
		KiiGroup group2 = new KiiGroup();
		group2.setName("MyGroup3");
		group2.setOwner(user3.getUserID());
		members = new KiiGroupMembers();
		members.addMember(user1.getUserID());
		rest.api().groups().save(group2, members);
		
		// getting own groups
		List<KiiGroup> user1OwnedGroups = rest.api().groups().getOwnGroups(user1);
		List<KiiGroup> user2OwnedGroups = rest.api().groups().getOwnGroups(user2);
		List<KiiGroup> user3OwnedGroups = rest.api().groups().getOwnGroups(user3);
		List<KiiGroup> user4OwnedGroups = rest.api().groups().getOwnGroups(user4);
		assertEquals(1, user1OwnedGroups.size());
		assertEquals(0, user2OwnedGroups.size());
		assertEquals(1, user3OwnedGroups.size());
		assertEquals(0, user4OwnedGroups.size());
		
		// getting belonged groups
		List<KiiGroup> user1BelongedGroups = rest.api().groups().getBelongGroups(user1);
		List<KiiGroup> user2BelongedGroups = rest.api().groups().getBelongGroups(user2);
		List<KiiGroup> user3BelongedGroups = rest.api().groups().getBelongGroups(user3);
		List<KiiGroup> user4BelongedGroups = rest.api().groups().getBelongGroups(user4);
		assertEquals(2, user1BelongedGroups.size());
		assertEquals(1, user2BelongedGroups.size());
		assertEquals(2, user3BelongedGroups.size());
		assertEquals(0, user4BelongedGroups.size());
		
		// changing group name
		rest.api().groups(group1).changeName("NewMyGroup");
		KiiGroup group1Renamed = rest.api().groups(group1).get();
		assertEquals("NewMyGroup", group1Renamed.getName());
		
		// remove/add member
		rest.api().groups(group1.getGroupID()).members(user3.getUserID()).remove();
		rest.api().groups(group1.getGroupID()).members(user4.getUserID()).add();
		// getting list of members
		members = rest.api().groups(group1).members().list();
		assertEquals(3, members.getMembers().size());
		assertTrue(members.getMembers().contains(user1.getUserID()));
		assertTrue(members.getMembers().contains(user2.getUserID()));
		assertTrue(members.getMembers().contains(user4.getUserID()));
		
		// changing owner
		assertEquals(user1.getUserID(), group1Renamed.getOwner());
		rest.api().groups(group1Renamed.getGroupID()).changeOwner(user2.getUserID());
		KiiGroup group3 = rest.api().groups(group1).get();
		assertEquals(user2.getUserID(), group3.getOwner());
		rest.setCredentials(user2);
		
		rest.api().groups(group1Renamed.getGroupID()).delete();
	}
}
