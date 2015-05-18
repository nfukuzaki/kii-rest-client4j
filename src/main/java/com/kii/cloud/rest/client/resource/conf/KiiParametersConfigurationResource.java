package com.kii.cloud.rest.client.resource.conf;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.KiiRestException;
import com.kii.cloud.rest.client.annotation.AdminAPI;
import com.kii.cloud.rest.client.model.KiiJsonProperty;
import com.kii.cloud.rest.client.model.conf.KiiAppConfigurationParameter;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.squareup.okhttp.Response;

/**
 * 
 */
public class KiiParametersConfigurationResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/parameters";
	
	public KiiParametersConfigurationResource(KiiAppConfigurationResource parent) {
		super(parent);
	}
	
	@AdminAPI
	public KiiAppConfigurationParameter get() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiAppConfigurationParameter(responseBody);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@AdminAPI
	public void set(KiiJsonProperty<?> property, Object value) throws KiiRestException {
		if (property == null) {
			throw new IllegalArgumentException("property is null");
		}
		this.set(property.getName(), value);
	}
	@AdminAPI
	public void set(String name, Object value) throws KiiRestException {
		if (name == null) {
			throw new IllegalArgumentException("property is null");
		}
		String requestBody = null;
		if (value == null) {
			requestBody = "null";
		} else {
			requestBody = value.toString();
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/" + name), Method.PUT, headers, MEDIA_TYPE_TEXT_PLAIN, requestBody);
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
