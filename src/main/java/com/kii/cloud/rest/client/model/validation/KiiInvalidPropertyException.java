package com.kii.cloud.rest.client.model.validation;

public class KiiInvalidPropertyException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public KiiInvalidPropertyException(String message) {
		super(message);
	}
}
