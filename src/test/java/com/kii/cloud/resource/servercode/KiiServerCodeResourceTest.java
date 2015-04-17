package com.kii.cloud.resource.servercode;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRest;
import com.kii.cloud.SkipAcceptableTestRunner;
import com.kii.cloud.TestApp;
import com.kii.cloud.TestAppFilter;
import com.kii.cloud.TestEnvironments;
import com.kii.cloud.model.KiiAdminCredentials;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiServerCodeResourceTest {
	@Test
	public void test() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiAdminCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		StringBuilder javascript = new StringBuilder();
		javascript.append("function updateObject(params, context, done){" + "\n");
		javascript.append("    console.log(\"updateObject\");" + "\n");
		javascript.append("    Kii.initializeWithSite(context.headers[\"X-Kii-AppID\"], context.headers[\"X-Kii-AppKey\"], params[\"baseUrl\"]);" + "\n");
		javascript.append("    var uri = params[\"uri\"];" + "\n");
		javascript.append("    var score = params[\"score\"];" + "\n");
		javascript.append("    var name = params[\"name\"];" + "\n");
		javascript.append("    var token = context.getAccessToken();" + "\n");
		javascript.append("    KiiUser.authenticateWithToken(token, {" + "\n");
		javascript.append("        success: function(theAuthedUser) {" + "\n");
		javascript.append("            var object = KiiObject.objectWithURI(uri);" + "\n");
		javascript.append("            object.set(\"score\", score);" + "\n");
		javascript.append("            object.set(\"name\", name);" + "\n");
		javascript.append("            object.save({" + "\n");
		javascript.append("              success: function(theObject) {" + "\n");
		javascript.append("                done(theObject);" + "\n");
		javascript.append("              }," + "\n");
		javascript.append("              failure: function(theObject, errorString) {" + "\n");
		javascript.append("                done(errorString);" + "\n");
		javascript.append("              }" + "\n");
		javascript.append("            });" + "\n");
		javascript.append("        }," + "\n");
		javascript.append("        failure: function(theUser, anErrorString) {" + "\n");
		javascript.append("            done(anErrorString);" + "\n");
		javascript.append("        }" + "\n");
		javascript.append("    });" + "\n");
		javascript.append("    " + "\n");
		javascript.append("}" + "\n");
		javascript.append("function returnUndefined(params,context){" + "\n");
		javascript.append("    console.log(\"returnUndefined\");" + "\n");
		javascript.append("    var test;" + "\n");
		javascript.append("    return test;" + "\n");
		javascript.append("}" + "\n");
		javascript.append("function returnNull(params,context){" + "\n");
		javascript.append("    console.log(\"returnNull\");" + "\n");
		javascript.append("    return null;" + "\n");
		javascript.append("}" + "\n");
		javascript.append("function returnObject(params,context){" + "\n");
		javascript.append("    console.log(\"returnObject\");" + "\n");
		javascript.append("    return {int:5, bool:false, str:\"string\", double:2.1234};" + "\n");
		javascript.append("}" + "\n");
		javascript.append("function returnArray(params,context){" + "\n");
		javascript.append("    console.log(\"returnArray\");" + "\n");
		javascript.append("    return [1];" + "\n");
		javascript.append("}" + "\n");
		javascript.append("function returnNumber(params,context){" + "\n");
		javascript.append("    console.log(\"returnNumber\");" + "\n");
		javascript.append("    return 3.14;" + "\n");
		javascript.append("}" + "\n");
		javascript.append("function returnString(params,context){" + "\n");
		javascript.append("    console.log(\"returnString\");" + "\n");
		javascript.append("    return \"cloud-code\";" + "\n");
		javascript.append("}" + "\n");
		javascript.append("function returnBool(params,context){" + "\n");
		javascript.append("    console.log(\"returnBool\");" + "\n");
		javascript.append("    return true;" + "\n");
		javascript.append("}" + "\n");
		javascript.append("function returnDate(params,context){" + "\n");
		javascript.append("    console.log(\"returnDate\");" + "\n");
		javascript.append("    return new Date(2013,07,01,01,01,01);" + "\n");
		javascript.append("}" + "\n");
		javascript.append("function returnMap(params,context){" + "\n");
		javascript.append("    console.log(\"returnMap\");" + "\n");
		javascript.append("    var map = {};" + "\n");
		javascript.append("    map['key1'] = 'value1';" + "\n");
		javascript.append("    return map;" + "\n");
		javascript.append("}" + "\n");
		javascript.append("function returnFunction(params,context){" + "\n");
		javascript.append("    console.log(\"returnFunction\");" + "\n");
		javascript.append("    function a(x){" + "\n");
		javascript.append("        return function (y) {" + "\n");
		javascript.append("            return x + y;" + "\n");
		javascript.append("        }" + "\n");
		javascript.append("     }" + "\n");
		javascript.append("    return a(10);" + "\n");
		javascript.append("}" + "\n");
		String versionID = rest.api().servercode().deploy(javascript.toString());
		
		rest.setCredentials(null);
		JsonObject result = rest.api().servercode(versionID).execute("returnMap", null);
		System.out.println(result);
	}
}
