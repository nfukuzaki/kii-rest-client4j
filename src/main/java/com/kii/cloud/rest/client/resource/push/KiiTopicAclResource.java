package com.kii.cloud.rest.client.resource.push;

import java.util.List;

import com.kii.cloud.rest.client.KiiRestException;
import com.kii.cloud.rest.client.model.storage.KiiGroup;
import com.kii.cloud.rest.client.model.storage.KiiThing;
import com.kii.cloud.rest.client.model.storage.KiiUser;
import com.kii.cloud.rest.client.model.storage.KiiAcl.Subject;
import com.kii.cloud.rest.client.model.storage.KiiAcl.TopicAction;
import com.kii.cloud.rest.client.resource.storage.KiiAclResource;

/**
 * Represents the topic acl resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/topics/{TOPIC_NAME}/acl
 * <li>https://hostname/api/apps/{APP_ID}/users/{USER_IDENTIFIER}/topics/{TOPIC_NAME}/acl
 * <li>https://hostname/api/apps/{APP_ID}/groups/{GROUP_ID}/topics/{TOPIC_NAME}/acl
 * <li>https://hostname/api/apps/{APP_ID}/things/{THING_ID}/topics/{TOPIC_NAME}/acl
 * </ul>
 *
 */
public class KiiTopicAclResource extends KiiAclResource {
	
	public KiiTopicAclResource(KiiTopicResource parent) {
		super(parent);
	}
	/**
	 * @param action
	 * @return
	 * @throws KiiRestException
	 */
	public List<Subject> list(TopicAction action) throws KiiRestException {
		return super.list(action);
	}
	/**
	 * @param action
	 * @param subject
	 * @return
	 * @throws KiiRestException
	 */
	public Subject get(TopicAction action, Subject subject) throws KiiRestException {
		return super.get(action, subject);
	}
	/**
	 * @param action
	 * @param user
	 * @throws KiiRestException
	 */
	public void grant(TopicAction action, KiiUser user) throws KiiRestException {
		this.grant(action, Subject.user(user));
	}
	/**
	 * @param action
	 * @param group
	 * @throws KiiRestException
	 */
	public void grant(TopicAction action, KiiGroup group) throws KiiRestException {
		this.grant(action, Subject.group(group));
	}
	/**
	 * @param action
	 * @param thing
	 * @throws KiiRestException
	 */
	public void grant(TopicAction action, KiiThing thing) throws KiiRestException {
		this.grant(action, Subject.thing(thing));
	}
	/**
	 * @param action
	 * @param subject
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/settings-topic-acl/
	 */
	public void grant(TopicAction action, Subject subject) throws KiiRestException {
		super.grant(action, subject);
	}
	/**
	 * @param action
	 * @param user
	 * @throws KiiRestException
	 */
	public void revok(TopicAction action, KiiUser user) throws KiiRestException {
		this.revok(action, Subject.user(user));
	}
	/**
	 * @param action
	 * @param group
	 * @throws KiiRestException
	 */
	public void revok(TopicAction action, KiiGroup group) throws KiiRestException {
		this.revok(action, Subject.group(group));
	}
	/**
	 * @param action
	 * @param thing
	 * @throws KiiRestException
	 */
	public void revok(TopicAction action, KiiThing thing) throws KiiRestException {
		this.revok(action, Subject.thing(thing));
	}
	/**
	 * @param action
	 * @param subject
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/settings-topic-acl/
	 */
	public void revok(TopicAction action, Subject subject) throws KiiRestException {
		super.revok(action, subject);
	}
}
