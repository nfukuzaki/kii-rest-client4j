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
}
