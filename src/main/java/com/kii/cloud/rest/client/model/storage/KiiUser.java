package com.kii.cloud.rest.client.model.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.model.KiiCredentialsContainer;
import com.kii.cloud.rest.client.model.KiiCustomableJsonModel;
import com.kii.cloud.rest.client.model.KiiJsonProperty;
import com.kii.cloud.rest.client.model.social.KiiSocialAccountInfo;
import com.kii.cloud.rest.client.model.validation.RangeLengthValidator;
import com.kii.cloud.rest.client.model.validation.RegularExpressionValidator;
import com.kii.cloud.rest.client.util.StringUtils;

public abstract class KiiUser extends KiiCustomableJsonModel<KiiUser> implements KiiCredentialsContainer {
	
	public static final Pattern USERNAME_PATTERN = Pattern.compile("[a-zA-Z0-9-_\\.]{3,64}$");
	public static final Pattern GLOBAL_PHONE_PATTERN = Pattern.compile("^[\\+][0-9.-]+");
	public static final Pattern LOCAL_PHONE_PATTERN = Pattern.compile("^[0-9]*$");
	public static final Pattern USER_ID_PATTERN = Pattern.compile("^[a-z0-9]{12}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{8}$");
	public static final Pattern COUNTRY_PATTERN = Pattern.compile("^[A-Z]{2}$");
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
	public static final KiiJsonProperty<Long> PROPERTY_INTERNAL_USER_ID = new KiiJsonProperty<Long>(Long.class, "internalUserID");
	public static final KiiJsonProperty<String> PROPERTY_USERNAME = new KiiJsonProperty<String>(String.class, "loginName", new RegularExpressionValidator(USERNAME_PATTERN));
	public static final KiiJsonProperty<String> PROPERTY_DISPLAY_NAME = new KiiJsonProperty<String>(String.class, "displayName", new RangeLengthValidator(1, 50));
	public static final KiiJsonProperty<String> PROPERTY_COUNTRY = new KiiJsonProperty<String>(String.class, "country", new RegularExpressionValidator(COUNTRY_PATTERN));
	public static final KiiJsonProperty<String> PROPERTY_EMAIL_ADDRESS = new KiiJsonProperty<String>(String.class, "emailAddress", new RegularExpressionValidator(EMAIL_ADDRESS_PATTERN));
	public static final KiiJsonProperty<Boolean> PROPERTY_EMAIL_ADDRESS_VERIFIED = new KiiJsonProperty<Boolean>(Boolean.class, "emailAddressVerified");
	public static final KiiJsonProperty<String> PROPERTY_PHONE_NUMBER = new KiiJsonProperty<String>(String.class, "phoneNumber", new RegularExpressionValidator(GLOBAL_PHONE_PATTERN, LOCAL_PHONE_PATTERN));
	public static final KiiJsonProperty<Boolean> PROPERTY_PHONE_NUMBER_VERIFIED = new KiiJsonProperty<Boolean>(Boolean.class, "phoneNumberVerified");
	public static final KiiJsonProperty<Boolean> PROPERTY_HAS_PASSWORD = new KiiJsonProperty<Boolean>(Boolean.class, "_hasPassword");
	public static final KiiJsonProperty<Boolean> PROPERTY_DISABLED = new KiiJsonProperty<Boolean>(Boolean.class, "_disabled");
	public static final KiiJsonProperty<JsonObject> PROPERTY_THIRD_PARTY_ACCOUNTS = new KiiJsonProperty<JsonObject>(JsonObject.class, "_thirdPartyAccounts");
	
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
	public boolean hasCredentials() {
		return !StringUtils.isEmpty(this.getAccessToken());
	}
	@Override
	public String getAccessToken() {
		return PROPERTY_ACCESS_TOKEN.get(this.credentials);
	}
	public KiiUser setAccessToken(String accessToken) {
		PROPERTY_ACCESS_TOKEN.set(this.credentials, accessToken);
		return this;
	}
	@Override
	public String getRefreshToken() {
		return PROPERTY_REFRESH_TOKEN.get(this.credentials);
	}
	public KiiUser setRefreshToken(String refreshToken) {
		PROPERTY_REFRESH_TOKEN.set(this.credentials, refreshToken);
		return this;
	}
	public long getInternalUserID() {
		return PROPERTY_INTERNAL_USER_ID.get(this.json);
	}
	public boolean hasPassword() {
		return PROPERTY_HAS_PASSWORD.get(this.json);
	}
	
	public abstract boolean isPseudo();
	
	public String getUserID() {
		return PROPERTY_USER_ID.get(this.json);
	}
	public KiiUser setUserID(String userID) {
		PROPERTY_USER_ID.set(this.json, userID);
		return this;
	}
	public boolean isDisabled() {
		return PROPERTY_DISABLED.get(this.json);
	}
	public List<KiiSocialAccountInfo> getThirdPartyAccounts() {
		List<KiiSocialAccountInfo> result = new ArrayList<KiiSocialAccountInfo>();
		JsonObject thirdPartyAccounts = PROPERTY_THIRD_PARTY_ACCOUNTS.get(this.json);
		if (thirdPartyAccounts != null) {
			for (Map.Entry<String, JsonElement> thirdPartyAccount : thirdPartyAccounts.entrySet()) {
				result.add(new KiiSocialAccountInfo((JsonObject)thirdPartyAccount.getValue()));
			}
		}
		return result;
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
