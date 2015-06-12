package com.kii.cloud.rest.client.model.uri;

import com.kii.cloud.rest.client.model.KiiScope;

/**
 * Represents the XXXX URI like following URIs:
 * 
 * <ul>
 * <li>kiicloud://{AppID}/topics/{TopicID}
 * <li>kiicloud://{AppID}/users/{UserID}/topics/{TopicID}
 * <li>kiicloud://{AppID}/group/{GroupID}/topics/{TopicID}
 * <li>kiicloud://{AppID}/things/{ThingsID}/topics/{TopicID}
 * </ul>
 */
public class KiiTopicURI extends KiiURI {
	
	private final KiiURI parent;
	private final String topicID;
	
	protected KiiTopicURI(KiiAppURI parent, String topicID) {
		this.parent = parent;
		this.topicID = topicID;
	}
	protected KiiTopicURI(KiiUserURI parent, String topicID) {
		this.parent = parent;
		this.topicID = topicID;
	}
	protected KiiTopicURI(KiiGroupURI parent, String topicID) {
		this.parent = parent;
		this.topicID = topicID;
	}
	protected KiiTopicURI(KiiThingURI parent, String topicID) {
		this.parent = parent;
		this.topicID = topicID;
	}
	public String getScopeID() {
		if (this.parent instanceof KiiUserURI) {
			KiiUserURI userURI = (KiiUserURI)this.parent;
			return userURI.getAccountType().getFullyQualifiedIdentifier(userURI.getIdentifier());
		} else if (this.parent instanceof KiiGroupURI) {
			return ((KiiGroupURI)this.parent).getGroupID();
		} else if (this.parent instanceof KiiThingURI) {
			KiiThingURI thingURI = (KiiThingURI)this.parent;
			return thingURI.getIdentifierType().getFullyQualifiedIdentifier(thingURI.getIdentifier());
		}
		return null;
	}
	public String getTopicID() {
		return this.topicID;
	}
	@Override
	public KiiScope getScope() {
		return this.parent.getScope();
	}
	@Override
	public String toUriString() {
		return this.parent.toUriString() + "/" + SEGMENT_TOPICS + "/" + this.topicID;
	}
}
