package com.kii.cloud.rest.client.resource.servercode;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestAppFilter;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.exception.KiiNotFoundException;
import com.kii.cloud.rest.client.model.KiiCredentials;
import com.kii.cloud.rest.client.model.servercode.KiiServerCodeVersion;
import com.kii.cloud.rest.client.util.GsonUtils;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiServerCodeResourceTest {
	@Test
	public void executeCurrentVersionTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		StringBuilder javascript = new StringBuilder();
		javascript.append("function returnNumber(params, context){" + "\n");
		javascript.append("    console.log(\"returnNumber\");" + "\n");
		javascript.append("    return 3.14;" + "\n");
		javascript.append("}" + "\n");
		String versionID = rest.api().servercode().deploy(javascript.toString());
		rest.api().servercode().setCurrentVersion(versionID);
		
		rest.setCredentials(null);
		JsonObject result = rest.api().servercode("current").execute("returnNumber",(JsonObject)null);
		assertEquals(new BigDecimal("3.14"), GsonUtils.getBigDecimal(result, "returnedValue"));
		assertEquals(2, (int)GsonUtils.getInt(result, "x_step_count"));
	}
	@Test
	public void executeSpecifiedVersionTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		StringBuilder javascript1 = new StringBuilder();
		javascript1.append("function returnNumber(params, context){" + "\n");
		javascript1.append("    console.log(\"returnNumber\");" + "\n");
		javascript1.append("    return 3.14;" + "\n");
		javascript1.append("}" + "\n");
		String versionID1 = rest.api().servercode().deploy(javascript1.toString());
		rest.api().servercode().setCurrentVersion(versionID1);
		
		StringBuilder javascript2 = new StringBuilder();
		javascript2.append("function returnNumber(params, context){" + "\n");
		javascript2.append("    console.log(\"returnNumber\");" + "\n");
		javascript2.append("    return 3;" + "\n");
		javascript2.append("}" + "\n");
		String versionID2 = rest.api().servercode().deploy(javascript2.toString());
		rest.api().servercode().setCurrentVersion(versionID2);
		
		rest.setCredentials(null);
		JsonObject result = rest.api().servercode(versionID1).execute("returnNumber", (JsonObject)null);
		assertEquals(new BigDecimal("3.14"), GsonUtils.getBigDecimal(result, "returnedValue"));
		assertEquals(2, (int)GsonUtils.getInt(result, "x_step_count"));
	}
	@Test
	public void manageServerCodeTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		// deploying server code
		StringBuilder javascript = new StringBuilder();
		javascript.append("function returnNumber(params, context){" + "\n");
		javascript.append("    return 3.14;" + "\n");
		javascript.append("}" + "\n");
		String versionID = rest.api().servercode().deploy(javascript.toString());
		// set current version
		rest.api().servercode().setCurrentVersion(versionID);
		String currentVersionID = rest.api().servercode().getCurrentVersion();
		assertEquals(versionID, currentVersionID);
		// reset current version
		rest.api().servercode().resetCurrentVersion();
		try {
			currentVersionID = rest.api().servercode().getCurrentVersion();
			fail("KiiRestException must be thrown.");
		} catch (KiiNotFoundException e) {
			assertEquals(404, e.getStatus());
		}
		// listing all versions
		List<KiiServerCodeVersion> versionIdList = rest.api().servercode().list();
		int versionsCount = versionIdList.size();
		assertTrue(1 < versionsCount);
		
		// getting server code.
		String script = rest.api().servercode(versionID).get();
		assertEquals(javascript.toString(), script);
		
		// deleting server code
		rest.api().servercode(versionID).delete();
		try {
			rest.api().servercode(versionID).get();
			fail("KiiRestException must be thrown.");
		} catch (KiiNotFoundException e) {
			assertEquals(404, e.getStatus());
		}
	}
	@Test
	public void deployFromFileTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		String versionID = rest.api().servercode().deploy(new File(getClass().getResource("get_server_time.js").getPath()));
		rest.api().servercode().setCurrentVersion(versionID);
		String currentVersionID = rest.api().servercode().getCurrentVersion();
		assertEquals(versionID, currentVersionID);
	}
}
