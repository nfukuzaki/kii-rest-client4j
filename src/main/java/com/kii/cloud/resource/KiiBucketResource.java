package com.kii.cloud.resource;

import com.kii.cloud.model.KiiObject;
import com.kii.cloud.model.KiiQuery;

public class KiiBucketResource extends KiiRestSubResource {
	public static final String BASE_PATH = "/buckets";
	
	private final String name;
	
	public KiiBucketResource(KiiAppResource parent, String name) {
		super(parent);
		this.name = name;
	}
	public KiiBucketResource(KiiUserResource parent, String name) {
		super(parent);
		this.name = name;
	}
	public KiiBucketResource(KiiGroupResource parent, String name) {
		super(parent);
		this.name = name;
	}
	public KiiBucketResource(KiiThingResource parent, String name) {
		super(parent);
		this.name = name;
	}
	public void query(KiiQuery query) {
	}
	public KiiObjectsResource objects() {
		return new KiiObjectsResource(this);
	}
	public KiiObjectResource objects(KiiObject object) {
		return objects(object.getObjectID());
	}
	public KiiObjectResource objects(String objectID) {
		return new KiiObjectResource(this, objectID);
	}
	@Override
	public String getPath() {
		return BASE_PATH + "/" + this.name;
	}
}
