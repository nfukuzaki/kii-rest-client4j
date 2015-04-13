package com.kii.cloud.resource.conf;

import com.kii.cloud.resource.KiiRestSubResource;

public class KiiParametersConfigurationResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/parameters";
	
	public KiiParametersConfigurationResource(KiiAppConfigurationResource parent) {
		super(parent);
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}

}
