package com.kii.cloud.resource;

import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiGroup;

public class KiiGroupsResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/groups";
	
	public KiiGroupsResource(KiiAppResource parent) {
		super(parent);
	}
	public void register(KiiGroup group) throws KiiRestException {
		
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
