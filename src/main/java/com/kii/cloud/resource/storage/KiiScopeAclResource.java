package com.kii.cloud.resource.storage;

import java.util.List;

import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.storage.KiiAcl.ScopeAction;
import com.kii.cloud.model.storage.KiiAcl.Subject;
import com.kii.cloud.resource.KiiAppResource;

public class KiiScopeAclResource extends KiiAclResource {
	public KiiScopeAclResource(KiiAppResource parent) {
		super(parent);
	}
	public KiiScopeAclResource(KiiGroupResource parent) {
		super(parent);
	}
	public KiiScopeAclResource(KiiUserResource parent) {
		super(parent);
	}
	public KiiScopeAclResource(KiiThingResource parent) {
		super(parent);
	}
	/**
	 * @param action
	 * @return
	 * @throws KiiRestException
	 */
	public List<Subject> list(ScopeAction action) throws KiiRestException {
		return super.list(action);
	}
	/**
	 * @param action
	 * @param subject
	 * @return
	 * @throws KiiRestException
	 */
	public Subject get(ScopeAction action, Subject subject) throws KiiRestException {
		return super.get(action, subject);
	}
	/**
	 * @param action
	 * @param subject
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/buckets/setting-acl/
	 */
	public void grant(ScopeAction action, Subject subject) throws KiiRestException {
		super.grant(action, subject);
	}
	/**
	 * @param action
	 * @param subject
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/buckets/setting-acl/
	 */
	public void revok(ScopeAction action, Subject subject) throws KiiRestException {
		super.grant(action, subject);
	}

}
