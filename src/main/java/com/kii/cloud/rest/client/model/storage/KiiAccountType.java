package com.kii.cloud.rest.client.model.storage;

public enum KiiAccountType {
	USER_ID(""),
	EMAIL("EMAIL"),
	PHONE("PHONE"),
	LOGIN_NAME("LOGIN_NAME");
	
	private final String prefix;
	private KiiAccountType(String prefix) {
		this.prefix = prefix;
	}
	public String getPrefix() {
		return this.prefix;
	}
	public String getFullyQualifiedIdentifier(String identifier) {
		if (this == USER_ID) {
			return identifier;
		}
		return this.getPrefix() + ":" + identifier;
	}
	public static KiiAccountType parseIdentifier(String identifier) {
		if (KiiUser.EMAIL_ADDRESS_PATTERN.matcher(identifier).matches()) {
			return EMAIL;
		} else if (KiiUser.GLOBAL_PHONE_PATTERN.matcher(identifier).matches() || KiiUser.LOCAL_PHONE_PATTERN.matcher(identifier).matches()) {
			return PHONE;
		} else if (KiiUser.USER_ID_PATTERN.matcher(identifier).matches()) {
			// FIXME:This code depends on the rule of issuing ID on current implementation.
			return USER_ID;
		}
		return LOGIN_NAME;
	}
	public static KiiAccountType fromString(String str) {
		for (KiiAccountType accountType : values()) {
			if (accountType.getPrefix().equals(str)) {
				return accountType;
			}
		}
		return null;
	}
}
