package com.kii.cloud.model.social;

public enum SocialProvider {
	FACEBOOK("facebook"),
	TWITTER("twitter"),
	LINKEDIN("linkedin"),
	YAHOO("yahoo"),
	GOOGLE("google"),
	LIVE("live"),
	BOX("box"),
	DROPBOX("dropbox"),
	RENREN("renren"),
	SINA("sina"),
	QQ("qq"),
	YOUWILL("youwill");
	
	private final String id;
	private SocialProvider(String id) {
		this.id = id;
	}
	
	public String getID() {
		return this.id;
	}
	
}
