package com.kii.cloud.rest.client.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class StringUtils {
	public static boolean isEmpty(final String s) {
		return (s == null || s.length() == 0);
	}
	public static boolean equals(final String str1, final String str2) {
		if (str1 == null || str2 == null) {
			return str1 == str2;
		} else if (str1 == str2) {
			return true;
		} else if (str1.length() != str2.length()) {
			return false;
		} else {
			return str1.equals(str2);
		}
	}
	public static boolean equalsIgnoreCase(final String str1, final String str2) {
		if (str1 == null || str2 == null) {
			return str1 == str2;
		} else if (str1 == str2) {
			return true;
		} else if (str1.length() != str2.length()) {
			return false;
		} else {
			return str1.equalsIgnoreCase(str2);
		}
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
	public static String urlEncode(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return s;
		}
	}
}
