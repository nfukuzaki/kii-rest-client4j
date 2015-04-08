package com.kii.cloud.resource;

import java.util.List;

import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiAcl.BucketAction;
import com.kii.cloud.model.KiiAcl.Subject;

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
