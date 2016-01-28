package com.kii.cloud.rest.client.resource.thingif;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.squareup.okhttp.Response;

public class KiiThingIfTargetStatesResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/states";
	
	public KiiThingIfTargetStatesResource(KiiThingIfTargetResource parent) {
		super(parent);
	}
	
	/**
	 * @return
	 * @throws KiiRestException
	 */
	public JsonObject get() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			return this.parseResponseAsJsonObject(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param state
	 * @throws KiiRestException
	 */
	public void save(JsonObject state) throws KiiRestException {
		if (state == null) {
			throw new IllegalArgumentException("state is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.PUT, headers, MEDIA_TYPE_APPLICATION_JSON, state);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}

	@Override
	public String getPath() {
		return BASE_PATH;
	}

}
