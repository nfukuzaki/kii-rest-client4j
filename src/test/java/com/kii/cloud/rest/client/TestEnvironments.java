package com.kii.cloud.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.kii.cloud.rest.client.util.GsonUtils;
import com.kii.cloud.rest.client.util.StringUtils;

/**
 * Reads the application configuration from test/resources/test_config.json.
 * Some tests needs admin credentials, but you must not push the admin credentials to the github.
 * You can write the admin credentials in test/resources/test_admin_credential_config.json.
 * This file is specified by the .gitignore file.
 * 
 * Format of test_admin_credential_config.json
 * [
 *     {
 *         "AppID"                    : "9a15048a",
 *         "ClientID"                 : "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
 *         "ClientSecret"             : "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
 *     },
 *     {
 *         "AppID"                    : "9a30c24b",
 *         "ClientID"                 : "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
 *         "ClientSecret"             : "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
 *         "TwitterAccessToken"       : "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
 *         "TwitterAccessTokenSecret" : "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
 *     },
 *     ....
 * ]
 */
public class TestEnvironments {
	
	public static final List<TestApp> TEST_APPS = new ArrayList<TestApp>();
	
	static {
		try {
			InputStream is = ClassLoader.getSystemResourceAsStream("test_config.json");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String conf = read(br);
			JsonArray apps = (JsonArray)new JsonParser().parse(conf);
			for (int i = 0; i < apps.size(); i++) {
				TEST_APPS.add(new TestApp(apps.get(i).getAsJsonObject()));
			}
			is = ClassLoader.getSystemResourceAsStream("test_admin_credential_config.json");
			if (is != null) {
				br = new BufferedReader(new InputStreamReader(is));
				conf = read(br);
				JsonArray credentials = (JsonArray)new JsonParser().parse(conf);
				for (int i = 0; i < credentials.size(); i++) {
					String appID = GsonUtils.getString(credentials.get(i).getAsJsonObject(), "AppID");
					String clientID = GsonUtils.getString(credentials.get(i).getAsJsonObject(), "ClientID");
					String clientSecret = GsonUtils.getString(credentials.get(i).getAsJsonObject(), "ClientSecret");
					String twitterAccessToken = GsonUtils.getString(credentials.get(i).getAsJsonObject(), "TwitterAccessToken");
					String twitterAccessTokenSecret = GsonUtils.getString(credentials.get(i).getAsJsonObject(), "TwitterAccessTokenSecret");
					if (!StringUtils.isEmpty(appID) && !StringUtils.isEmpty(clientID) && !StringUtils.isEmpty(clientSecret)) {
						for (TestApp testApp : TEST_APPS) {
							if (StringUtils.equals(appID, testApp.getAppID())) {
								testApp.setClientID(clientID);
								testApp.setClientSecret(clientSecret);
								if (!StringUtils.isEmpty(twitterAccessToken)) {
									testApp.setTwitterAccessToken(twitterAccessToken);
								}
								if (!StringUtils.isEmpty(twitterAccessTokenSecret)) {
									testApp.setTwitterAccessTokenSecret(twitterAccessTokenSecret);
								}
							}
						}
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("failed to parse test_config.json", e);
		}
	}
	private static String read(BufferedReader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		try {
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} finally {
			reader.close();
		}
	}
	public static TestApp random() throws TestAppNotFoundException {
		if (TEST_APPS.size() == 0) {
			throw new TestAppNotFoundException("There is no app for test.");
		}
		Random random = new Random(System.currentTimeMillis());
		int index = random.nextInt(TEST_APPS.size());
		return TEST_APPS.get(index);
	}
	public static TestApp random(TestAppFilter filter) throws TestAppNotFoundException {
		List<TestApp> acceptedApps = listByFilter(filter);
		if (acceptedApps.size() == 0) {
			throw new TestAppNotFoundException("There is no app that match the filter. filter=" + filter.toString());
		}
		Random random = new Random(System.currentTimeMillis());
		int index = random.nextInt(acceptedApps.size());
		return acceptedApps.get(index);
	}
	public static List<TestApp> listByFilter(TestAppFilter filter) {
		List<TestApp> acceptedApps = new ArrayList<TestApp>();
		for (TestApp app : TEST_APPS) {
			if (filter.accept(app)) {
				acceptedApps.add(app);
			}
		}
		return acceptedApps;
	}
}
