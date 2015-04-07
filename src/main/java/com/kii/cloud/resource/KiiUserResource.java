package com.kii.cloud.resource;

import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiNormalUser;
import com.kii.cloud.model.KiiPseudoUser;
import com.kii.cloud.model.KiiUser;
import com.kii.cloud.util.StringUtils;
import com.squareup.okhttp.MediaType;

public class KiiUserResource extends KiiRestSubResource {
	
	public static final MediaType MEDIA_TYPE_USER_UPDATE_REQUEST = MediaType.parse("application/vnd.kii.UserUpdateRequest+json");
	public static final MediaType MEDIA_TYPE_RESET_PASSWORD_REQUEST = MediaType.parse("application/vnd.kii.ResetPasswordRequest+json");
	public static final MediaType MEDIA_TYPE_CHANGE_PASSWORD_REQUEST = MediaType.parse("application/vnd.kii.ChangePasswordRequest+json");
	
	public enum NotificationMethod {
		EMAIL,
		SMS;
	}
	
	private final String identifier;
	public KiiUserResource(KiiUsersResource parent, String identifier) {
		super(parent);
		this.identifier = identifier;
	}
	public KiiUser get() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject response = this.executeGet(headers);
		if (KiiUser.PROPERTY_HAS_PASSWORD.getBoolean(response)) {
			return new KiiNormalUser(response);
		} else {
			return new KiiPseudoUser(response);
		}
	}
	public void update(KiiUser user) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		this.executePost(headers, MEDIA_TYPE_USER_UPDATE_REQUEST, user.toJsonString());
	}
	// FIXME:インターフェースがちょっと微妙
	public void updateToNormal(KiiPseudoUser user, String username, String email, String phone, String password) throws KiiRestException {
		if (StringUtils.isEmpty(username) && StringUtils.isEmpty(email) && StringUtils.isEmpty(phone)) {
			throw new IllegalArgumentException("");
		}
		if (StringUtils.isEmpty(password)) {
			throw new IllegalArgumentException("");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject request = new JsonObject();
		request.addProperty("password", password);
		if (!StringUtils.isEmpty(username)) {
			request.addProperty(KiiUser.PROPERTY_USERNAME.getName(), username);
		}
		if (!StringUtils.isEmpty(email)) {
			request.addProperty(KiiUser.PROPERTY_EMAIL_ADDRESS.getName(), email);
		}
		if (!StringUtils.isEmpty(phone)) {
			request.addProperty(KiiUser.PROPERTY_PHONE_NUMBER.getName(), phone);
		}
		this.executePost(headers, MEDIA_TYPE_USER_UPDATE_REQUEST, request);
	}
	public void delete() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		this.executeDelete(headers);
	}
	public void changePassword(String oldPassword, String newPassword) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject request = new JsonObject();
		request.addProperty("oldPassword", oldPassword);
		request.addProperty("newPassword", newPassword);
		this.executePut("/password", headers, MEDIA_TYPE_CHANGE_PASSWORD_REQUEST, request);
	}
	public void resetPassword(NotificationMethod notificationMethod) throws KiiRestException {
		Map<String, String> headers = this.newAppHeaders();
		JsonObject request = new JsonObject();
		request.addProperty("notificationMethod", notificationMethod.name());
		this.executePost("/password/request-reset", headers, MEDIA_TYPE_RESET_PASSWORD_REQUEST, request);
	}
	
	public KiiBucketResource buckets(String name) {
		return new KiiBucketResource(this, name);
	}
	public KiiEncryptedBucketResource encryptedBuckets(String name) {
		return new KiiEncryptedBucketResource(this, name);
	}
	public KiiTopicsResource topics() {
		return new KiiTopicsResource(this);
	}
	public KiiTopicResource topics(String name) {
		return new KiiTopicResource(this.topics(), name);
	}
	@Override
	public String getPath() {
		return "/" + KiiUser.getAccountType(this.identifier) + this.identifier;
	}
}
