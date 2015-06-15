package com.kii.cloud.rest.client.resource.storage;

import com.kii.cloud.rest.client.resource.KiiAppResource;

/**
 * Represents the bucket resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/buckets/CRYPTO:{BUCKET_ID}
 * <li>https://hostname/api/apps/{APP_ID}/users/{USER_IDENTIFIER}/buckets/CRYPTO:{BUCKET_ID}
 * <li>https://hostname/api/apps/{APP_ID}/groups/{GROUP_ID}/buckets/CRYPTO:{BUCKET_ID}
 * <li>https://hostname/api/apps/{APP_ID}/things/{THING_ID}/buckets/CRYPTO:{BUCKET_ID}
 * </ul>
 *
 */
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
		return BASE_PATH + "/CRYPTO:" + this.bucketID;
	}

}
