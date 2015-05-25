package com.kii.cloud.rest.client;

import com.kii.cloud.rest.client.logging.KiiDefaultLogger;
import com.kii.cloud.rest.client.logging.KiiEmptyLogger;
import com.kii.cloud.rest.client.logging.KiiLogger;
import com.kii.cloud.rest.client.model.KiiCredentialsContainer;
import com.kii.cloud.rest.client.resource.KiiAppResource;
import com.kii.cloud.rest.client.resource.servercode.KiiDevlogResource;

public class KiiRest {
	
	public enum Site {
		US("https://api.kii.com/api",     "wss://apilog.kii.com:443/logs"),
		JP("https://api-jp.kii.com/api",  "wss://apilog-jp.kii.com:443/logs"),
		CN("https://api-cn2.kii.com/api", "wss://apilog-cn2.kii.com:443/logs"),
		SG("https://api-sg.kii.com/api",  "wss://apilog-sg.kii.com:443/logs");
		private final String endpoint;
		private final String devlogEndpoint;
		private Site(String endpoint, String devlogEndpoint) {
			this.endpoint = endpoint;
			this.devlogEndpoint = devlogEndpoint;
		}
		public String getEndpoint() {
			return endpoint;
		}
		public String getDevlogEndpoint() {
			return devlogEndpoint;
		}
	}

	private final String appID;
	private final String appKey;
	private final String endpoint;
	private final String devlogEndpoint;
	private KiiCredentialsContainer credentials;
	private KiiLogger logger = KiiDefaultLogger.INSTANCE;
	
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
		return new KiiAppResource(this.appID, this.appKey, this.endpoint, this.credentials, this.logger);
	}
	public KiiDevlogResource logs() {
		return new KiiDevlogResource(this.appID, this.appKey, this.devlogEndpoint, this.credentials, this.logger);
	}
	/**
	 * @param credentials Try anonymous access if specify null.
	 */
	public void setCredentials(KiiCredentialsContainer credentials) {
		if (credentials != null && !credentials.hasCredentials()) {
			throw new IllegalArgumentException("credentials does not have an access token");
		}
		this.credentials = credentials;
	}
	public KiiCredentialsContainer getCredentials() {
		return this.credentials;
	}
	public boolean hasCredentials() {
		return this.credentials != null;
	}
	public void setLogger(KiiLogger logger) {
		if (logger == null) {
			this.logger = KiiEmptyLogger.INSTANCE;
		} else {
			this.logger = logger;
		}
	}
}
