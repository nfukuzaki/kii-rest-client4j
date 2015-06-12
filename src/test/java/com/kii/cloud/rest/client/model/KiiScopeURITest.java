//package com.kii.cloud.rest.client.model;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
//
//@RunWith(SkipAcceptableTestRunner.class)
//public class KiiScopeURITest {
//	@Test
//	public void userScopeTest() throws Exception {
//		final String userID = "1234-5678-abcd-efgh";
//		final String uriString = "kiicloud://users/" + userID;
//		KiiUserURI uri = new KiiUserURI(userID);
//		assertEquals(userID, uri.getScopeID());
//		assertEquals(uriString, uri.toString());
//		assertEquals(uri, KiiUserURI.create(uriString));
//		assertEquals(KiiUserURI.create(uriString), KiiURI.parse(uriString));
//	}
//	@Test
//	public void groupScopeTest() throws Exception {
//		final String groupID = "abcd-efgh-1234-5678";
//		final String uriString = "kiicloud://groups/" + groupID;
//		KiiGroupURI uri = new KiiGroupURI(groupID);
//		assertEquals(groupID, uri.getScopeID());
//		assertEquals(uriString, uri.toString());
//		assertEquals(uri, KiiGroupURI.create(uriString));
//		assertEquals(KiiGroupURI.create(uriString), KiiURI.parse(uriString));
//	}
//	@Test
//	public void thingScopeTest() throws Exception {
//		final String thingID = "abcd-1234-efgh-5678";
//		final String uriString = "kiicloud://things/" + thingID;
//		KiiThingURI uri = new KiiThingURI(thingID);
//		assertEquals(thingID, uri.getScopeID());
//		assertEquals(uriString, uri.toString());
//		assertEquals(uri, KiiThingURI.create(uriString));
//		assertEquals(KiiThingURI.create(uriString), KiiURI.parse(uriString));
//	}
//}
