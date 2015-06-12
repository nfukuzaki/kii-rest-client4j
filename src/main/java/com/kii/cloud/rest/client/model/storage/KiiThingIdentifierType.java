package com.kii.cloud.rest.client.model.storage;

public enum KiiThingIdentifierType {
	THING_ID(""),
	VENDOR_THING_ID("VENDOR_THING_ID");
	
	private final String prefix;
	private KiiThingIdentifierType(String prefix) {
		this.prefix = prefix;
	}
	public String getPrefix() {
		return this.prefix;
	}
	public String getFullyQualifiedIdentifier(String identifier) {
		if (this == THING_ID) {
			return identifier;
		}
		return this.getPrefix() + ":" + identifier;
	}
	public static KiiThingIdentifierType parseIdentifier(String identifier) {
		if (identifier.startsWith("th.")) {
			return THING_ID;
		}
		return VENDOR_THING_ID;
	}
	public static KiiThingIdentifierType fromString(String str) {
		for (KiiThingIdentifierType identifierType : values()) {
			if (identifierType.getPrefix().equals(str)) {
				return identifierType;
			}
		}
		return null;
	}
}
