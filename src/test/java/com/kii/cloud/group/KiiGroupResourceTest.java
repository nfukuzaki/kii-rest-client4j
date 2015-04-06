package com.kii.cloud.group;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.kii.cloud.KiiRest;
import com.kii.cloud.TestApp;
import com.kii.cloud.TestEnvironments;
import com.kii.cloud.model.KiiGroup;
import com.kii.cloud.model.KiiGroupMembers;
import com.kii.cloud.model.KiiNormalUser;
import com.kii.cloud.model.KiiPseudoUser;

public class KiiGroupResourceTest {
	@Test
	public void test() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.AppID, testApp.AppKey, testApp.Site);
		
		KiiPseudoUser user1 = new KiiPseudoUser();
		KiiPseudoUser user2 = new KiiPseudoUser();
		KiiNormalUser user3 = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		
		user1 = rest.api().user().register(user1);
		user2 = rest.api().user().register(user2);
		user3 = rest.api().user().register(user3, "password");
		
		rest.setCredentials(user1);
		
		KiiGroup group = new KiiGroup();
		group.setName("MyGroup");
		group.setOwner(user1.getUserID());
		
		KiiGroupMembers members = new KiiGroupMembers();
		members.addMember(user2.getUserID());
		members.addMember(user3.getUserID());
		rest.api().group().save(group, members);
		
		
		rest.api().group(group).get();
	}
}
