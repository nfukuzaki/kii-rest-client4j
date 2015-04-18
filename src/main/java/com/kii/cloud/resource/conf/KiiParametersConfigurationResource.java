package com.kii.cloud.resource.conf;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.annotation.AdminAPI;
import com.kii.cloud.model.conf.KiiAppConfigurationParameter;
import com.kii.cloud.resource.KiiRestRequest;
import com.kii.cloud.resource.KiiRestSubResource;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.squareup.okhttp.Response;

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
	public void set(String name, Object value) throws KiiRestException {
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
