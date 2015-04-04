package com.kii.cloud.resource;

public class KiiTopicsResource extends KiiRestSubResource {
	public KiiTopicsResource(KiiAppResource parent) {
		super(parent);
	}
	public KiiTopicsResource(KiiUserResource parent) {
		super(parent);
	}
	public KiiTopicsResource(KiiGroupResource parent) {
		super(parent);
	}
	public KiiTopicsResource(KiiThingResource parent) {
		super(parent);
	}

	@Override
	public String getPath() {
		return null;
	}
}
