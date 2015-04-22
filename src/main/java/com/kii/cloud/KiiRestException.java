package com.kii.cloud;

import com.google.gson.JsonObject;
import com.kii.cloud.util.GsonUtils;

public class KiiRestException extends Exception {
	private static final long serialVersionUID = 1L;
	private final int status;
	private final JsonObject body;
	
	public KiiRestException(String message) {
		this(message, null);
	}
	public KiiRestException(String message, Exception cause) {
		super(message, cause);
		this.status = 0;
		this.body = null;
	}
	public KiiRestException(int status, JsonObject body) {
		this(null, status, body);
	}
	public KiiRestException(String message, int status, JsonObject body) {
		super(message);
		this.status = status;
		this.body = body;
	}
	public int getStatus() {
		return status;
	}
	public JsonObject getBody() {
		return body;
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
			sb.append("Status:" + this.status);
		}
		if (this.body != null) {
			sb.append("Body:" + this.body.toString() + System.lineSeparator());
		} else {
			sb.append("Body:" + System.lineSeparator());
		}
		if (this.getCause() != null) {
			sb.append("Cause:" + this.getCause().getMessage() + System.lineSeparator());
		}
		return sb.toString();
	}
}
