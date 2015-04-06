package com.kii.cloud;

public class TestUtils {
	public static String getRandomGlobalJpPhoneNumber() {
		String currentTime = String.valueOf(System.currentTimeMillis());
		return "+81901" + currentTime.substring(currentTime.length() - 7, currentTime.length());
	}
}
