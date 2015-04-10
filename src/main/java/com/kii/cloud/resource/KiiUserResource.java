package com.kii.cloud.resource;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.annotation.AnonymousAPI;
import com.kii.cloud.model.KiiNormalUser;
import com.kii.cloud.model.KiiPseudoUser;
import com.kii.cloud.model.KiiUser;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.kii.cloud.util.StringUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

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
	
	public KiiScopeAclResource acl() {
		return new KiiScopeAclResource(this);
	}
	
	/**
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-users/retrieving-other-user-s-data/
	 */
	public KiiUser get() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			if (KiiUser.PROPERTY_HAS_PASSWORD.get(responseBody)) {
				return new KiiNormalUser(responseBody);
			} else {
				return new KiiPseudoUser(responseBody);
			}
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param user
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-users/user-attributes/
	 */
	public void update(KiiUser user) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MEDIA_TYPE_USER_UPDATE_REQUEST, user.toJsonString());
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param user
	 * @param username
	 * @param email
	 * @param phone
	 * @param password
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-users/pseudo-users/
	 */
	public void updateToNormal(KiiPseudoUser user, String username, String email, String phone, String password) throws KiiRestException {
		// FIXME:インターフェースがちょっと微妙
		if (StringUtils.isEmpty(username) && StringUtils.isEmpty(email) && StringUtils.isEmpty(phone)) {
			throw new IllegalArgumentException("");
		}
		if (StringUtils.isEmpty(password)) {
			throw new IllegalArgumentException("");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("password", password);
		if (!StringUtils.isEmpty(username)) {
			requestBody.addProperty(KiiUser.PROPERTY_USERNAME.getName(), username);
		}
		if (!StringUtils.isEmpty(email)) {
			requestBody.addProperty(KiiUser.PROPERTY_EMAIL_ADDRESS.getName(), email);
		}
		if (!StringUtils.isEmpty(phone)) {
			requestBody.addProperty(KiiUser.PROPERTY_PHONE_NUMBER.getName(), phone);
		}
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MEDIA_TYPE_USER_UPDATE_REQUEST, requestBody);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-users/deleting-users/
	 */
	public void delete() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.DELETE, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param oldPassword
	 * @param newPassword
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-users/passwords/
	 */
	public void changePassword(String oldPassword, String newPassword) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("oldPassword", oldPassword);
		requestBody.addProperty("newPassword", newPassword);
		KiiRestRequest request = new KiiRestRequest(getUrl("/password"), Method.PUT, headers, MEDIA_TYPE_CHANGE_PASSWORD_REQUEST, requestBody);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param notificationMethod
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-users/passwords/
	 */
	@AnonymousAPI
	public void resetPassword(NotificationMethod notificationMethod) throws KiiRestException {
		Map<String, String> headers = this.newAppHeaders();
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("notificationMethod", notificationMethod.name());
		KiiRestRequest request = new KiiRestRequest(getUrl("/password/request-reset"), Method.POST, headers, MEDIA_TYPE_RESET_PASSWORD_REQUEST, requestBody);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	public KiiBucketResource buckets(String name) {
		return new KiiBucketResource(this, name);
	}
	public KiiEncryptedBucketResource encryptedBuckets(String name) {
		return new KiiEncryptedBucketResource(this, name);
	}
	public KiiTopicResource topics(String name) {
		return new KiiTopicResource(this, name);
	}
	@Override
	public String getPath() {
		return "/" + KiiUser.getAccountType(this.identifier) + this.identifier;
	}
}
