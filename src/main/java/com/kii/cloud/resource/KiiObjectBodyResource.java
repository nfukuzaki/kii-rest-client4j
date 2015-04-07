package com.kii.cloud.resource;

public class KiiObjectBodyResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/body";
	
	public KiiObjectBodyResource(KiiObjectResource parent) {
		super(parent);
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}

}
