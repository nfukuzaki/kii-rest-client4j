package com.kii.cloud.resource;

import com.kii.cloud.KiiRestException;
import com.kii.cloud.annotation.AdminAPI;
import com.kii.cloud.model.KiiServerHookConfiguration;

public class KiiServerCodeHooksResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/hooks";
	
	public KiiServerCodeHooksResource(KiiAppResource parent) {
		super(parent);
	}
	@AdminAPI
	public String deploy(KiiServerHookConfiguration config) throws KiiRestException {
		return null;
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}

}
