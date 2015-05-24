package com.kii.cloud.rest.client.resource.social;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.social.KiiSocialProvider;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.storage.KiiUserResource;
import com.kii.cloud.rest.client.util.StringUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

public class KiiUserSocialIntegrationResource extends KiiRestSubResource {
	
	private static final KiiSocialProvider[] SUPPORTED_SOCIAL_PROVIDERS = {
		KiiSocialProvider.FACEBOOK,
		KiiSocialProvider.GOOGLE,
		KiiSocialProvider.TWITTER,
		KiiSocialProvider.QQ,
		KiiSocialProvider.RENREN
	};
	private final KiiSocialProvider provider;
	
	public KiiUserSocialIntegrationResource(KiiUserResource parent, KiiSocialProvider provider) {
		super(parent);
		if (Arrays.binarySearch(SUPPORTED_SOCIAL_PROVIDERS, provider) < 0) {
			throw new UnsupportedOperationException(provider + " is not supported");
		}
		this.provider = provider;
	}
	
	/**
	 * Links a Kii Account to a SNS Account
	 * 
	 * @param accessToken
	 * @throws KiiRestException
	 */
	public void link(String accessToken) throws KiiRestException {
		this.link(accessToken, null);
	}
	/**
	 * Links a Kii Account to a QQ Account
	 * In order to use QQ integration your app needs to be configured with your QQ access token and openID.
	 * 
	 * @param accessToken
	 * @param openID
	 * @throws KiiRestException
	 */
	public void link(String accessToken, String openID) throws KiiRestException {
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
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/link"), Method.POST, headers, MediaType.parse(this.provider.getLinkRequestContentType()), requestBody);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * Unlinking a Kii Account from a SNS Account
	 * 
	 * @throws KiiRestException
	 */
	public void unlink() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/unlink"), Method.POST, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@Override
	public String getPath() {
		return "/" + this.provider.getID();
	}
}
