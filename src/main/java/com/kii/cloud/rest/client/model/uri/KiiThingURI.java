package com.kii.cloud.rest.client.model.uri;

import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.model.storage.KiiThingIdentifierType;
import com.kii.cloud.rest.client.util.StringUtils;

/**
 * Represents the XXXX URI like following URIs:
 * 
 * <ul>
 * <li>kiicloud://{AppID}/things/{ThingsID}
 * <li>kiicloud://{AppID}/things/VENDOR_THING_ID:{VendorThingsID}
 * </ul>
 */
public class KiiThingURI extends KiiURI {
	
	public static KiiThingURI parse(String str) {
		if (StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException("str is null or empty");
		}
		if (!str.startsWith(SCHEME)) {
			throw new IllegalArgumentException("URI should start with 'kiicloud://'");
		}
		String[] segments = str.replace(SCHEME, "").split("/");
		if (segments.length == 2) {
			if (StringUtils.equals(SEGMENT_THINGS, segments[0])) {
				return new KiiThingURI(new KiiAppURI(UNKNOWN_APP_ID), segments[1]);
			}
		} else if (segments.length == 3) {
			if (StringUtils.equals(SEGMENT_THINGS, segments[1])) {
				return new KiiThingURI(new KiiAppURI(segments[0]), segments[2]);
			}
		}
		throw new IllegalArgumentException("invalid URI : " + str);
	}
	
	private final KiiAppURI parent;
	private final KiiThingIdentifierType identifierType;
	private final String identifier;
	
	protected KiiThingURI(KiiAppURI parent, String identifier) {
		this.parent = parent;
		String[] array = identifier.split(":");
		if (array.length > 1) {
			this.identifierType = KiiThingIdentifierType.fromString(array[0]);
			this.identifier = array[1];
		} else {
			this.identifierType = KiiThingIdentifierType.parseIdentifier(identifier);
			this.identifier = identifier;
		}
	}
	protected KiiThingURI(KiiAppURI parent, KiiThingIdentifierType identifierType, String identifier) {
		this.parent = parent;
		this.identifierType = identifierType;
		this.identifier = identifier;
	}
	public KiiThingIdentifierType getIdentifierType() {
		return this.identifierType;
	}
	public String getIdentifier() {
		return this.identifier;
	}
	@Override
	public KiiScope getScope() {
		return KiiScope.THING;
	}
	@Override
	public String toUriString() {
		return this.parent.toUriString() + "/" + SEGMENT_THINGS + "/" + this.identifierType.getFullyQualifiedIdentifier(this.identifier);
	}

}
