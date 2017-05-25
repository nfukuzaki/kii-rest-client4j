package com.kii.cloud.rest.client.resource.servercode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.annotation.AdminAPI;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.servercode.KiiServerCodeVersion;
import com.kii.cloud.rest.client.resource.KiiAppResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.kii.cloud.rest.client.util.GsonUtils;
import com.kii.cloud.rest.client.util.IOUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Represents the server code resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/server-code
 * </ul>
 */
public class KiiServerCodesResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/server-code";
	
	public static final MediaType MEDIA_TYPE_APPLICATION_JAVASCRIPT = MediaType.parse("application/javascript");
	public static final MediaType MEDIA_TYPE_METADATA = MediaType.parse("application/vnd.kii.ServerCodeMetadataRequest+json");
	
	public KiiServerCodesResource(KiiAppResource parent) {
		super(parent);
	}
	
	/**
	 * @param file extension must be 'zip' or 'js' and file encoding must be UTF-8.
	 * @return
	 * @throws KiiRestException
	 */
	@AdminAPI
	public String deploy(File file) throws KiiRestException, IOException {
		if (file == null) {
			throw new IllegalArgumentException("file is null");
		}
		String script = IOUtils.readAsString(new FileInputStream(file));
		return this.deploy(script);
	}
	/**
	 * @param javascript
	 * @return versionID
	 * @throws KiiRestException
	 */
	@AdminAPI
	public String deploy(String javascript) throws KiiRestException {
		if (javascript == null) {
			throw new IllegalArgumentException("javascript is null");
		}
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
	 * 
	 * @param file
	 * @param environmentVersion
	 * @return
	 * @throws KiiRestException
	 * @throws IOException
	 */
	@AdminAPI
	public String deploy(File file, String environmentVersion) throws KiiRestException, IOException {
		if (file == null) {
			throw new IllegalArgumentException("file is null");
		}
		String script = IOUtils.readAsString(new FileInputStream(file));
		return this.deploy(script, environmentVersion);
	}
	/**
	 * 
	 * @param javascript
	 * @param environmentVersion
	 * @return
	 * @throws KiiRestException
	 */
	@AdminAPI
	public String deploy(String javascript, String environmentVersion) throws KiiRestException {
		if (javascript == null) {
			throw new IllegalArgumentException("javascript is null");
		}
		JsonObject metadata = new JsonObject();
		metadata.addProperty("environmentVersion", environmentVersion);
		RequestBody multipartBody = new MultipartBuilder()
			.type(MultipartBuilder.FORM)
			.addFormDataPart("metadata", "metadata", RequestBody.create(MEDIA_TYPE_METADATA, metadata.toString()))
			.addFormDataPart("server-code", "server-code", RequestBody.create(MEDIA_TYPE_APPLICATION_JAVASCRIPT, javascript))
			.build();
		
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), headers, multipartBody);
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
		if (versionID == null) {
			throw new IllegalArgumentException("versionID is null");
		}
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
