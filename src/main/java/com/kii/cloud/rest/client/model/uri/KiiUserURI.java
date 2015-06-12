package com.kii.cloud.rest.client.model.uri;

import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.model.storage.KiiAccountType;

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
}
