package com.kii.cloud.rest.client;

public class TestAppNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public TestAppNotFoundException(String msg) {
		super(msg);
	}
}
