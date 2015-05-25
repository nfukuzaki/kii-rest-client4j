package com.kii.cloud.rest.client.resource.social;

import java.io.IOException;
import java.util.Map;

import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.social.KiiNativeSocialCredentials;
import com.kii.cloud.rest.client.model.social.KiiSocialProvider;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.storage.KiiUserResource;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

public class KiiUserSocialIntegrationResource extends KiiRestSubResource {
	
	public KiiUserSocialIntegrationResource(KiiUserResource parent) {
		super(parent);
	}
	
	/**
	 * Links a Kii Account to a SNS Account
	 * 
	 * @param accessToken
	 * @param openID
	 * @throws KiiRestException
	 */
	
	
	public void link(KiiNativeSocialCredentials socialCredentials) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(
				getUrl("%s/link", socialCredentials.getProvider().getID()),
				Method.POST,
				headers,
				MediaType.parse(socialCredentials.getProvider().getLinkRequestContentType()),
				socialCredentials.getJsonObject());
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
	public void unlink(KiiSocialProvider provider) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("%s/unlink", provider.getID()), Method.POST, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@Override
	public String getPath() {
		return "/";
	}
}
