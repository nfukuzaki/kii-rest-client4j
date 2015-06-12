package com.kii.cloud.rest.client.model.uri;

import com.kii.cloud.rest.client.model.KiiScope;

/**
 * Represents the XXXX URI like following URIs:
 * 
 * <ul>
 * </ul>
 */
public class KiiAppURI extends KiiURI {
	private final String appID;
	
	protected KiiAppURI(String appID) {
		this.appID = appID;
	}
	public String getAppID() {
		return this.appID;
	}
	@Override
	public KiiScope getScope() {
		return KiiScope.APP;
	}
	@Override
	public String toUriString() {
		return SCHEME + this.appID;
	}
}
