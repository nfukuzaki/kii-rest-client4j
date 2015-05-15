package com.kii.cloud.resource.storage;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.annotation.AdminAPI;
import com.kii.cloud.annotation.AnonymousAPI;
import com.kii.cloud.model.storage.KiiNormalUser;
import com.kii.cloud.model.storage.KiiPseudoUser;
import com.kii.cloud.model.storage.KiiUser;
import com.kii.cloud.resource.KiiRestRequest;
import com.kii.cloud.resource.KiiRestSubResource;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.kii.cloud.resource.push.KiiTopicResource;
import com.kii.cloud.util.GsonUtils;
import com.kii.cloud.util.StringUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

/**
 * Represents the users resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/users/{USER_IDENTIFIER}
 * </ul>
 */
public class KiiUserResource extends KiiRestSubResource {
	
	public static final MediaType MEDIA_TYPE_USER_UPDATE_REQUEST = MediaType.parse("application/vnd.kii.UserUpdateRequest+json");
	public static final MediaType MEDIA_TYPE_RESET_PASSWORD_REQUEST = MediaType.parse("application/vnd.kii.ResetPasswordRequest+json");
	public static final MediaType MEDIA_TYPE_CHANGE_PASSWORD_REQUEST = MediaType.parse("application/vnd.kii.ChangePasswordRequest+json");
	public static final MediaType MEDIA_TYPE_ADDRESS_VERIFICATION_REQUEST = MediaType.parse("application/vnd.kii.AddressVerificationRequest+json");
	
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
		if (user == null) {
			throw new IllegalArgumentException("user is null");
		}
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
		if (user == null) {
			throw new IllegalArgumentException("user is null");
		}
		if (StringUtils.isEmpty(username) && StringUtils.isEmpty(email) && StringUtils.isEmpty(phone)) {
			throw new IllegalArgumentException("need to specify at least username or email or phone");
		}
		if (StringUtils.isEmpty(password)) {
			throw new IllegalArgumentException("password is null or empty");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("password", password);
		if (!StringUtils.isEmpty(username)) {
			KiiUser.PROPERTY_USERNAME.set(requestBody, username);
		}
		if (!StringUtils.isEmpty(email)) {
			KiiUser.PROPERTY_EMAIL_ADDRESS.set(requestBody, email);
		}
		if (!StringUtils.isEmpty(phone)) {
			KiiUser.PROPERTY_PHONE_NUMBER.set(requestBody, phone);
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
		if (oldPassword == null) {
			throw new IllegalArgumentException("oldPassword is null");
		}
		if (newPassword == null) {
			throw new IllegalArgumentException("newPassword is null");
		}
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
		if (notificationMethod == null) {
			throw new IllegalArgumentException("notificationMethod is null");
		}
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
	/**
	 * @return
	 * @throws KiiRestException
	 */
	@AdminAPI
	public String getEmailVerificationCode() throws KiiRestException {
		return getVerificationCode("email-address");
	}
	/**
	 * @return
	 * @throws KiiRestException
	 */
	@AdminAPI
	public String getPhoneVerificationCode() throws KiiRestException {
		return getVerificationCode("phone-number");
	}
	private String getVerificationCode(String addressType) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/%s/verification-code", addressType), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return GsonUtils.getString(responseBody, "verificationCode");
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @throws KiiRestException
	 */
	@AdminAPI
	public String resetPhoneVerification() throws KiiRestException {
		return this.resetVerification("phone-number");
	}
	private String resetVerification(String addressType) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/%s/reset-verification-code", addressType), Method.POST, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return GsonUtils.getString(responseBody, "verificationCode");
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @throws KiiRestException
	 */
	public void resendEmailVerification() throws KiiRestException {
		this.resendVerification("email-address");
	}
	/**
	 * @throws KiiRestException
	 */
	public void resendPhoneVerification() throws KiiRestException {
		this.resendVerification("phone-number");
	}
	private void resendVerification(String addressType) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/%s/resend-verification", addressType), Method.POST, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param verificationCode
	 * @throws KiiRestException
	 */
	public void verifyEmail(String verificationCode) throws KiiRestException {
		this.verifyAddress("email-address", verificationCode);
	}
	/**
	 * @param code
	 * @throws KiiRestException
	 */
	public void verifyPhone(String verificationCode) throws KiiRestException {
		this.verifyAddress("phone-number", verificationCode);
	}
	private void verifyAddress(String addressType, String verificationCode) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("verificationCode", verificationCode);
		KiiRestRequest request = new KiiRestRequest(getUrl("/%s/verify", addressType), Method.POST, headers, MEDIA_TYPE_ADDRESS_VERIFICATION_REQUEST, requestBody);
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
