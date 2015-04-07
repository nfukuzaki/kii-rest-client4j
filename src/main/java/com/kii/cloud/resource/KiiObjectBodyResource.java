package com.kii.cloud.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.kii.cloud.util.GsonUtils;
import com.kii.cloud.util.IOUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

public class KiiObjectBodyResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/body";
	
	public static final MediaType MEDIA_TYPE_START_OBJECT_BODY_UPDATE_REQUEST = MediaType.parse("application/vnd.kii.StartObjectBodyUploadRequest+json");
	public static final MediaType MEDIA_TYPE_START_OBJECT_BODY_PUBLICATION_REQUEST = MediaType.parse("application/vnd.kii.ObjectBodyPublicationRequest+json");
	
	public KiiObjectBodyResource(KiiObjectResource parent) {
		super(parent);
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
	public boolean exists() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.HEAD, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
			return response.isSuccessful();
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	public void upload(String contentType, InputStream stream) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.PUT, headers, MediaType.parse(contentType), stream);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	public void download(String contentType, OutputStream stream) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			InputStream responseBody = this.parseResponseAsInputStream(request, response);
			try {
				IOUtils.copy(responseBody, stream);
			} catch (IOException e) {
				throw new KiiRestException("", e);
			}
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	public String publish() throws KiiRestException {
		return this.publish(0);
	}
	public String publish(long expiresAt) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject requestBody = new JsonObject();
		if (expiresAt > 0) {
			requestBody.addProperty("expiresAt", expiresAt);
		}
		KiiRestRequest request = new KiiRestRequest(getUrl("/publish"), Method.POST, headers, MEDIA_TYPE_START_OBJECT_BODY_PUBLICATION_REQUEST, requestBody);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return GsonUtils.getString(responseBody, "url");
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
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
}
