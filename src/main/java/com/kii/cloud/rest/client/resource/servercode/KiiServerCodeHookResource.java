package com.kii.cloud.rest.client.resource.servercode;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.annotation.AdminAPI;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.servercode.KiiServerHookConfiguration;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.kii.cloud.rest.client.util.StringUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

/**
 * Represents the specified version server hook resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/hooks/versions/{SERVER_CODE_VERSION}
 * </ul>
 */
public class KiiServerCodeHookResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/versions";
	public static final MediaType MEDIA_TYPE_HOOKS_DEPLOYMENT_REQUEST = MediaType.parse("application/vnd.kii.HooksDeploymentRequest+json");
	
	private final String version;
	
	public KiiServerCodeHookResource(KiiServerCodeHooksResource parent) {
		super(parent);
		this.version = "current";
	}
	public KiiServerCodeHookResource(KiiServerCodeHooksResource parent, String version) {
		super(parent);
		if (StringUtils.isEmpty(version)) {
			throw new IllegalArgumentException("version is null or empty");
		}
		this.version = version;
	}
	/**
	 * @param config
	 * @throws KiiRestException
	 */
	@AdminAPI
	public void deploy(KiiServerHookConfiguration config) throws KiiRestException {
		if (config == null) {
			throw new IllegalArgumentException("config is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.PUT, headers, MEDIA_TYPE_HOOKS_DEPLOYMENT_REQUEST, config.toJson());
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @return
	 * @throws KiiRestException
	 */
	@AdminAPI
	public boolean exists() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.HEAD, headers);
		try {
			Response response = this.execute(request);
			this.logResponse(request, response);
			return response.isSuccessful();
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @return
	 * @throws KiiRestException
	 */
	@AdminAPI
	public KiiServerHookConfiguration get() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiServerHookConfiguration(responseBody);
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
	@Override
	public String getPath() {
		return BASE_PATH + "/" + this.version;
	}

}
