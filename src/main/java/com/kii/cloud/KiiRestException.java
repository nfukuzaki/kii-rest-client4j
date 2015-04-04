package com.kii.cloud;

import com.google.gson.JsonObject;

public class KiiRestException extends Exception {
	private static final long serialVersionUID = 1L;
	private final int status;
	private final JsonObject body;
	
	public KiiRestException(String message, Exception cause) {
		super(message, cause);
		this.status = 0;
		this.body = new JsonObject();
	}
	public KiiRestException(int status, JsonObject body) {
		this(null, status, body);
	}
	public KiiRestException(String message, int status, JsonObject body) {
		super(message + "   -- status:" + status);
		this.status = status;
		this.body = body;
	}
	public int getStatus() {
		return status;
	}
	public JsonObject getBody() {
		return body;
	}
}
