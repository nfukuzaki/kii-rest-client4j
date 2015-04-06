package com.kii.cloud.resource;

import com.google.gson.JsonObject;

public class KiiTopicResource extends KiiRestSubResource {
	private final String name;
	public KiiTopicResource(KiiTopicsResource parent, String name) {
		super(parent);
		this.name = name;
	}
	public void get() {
	}
	public void delete() {
	}
	public void send(JsonObject message) {
	}
	public void subscribe() {
	}
	public void unsubscribe() {
	}
	public KiiAclResource acl() {
		return null;
	}
	@Override
	public String getPath() {
		return null;
	}
}
