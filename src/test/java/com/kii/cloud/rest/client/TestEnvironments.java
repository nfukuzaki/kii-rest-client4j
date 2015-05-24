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

/**
 * test/resources/test_config.json
 */
public class TestEnvironments {
	
	public static final List<TestApp> TEST_APPS = new ArrayList<TestApp>();
	
	static {
		try {
			InputStream is = ClassLoader.getSystemResourceAsStream("test_config.json");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			try {
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
			} finally {
				br.close();
			}
			JsonArray apps = (JsonArray)new JsonParser().parse(sb.toString());
			for (int i = 0; i < apps.size(); i++) {
				TEST_APPS.add(new TestApp(apps.get(i).getAsJsonObject()));
			}
		} catch (IOException e) {
			throw new RuntimeException("failed to parse test_config.json", e);
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
