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
	
	public static KiiAppURI newURI(String appID) {
		return new KiiAppURI(appID);
	}
	
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appID == null) ? 0 : appID.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KiiAppURI other = (KiiAppURI) obj;
		if (appID == null) {
			if (other.appID != null)
				return false;
		} else if (!appID.equals(other.appID))
			return false;
		return true;
	}
}
