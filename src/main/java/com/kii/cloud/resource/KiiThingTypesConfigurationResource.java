package com.kii.cloud.resource;

public class KiiThingTypesConfigurationResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/thing-types";
	
	public KiiThingTypesConfigurationResource(KiiAppConfigurationResource parent) {
		super(parent);
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}

}
