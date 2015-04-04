package com.kii.cloud.model;

import java.util.regex.Pattern;

import com.google.gson.JsonObject;
import com.kii.cloud.util.GsonUtils;

public abstract class KiiUser extends KiiCustomableJsonModel<KiiUser> implements KiiRestContext {
	
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
	
	public static final String PROPERTY_ID = "id";
	public static final String PROPERTY_ACCESS_TOKEN = "_accessToken";
	public static final String PROPERTY_REFRESH_TOKEN = "_refreshToken";
	public static final String PROPERTY_EXPIRES_IN = "expires_in";
	public static final String PROPERTY_USER_ID = "userID";
	public static final String PROPERTY_INTERNAL_USER_ID = "internalUserID";
	public static final String PROPERTY_USERNAME = "loginName";
	public static final String PROPERTY_DISPLAY_NAME = "displayName";
	public static final String PROPERTY_COUNTRY = "country";
	public static final String PROPERTY_EMAIL_ADDRESS = "emailAddress";
	public static final String PROPERTY_EMAIL_ADDRESS_VERIFIED = "emailAddressVerified";
	public static final String PROPERTY_PHONE_NUMBER = "phoneNumber";
	public static final String PROPERTY_PHONE_NUMBER_VERIFIED = "phoneNumberVerified";
	public static final String PROPERTY_HAS_PASSWORD = "_hasPassword";
	public static final String PROPERTY_DISABLED = "_disabled";
	
	protected final JsonObject credentials = new JsonObject();
	
	@Override
	public String getAccessToken() {
		return GsonUtils.getString(credentials, PROPERTY_ACCESS_TOKEN);
	}
	public KiiUser setAccessToken(String accessToken) {
		credentials.addProperty(PROPERTY_ACCESS_TOKEN, accessToken);
		return this;
	}
	@Override
	public String getRefreshToken() {
		return GsonUtils.getString(credentials, PROPERTY_REFRESH_TOKEN);
	}
	public KiiUser setRefreshToken(String refreshToken) {
		credentials.addProperty(PROPERTY_REFRESH_TOKEN, refreshToken);
		return this;
	}
	
	public abstract boolean isPseudo();
	
	public String getUserID() {
		return GsonUtils.getString(this.json, PROPERTY_USER_ID);
	}
	public KiiUser setUserID(String userID) {
		this.json.addProperty(PROPERTY_USER_ID, userID);
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
