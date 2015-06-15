package com.kii.cloud.rest.client.model.uri;

import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.util.StringUtils;

/**
 * Represents the XXXX URI like following URIs:
 * 
 * <ul>
 * <li>kiicloud://{AppID}/groups/{GroupID}
 * </ul>
 */
public class KiiGroupURI extends KiiURI {
	
	public static KiiGroupURI parse(String str) {
		if (StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException("str is null or empty");
		}
		if (!str.startsWith(SCHEME)) {
			throw new IllegalArgumentException("URI should start with 'kiicloud://'");
		}
		String[] segments = str.replace(SCHEME, "").split("/");
		if (segments.length == 2) {
			if (StringUtils.equals(SEGMENT_GROUPS, segments[0])) {
				return new KiiGroupURI(new KiiAppURI(UNKNOWN_APP_ID), segments[1]);
			}
		} else if (segments.length == 3) {
			if (StringUtils.equals(SEGMENT_GROUPS, segments[1])) {
				return new KiiGroupURI(new KiiAppURI(segments[0]), segments[2]);
			}
		}
		throw new IllegalArgumentException("invalid URI : " + str);
	}
	
	private final KiiAppURI parent;
	private final String groupID;
	
	protected KiiGroupURI(KiiAppURI parent, String groupID) {
		this.parent = parent;
		this.groupID = groupID;
	}
	public String getGroupID() {
		return this.groupID;
	}
	@Override
	public KiiScope getScope() {
		return KiiScope.GROUP;
	}
	@Override
	public String toUriString() {
		return this.parent.toUriString() + "/" + SEGMENT_GROUPS + "/" + this.groupID;
	}
}
