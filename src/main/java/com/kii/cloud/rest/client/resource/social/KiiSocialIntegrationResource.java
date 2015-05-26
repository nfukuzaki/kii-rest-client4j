package com.kii.cloud.rest.client.resource.social;

import com.kii.cloud.rest.client.resource.KiiAppResource;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;

public class KiiSocialIntegrationResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/integration";
	
	public KiiSocialIntegrationResource(KiiAppResource parent) {
		super(parent);
	}
	public KiiFacebookIntegrationResource facebook() {
		return new KiiFacebookIntegrationResource(this);
	}
	public KiiGoogleIntegrationResource google() {
		return new KiiGoogleIntegrationResource(this);
	}
	public KiiTwitterIntegrationResource twitter() {
		return new KiiTwitterIntegrationResource(this);
	}
	public KiiQqIntegrationResource qq() {
		return new KiiQqIntegrationResource(this);
	}
	public KiiRenrenIntegrationResource renren() {
		return new KiiRenrenIntegrationResource(this);
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
