package com.kii.cloud.rest.client.resource.storage;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.annotation.AdminAPI;
import com.kii.cloud.rest.client.annotation.AnonymousAPI;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.model.push.KiiTopic;
import com.kii.cloud.rest.client.model.storage.KiiAccountType;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiPseudoUser;
import com.kii.cloud.rest.client.model.storage.KiiUser;
import com.kii.cloud.rest.client.model.uri.KiiUserURI;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiScopedResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.kii.cloud.rest.client.resource.push.KiiTopicResource;
import com.kii.cloud.rest.client.resource.push.KiiTopicsResource;
import com.kii.cloud.rest.client.resource.social.KiiUserFacebookIntegrationResource;
import com.kii.cloud.rest.client.resource.social.KiiUserGoogleIntegrationResource;
import com.kii.cloud.rest.client.resource.social.KiiUserQqIntegrationResource;
import com.kii.cloud.rest.client.resource.social.KiiUserRenrenIntegrationResource;
import com.kii.cloud.rest.client.resource.social.KiiUserTwitterIntegrationResource;
import com.kii.cloud.rest.client.util.GsonUtils;
import com.kii.cloud.rest.client.util.StringUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

/**
 * Represents the users resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/users/{USER_IDENTIFIER}
 * </ul>
 */
public class KiiUserResource extends KiiRestSubResource implements KiiScopedResource {
	
	public static final MediaType MEDIA_TYPE_USER_UPDATE_REQUEST = MediaType.parse("application/vnd.kii.UserUpdateRequest+json");
	public static final MediaType MEDIA_TYPE_RESET_PASSWORD_REQUEST = MediaType.parse("application/vnd.kii.ResetPasswordRequest+json");
	public static final MediaType MEDIA_TYPE_CHANGE_PASSWORD_REQUEST = MediaType.parse("application/vnd.kii.ChangePasswordRequest+json");
	public static final MediaType MEDIA_TYPE_ADDRESS_VERIFICATION_REQUEST = MediaType.parse("application/vnd.kii.AddressVerificationRequest+json");
	
	public enum NotificationMethod {
		EMAIL,
		SMS;
	}
	private final KiiAccountType accountType;
	private final String identifier;
	public KiiUserResource(KiiUsersResource parent, String identifier) {
		super(parent);
		if (StringUtils.isEmpty(identifier)) {
			throw new IllegalArgumentException("identifier is null or empty");
		}
		String[] array = identifier.split(":");
		if (array.length > 1) {
			this.accountType = KiiAccountType.fromString(array[0]);
			this.identifier = array[1];
		} else {
			this.accountType = KiiAccountType.parseIdentifier(identifier);
			this.identifier = identifier;
		}
	}
	public KiiUserResource(KiiUsersResource parent, KiiAccountType accountType, String identifier) {
		super(parent);
		this.accountType = accountType;
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
			// Cannot access has_password field if 'Expose Full User Data To Others'=false by other users.
			if (KiiUser.PROPERTY_HAS_PASSWORD.get(responseBody) == null || KiiUser.PROPERTY_HAS_PASSWORD.get(responseBody)) {
				return new KiiNormalUser(responseBody).setURI(this.getURI());
			} else {
				return new KiiPseudoUser(responseBody).setURI(this.getURI());
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
			user.setURI(this.getURI());
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
			user.setURI(this.getURI());
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
	 * @see http://docs.kii.com/en/guides/cloudsdk/rest/managing-users/password-change/
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
	 * @see http://docs.kii.com/en/guides/cloudsdk/rest/managing-users/password-reset/
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
	public KiiTopicsResource topics() {
		return new KiiTopicsResource(this);
	}
	public KiiTopicResource topics(KiiTopic topic) {
		if (topic == null) {
			throw new IllegalArgumentException("topic is null"); 
		}
		if (topic.getURI() != null && topic.getURI().getScope() != KiiScope.USER) {
			throw new IllegalArgumentException("topic scope is not User");
		}
		return new KiiTopicResource(this.topics(), topic.getTopicID());
	}
	public KiiTopicResource topics(String topicID) {
		return new KiiTopicResource(this.topics(), topicID);
	}
	public KiiUserFacebookIntegrationResource facebook() {
		return new KiiUserFacebookIntegrationResource(this);
	}
	public KiiUserGoogleIntegrationResource google() {
		return new KiiUserGoogleIntegrationResource(this);
	}
	public KiiUserTwitterIntegrationResource twitter() {
		return new KiiUserTwitterIntegrationResource(this);
	}
	public KiiUserRenrenIntegrationResource renren() {
		return new KiiUserRenrenIntegrationResource(this);
	}
	public KiiUserQqIntegrationResource qq() {
		return new KiiUserQqIntegrationResource(this);
	}
	@Override
	public String getPath() {
		return "/" + this.accountType.getFullyQualifiedIdentifier(this.identifier);
	}
	@Override
	public KiiScope getScope() {
		return ((KiiUsersResource)this.parent).getScope();
	}
	public String getScopeIdentifier() {
		return this.accountType.getFullyQualifiedIdentifier(this.identifier);
	}
	public KiiUserURI getURI() {
		return KiiUserURI.newURI(this.getAppID(), this.identifier);
	}
}
