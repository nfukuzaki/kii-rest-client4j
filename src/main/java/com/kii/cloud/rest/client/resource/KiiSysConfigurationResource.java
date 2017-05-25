package com.kii.cloud.rest.client.resource;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.logging.KiiLogger;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.squareup.okhttp.Response;

/**
 * Represents the system configuration resource like following URI:
 * 
 * <ul>
 * <li>https://hostname/api/system-configuration
 * </ul>
 */
public class KiiSysConfigurationResource extends KiiRestResource {
	
	public static final String BASE_PATH = "/system-configuration";
	
	private final String appID;
	private final String appKey;
	private final String endpoint;
	private final KiiLogger logger;
	
	public KiiSysConfigurationResource(String appID, String appKey, String endpoint, KiiLogger logger) {
		this.appID = appID;
		this.appKey = appKey;
		this.endpoint = endpoint;
		this.logger = logger;
	}
	
	public JsonObject getServercodeEnvironmentVersions() throws KiiRestException {
		Map<String, String> headers = newAppHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/servercode-environment-versions"), Method.GET, headers);
		try {
			Response response = this.execute(request);
			return this.parseResponseAsJsonObject(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	
	@Override
	public String getPath() {
		return this.endpoint + BASE_PATH;
	}

	@Override
	protected KiiRestResource getParent() {
		// This class is root resource
		return null;
	}
	@Override
	protected KiiLogger getLogger() {
		return this.logger;
	}
	@Override
	protected void setAppHeader(Map<String, String> headers) {
		headers.put("X-Kii-AppID", this.appID);
		headers.put("X-Kii-AppKey", this.appKey);
	}

}
