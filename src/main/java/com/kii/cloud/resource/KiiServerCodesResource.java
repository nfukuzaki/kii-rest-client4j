package com.kii.cloud.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.annotation.AdminAPI;
import com.kii.cloud.model.KiiServerCodeVersion;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.kii.cloud.util.GsonUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

public class KiiServerCodesResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/server-code";
	
	public static final MediaType MEDIA_TYPE_APPLICATION_JAVASCRIPT = MediaType.parse("application/javascript");
	
	public KiiServerCodesResource(KiiAppResource parent) {
		super(parent);
	}
	/**
	 * @param javascript
	 * @return versionID
	 * @throws KiiRestException
	 */
	@AdminAPI
	public String deploy(String javascript) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MEDIA_TYPE_APPLICATION_JAVASCRIPT, javascript);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return GsonUtils.getString(responseBody, "versionID");
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @return versionID
	 * @throws KiiRestException
	 */
	@AdminAPI
	public String getCurrentVersion() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/versions/current"), Method.GET, headers);
		try {
			Response response = this.execute(request);
			return this.parseResponseAsString(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param versionID
	 * @throws KiiRestException
	 */
	@AdminAPI
	public void setCurrentVersion(String versionID) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/versions/current"), Method.PUT, headers, MEDIA_TYPE_TEXT_PLAIN, versionID);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @throws KiiRestException
	 */
	@AdminAPI
	public void resetCurrentVersion() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/versions/current"), Method.DELETE, headers);
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
	public List<KiiServerCodeVersion> list() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/versions"), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			JsonArray array = GsonUtils.getJsonArray(responseBody, "versions");
			List<KiiServerCodeVersion> versions = new ArrayList<KiiServerCodeVersion>();
			for (int i = 0; i < array.size(); i++) {
				versions.add(new KiiServerCodeVersion(array.get(i).getAsJsonObject()));
			}
			return versions;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
