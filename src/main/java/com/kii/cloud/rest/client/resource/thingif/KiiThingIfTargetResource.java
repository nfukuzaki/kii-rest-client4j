package com.kii.cloud.rest.client.resource.thingif;

import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiThingIfResource;

public class KiiThingIfTargetResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/targets/";
	
	private final String targetID;
	
	public KiiThingIfTargetResource(KiiThingIfResource parent, String targetID) {
		super(parent);
		this.targetID = targetID;
	}
	
	public KiiThingIfTargetStatesResource states() {
		return new KiiThingIfTargetStatesResource(this);
	}
	
	@Override
	public String getPath() {
		return BASE_PATH + this.targetID;
	}

}
