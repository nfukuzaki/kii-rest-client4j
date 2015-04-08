package com.kii.cloud.resource;

import java.util.List;

import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiAcl.Subject;
import com.kii.cloud.model.KiiAcl.TopicAction;

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
