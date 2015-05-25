package com.kii.cloud.rest.client.model.social;

import com.kii.cloud.rest.client.model.KiiJsonModel;
import com.kii.cloud.rest.client.model.KiiJsonProperty;
import com.kii.cloud.rest.client.util.StringUtils;

public class KiiNativeSocialCredentials extends KiiJsonModel {
	
	public static final KiiJsonProperty<String> PROPERTY_ACCESS_TOKEN = new KiiJsonProperty<String>(String.class, "accessToken");
	public static final KiiJsonProperty<String> PROPERTY_ACCESS_TOKEN_SECRET = new KiiJsonProperty<String>(String.class, "accessTokenSecret");
	public static final KiiJsonProperty<String> PROPERTY_OPEN_ID = new KiiJsonProperty<String>(String.class, "openID");
	
	private final KiiSocialProvider provider;
	
	public static KiiNativeSocialCredentials facebook(String accessToken) {
		if (StringUtils.isEmpty(accessToken)) {
			throw new IllegalArgumentException("accessToken is null or empty");
		}
		return new KiiNativeSocialCredentials(KiiSocialProvider.FACEBOOK, accessToken, null, null);
	}
	public static KiiNativeSocialCredentials google(String accessToken) {
		if (StringUtils.isEmpty(accessToken)) {
			throw new IllegalArgumentException("accessToken is null or empty");
		}
		return new KiiNativeSocialCredentials(KiiSocialProvider.GOOGLE, accessToken, null, null);
	}
	public static KiiNativeSocialCredentials twitter(String accessToken, String accessTokenSecret) {
		if (StringUtils.isEmpty(accessToken)) {
			throw new IllegalArgumentException("accessToken is null or empty");
		}
		if (StringUtils.isEmpty(accessTokenSecret)) {
			throw new IllegalArgumentException("accessTokenSecret is null or empty");
		}
		return new KiiNativeSocialCredentials(KiiSocialProvider.TWITTER, accessToken, accessTokenSecret, null);
	}
	public static KiiNativeSocialCredentials qq(String accessToken, String openID) {
		if (StringUtils.isEmpty(accessToken)) {
			throw new IllegalArgumentException("accessToken is null or empty");
		}
		if (StringUtils.isEmpty(openID)) {
			throw new IllegalArgumentException("openID is null or empty");
		}
		return new KiiNativeSocialCredentials(KiiSocialProvider.QQ, accessToken, null, openID);
	}
	public static KiiNativeSocialCredentials renren(String accessToken) {
		if (StringUtils.isEmpty(accessToken)) {
			throw new IllegalArgumentException("accessToken is null or empty");
		}
		return new KiiNativeSocialCredentials(KiiSocialProvider.RENREN, accessToken, null, null);
	}
	
	
	private KiiNativeSocialCredentials(KiiSocialProvider provider, String accessToken, String accessTokenSecret, String openID) {
		super();
		this.provider = provider;
		PROPERTY_ACCESS_TOKEN.set(this.json, accessToken);
		if (accessTokenSecret != null) {
			PROPERTY_ACCESS_TOKEN_SECRET.set(this.json, accessTokenSecret);
		}
		if (openID != null) {
			PROPERTY_OPEN_ID.set(this.json, openID);
		}
	}
	public KiiSocialProvider getProvider() {
		return provider;
	}
}
