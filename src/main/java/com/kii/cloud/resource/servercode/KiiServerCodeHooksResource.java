package com.kii.cloud.resource.servercode;

import com.kii.cloud.resource.KiiAppResource;
import com.kii.cloud.resource.KiiRestSubResource;

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
