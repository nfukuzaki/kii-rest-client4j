package com.kii.cloud.rest.client.exception;

import com.google.gson.JsonObject;
import com.squareup.okhttp.Headers;

public class KiiServiceUnavailableException extends KiiRestException {
	private static final long serialVersionUID = 1L;
	
	public KiiServiceUnavailableException(String message) {
		super(message, 503, null, null);
	}
	public KiiServiceUnavailableException(JsonObject body, Headers httpHeaders) {
		super(null, 503, body, httpHeaders);
	}
	public KiiServiceUnavailableException(String message, JsonObject body, Headers httpHeaders) {
		super(message, 503, body, httpHeaders);
	}

}
