package com.kii.cloud.resource;

public abstract class KiiRestSubResource extends KiiRestResource {
	protected final KiiRestResource parent;
	public KiiRestSubResource(KiiRestResource parent) {
		this.parent = parent;
	}
	@Override
	protected KiiRestResource getParent() {
		return this.parent;
	}
}
