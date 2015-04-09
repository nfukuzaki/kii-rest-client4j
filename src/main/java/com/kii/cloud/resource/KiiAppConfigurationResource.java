package com.kii.cloud.resource;

public class KiiAppConfigurationResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/configuration";
	
	public KiiAppConfigurationResource(KiiAppResource parent) {
		super(parent);
	}
	public KiiParametersConfigurationResource parameters() {
		return new KiiParametersConfigurationResource(this);
	}
	public KiiThingTypesConfigurationResource thingTypes() {
		return new KiiThingTypesConfigurationResource(this);
	}
	
	@Override
	public String getPath() {
		return BASE_PATH;
	}

}
