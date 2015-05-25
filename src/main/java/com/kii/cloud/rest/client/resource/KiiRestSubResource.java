package com.kii.cloud.rest.client.resource;

import com.kii.cloud.rest.client.logging.KiiLogger;

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
	@Override
	protected KiiLogger getLogger() {
		return this.parent.getLogger();
	}
}
