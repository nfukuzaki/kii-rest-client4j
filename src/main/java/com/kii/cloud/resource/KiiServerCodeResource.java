package com.kii.cloud.resource;

import com.google.gson.JsonObject;

public class KiiServerCodeResource extends KiiRestSubResource {
	private final String version;
	public KiiServerCodeResource(KiiAppResource parent) {
		super(parent);
		this.version = "current";
	}
	public KiiServerCodeResource(KiiAppResource parent, String version) {
		super(parent);
		this.version = version;
	}
	public void execute(String endpoint, JsonObject args) {
		
	}
	@Override
	public String getPath() {
		return null;
	}
}
