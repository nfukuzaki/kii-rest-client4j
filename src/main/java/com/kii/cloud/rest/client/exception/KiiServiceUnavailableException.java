package com.kii.cloud.rest.client.exception;

import com.google.gson.JsonObject;

public class KiiServiceUnavailableException extends KiiRestException {
	private static final long serialVersionUID = 1L;
	
	public KiiServiceUnavailableException(String message) {
		super(message, 503, null);
	}
	public KiiServiceUnavailableException(JsonObject body) {
		super(null, 503, body);
	}
	public KiiServiceUnavailableException(String message, JsonObject body) {
		super(message, 503, body);
	}

}
