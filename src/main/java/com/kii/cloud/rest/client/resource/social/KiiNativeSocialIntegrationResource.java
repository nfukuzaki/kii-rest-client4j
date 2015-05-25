package com.kii.cloud.rest.client.resource.social;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.social.KiiNativeSocialCredentials;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiUser;
import com.kii.cloud.rest.client.resource.KiiAppResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.kii.cloud.rest.client.util.GsonUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

public class KiiNativeSocialIntegrationResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/integration";
	
	public KiiNativeSocialIntegrationResource(KiiAppResource parent) {
		super(parent);
	}
	/**
	 * Login with SNS Account
	 * 
	 * @param socialCredentials
	 * @return
	 * @throws KiiRestException
	 */
	public KiiUser login(KiiNativeSocialCredentials socialCredentials) throws KiiRestException {
		Map<String, String> headers = this.newAppHeaders();
		KiiRestRequest request = new KiiRestRequest(
				getUrl("/" + socialCredentials.getProvider().getID()),
				Method.POST,
				headers,
				MediaType.parse(socialCredentials.getProvider().getTokenRequestContentType()),
				socialCredentials.getJsonObject());
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
		return BASE_PATH;
	}
}
