package com.kii.cloud.rest.client.resource.servercode;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.model.KiiCredentials;
import com.kii.cloud.rest.client.model.servercode.KiiServerCodeVersion;
import com.kii.cloud.rest.client.util.GsonUtils;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiServerCodeResourceMultiEnvTest {
	@Test
	public void executeSpecified0Test() throws Exception {
		KiiRest rest = new KiiRest("xf8t0wg4t0zr", "dd21c3f93e894e31863b040e9a94c80e", "https://api-development-jp.internal.kii.com/api");
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken("0a64e9dcf4f283b689f210bf921975ab", "032abfac680471af49135d85d0bd3968a05eb31d9c68848249b3b4376f6e1436");
		rest.setCredentials(cred);
		
		StringBuilder javascript = new StringBuilder();
		javascript.append("function returnNumber(params, context){" + "\n");
		javascript.append("    console.log(\"returnNumber\");" + "\n");
		javascript.append("    return 3.14;" + "\n");
		javascript.append("}" + "\n");
		String versionID = rest.api().servercode().deploy(javascript.toString());
		Thread.sleep(2000);
		rest.api().servercode().setCurrentVersion(versionID);
		Thread.sleep(2000);
		
		rest.setCredentials(null);
		JsonObject result = rest.api().servercode("current").execute("returnNumber",(JsonObject)null, "0");
		assertEquals(new BigDecimal("3.14"), GsonUtils.getBigDecimal(result, "returnedValue"));
		assertEquals(2, (int)GsonUtils.getInt(result, "x_step_count"));
		assertEquals("0", GsonUtils.getString(result, "x_environment_version"));
	}
	@Test
	public void executeSpecified6Test() throws Exception {
		KiiRest rest = new KiiRest("xf8t0wg4t0zr", "dd21c3f93e894e31863b040e9a94c80e", "https://api-development-jp.internal.kii.com/api");
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken("0a64e9dcf4f283b689f210bf921975ab", "032abfac680471af49135d85d0bd3968a05eb31d9c68848249b3b4376f6e1436");
		rest.setCredentials(cred);
		
		StringBuilder javascript = new StringBuilder();
		javascript.append("function returnNumber(params, context){" + "\n");
		javascript.append("    console.log(\"returnNumber\");" + "\n");
		javascript.append("    return 3.14;" + "\n");
		javascript.append("}" + "\n");
		String versionID = rest.api().servercode().deploy(javascript.toString());
		Thread.sleep(2000);
		rest.api().servercode().setCurrentVersion(versionID);
		Thread.sleep(2000);
		
		rest.setCredentials(null);
		JsonObject result = rest.api().servercode("current").execute("returnNumber",(JsonObject)null, "6");
		assertEquals(new BigDecimal("3.14"), GsonUtils.getBigDecimal(result, "returnedValue"));
		assertEquals(2, (int)GsonUtils.getInt(result, "x_step_count"));
		assertEquals("6", GsonUtils.getString(result, "x_environment_version"));
	}
	@Test
	public void executeAttached0Test() throws Exception {
		KiiRest rest = new KiiRest("xf8t0wg4t0zr", "dd21c3f93e894e31863b040e9a94c80e", "https://api-development-jp.internal.kii.com/api");
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken("0a64e9dcf4f283b689f210bf921975ab", "032abfac680471af49135d85d0bd3968a05eb31d9c68848249b3b4376f6e1436");
		rest.setCredentials(cred);
		
		StringBuilder javascript = new StringBuilder();
		javascript.append("function returnNumber(params, context){" + "\n");
		javascript.append("    console.log(\"returnNumber\");" + "\n");
		javascript.append("    return 3.14;" + "\n");
		javascript.append("}" + "\n");
		String versionID = rest.api().servercode().deploy(javascript.toString(), "0");
		Thread.sleep(2000);
		rest.api().servercode().setCurrentVersion(versionID);
		Thread.sleep(2000);
		
		rest.setCredentials(null);
		JsonObject result = rest.api().servercode("current").execute("returnNumber",(JsonObject)null);
		assertEquals(new BigDecimal("3.14"), GsonUtils.getBigDecimal(result, "returnedValue"));
		assertEquals(2, (int)GsonUtils.getInt(result, "x_step_count"));
		assertEquals("0", GsonUtils.getString(result, "x_environment_version"));
	}
	@Test
	public void executeAttached6Test() throws Exception {
		KiiRest rest = new KiiRest("xf8t0wg4t0zr", "dd21c3f93e894e31863b040e9a94c80e", "https://api-development-jp.internal.kii.com/api");
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken("0a64e9dcf4f283b689f210bf921975ab", "032abfac680471af49135d85d0bd3968a05eb31d9c68848249b3b4376f6e1436");
		rest.setCredentials(cred);
		
		StringBuilder javascript = new StringBuilder();
		javascript.append("function returnNumber(params, context){" + "\n");
		javascript.append("    console.log(\"returnNumber\");" + "\n");
		javascript.append("    return 3.14;" + "\n");
		javascript.append("}" + "\n");
		String versionID = rest.api().servercode().deploy(javascript.toString(), "6");
		Thread.sleep(2000);
		rest.api().servercode().setCurrentVersion(versionID);
		Thread.sleep(2000);
		
		rest.setCredentials(null);
		JsonObject result = rest.api().servercode("current").execute("returnNumber",(JsonObject)null);
		assertEquals(new BigDecimal("3.14"), GsonUtils.getBigDecimal(result, "returnedValue"));
		assertEquals(2, (int)GsonUtils.getInt(result, "x_step_count"));
		assertEquals("6", GsonUtils.getString(result, "x_environment_version"));
	}
	@Test
	public void executeSpecified6Attached0Test() throws Exception {
		KiiRest rest = new KiiRest("xf8t0wg4t0zr", "dd21c3f93e894e31863b040e9a94c80e", "https://api-development-jp.internal.kii.com/api");
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken("0a64e9dcf4f283b689f210bf921975ab", "032abfac680471af49135d85d0bd3968a05eb31d9c68848249b3b4376f6e1436");
		rest.setCredentials(cred);
		
		StringBuilder javascript = new StringBuilder();
		javascript.append("function returnNumber(params, context){" + "\n");
		javascript.append("    console.log(\"returnNumber\");" + "\n");
		javascript.append("    return 3.14;" + "\n");
		javascript.append("}" + "\n");
		String versionID = rest.api().servercode().deploy(javascript.toString(), "0");
		Thread.sleep(2000);
		rest.api().servercode().setCurrentVersion(versionID);
		Thread.sleep(2000);
		
		rest.setCredentials(null);
		JsonObject result = rest.api().servercode("current").execute("returnNumber",(JsonObject)null, "6");
		assertEquals(new BigDecimal("3.14"), GsonUtils.getBigDecimal(result, "returnedValue"));
		assertEquals(2, (int)GsonUtils.getInt(result, "x_step_count"));
		assertEquals("6", GsonUtils.getString(result, "x_environment_version"));
	}
	@Test
	public void executeSpecified0Attached6Test() throws Exception {
		KiiRest rest = new KiiRest("xf8t0wg4t0zr", "dd21c3f93e894e31863b040e9a94c80e", "https://api-development-jp.internal.kii.com/api");
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken("0a64e9dcf4f283b689f210bf921975ab", "032abfac680471af49135d85d0bd3968a05eb31d9c68848249b3b4376f6e1436");
		rest.setCredentials(cred);
		
		StringBuilder javascript = new StringBuilder();
		javascript.append("function returnNumber(params, context){" + "\n");
		javascript.append("    console.log(\"returnNumber\");" + "\n");
		javascript.append("    return 3.14;" + "\n");
		javascript.append("}" + "\n");
		String versionID = rest.api().servercode().deploy(javascript.toString(), "6");
		Thread.sleep(2000);
		rest.api().servercode().setCurrentVersion(versionID);
		Thread.sleep(2000);
		
		rest.setCredentials(null);
		JsonObject result = rest.api().servercode("current").execute("returnNumber",(JsonObject)null, "0");
		assertEquals(new BigDecimal("3.14"), GsonUtils.getBigDecimal(result, "returnedValue"));
		assertEquals(2, (int)GsonUtils.getInt(result, "x_step_count"));
		assertEquals("0", GsonUtils.getString(result, "x_environment_version"));
	}
	@Test
	public void listTest() throws Exception {
		KiiRest rest = new KiiRest("xf8t0wg4t0zr", "dd21c3f93e894e31863b040e9a94c80e", "https://api-development-jp.internal.kii.com/api");
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken("0a64e9dcf4f283b689f210bf921975ab", "032abfac680471af49135d85d0bd3968a05eb31d9c68848249b3b4376f6e1436");
		rest.setCredentials(cred);
		
		StringBuilder javascript = new StringBuilder();
		javascript.append("function returnNumber(params, context){" + "\n");
		javascript.append("    console.log(\"returnNumber\");" + "\n");
		javascript.append("    return 3.14;" + "\n");
		javascript.append("}" + "\n");
		String versionID1 = rest.api().servercode().deploy(javascript.toString(), "6");
		Thread.sleep(2000);

		String versionID2 = rest.api().servercode().deploy(javascript.toString(), "0");
		Thread.sleep(2000);

		List<KiiServerCodeVersion> codes = rest.api().servercode().list();
		for (KiiServerCodeVersion code : codes) {
			if (versionID1.equals(code.getVersionID())) {
				assertEquals("6", code.getEnvironmentVersion());
			}
			if (versionID2.equals(code.getVersionID())) {
				assertEquals("0", code.getEnvironmentVersion());
			}
		}
	}
}
