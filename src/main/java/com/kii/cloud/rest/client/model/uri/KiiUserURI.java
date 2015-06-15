package com.kii.cloud.rest.client.model.uri;

import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.model.storage.KiiAccountType;
import com.kii.cloud.rest.client.util.StringUtils;

/**
 * Represents the XXXX URI like following URIs:
 * 
 * <ul>
 * <li>kiicloud://{AppID}/users/{UserID}
 * <li>kiicloud://{AppID}/users/EMAIL:{Email}
 * <li>kiicloud://{AppID}/users/PHONE:{PhoneNumber}
 * <li>kiicloud://{AppID}/users/LOGIN_NAME:{Username}
 * </ul>
 */
public class KiiUserURI extends KiiURI {
	
	public static KiiUserURI newURI(String appID, String identifier) {
		return new KiiUserURI(new KiiAppURI(appID), identifier);
	}
	public static KiiUserURI parse(String str) {
		if (StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException("str is null or empty");
		}
		if (!str.startsWith(SCHEME)) {
			throw new IllegalArgumentException("URI should start with 'kiicloud://'");
		}
		String[] segments = str.replace(SCHEME, "").split("/");
		if (segments.length == 2) {
			if (StringUtils.equals(SEGMENT_USERS, segments[0])) {
				return new KiiUserURI(new KiiAppURI(UNKNOWN_APP_ID), segments[1]);
			}
		} else if (segments.length == 3) {
			if (StringUtils.equals(SEGMENT_USERS, segments[1])) {
				return new KiiUserURI(new KiiAppURI(segments[0]), segments[2]);
			}
		}
		throw new IllegalArgumentException("invalid URI : " + str);
	}
	
	private final KiiAppURI parent;
	private final KiiAccountType accountType;
	private final String identifier;
	
	protected KiiUserURI(KiiAppURI parent, String identifier) {
		this.parent = parent;
		String[] array = identifier.split(":");
		if (array.length > 1) {
			this.accountType = KiiAccountType.fromString(array[0]);
			this.identifier = array[1];
		} else {
			this.accountType = KiiAccountType.parseIdentifier(identifier);
			this.identifier = identifier;
		}
	}
	public KiiAccountType getAccountType() {
		return this.accountType;
	}
	public String getIdentifier() {
		return this.identifier;
	}
	@Override
	public KiiScope getScope() {
		return KiiScope.USER;
	}
	@Override
	public String toUriString() {
		return this.parent.toUriString() + "/" + SEGMENT_USERS + "/" + this.accountType.getFullyQualifiedIdentifier(this.identifier);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountType == null) ? 0 : accountType.hashCode());
		result = prime * result
				+ ((identifier == null) ? 0 : identifier.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
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
		KiiUserURI other = (KiiUserURI) obj;
		if (accountType != other.accountType)
			return false;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}
}
