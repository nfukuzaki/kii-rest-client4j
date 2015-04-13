package com.kii.cloud.resource;

public class KiiServerCodeHooksResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/hooks";
	
	public KiiServerCodeHooksResource(KiiAppResource parent) {
		super(parent);
	}
	public KiiServerCodeHookExecutionsResource executions() {
		return new KiiServerCodeHookExecutionsResource(this);
	}
	
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
