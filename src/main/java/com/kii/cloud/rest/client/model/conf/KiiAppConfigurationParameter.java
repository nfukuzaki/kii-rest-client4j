package com.kii.cloud.rest.client.model.conf;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.model.KiiJsonModel;
import com.kii.cloud.rest.client.model.KiiJsonProperty;
import com.kii.cloud.rest.client.model.social.KiiSocialProvider;
import com.kii.cloud.rest.client.util.GsonUtils;

public class KiiAppConfigurationParameter extends KiiJsonModel {
	
	public static final String PROPERTY_NAME_CONSUMER_KEY = "consumer_key";
	public static final String PROPERTY_NAME_CONSUMER_SECRET = "consumer_secret";
	
	public static final KiiJsonProperty<Boolean> PROPERTY_IS_MASTER_APP = new KiiJsonProperty<Boolean>(Boolean.class, "isMasterApp");
	public static final KiiJsonProperty<Boolean> PROPERTY_IS_THING_TYPE_CONFIGURATION_REQUIRED = new KiiJsonProperty<Boolean>(Boolean.class, "isThingTypeConfigurationRequired");
	public static final KiiJsonProperty<Boolean> PROPERTY_PHONE_NUMBER_VERIFICATION_REQUIRED = new KiiJsonProperty<Boolean>(Boolean.class, "phoneNumberVerificationRequired");
	public static final KiiJsonProperty<Boolean> PROPERTY_EMAIL_ADDRESS_VERIFICATION_REQUIRED = new KiiJsonProperty<Boolean>(Boolean.class, "emailAddressVerificationRequired");
	public static final KiiJsonProperty<Boolean> PROPERTY_EXPOSE_FULL_USER_DATA_TO_OTHERS = new KiiJsonProperty<Boolean>(Boolean.class, "exposeFullUserDataToOthers");
	public static final KiiJsonProperty<Boolean> PROPERTY_RESERVED_FIELDS_VALIDATION = new KiiJsonProperty<Boolean>(Boolean.class, "reservedFieldsValidation");
	public static final KiiJsonProperty<Boolean> PROPERTY_REFRESH_TOKEN_ENABLED = new KiiJsonProperty<Boolean>(Boolean.class, "refreshTokenEnabled");
	public static final KiiJsonProperty<Boolean> PROPERTY_SEND_REFERRAL_FOR_LONG_APNS = new KiiJsonProperty<Boolean>(Boolean.class, "sendReferralForLongAPNS");
	
	public static final KiiJsonProperty<String> PROPERTY_GCM_COLLAPSE_KEY_DEFAULT_BEHAVIOR = new KiiJsonProperty<String>(String.class, "gcmCollapseKeyDefaultBehavior");
	public static final KiiJsonProperty<String> PROPERTY_PASSWORD_RESET_METHOD = new KiiJsonProperty<String>(String.class, "passwordResetMethod");
	public static final KiiJsonProperty<String> PROPERTY_EMAIL_ADDRESS = new KiiJsonProperty<String>(String.class, "emailAddress");
	public static final KiiJsonProperty<String> PROPERTY_SMS_FROM = new KiiJsonProperty<String>(String.class, "smsFrom");
	public static final KiiJsonProperty<String> PROPERTY_VERIFICATION_SMS_TEMPLATE = new KiiJsonProperty<String>(String.class, "verificationSmsTemplate");
	public static final KiiJsonProperty<String> PROPERTY_PASSWORD_RESET_OK_REDIRECTION_URL = new KiiJsonProperty<String>(String.class, "passwordResetOKRedirectionURL");
	public static final KiiJsonProperty<String> PROPERTY_PASSWORD_RESET_FAILURE_REDIRECTION_URL = new KiiJsonProperty<String>(String.class, "passwordResetFailureRedirectionURL");
	public static final KiiJsonProperty<String> PROPERTY_EMAIL_VERIFICATION_OK_REDIRECTION_URL = new KiiJsonProperty<String>(String.class, "emailVerificationOKRedirectionURL");
	public static final KiiJsonProperty<String> PROPERTY_EMAIL_VERIFICATION_FAILURE_REDIRECTION_URL = new KiiJsonProperty<String>(String.class, "emailVerificationFailureRedirectionURL");
	public static final KiiJsonProperty<String> PROPERTY_SERVER_CODE_VERSION_ID = new KiiJsonProperty<String>(String.class, "serverCodeVersionID");
	public static final KiiJsonProperty<String> PROPERTY_OAUTH2_INTERNAL_LOGIN_FORM_URL = new KiiJsonProperty<String>(String.class, "oauth2InternalLoginFormURL");
	
	public static final KiiJsonProperty<Long> PROPERTY_DEFAULT_TOKEN_EXPIRATION_SECONDS = new KiiJsonProperty<Long>(Long.class, "defaultTokenExpirationSeconds");
	public static final KiiJsonProperty<Long> PROPERTY_MAX_TOKEN_EXPIRATION_SECONDS = new KiiJsonProperty<Long>(Long.class, "maxTokenExpirationSeconds");
	public static final KiiJsonProperty<Long> PROPERTY_PASSWORD_RESET_TIMEOUT_SECONDS = new KiiJsonProperty<Long>(Long.class, "passwordResetTimeoutSeconds");


	public KiiAppConfigurationParameter(JsonObject json) {
		super(json);
	}
	public String getConsumerKey(KiiSocialProvider provider) {
		return GsonUtils.getString(this.json, provider.getSocialAuthPrefix() + PROPERTY_NAME_CONSUMER_KEY);
	}
	public String getConsumerSecret(KiiSocialProvider provider) {
		return GsonUtils.getString(this.json, provider.getSocialAuthPrefix() + PROPERTY_NAME_CONSUMER_SECRET);
	}
	
	public boolean has(KiiJsonProperty<?> property) {
		return this.json.has(property.getName());
	}
	public Boolean isMasterApp() {
		return PROPERTY_IS_MASTER_APP.get(this.json);
	}
	public Boolean isThingTypeConfigurationRequired() {
		return PROPERTY_IS_THING_TYPE_CONFIGURATION_REQUIRED.get(this.json);
	}
	public Boolean phoneNumberVerificationRequired() {
		return PROPERTY_PHONE_NUMBER_VERIFICATION_REQUIRED.get(this.json);
	}
	public Boolean emailAddressVerificationRequired() {
		return PROPERTY_EMAIL_ADDRESS_VERIFICATION_REQUIRED.get(this.json);
	}
	public Boolean exposeFullUserDataToOthers() {
		return PROPERTY_EXPOSE_FULL_USER_DATA_TO_OTHERS.get(this.json);
	}
	public Boolean reservedFieldsValidation() {
		return PROPERTY_RESERVED_FIELDS_VALIDATION.get(this.json);
	}
	public Boolean refreshTokenEnabled() {
		return PROPERTY_REFRESH_TOKEN_ENABLED.get(this.json);
	}
	public Boolean sendReferralForLongAPNS() {
		return PROPERTY_SEND_REFERRAL_FOR_LONG_APNS.get(this.json);
	}
	
	public String gcmCollapseKeyDefaultBehavior() {
		return PROPERTY_GCM_COLLAPSE_KEY_DEFAULT_BEHAVIOR.get(this.json);
	}
	public String passwordResetMethod() {
		return PROPERTY_PASSWORD_RESET_METHOD.get(this.json);
	}
	public String emailAddress() {
		return PROPERTY_EMAIL_ADDRESS.get(this.json);
	}
	public String smsFrom() {
		return PROPERTY_SMS_FROM.get(this.json);
	}
	public String verificationSmsTemplate() {
		return PROPERTY_VERIFICATION_SMS_TEMPLATE.get(this.json);
	}
	public String passwordResetOKRedirectionURL() {
		return PROPERTY_PASSWORD_RESET_OK_REDIRECTION_URL.get(this.json);
	}
	public String passwordResetFailureRedirectionURL() {
		return PROPERTY_PASSWORD_RESET_FAILURE_REDIRECTION_URL.get(this.json);
	}
	public String emailVerificationOKRedirectionURL() {
		return PROPERTY_EMAIL_VERIFICATION_OK_REDIRECTION_URL.get(this.json);
	}
	public String emailVerificationFailureRedirectionURL() {
		return PROPERTY_EMAIL_VERIFICATION_FAILURE_REDIRECTION_URL.get(this.json);
	}
	public String serverCodeVersionID() {
		return PROPERTY_SERVER_CODE_VERSION_ID.get(this.json);
	}
	public String oauth2InternalLoginFormURL() {
		return PROPERTY_OAUTH2_INTERNAL_LOGIN_FORM_URL.get(this.json);
	}
	
	public Long defaultTokenExpirationSeconds() {
		return PROPERTY_DEFAULT_TOKEN_EXPIRATION_SECONDS.get(this.json);
	}
	public Long maxTokenExpirationSeconds() {
		return PROPERTY_MAX_TOKEN_EXPIRATION_SECONDS.get(this.json);
	}
	public Long passwordResetTimeoutSeconds() {
		return PROPERTY_PASSWORD_RESET_TIMEOUT_SECONDS.get(this.json);
	}
}
