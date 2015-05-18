package com.kii.cloud.rest.client.resource.storage;

import java.util.List;

import com.kii.cloud.rest.client.KiiRestException;
import com.kii.cloud.rest.client.model.storage.KiiGroup;
import com.kii.cloud.rest.client.model.storage.KiiThing;
import com.kii.cloud.rest.client.model.storage.KiiUser;
import com.kii.cloud.rest.client.model.storage.KiiAcl.BucketAction;
import com.kii.cloud.rest.client.model.storage.KiiAcl.Subject;

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
	 * @param user
	 * @throws KiiRestException
	 */
	public void grant(BucketAction action, KiiUser user) throws KiiRestException {
		this.grant(action, Subject.user(user));
	}
	/**
	 * @param action
	 * @param group
	 * @throws KiiRestException
	 */
	public void grant(BucketAction action, KiiGroup group) throws KiiRestException {
		this.grant(action, Subject.group(group));
	}
	/**
	 * @param action
	 * @param thing
	 * @throws KiiRestException
	 */
	public void grant(BucketAction action, KiiThing thing) throws KiiRestException {
		this.grant(action, Subject.thing(thing));
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
	 * @param user
	 * @throws KiiRestException
	 */
	public void revok(BucketAction action, KiiUser user) throws KiiRestException {
		this.revok(action, Subject.user(user));
	}
	/**
	 * @param action
	 * @param group
	 * @throws KiiRestException
	 */
	public void revok(BucketAction action, KiiGroup group) throws KiiRestException {
		this.revok(action, Subject.group(group));
	}
	/**
	 * @param action
	 * @param thing
	 * @throws KiiRestException
	 */
	public void revok(BucketAction action, KiiThing thing) throws KiiRestException {
		this.revok(action, Subject.thing(thing));
	}
	/**
	 * @param action
	 * @param subject
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/buckets/setting-acl/
	 */
	public void revok(BucketAction action, Subject subject) throws KiiRestException {
		super.revok(action, subject);
	}

}
