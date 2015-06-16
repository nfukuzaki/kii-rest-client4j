package com.kii.cloud.rest.client;

public class TestUtils {
	public static String randomGlobalJpPhoneNumber() {
		String currentTime = String.valueOf(System.currentTimeMillis());
		return "+81901" + currentTime.substring(currentTime.length() - 7, currentTime.length());
	}
}
