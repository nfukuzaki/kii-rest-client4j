package com.kii.cloud.resource;

public class KiiAnalyticsResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/analytics";
	
	public KiiAnalyticsResource(KiiAppResource parent) {
		super(parent);
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
