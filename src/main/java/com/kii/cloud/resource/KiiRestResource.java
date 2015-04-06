package com.kii.cloud.resource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.OkHttpClientFactory;
import com.kii.cloud.util.Path;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public abstract class KiiRestResource {
	
	public static final MediaType MEDIA_TYPE_APPLICATION_JSON = MediaType.parse("application/json");
	
	protected static final OkHttpClient client = OkHttpClientFactory.newInstance();
	
	protected abstract KiiRestResource getParent();
	public abstract String getPath();
	
	protected KiiAppResource getRootResource() {
		KiiRestResource parent = this;
		while (parent.getParent() != null) {
			parent = parent.getParent();
		}
		return (KiiAppResource)parent;
	}
	public String getUrl() {
		return createPath("", this);
	}
	private String createPath(String path, KiiRestResource resource) {
		if (resource.getParent() != null) {
			path = createPath(path, resource.getParent());
		}
		return Path.combine(path, resource.getPath());
	}
	/**
	 * Create http headers that is set X-Kii-AppID and X-Kii-AppKey.
	 * 
	 * @return
	 */
	protected Map<String, String> newAppHeaders() {
		Map<String, String> headers = new HashMap<String, String>();
		this.setAppHeader(headers);
		return headers;
	}
	/**
	 * Create http headers that is set X-Kii-AppID and X-Kii-AppKey and Authorization.
	 * 
	 * @return
	 */
	protected Map<String, String> newAuthorizedHeaders() {
		Map<String, String> headers = new HashMap<String, String>();
		this.setAppHeader(headers);
		this.setAuthorizationHeader(headers);
		return headers;
	}
	protected void setAppHeader(Map<String, String> headers) {
		if (getParent() != null) {
			getParent().setAppHeader(headers);
		}
	}
	protected void setAuthorizationHeader(Map<String, String> headers) {
		if (getParent() != null) {
			getParent().setAuthorizationHeader(headers);
		}
	}
	protected JsonObject executeGet(Map<String, String> headers) throws KiiRestException {
		String curl = this.toCurl("GET", headers);
		Request request = new Request.Builder()
			.url(this.getUrl())
			.headers(Headers.of(headers))
			.get()
			.build();
		try {
			Response response = client.newCall(request).execute();
			return parseResponse(curl, response);
		} catch (IOException e) {
			throw new KiiRestException(curl, e);
		}
	}
	protected JsonObject executePost(Map<String, String> headers, MediaType contentType, JsonObject entity) throws KiiRestException {
		return this.executePost(headers, contentType, entity.toString());
	}
	protected JsonObject executePost(Map<String, String> headers, MediaType contentType, String entity) throws KiiRestException {
		String curl = this.toCurl("POST", headers, contentType, entity);
		if (entity == null) {
			entity = "";
		}
		RequestBody requestBody = RequestBody.create(contentType, entity);
		Request request = new Request.Builder()
			.url(this.getUrl())
			.headers(Headers.of(headers))
			.post(requestBody)
			.build();
		OkHttpClient postClient = client.clone();
		postClient.setRetryOnConnectionFailure(false);
		try {
			Response response = client.newCall(request).execute();
			return parseResponse(curl, response);
		} catch (IOException e) {
			throw new KiiRestException(curl, e);
		}
	}
	protected JsonObject executePut(Map<String, String> headers, MediaType contentType, JsonObject entity) throws KiiRestException {
		return this.executePut(headers, contentType, entity.toString());
	}
	protected JsonObject executePut(Map<String, String> headers, MediaType contentType, String entity) throws KiiRestException {
		String curl = this.toCurl("PUT", headers, contentType, entity);
		if (entity == null) {
			entity = "";
		}
		RequestBody requestBody = RequestBody.create(contentType, entity);
		Request request = new Request.Builder()
			.url(this.getUrl())
			.headers(Headers.of(headers))
			.put(requestBody)
			.build();
		OkHttpClient postClient = client.clone();
		postClient.setRetryOnConnectionFailure(false);
		try {
			Response response = client.newCall(request).execute();
			return parseResponse(curl, response);
		} catch (IOException e) {
			throw new KiiRestException(curl, e);
		}
	}
	protected JsonObject executeDelete(Map<String, String> headers) throws KiiRestException {
		String curl = this.toCurl("DELETE", headers);
		Request request = new Request.Builder()
			.url(this.getUrl())
			.headers(Headers.of(headers))
			.delete()
			.build();
		try {
			Response response = client.newCall(request).execute();
			return parseResponse(curl, response);
		} catch (IOException e) {
			throw new KiiRestException(curl, e);
		}
	}
	private JsonObject parseResponse(String curl, Response response) throws KiiRestException {
		try {
			String body = response.body().string();
			if (!response.isSuccessful()) {
				JsonObject errorDetail = null;
				try {
					errorDetail = (JsonObject)new JsonParser().parse(body);
				} catch (Exception ignore) {
				}
				System.out.println(curl);
				System.out.println(body);
				throw new KiiRestException(curl, response.code(), errorDetail);
			}
			System.out.println(curl);
			System.out.println(body);
			return (JsonObject)new JsonParser().parse(body);
		} catch (IOException e) {
			throw new KiiRestException(curl, e);
		}
	}
	private String toCurl(String method, Map<String, String> headers) {
		return toCurl(method, headers, null, null);
	}
	private String toCurl(String method, Map<String, String> headers, MediaType contentType, String entity) {
		StringBuilder curl = new StringBuilder();
		curl.append("curl -v -X " + method);
		if (contentType != null) {
			curl.append(" -H 'Content-Type:" + contentType.toString() + "'");
		}
		for (Map.Entry<String, String> header : headers.entrySet()) {
			curl.append(" -H '" + header.getKey() + ":" + header.getValue() + "'");
		}
		curl.append(" '" + this.getUrl() + "'");
		if (entity != null && !"".equals(entity)) {
			curl.append(" -d '" + entity + "'");
		}
		return curl.toString();
	}
}
