package com.kii.cloud.resource;

public class KiiEncryptedBucketResource extends KiiBucketResource {
	public KiiEncryptedBucketResource(KiiAppResource parent, String name) {
		super(parent, name);
	}
	public KiiEncryptedBucketResource(KiiUserResource parent, String name) {
		super(parent, name);
	}
	public KiiEncryptedBucketResource(KiiGroupResource parent, String name) {
		super(parent, name);
	}
	public KiiEncryptedBucketResource(KiiThingResource parent, String name) {
		super(parent, name);
	}
	@Override
	protected String getBucketType() {
		return "crypto";
	}
	@Override
	public String getPath() {
		return BASE_PATH + "/CRYPTO:" + this.name;
	}

}
