package com.kii.cloud.rest.client.exception;

import com.google.gson.JsonObject;

public class KiiInternalServerErrorException extends KiiRestException {
	private static final long serialVersionUID = 1L;
	
	public KiiInternalServerErrorException(String message) {
		super(message, 500, null);
	}
	public KiiInternalServerErrorException(JsonObject body) {
		super(null, 500, body);
	}

}
