package com.kii.cloud.resource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.OkHttpClientFactory;
import com.kii.cloud.util.Path;
import com.kii.cloud.util.StringUtils;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public abstract class KiiRestResource {
	
	public static final MediaType MEDIA_TYPE_APPLICATION_JSON = MediaType.parse("application/json");
	public static final MediaType MEDIA_TYPE_TEXT_PLAIN = MediaType.parse("text/plain");
	
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
	public String getUrl(String path) {
		return this.getUrl(path, null);
	}
	public String getUrl(String path, Map<String, String> params) {
		String url = createPath("", this);
		if (params != null) {
			StringBuilder queryParams = new StringBuilder();
			for (Map.Entry<String, String> param : params.entrySet()) {
				if (queryParams.length() == 0) {
					queryParams.append("?");
				} else {
					queryParams.append("&");
				}
				queryParams.append(param.getKey() + "=" + param.getValue());
			}
			url = url + queryParams.toString();
		}
		return Path.combine(url, path);
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
	protected Response executeHead(Map<String, String> headers) throws KiiRestException {
		return this.executeHead(null, headers);
	}
	protected Response executeHead(String path, Map<String, String> headers) throws KiiRestException {
		String curl = this.toCurl(path, "HEAD", headers);
		Request request = new Request.Builder()
			.url(this.getUrl(path))
			.headers(Headers.of(headers))
			.head()
			.build();
		try {
			return client.newCall(request).execute();
		} catch (IOException e) {
			throw new KiiRestException(curl, e);
		}
	}
	protected JsonObject executeGet(Map<String, String> headers) throws KiiRestException {
		return this.executeGet(headers, null);
	}
	protected JsonObject executeGet(Map<String, String> headers, Map<String, String> params) throws KiiRestException {
		return this.executeGet(null, headers, params);
	}
	protected JsonObject executeGet(String path, Map<String, String> headers) throws KiiRestException {
		return this.executeGet(null, headers, null);
	}
	protected JsonObject executeGet(String path, Map<String, String> headers, Map<String, String> params) throws KiiRestException {
		String curl = this.toCurl(path, "GET", headers);
		Request request = new Request.Builder()
			.url(this.getUrl(path))
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
		return this.executePost(null, headers, contentType, entity);
	}
	protected JsonObject executePost(String path, Map<String, String> headers, MediaType contentType, JsonObject entity) throws KiiRestException {
		return this.executePost(path, headers, contentType, entity.toString());
	}
	protected JsonObject executePost(Map<String, String> headers, MediaType contentType, String entity) throws KiiRestException {
		return this.executePost(null, headers, contentType, entity);
	}
	protected JsonObject executePost(String path, Map<String, String> headers, MediaType contentType, String entity) throws KiiRestException {
		String curl = this.toCurl(path, "POST", headers, null, contentType, entity);
		if (entity == null) {
			entity = "";
		}
		RequestBody requestBody = RequestBody.create(contentType, entity);
		Request request = new Request.Builder()
			.url(this.getUrl(path))
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
		return this.executePut(null, headers, contentType, entity);
	}
	protected JsonObject executePut(String path, Map<String, String> headers, MediaType contentType, JsonObject entity) throws KiiRestException {
		return this.executePut(path, headers, contentType, entity.toString());
	}
	protected JsonObject executePut(Map<String, String> headers, MediaType contentType, String entity) throws KiiRestException {
		return this.executePut(null, headers, contentType, entity);
	}
	protected JsonObject executePut(String path, Map<String, String> headers, MediaType contentType, String entity) throws KiiRestException {
		String curl = this.toCurl(path, "PUT", headers, null, contentType, entity);
		if (entity == null) {
			entity = "";
		}
		RequestBody requestBody = RequestBody.create(contentType, entity);
		Request request = new Request.Builder()
			.url(this.getUrl(path))
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
		return this.executeDelete(null, headers);
	}
	protected JsonObject executeDelete(String path, Map<String, String> headers) throws KiiRestException {
		String curl = this.toCurl(path, "DELETE", headers);
		Request request = new Request.Builder()
			.url(this.getUrl(path))
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
				System.out.println(curl + "  : " + response.code());
				System.out.println(body);
				throw new KiiRestException(curl, response.code(), errorDetail);
			}
			System.out.println(curl + "  : " + response.code());
			System.out.println(body);
			if (StringUtils.isEmpty(body)) {
				return null;
			}
			return (JsonObject)new JsonParser().parse(body);
		} catch (IOException e) {
			throw new KiiRestException(curl, e);
		}
	}
	private String toCurl(String path, String method, Map<String, String> headers) {
		return toCurl(path, method, headers, null);
	}
	private String toCurl(String path, String method, Map<String, String> headers, Map<String, String> params) {
		return toCurl(path, method, headers, params, null, null);
	}
	private String toCurl(String path, String method, Map<String, String> headers, Map<String, String> params, MediaType contentType, String entity) {
		StringBuilder curl = new StringBuilder();
		curl.append("curl -v -X " + method);
		if (contentType != null) {
			curl.append(" -H 'Content-Type:" + contentType.toString() + "'");
		}
		for (Map.Entry<String, String> header : headers.entrySet()) {
			curl.append(" -H '" + header.getKey() + ":" + header.getValue() + "'");
		}
		curl.append(" '" + this.getUrl(path) + "'");
		if (entity != null && !"".equals(entity)) {
			curl.append(" -d '" + entity + "'");
		}
		return curl.toString();
	}
}
