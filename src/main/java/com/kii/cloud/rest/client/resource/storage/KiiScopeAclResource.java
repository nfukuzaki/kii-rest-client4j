package com.kii.cloud.rest.client.resource.storage;

import java.util.List;

import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.model.storage.KiiGroup;
import com.kii.cloud.rest.client.model.storage.KiiThing;
import com.kii.cloud.rest.client.model.storage.KiiUser;
import com.kii.cloud.rest.client.model.storage.KiiAcl.ScopeAction;
import com.kii.cloud.rest.client.model.storage.KiiAcl.Subject;
import com.kii.cloud.rest.client.resource.KiiAppResource;
import com.kii.cloud.rest.client.resource.KiiScopedResource;

/**
 * Represents the scope acl resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/acl
 * <li>https://hostname/api/apps/{APP_ID}/users/{USER_IDENTIFIER}/acl
 * <li>https://hostname/api/apps/{APP_ID}/groups/{GROUP_ID}/acl
 * <li>https://hostname/api/apps/{APP_ID}/things/{THING_ID}/acl
 * </ul>
 *
 */
public class KiiScopeAclResource extends KiiAclResource implements KiiScopedResource {
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
	@Override
	public KiiScope getScope() {
		if (this.parent instanceof KiiAppResource) {
			return KiiScope.APP;
		} else if (this.parent instanceof KiiGroupResource) {
			return KiiScope.GROUP;
		} else if (this.parent instanceof KiiUserResource) {
			return KiiScope.USER;
		} else if (this.parent instanceof KiiThingResource) {
			return KiiScope.THING;
		} else {
			throw new AssertionError("detected the unexpected scope.");
		}
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
	 * @param user
	 * @throws KiiRestException
	 */
	public void grant(ScopeAction action, KiiUser user) throws KiiRestException {
		this.grant(action, Subject.user(user));
	}
	/**
	 * @param action
	 * @param group
	 * @throws KiiRestException
	 */
	public void grant(ScopeAction action, KiiGroup group) throws KiiRestException {
		this.grant(action, Subject.group(group));
	}
	/**
	 * @param action
	 * @param thing
	 * @throws KiiRestException
	 */
	public void grant(ScopeAction action, KiiThing thing) throws KiiRestException {
		this.grant(action, Subject.thing(thing));
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
	 * @param user
	 * @throws KiiRestException
	 */
	public void revok(ScopeAction action, KiiUser user) throws KiiRestException {
		this.revok(action, Subject.user(user));
	}
	/**
	 * @param action
	 * @param group
	 * @throws KiiRestException
	 */
	public void revok(ScopeAction action, KiiGroup group) throws KiiRestException {
		this.revok(action, Subject.group(group));
	}
	/**
	 * @param action
	 * @param thing
	 * @throws KiiRestException
	 */
	public void revok(ScopeAction action, KiiThing thing) throws KiiRestException {
		this.revok(action, Subject.thing(thing));
	}
	/**
	 * @param action
	 * @param subject
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/buckets/setting-acl/
	 */
	public void revok(ScopeAction action, Subject subject) throws KiiRestException {
		super.revok(action, subject);
	}

}
