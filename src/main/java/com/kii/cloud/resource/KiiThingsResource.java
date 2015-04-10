package com.kii.cloud.resource;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.annotation.AnonymousAPI;
import com.kii.cloud.model.KiiThing;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

public class KiiThingsResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/things";
	
	public static final MediaType MEDIA_TYPE_REGISTRATION_REQUEST = MediaType.parse("application/vnd.kii.ThingRegistrationAndAuthorizationRequest+json");
	
	public KiiThingsResource(KiiAppResource parent) {
		super(parent);
	}
	/**
	 * @param thing
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/management/
	 */
	@AnonymousAPI
	public KiiThing register(KiiThing thing) throws KiiRestException {
		Map<String, String> headers = this.newAppHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MEDIA_TYPE_REGISTRATION_REQUEST, thing.getJsonObject());
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			
			String accessToken = KiiThing.PROPERTY_ACCESS_TOKEN.getString(responseBody);
			String refreshToken = KiiThing.PROPERTY_REFRESH_TOKEN.getString(responseBody);
			responseBody.remove(KiiThing.PROPERTY_ACCESS_TOKEN.getName());
			responseBody.remove(KiiThing.PROPERTY_REFRESH_TOKEN.getName());
			KiiThing registeredThing = new KiiThing(responseBody);
			registeredThing.setAccessToken(accessToken);
			registeredThing.setRefreshToken(refreshToken);
			return registeredThing;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
