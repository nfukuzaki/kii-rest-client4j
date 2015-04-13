package com.kii.cloud.resource;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.annotation.AdminAPI;
import com.kii.cloud.model.KiiServerHookConfiguration;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

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
		this.version = version;
	}
	/**
	 * @param config
	 * @throws KiiRestException
	 */
	@AdminAPI
	public void deploy(KiiServerHookConfiguration config) throws KiiRestException {
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
