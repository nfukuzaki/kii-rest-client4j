package com.kii.cloud.rest.client.exception;

import com.google.gson.JsonObject;
import com.squareup.okhttp.Headers;

public class KiiInternalServerErrorException extends KiiRestException {
	private static final long serialVersionUID = 1L;
	
	public KiiInternalServerErrorException(String message) {
		super(message, 500, null, null);
	}
	public KiiInternalServerErrorException(JsonObject body, Headers httpHeaders) {
		super(null, 500, body, httpHeaders);
	}
	public KiiInternalServerErrorException(String message, JsonObject body, Headers httpHeaders) {
		super(message, 500, body, httpHeaders);
	}

}
