package com.kii.cloud.rest.client.resource.conf;

import com.kii.cloud.rest.client.resource.KiiAppResource;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;

/**
 * Represents the application configuration resource like following URI:
 * 
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/configuration
 * </ul>
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
