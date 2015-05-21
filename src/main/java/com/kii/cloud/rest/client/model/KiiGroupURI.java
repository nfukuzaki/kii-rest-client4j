package com.kii.cloud.rest.client.model;

import com.kii.cloud.rest.client.util.StringUtils;

/**
 * Represents the Group URI.
 * 
 * @see http://documentation.kii.com/en/starts/cloudsdk/cloudoverview/idanduri/
 */
public class KiiGroupURI extends KiiScopeURI {
	
	public static KiiGroupURI create(String str) {
		if (StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException("str is null or empty");
		}
		if (!str.startsWith(SCHEME)) {
			throw new IllegalArgumentException("Group URI should start with 'kiicloud://'");
		}
		String[] segments = str.replace(SCHEME, "").split("/");
		if (segments.length == 2) {
			if (!StringUtils.equals(KiiScope.GROUP.getCollectionName(), segments[0])) {
				throw new IllegalArgumentException("'" + str + "' is not Kii Group URI");
			}
			return new KiiGroupURI(segments[1]);
		} else {
			throw new IllegalArgumentException("'" + str + "' is not Kii Group URI");
		}
	}

	public KiiGroupURI(String groupID) {
		super(KiiScope.GROUP, groupID);
	}

}
