package com.kii.cloud.resource.storage;

import java.util.List;

import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.storage.KiiAcl.ObjectAction;
import com.kii.cloud.model.storage.KiiAcl.Subject;

/**
 * Represents the object acl resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/buckets/{BUCKET_NAME}/objects/{OBJECT_ID}/acl
 * <li>https://hostname/api/apps/{APP_ID}/users/{USER_IDENTIFIER}/buckets/{BUCKET_NAME}/objects/{OBJECT_ID}/acl
 * <li>https://hostname/api/apps/{APP_ID}/groups/{GROUP_ID}/buckets/{BUCKET_NAME}/objects/{OBJECT_ID}/acl
 * <li>https://hostname/api/apps/{APP_ID}/things/{THING_ID}/buckets/{BUCKET_NAME}/objects/{OBJECT_ID}/acl
 * </ul>
 *
 */
public class KiiObjectAclResource extends KiiAclResource {
	public KiiObjectAclResource(KiiObjectResource parent) {
		super(parent);
	}
	/**
	 * @param action
	 * @return
	 * @throws KiiRestException
	 */
	public List<Subject> list(ObjectAction action) throws KiiRestException {
		return super.list(action);
	}
	/**
	 * @param action
	 * @param subject
	 * @return
	 * @throws KiiRestException
	 */
	public Subject get(ObjectAction action, Subject subject) throws KiiRestException {
		return super.get(action, subject);
	}
	/**
	 * @param action
	 * @param subject
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/scoping/
	 */
	public void grant(ObjectAction action, Subject subject) throws KiiRestException {
		super.grant(action, subject);
	}
	/**
	 * @param action
	 * @param subject
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/scoping/
	 */
	public void revok(ObjectAction action, Subject subject) throws KiiRestException {
		super.revok(action, subject);
	}

}
