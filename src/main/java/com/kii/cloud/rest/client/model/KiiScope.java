package com.kii.cloud.rest.client.model;

public enum KiiScope {
	APP("app", "apps"),
	GROUP("group", "groups"),
	USER("user", "users"),
	THING("thing", "things");
	
	private final String name;
	private final String collectionName;
	
	private KiiScope(String name, String collectionName) {
		this.name = name;
		this.collectionName = collectionName;
	}
	public String getName() {
		return name;
	}
	public String getCollectionName() {
		return collectionName;
	}
	public static KiiScope fromString(String str) {
		for (KiiScope scope : values()) {
			if (scope.name.equals(str) || scope.collectionName.equals(str)) {
				return scope;
			}
		}
		return null;
	}
}
