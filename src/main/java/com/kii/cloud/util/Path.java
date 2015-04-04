package com.kii.cloud.util;


public class Path {
	public static String combine(String path1, String path2) {
		if (StringUtils.isEmpty(path1)) {
			return path2;
		}
		if (StringUtils.isEmpty(path2)) {
			return path1;
		}
		if (path1.endsWith("/") && path2.startsWith("/")) {
			return path1 + path2.substring(1);
		} else if (!path1.endsWith("/") && !path2.startsWith("/")) {
			return path1 + "/" + path2;
		} else {
			return path1 + path2;
		}
	}
}
