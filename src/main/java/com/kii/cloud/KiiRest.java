package com.kii.cloud;

import com.kii.cloud.model.KiiCredentialsContainer;
import com.kii.cloud.resource.KiiAppResource;

public class KiiRest {
	
	public enum Site {
		US("https://api.kii.com/api",     "wss://apilog.kii.com"),
		JP("https://api-jp.kii.com/api",  "wss://apilog-jp.kii.com"),
		CN("https://api-cn2.kii.com/api", "wss://apilog-cn2.kii.com"),
		SG("https://api-sg.kii.com/api",  "wss://apilog-sg.kii.com");
		private final String endpoint;
		private final String devlogEndpoint;
		private Site(String endpoint, String devlogEndpoint) {
			this.endpoint = endpoint;
			this.devlogEndpoint = devlogEndpoint;
		}
	}

	private final String appID;
	private final String appKey;
	private final String endpoint;
	private final String devlogEndpoint;
	private KiiCredentialsContainer credentials;
	
	public KiiRest(String appID, String appKey, Site site) {
		this(appID, appKey, site.endpoint, site.devlogEndpoint);
	}
	public KiiRest(String appID, String appKey, String endpoint) {
		this(appID, appKey, endpoint, null);
	}
	public KiiRest(String appID, String appKey, String endpoint, String devlogEndpoint) {
		this.appID = appID;
		this.appKey = appKey;
		this.endpoint = endpoint;
		this.devlogEndpoint = devlogEndpoint;
	}
	public KiiAppResource api() {
		return new KiiAppResource(this.appID, this.appKey, this.endpoint, this.credentials);
	}
	public void setCredentials(KiiCredentialsContainer credentials) {
		this.credentials = credentials;
	}
	public KiiCredentialsContainer getCredentials() {
		return this.credentials;
	}
	public boolean hasCredentials() {
		return this.credentials != null;
	}
}
