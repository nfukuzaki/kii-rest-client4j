package com.kii.cloud.rest.client.resource.social;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.social.KiiSocialProvider;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiUser;
import com.kii.cloud.rest.client.resource.KiiAppResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.kii.cloud.rest.client.util.GsonUtils;
import com.kii.cloud.rest.client.util.StringUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

public class KiiNativeSocialIntegrationResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/integration";
	private static final KiiSocialProvider[] SUPPORTED_SOCIAL_PROVIDERS = {
		KiiSocialProvider.FACEBOOK,
		KiiSocialProvider.GOOGLE,
		KiiSocialProvider.TWITTER,
		KiiSocialProvider.QQ,
		KiiSocialProvider.RENREN
	};
	
	private final KiiSocialProvider provider;
	
	public KiiNativeSocialIntegrationResource(KiiAppResource parent, KiiSocialProvider provider) {
		super(parent);
		if (Arrays.binarySearch(SUPPORTED_SOCIAL_PROVIDERS, provider) < 0) {
			throw new UnsupportedOperationException(provider + " is not supported");
		}
		this.provider = provider;
	}
	/**
	 * Login with SNS Account
	 * 
	 * @param accessToken
	 * @throws KiiRestException
	 */
	public void login(String accessToken) throws KiiRestException {
		this.login(accessToken, null);
	}
	/**
	 * Login with QQ Account
	 * In order to use QQ integration your app needs to be configured with your QQ access token and openID.
	 * 
	 * @param accessToken
	 * @param openID
	 * @throws KiiRestException
	 */
	public KiiUser login(String accessToken, String openID) throws KiiRestException {
		if (StringUtils.isEmpty(accessToken)) {
			throw new IllegalArgumentException("accessToken is null or empty");
		}
		if (this.provider == KiiSocialProvider.QQ && StringUtils.isEmpty(openID)) {
			throw new IllegalArgumentException("QQ integration requires openID");
		}
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("accessToken", accessToken);
		if (this.provider == KiiSocialProvider.QQ) {
			requestBody.addProperty("openID", openID);
		}
		Map<String, String> headers = this.newAppHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/link"), Method.POST, headers, MediaType.parse(this.provider.getTokenRequestContentType()), requestBody);
		
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			// FIXME:User might be pseudo user
			KiiNormalUser user = new KiiNormalUser();
			user.setUserID(GsonUtils.getString(responseBody, "id"));
			user.setAccessToken(GsonUtils.getString(responseBody, "access_token"));
			return user;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	
	@Override
	public String getPath() {
		return BASE_PATH + "/" + this.provider.getID();
	}
}
