package com.kii.cloud.resource.storage;

import java.util.List;

import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.storage.KiiAcl.BucketAction;
import com.kii.cloud.model.storage.KiiAcl.Subject;

/**
 * Represents the bucket acl resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/buckets/{BUCKET_NAME}/acl
 * <li>https://hostname/api/apps/{APP_ID}/users/{USER_IDENTIFIER}/buckets/{BUCKET_NAME}/acl
 * <li>https://hostname/api/apps/{APP_ID}/groups/{GROUP_ID}/buckets/{BUCKET_NAME}/acl
 * <li>https://hostname/api/apps/{APP_ID}/things/{THING_ID}/buckets/{BUCKET_NAME}/acl
 * </ul>
 *
 */
public class KiiBucketAclResource extends KiiAclResource {
	public KiiBucketAclResource(KiiBucketResource parent) {
		super(parent);
	}
	/**
	 * @param action
	 * @return
	 * @throws KiiRestException
	 */
	public List<Subject> list(BucketAction action) throws KiiRestException {
		return super.list(action);
	}
	/**
	 * @param action
	 * @param subject
	 * @return
	 * @throws KiiRestException
	 */
	public Subject get(BucketAction action, Subject subject) throws KiiRestException {
		return super.get(action, subject);
	}
	/**
	 * @param action
	 * @param subject
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/buckets/setting-acl/
	 */
	public void grant(BucketAction action, Subject subject) throws KiiRestException {
		super.grant(action, subject);
	}
	/**
	 * @param action
	 * @param subject
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/buckets/setting-acl/
	 */
	public void revok(BucketAction action, Subject subject) throws KiiRestException {
		super.grant(action, subject);
	}

}
