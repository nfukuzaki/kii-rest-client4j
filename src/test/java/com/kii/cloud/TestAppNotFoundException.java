package com.kii.cloud;

public class TestAppNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public TestAppNotFoundException(String msg) {
		super(msg);
	}
}
