package com.kii.cloud.rest.client.exception;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.util.GsonUtils;
import com.squareup.okhttp.Headers;

public class KiiRestException extends Exception {
	private static final long serialVersionUID = 1L;
	private final int status;
	private final JsonObject body;
	private final Headers httpHeaders;
	
	public KiiRestException(String message) {
		this(message, null);
	}
	public KiiRestException(String message, Exception cause) {
		super(message, cause);
		this.status = 0;
		this.body = null;
		this.httpHeaders = new Headers.Builder().build();
	}
	public KiiRestException(String message, int status, JsonObject body, Headers httpHeaders) {
		super(message);
		this.status = status;
		this.body = body;
		this.httpHeaders = httpHeaders;
	}
	public int getStatus() {
		return status;
	}
	public JsonObject getBody() {
		return body;
	}
	public Headers getHttpHeaders() {
		return this.httpHeaders;
	}
	public String getErrorCode() {
		if (this.body == null) {
			return null;
		}
		return GsonUtils.getString(this.body, "errorCode");
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getMessage() + System.lineSeparator());
		if (this.status != 0) {
			sb.append(" Status:" + this.status);
		}
		if (this.body != null) {
			sb.append(" Body:" + this.body.toString() + System.lineSeparator());
		} else {
			sb.append(" Body:" + System.lineSeparator());
		}
		if (this.getCause() != null) {
			sb.append(" Cause:" + this.getCause().getMessage() + System.lineSeparator());
		}
		return sb.toString();
	}
}
