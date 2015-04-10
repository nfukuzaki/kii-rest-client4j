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
	
	public static final KiiJsonProperty<String> PROPERTY_ACCESS_TOKEN = new KiiJsonProperty<String>(String.class, "_accessToken", "accessToken");
	public static final KiiJsonProperty<String> PROPERTY_REFRESH_TOKEN = new KiiJsonProperty<String>(String.class, "_refreshToken", "refresh_token");
	public static final KiiJsonProperty<Long> PROPERTY_EXPIRES_IN = new KiiJsonProperty<Long>(Long.class, "expires_in");
	public static final KiiJsonProperty<String> PROPERTY_USER_ID = new KiiJsonProperty<String>(String.class, "userID", "id");
	public static final KiiJsonProperty<String> PROPERTY_INTERNAL_USER_ID = new KiiJsonProperty<String>(String.class, "internalUserID");
	public static final KiiJsonProperty<String> PROPERTY_USERNAME = new KiiJsonProperty<String>(String.class, "loginName");
	public static final KiiJsonProperty<String> PROPERTY_DISPLAY_NAME = new KiiJsonProperty<String>(String.class, "displayName");
	public static final KiiJsonProperty<String> PROPERTY_COUNTRY = new KiiJsonProperty<String>(String.class, "country");
	public static final KiiJsonProperty<String> PROPERTY_EMAIL_ADDRESS = new KiiJsonProperty<String>(String.class, "emailAddress");
	public static final KiiJsonProperty<Boolean> PROPERTY_EMAIL_ADDRESS_VERIFIED = new KiiJsonProperty<Boolean>(Boolean.class, "emailAddressVerified");
	public static final KiiJsonProperty<String> PROPERTY_PHONE_NUMBER = new KiiJsonProperty<String>(String.class, "phoneNumber");
	public static final KiiJsonProperty<Boolean> PROPERTY_PHONE_NUMBER_VERIFIED = new KiiJsonProperty<Boolean>(Boolean.class, "phoneNumberVerified");
	public static final KiiJsonProperty<Boolean> PROPERTY_HAS_PASSWORD = new KiiJsonProperty<Boolean>(Boolean.class, "_hasPassword");
	public static final KiiJsonProperty<Boolean> PROPERTY_DISABLED = new KiiJsonProperty<Boolean>(Boolean.class, "_disabled");
	
	public static final String ME = "me";
	
	protected final JsonObject credentials = new JsonObject();
	
	public KiiUser() {
	}
	public KiiUser(JsonObject json) {
		super(json);
	}
	
	@Override
	public String getID() {
		return this.getUserID();
	}
	@Override
	public String getAccessToken() {
		return PROPERTY_ACCESS_TOKEN.get(this.credentials);
	}
	public KiiUser setAccessToken(String accessToken) {
		credentials.addProperty(PROPERTY_ACCESS_TOKEN.getName(), accessToken);
		return this;
	}
	@Override
	public String getRefreshToken() {
		return PROPERTY_REFRESH_TOKEN.get(this.credentials);
	}
	public KiiUser setRefreshToken(String refreshToken) {
		credentials.addProperty(PROPERTY_REFRESH_TOKEN.getName(), refreshToken);
		return this;
	}
	@Override
	public boolean isAdmin() {
		return false;
	}
	
	public abstract boolean isPseudo();
	
	public String getUserID() {
		return PROPERTY_USER_ID.get(this.json);
	}
	public KiiUser setUserID(String userID) {
		this.json.addProperty(PROPERTY_USER_ID.getName(), userID);
		return this;
	}
	public boolean isDisabled() {
		return PROPERTY_DISABLED.get(this.json);
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
