package com.kii.cloud.rest.client.resource;

/**
 * This class is base class for all resource classes that have a parent resource.
 */
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
