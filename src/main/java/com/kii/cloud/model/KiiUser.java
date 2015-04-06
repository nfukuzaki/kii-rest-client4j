package com.kii.cloud.model;

import java.util.regex.Pattern;

import com.google.gson.JsonObject;

public abstract class KiiUser extends KiiCustomableJsonModel<KiiUser> implements KiiCredentialsContainer {
	
	public static final Pattern USERNAME_PATTERN = Pattern.compile("[a-zA-Z0-9-_\\.]{3,64}$");
	public static final Pattern GLOBAL_PHONE_PATTERN = Pattern.compile("^[\\+][0-9.-]+");
	public static final Pattern LOCAL_PHONE_PATTERN = Pattern.compile("^[0-9]*$");
	public static final Pattern USER_ID_PATTERN = Pattern.compile("^[a-z0-9]{12}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{8}$");
	public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
			"[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
			"\\@" +
			"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
			"(" +
			"\\." +
			"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
			")+"
	);
	
	public static final KiiJsonProperty PROPERTY_ACCESS_TOKEN = new KiiJsonProperty("_accessToken", "accessToken");
	public static final KiiJsonProperty PROPERTY_REFRESH_TOKEN = new KiiJsonProperty("_refreshToken", "refresh_token");
	public static final KiiJsonProperty PROPERTY_EXPIRES_IN = new KiiJsonProperty("expires_in");
	public static final KiiJsonProperty PROPERTY_USER_ID = new KiiJsonProperty("userID", "id");
	public static final KiiJsonProperty PROPERTY_INTERNAL_USER_ID = new KiiJsonProperty("internalUserID");
	public static final KiiJsonProperty PROPERTY_USERNAME = new KiiJsonProperty("loginName");
	public static final KiiJsonProperty PROPERTY_DISPLAY_NAME = new KiiJsonProperty("displayName");
	public static final KiiJsonProperty PROPERTY_COUNTRY = new KiiJsonProperty("country");
	public static final KiiJsonProperty PROPERTY_EMAIL_ADDRESS = new KiiJsonProperty("emailAddress");
	public static final KiiJsonProperty PROPERTY_EMAIL_ADDRESS_VERIFIED = new KiiJsonProperty("emailAddressVerified");
	public static final KiiJsonProperty PROPERTY_PHONE_NUMBER = new KiiJsonProperty("phoneNumber");
	public static final KiiJsonProperty PROPERTY_PHONE_NUMBER_VERIFIED = new KiiJsonProperty("phoneNumberVerified");
	public static final KiiJsonProperty PROPERTY_HAS_PASSWORD = new KiiJsonProperty("_hasPassword");
	public static final KiiJsonProperty PROPERTY_DISABLED = new KiiJsonProperty("_disabled");
	
	protected final JsonObject credentials = new JsonObject();
	
	@Override
	public String getID() {
		return this.getUserID();
	}
	@Override
	public String getAccessToken() {
		return PROPERTY_ACCESS_TOKEN.getString(credentials);
	}
	public KiiUser setAccessToken(String accessToken) {
		credentials.addProperty(PROPERTY_ACCESS_TOKEN.getName(), accessToken);
		return this;
	}
	@Override
	public String getRefreshToken() {
		return PROPERTY_REFRESH_TOKEN.getString(credentials);
	}
	public KiiUser setRefreshToken(String refreshToken) {
		credentials.addProperty(PROPERTY_REFRESH_TOKEN.getName(), refreshToken);
		return this;
	}
	
	public abstract boolean isPseudo();
	
	public String getUserID() {
		return PROPERTY_USER_ID.getString(this.json);
	}
	public KiiUser setUserID(String userID) {
		this.json.addProperty(PROPERTY_USER_ID.getName(), userID);
		return this;
	}
	public static String getAccountType(String identifier) {
		if (KiiUser.EMAIL_ADDRESS_PATTERN.matcher(identifier).matches()) {
			return "EMAIL:";
		} else if (KiiUser.GLOBAL_PHONE_PATTERN.matcher(identifier).matches() || KiiUser.LOCAL_PHONE_PATTERN.matcher(identifier).matches()) {
			return "PHONE:";
		} else if (KiiUser.USER_ID_PATTERN.matcher(identifier).matches()) {
			// FIXME:This code depends on the rule of issuing ID on current implementation.
			return "";
		}
		return "LOGIN_NAME:";
	}
}
