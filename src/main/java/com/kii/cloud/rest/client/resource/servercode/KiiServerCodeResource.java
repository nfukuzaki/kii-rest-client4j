package com.kii.cloud.rest.client.resource.servercode;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.annotation.AdminAPI;
import com.kii.cloud.rest.client.annotation.AnonymousAPI;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.kii.cloud.rest.client.util.StringUtils;
import com.squareup.okhttp.Response;

/**
 * Represents the specified version server code resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/server-code/versions/{SERVER_CODE_VERSION}
 * </ul>
 */
public class KiiServerCodeResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/versions";
	
	private final String version;
	public KiiServerCodeResource(KiiServerCodesResource parent) {
		super(parent);
		this.version = "current";
	}
	public KiiServerCodeResource(KiiServerCodesResource parent, String version) {
		super(parent);
		if (StringUtils.isEmpty(version)) {
			throw new IllegalArgumentException("version is null or empty");
		}
		this.version = version;
	}
	
	/**
	 * @return
	 * @throws KiiRestException
	 */
	@AdminAPI
	public String get() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			return this.parseResponseAsString(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @throws KiiRestException
	 */
	@AdminAPI
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
	 * @param endpoint
	 * @return
	 * @throws KiiRestException
	 */
	public JsonObject execute(String endpoint) throws KiiRestException {
		return this.execute(endpoint, null);
	}
	/**
	 * @param endpoint
	 * @param args
	 * @see http://documentation.kii.com/en/guides/serverextension/executing_servercode/manual_execution/
	 */
	@AnonymousAPI
	public JsonObject execute(String endpoint, JsonObject args) throws KiiRestException {
		if (endpoint == null) {
			throw new IllegalArgumentException("endpoint is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		if (args == null) {
			args = new JsonObject();
		}
		KiiRestRequest request = new KiiRestRequest(getUrl("/" + endpoint), Method.POST, headers, MEDIA_TYPE_APPLICATION_JSON, args);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			String stepCount = response.header("X-Step-count");
			if (!StringUtils.isEmpty(stepCount)) {
				responseBody.addProperty("x_step_count", stepCount);
			}
			return responseBody;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@Override
	public String getPath() {
		return BASE_PATH + "/" + this.version;
	}
}
