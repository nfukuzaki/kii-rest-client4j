package com.kii.cloud.rest.client.resource.storage;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.annotation.AnonymousAPI;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.model.storage.KiiThing;
import com.kii.cloud.rest.client.model.uri.KiiThingURI;
import com.kii.cloud.rest.client.resource.KiiAppResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiScopedResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

/**
 * Represents the things resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/things
 * </ul>
 */
public class KiiThingsResource extends KiiRestSubResource implements KiiScopedResource {
	
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
		if (thing == null) {
			throw new IllegalArgumentException("thing is null");
		}
		Map<String, String> headers = this.newAppHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MEDIA_TYPE_REGISTRATION_REQUEST, thing.getJsonObject());
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			
			String accessToken = KiiThing.PROPERTY_ACCESS_TOKEN.get(responseBody);
			String refreshToken = KiiThing.PROPERTY_REFRESH_TOKEN.get(responseBody);
			responseBody.remove(KiiThing.PROPERTY_ACCESS_TOKEN.getName());
			responseBody.remove(KiiThing.PROPERTY_REFRESH_TOKEN.getName());
			KiiThing registeredThing = new KiiThing(responseBody);
			registeredThing.setAccessToken(accessToken);
			registeredThing.setRefreshToken(refreshToken);
			registeredThing.setURI(KiiThingURI.newURI(this.getAppID(), registeredThing.getThingID()));
			return registeredThing;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
	@Override
	public KiiScope getScope() {
		return KiiScope.THING;
	}
}
