package com.kii.cloud.resource.servercode;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRest;
import com.kii.cloud.SkipAcceptableTestRunner;
import com.kii.cloud.TestApp;
import com.kii.cloud.TestAppFilter;
import com.kii.cloud.TestEnvironments;
import com.kii.cloud.model.KiiAdminCredentials;
import com.kii.cloud.util.GsonUtils;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiServerCodeHookResourceTest {
	@Test
	public void test() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiAdminCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		StringBuilder javascript = new StringBuilder();
		javascript.append("function returnNumber(params, context){" + "\n");
		javascript.append("    console.log(\"returnNumber\");" + "\n");
		javascript.append("    return 3.14;" + "\n");
		javascript.append("}" + "\n");
		String versionID = rest.api().servercode().deploy(javascript.toString());
		rest.api().servercode().setCurrentVersion(versionID);
		
		rest.setCredentials(null);
		JsonObject result = rest.api().servercode("current").execute("returnNumber", null);
		assertEquals(new BigDecimal("3.14"), GsonUtils.getBigDecimal(result, "returnedValue"));
		assertEquals(2, (int)GsonUtils.getInt(result, "x_step_count"));
	}
}
