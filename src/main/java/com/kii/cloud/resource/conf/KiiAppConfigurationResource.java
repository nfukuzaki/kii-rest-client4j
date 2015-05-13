package com.kii.cloud.resource.conf;

import com.kii.cloud.resource.KiiAppResource;
import com.kii.cloud.resource.KiiRestSubResource;

/**
 * 
 */
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
	public KiiThingTypeConfigurationResource thingTypes(String thingType) {
		return new KiiThingTypeConfigurationResource(this.thingTypes(), thingType);
	}
	
	@Override
	public String getPath() {
		return BASE_PATH;
	}

}
