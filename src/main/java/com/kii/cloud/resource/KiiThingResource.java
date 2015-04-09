package com.kii.cloud.resource;

import com.kii.cloud.model.KiiThing;

public class KiiThingResource extends KiiRestSubResource {
	
	private final String thingID;
	private final String vendorThingID;
	
	public KiiThingResource(KiiThingsResource parent, String identifier) {
		super(parent);
		if (KiiThing.THING_ID_PATTERN.matcher(identifier).matches()) {
			this.thingID = identifier;
			this.vendorThingID = null;
		} else {
			this.thingID = null;
			this.vendorThingID = identifier;
		}
	}
	public KiiScopeAclResource acl() {
		return new KiiScopeAclResource(this);
	}
	
	@Override
	public String getPath() {
		if (this.thingID != null) {
			return "/" + this.thingID;
		}
		return "/VENDOR_THING_ID:" + this.vendorThingID;
	}
}
