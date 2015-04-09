package com.kii.cloud.resource;

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
