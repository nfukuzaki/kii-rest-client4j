package com.kii.cloud.rest.client.model;

public abstract class KiiURI {
	public static final String SCHEME = "kiicloud://";
	
	protected final KiiScope scope;
	
	public KiiURI(KiiScope scope) {
		this.scope = scope;
	}
	public KiiScope getScope() {
		return scope;
	}
}
