package com.kii.cloud;

import com.kii.cloud.model.KiiRestContext;
import com.kii.cloud.resource.KiiAppResource;

public class KiiRest {
	
	public enum Site {
		US("https://api.kii.com/api"),
		JP("https://api-jp.kii.com/api"),
		CN("https://api-cn2.kii.com/api"),
		SG("https://api-sg.kii.com/api");
		private final String endpoint;
		private Site(String endpoint) {
			this.endpoint = endpoint;
		}
	}
	
	private final String appID;
	private final String appKey;
	private final String endpoint;
	private KiiRestContext context;
	
	public KiiRest(String appID, String appKey, Site site) {
		this(appID, appKey, site.endpoint);
	}
	public KiiRest(String appID, String appKey, String endpoint) {
		this.appID = appID;
		this.appKey = appKey;
		this.endpoint = endpoint;
	}
	public KiiAppResource api() {
		return new KiiAppResource(this.appID, this.appKey, this.endpoint, this.context);
	}
	public void setContext(KiiRestContext context) {
		this.context = context;
	}
}
