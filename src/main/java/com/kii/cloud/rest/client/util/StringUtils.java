package com.kii.cloud.rest.client.util;

public class StringUtils {
	public static boolean isEmpty(final String s) {
		return (s == null || s.length() == 0);
	}
	public static String capitalize(final String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		char firstChar = str.charAt(0);
		if (Character.isTitleCase(firstChar)) {
			return str;
		}
		return new StringBuilder(strLen)
			.append(Character.toTitleCase(firstChar))
			.append(str.substring(1))
			.toString();
	}
}
