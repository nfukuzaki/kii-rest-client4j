package com.kii.cloud.rest.client.model.social;

import com.kii.cloud.rest.client.util.StringUtils;

public enum KiiSocialProvider {
	FACEBOOK("facebook", "graph.facebook.com."),
	TWITTER("twitter", "twitter.com."),
	LINKEDIN("linkedin", "api.linkedin.com."),
	YAHOO("yahoo", "api.login.yahoo.com."),
	GOOGLE("google", "googleapis.com."),
	LIVE("live", "consent.live.com."),
	BOX("box", "box."),
	DROPBOX("dropbox", "dropbox."),
	RENREN("renren", "renren."),
	SINA("sina", "sina."),
	QQ("qq", "qq."),
	YOUWILL("youwill", "youwill."),
	KII("kii", "kii.");
	
	private final String id;
	private final String socialAuthPrefix;
	private KiiSocialProvider(String id, String socialAuthPrefix) {
		this.id = id;
		this.socialAuthPrefix = socialAuthPrefix;
	}
	
	public String getID() {
		return this.id;
	}
	public String getSocialAuthPrefix() {
		return socialAuthPrefix;
	}
	public String getTokenRequestContentType() {
		String providerName = StringUtils.capitalize(this.id);
		return "application/vnd.kii.AuthToken" + providerName + "Request+json";
	}
	public String getLinkRequestContentType() {
		String providerName = StringUtils.capitalize(this.id);
		return "application/vnd.kii.Link" + providerName + "Request+json";
	}
}
