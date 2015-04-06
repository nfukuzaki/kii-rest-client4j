package com.kii.cloud;

public class TestApp {
	public final String AppName;
	public final String AppID;
	public final String AppKey;
	public final KiiRest.Site Site;
	public TestApp(String appName, String appID, String appKey, KiiRest.Site site) {
		this.AppName = appName;
		this.AppID = appID;
		this.AppKey = appKey;
		this.Site = site;
	}
}
