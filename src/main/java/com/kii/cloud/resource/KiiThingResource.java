package com.kii.cloud.resource;

public class KiiThingResource extends KiiRestSubResource {
	public KiiThingResource(KiiAppResource parent) {
		super(parent);
	}
	
	public KiiScopeAclResource acl() {
		return new KiiScopeAclResource(this);
	}
	
	@Override
	public String getPath() {
		return null;
	}
}
