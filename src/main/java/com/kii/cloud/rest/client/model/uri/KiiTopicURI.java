package com.kii.cloud.rest.client.model.uri;

import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.util.StringUtils;

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
	
	public static KiiTopicURI newAppScopeURI(String appID, String topicID) {
		return new KiiTopicURI(new KiiAppURI(appID), topicID);
	}
	public static KiiTopicURI newUserScopeURI(String appID, String userIdentifier, String topicID) {
		return new KiiTopicURI(new KiiUserURI(new KiiAppURI(appID), userIdentifier), topicID);
	}
	public static KiiTopicURI newGroupScopeURI(String appID, String groupID, String topicID) {
		return new KiiTopicURI(new KiiGroupURI(new KiiAppURI(appID), groupID), topicID);
	}
	public static KiiTopicURI newThingScopeURI(String appID, String thingIdentifier, String topicID) {
		return new KiiTopicURI(new KiiThingURI(new KiiAppURI(appID), thingIdentifier), topicID);
	}

	public static KiiTopicURI parse(String str) {
		if (StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException("str is null or empty");
		}
		if (!str.startsWith(SCHEME)) {
			throw new IllegalArgumentException("URI should start with 'kiicloud://'");
		}
		String[] segments = str.replace(SCHEME, "").split("/");
		if (segments.length == 3) {
			if (StringUtils.equals(SEGMENT_TOPICS, segments[1])) {
				return new KiiTopicURI(new KiiAppURI(segments[0]), segments[2]);
			}
		} else if (segments.length == 5) {
			if (StringUtils.equals(SEGMENT_TOPICS, segments[3])) {
				if (StringUtils.equals(SEGMENT_USERS, segments[1])) {
					return new KiiTopicURI(new KiiUserURI(new KiiAppURI(segments[0]), segments[2]), segments[4]);
				} else if (StringUtils.equals(SEGMENT_GROUPS, segments[1])) {
					return new KiiTopicURI(new KiiGroupURI(new KiiAppURI(segments[0]), segments[2]), segments[4]);
				} else if (StringUtils.equals(SEGMENT_THINGS, segments[1])) {
					return new KiiTopicURI(new KiiThingURI(new KiiAppURI(segments[0]), segments[2]), segments[4]);
				}
			}
		}
		throw new IllegalArgumentException("invalid URI : " + str);
	}
	
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((topicID == null) ? 0 : topicID.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KiiTopicURI other = (KiiTopicURI) obj;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (topicID == null) {
			if (other.topicID != null)
				return false;
		} else if (!topicID.equals(other.topicID))
			return false;
		return true;
	}
}
