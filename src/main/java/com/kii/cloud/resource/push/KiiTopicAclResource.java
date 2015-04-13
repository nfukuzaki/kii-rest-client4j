package com.kii.cloud.resource.push;

import java.util.List;

import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.storage.KiiAcl.Subject;
import com.kii.cloud.model.storage.KiiAcl.TopicAction;
import com.kii.cloud.resource.storage.KiiAclResource;

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
	 * @param subject
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/settings-topic-acl/
	 */
	public void grant(TopicAction action, Subject subject) throws KiiRestException {
		super.grant(action, subject);
	}
	/**
	 * @param action
	 * @param subject
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/settings-topic-acl/
	 */
	public void revok(TopicAction action, Subject subject) throws KiiRestException {
		super.grant(action, subject);
	}
}
