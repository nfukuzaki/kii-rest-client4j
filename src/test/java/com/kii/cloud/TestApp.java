package com.kii.cloud;

public class TestApp {
	public final String AppName;
	public final String AppID;
	public final String AppKey;
	public final String ClientID;
	public final String ClientSecret;
	public final KiiRest.Site Site;
	public TestApp(String appName, String appID, String appKey, KiiRest.Site site) {
		this.AppName = appName;
		this.AppID = appID;
		this.AppKey = appKey;
		this.ClientID = null;
		this.ClientSecret = null;
		this.Site = site;
	}
	public TestApp(String appName, String appID, String appKey, String clientID, String clientSecret, KiiRest.Site site) {
		this.AppName = appName;
		this.AppID = appID;
		this.AppKey = appKey;
		this.ClientID = clientID;
		this.ClientSecret = clientSecret;
		this.Site = site;
	}
}
