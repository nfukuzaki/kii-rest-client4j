package com.kii.cloud.rest.client.model.uri;

import com.kii.cloud.rest.client.model.KiiScope;

/**
 * Represents the XXXX URI like following URIs:
 * 
 * <ul>
 * <li>kiicloud://{AppID}/group/{GroupID}
 * </ul>
 */
public class KiiGroupURI extends KiiURI {
	
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
