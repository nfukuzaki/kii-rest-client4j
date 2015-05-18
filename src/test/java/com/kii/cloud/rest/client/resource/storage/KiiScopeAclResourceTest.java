package com.kii.cloud.rest.client.resource.storage;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestAppFilter;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.KiiAdminCredentials;
import com.kii.cloud.rest.client.model.storage.KiiGroup;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiThing;
import com.kii.cloud.rest.client.model.storage.KiiAcl.Action;
import com.kii.cloud.rest.client.model.storage.KiiAcl.ScopeAction;
import com.kii.cloud.rest.client.model.storage.KiiAcl.Subject;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiScopeAclResourceTest {
	@Test
	public void appScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		
		KiiAdminCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		// listing ACL
		Map<Action,List<Subject>> ACLs = rest.api().acl().list();
		assertEquals(1, ACLs.get(ScopeAction.CREATE_NEW_BUCKET).size());
		assertEquals("ANY_AUTHENTICATED_USER", ACLs.get(ScopeAction.CREATE_NEW_BUCKET).get(0).getID());
		
		// granting ACL
		rest.api().acl().grant(ScopeAction.CREATE_NEW_BUCKET, Subject.user(user));
		ACLs = rest.api().acl().list();
		assertEquals(1, ACLs.size());
		assertEquals(2, ACLs.get(ScopeAction.CREATE_NEW_BUCKET).size());
		assertEquals("ANY_AUTHENTICATED_USER", ACLs.get(ScopeAction.CREATE_NEW_BUCKET).get(0).getID());
		assertEquals(user.getUserID(), ACLs.get(ScopeAction.CREATE_NEW_BUCKET).get(1).getID());
		
		// revoking ACL
		rest.api().acl().revok(ScopeAction.CREATE_NEW_BUCKET, ACLs.get(ScopeAction.CREATE_NEW_BUCKET).get(1));
		ACLs = rest.api().acl().list();
		assertEquals(1, ACLs.size());
		assertEquals(1, ACLs.get(ScopeAction.CREATE_NEW_BUCKET).size());
		assertEquals("ANY_AUTHENTICATED_USER", ACLs.get(ScopeAction.CREATE_NEW_BUCKET).get(0).getID());
	}
	@Test
	public void userScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		
		rest.setCredentials(user);
		
		// listing ACL
		Map<Action,List<Subject>> ACLs = rest.api().users(user).acl().list();
		assertEquals(2, ACLs.size());
		assertEquals(1, ACLs.get(ScopeAction.CREATE_NEW_BUCKET).size());
		assertEquals(user.getUserID(), ACLs.get(ScopeAction.CREATE_NEW_BUCKET).get(0).getID());
		assertEquals(1, ACLs.get(ScopeAction.CREATE_NEW_TOPIC).size());
		assertEquals(user.getUserID(), ACLs.get(ScopeAction.CREATE_NEW_TOPIC).get(0).getID());
		
		// granting ACL
		rest.api().users(user).acl().grant(ScopeAction.CREATE_NEW_BUCKET, Subject.ANY_AUTHENTICATED_USER);
		ACLs = rest.api().users(user).acl().list();
		assertEquals(2, ACLs.size());
		assertEquals(2, ACLs.get(ScopeAction.CREATE_NEW_BUCKET).size());
		assertEquals("ANY_AUTHENTICATED_USER", ACLs.get(ScopeAction.CREATE_NEW_BUCKET).get(0).getID());
		assertEquals(user.getUserID(), ACLs.get(ScopeAction.CREATE_NEW_BUCKET).get(1).getID());
		
		// revoking ACL
		rest.api().users(user).acl().revok(ScopeAction.CREATE_NEW_BUCKET, Subject.ANY_AUTHENTICATED_USER);
		ACLs = rest.api().users(user).acl().list();
		assertEquals(2, ACLs.size());
		assertEquals(1, ACLs.get(ScopeAction.CREATE_NEW_BUCKET).size());
		assertEquals(user.getUserID(), ACLs.get(ScopeAction.CREATE_NEW_BUCKET).get(0).getID());
	}
	@Test
	public void groupScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
		user = rest.api().users().register(user, "password");
		
		rest.setCredentials(user);
		
		KiiGroup group = new KiiGroup();
		group.setName("MyGroup");
		group.setOwner(user.getUserID());
		rest.api().groups().save(group);
		
		// listing ACL
		Map<Action,List<Subject>> ACLs = rest.api().groups(group).acl().list();
		assertEquals(2, ACLs.size());
		assertEquals(2, ACLs.get(ScopeAction.CREATE_NEW_BUCKET).size());
		assertEquals(group.getGroupID(), ACLs.get(ScopeAction.CREATE_NEW_BUCKET).get(0).getID());
		assertEquals(user.getUserID(), ACLs.get(ScopeAction.CREATE_NEW_BUCKET).get(1).getID());
		assertEquals(2, ACLs.get(ScopeAction.CREATE_NEW_TOPIC).size());
		assertEquals(group.getGroupID(), ACLs.get(ScopeAction.CREATE_NEW_TOPIC).get(0).getID());
		assertEquals(user.getUserID(), ACLs.get(ScopeAction.CREATE_NEW_TOPIC).get(1).getID());
		
		// granting ACL
		rest.api().groups(group).acl().grant(ScopeAction.CREATE_NEW_BUCKET, Subject.ANY_AUTHENTICATED_USER);
		ACLs = rest.api().groups(group).acl().list();
		assertEquals(2, ACLs.size());
		assertEquals(3, ACLs.get(ScopeAction.CREATE_NEW_BUCKET).size());
		assertEquals(group.getGroupID(), ACLs.get(ScopeAction.CREATE_NEW_BUCKET).get(0).getID());
		assertEquals("ANY_AUTHENTICATED_USER", ACLs.get(ScopeAction.CREATE_NEW_BUCKET).get(1).getID());
		assertEquals(user.getUserID(), ACLs.get(ScopeAction.CREATE_NEW_BUCKET).get(2).getID());
		
		// revoking ACL
		rest.api().groups(group).acl().revok(ScopeAction.CREATE_NEW_BUCKET, Subject.ANY_AUTHENTICATED_USER);
		ACLs = rest.api().groups(group).acl().list();
		assertEquals(2, ACLs.size());
		assertEquals(2, ACLs.get(ScopeAction.CREATE_NEW_BUCKET).size());
		assertEquals(group.getGroupID(), ACLs.get(ScopeAction.CREATE_NEW_BUCKET).get(0).getID());
		assertEquals(user.getUserID(), ACLs.get(ScopeAction.CREATE_NEW_BUCKET).get(1).getID());
	}
	@Test
	public void thingScopeTest() throws Exception {
		TestApp testApp = TestEnvironments.random();
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		String vendorThingID = "thing-" + System.currentTimeMillis();
		String password = "pa$$word";
		
		// registering thing
		KiiThing thing = new KiiThing()
			.setVendorThingID(vendorThingID)
			.setProductName("KiiCloud")
			.setPassword(password);
		thing = rest.api().things().register(thing);
		String thingID = thing.getThingID();
		
		rest.setCredentials(thing);
		
		// listing ACL
		Map<Action,List<Subject>> ACLs = rest.api().things(thing).acl().list();
		assertEquals(2, ACLs.size());
		assertEquals(1, ACLs.get(ScopeAction.CREATE_NEW_BUCKET).size());
		assertEquals(thingID, ACLs.get(ScopeAction.CREATE_NEW_BUCKET).get(0).getID());
		assertEquals(1, ACLs.get(ScopeAction.CREATE_NEW_TOPIC).size());
		assertEquals(thingID, ACLs.get(ScopeAction.CREATE_NEW_TOPIC).get(0).getID());
		
		// granting ACL
		rest.api().things(thing).acl().grant(ScopeAction.CREATE_NEW_BUCKET, Subject.ANY_AUTHENTICATED_USER);
		ACLs = rest.api().things(thing).acl().list();
		assertEquals(2, ACLs.size());
		assertEquals(2, ACLs.get(ScopeAction.CREATE_NEW_BUCKET).size());
		assertEquals(thingID, ACLs.get(ScopeAction.CREATE_NEW_BUCKET).get(0).getID());
		assertEquals("ANY_AUTHENTICATED_USER", ACLs.get(ScopeAction.CREATE_NEW_BUCKET).get(1).getID());
		
		// revoking ACL
		rest.api().things(thing).acl().revok(ScopeAction.CREATE_NEW_BUCKET, Subject.ANY_AUTHENTICATED_USER);
		ACLs = rest.api().things(thing).acl().list();
		assertEquals(2, ACLs.size());
		assertEquals(1, ACLs.get(ScopeAction.CREATE_NEW_BUCKET).size());
		assertEquals(thingID, ACLs.get(ScopeAction.CREATE_NEW_BUCKET).get(0).getID());
	}
}
