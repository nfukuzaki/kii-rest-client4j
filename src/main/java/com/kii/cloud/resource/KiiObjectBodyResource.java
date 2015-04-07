package com.kii.cloud.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
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
		Response response = this.executeHead(headers);
		return response.code() == 200;
	}
	public void upload(String contentType, InputStream stream) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		this.executePut(headers, MediaType.parse(contentType), stream);
	}
	public void download(String contentType, OutputStream stream) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		InputStream response = this.executeGetAsInputStream(headers);
		try {
			IOUtils.copy(response, stream);
		} catch (IOException e) {
			throw new KiiRestException("", e);
		}
	}
	public String publish() throws KiiRestException {
		return this.publish(0);
	}
	public String publish(long expiresAt) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject request = new JsonObject();
		if (expiresAt > 0) {
			request.addProperty("expiresAt", expiresAt);
		}
		JsonObject response = this.executePost("/publish", headers, MEDIA_TYPE_START_OBJECT_BODY_PUBLICATION_REQUEST, request);
		return GsonUtils.getString(response, "url");
	}
	public void delete() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		this.executeDelete(headers);
	}
}
