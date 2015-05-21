package com.kii.cloud.rest.client.model;

import com.kii.cloud.rest.client.util.StringUtils;

/**
 * Represents the User URI.
 * 
 * @see http://documentation.kii.com/en/starts/cloudsdk/cloudoverview/idanduri/
 */
public class KiiUserURI extends KiiScopeURI {
	
	public static KiiUserURI create(String str) {
		if (StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException("str is null or empty");
		}
		if (!str.startsWith(SCHEME)) {
			throw new IllegalArgumentException("User URI should start with 'kiicloud://'");
		}
		String[] segments = str.replace(SCHEME, "").split("/");
		if (segments.length == 2) {
			if (!StringUtils.equals(KiiScope.USER.getCollectionName(), segments[0])) {
				throw new IllegalArgumentException("'" + str + "' is not Kii User URI");
			}
			return new KiiUserURI(segments[1]);
		} else {
			throw new IllegalArgumentException("'" + str + "' is not Kii User URI");
		}
	}
	
	public KiiUserURI(String userID) {
		super(KiiScope.USER, userID);
	}

}
