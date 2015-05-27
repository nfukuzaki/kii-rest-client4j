package com.kii.cloud.rest.client.model.storage;

import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.util.StringUtils;

public class KiiAcl {
	
	public static Action parseAction(String actionString) {
		if (StringUtils.isEmpty(actionString)) {
			throw new IllegalArgumentException("actionString is null.");
		}
		for (ScopeAction action : ScopeAction.values()) {
			if (actionString.equals(action.toString())) {
				return action;
			}
		}
		for (BucketAction action : BucketAction.values()) {
			if (actionString.equals(action.toString())) {
				return action;
			}
		}
		for (ObjectAction action : ObjectAction.values()) {
			if (actionString.equals(action.toString())) {
				return action;
			}
		}
		for (TopicAction action : TopicAction.values()) {
			if (actionString.equals(action.toString())) {
				return action;
			}
		}
		throw new IllegalArgumentException(actionString + " is not Action.");
	}
	
	public interface Action {
	}
	public enum ScopeAction implements Action {
		CREATE_NEW_BUCKET,
		CREATE_NEW_TOPIC
	}
	public enum BucketAction implements Action {
		QUERY_OBJECTS_IN_BUCKET,
		CREATE_OBJECTS_IN_BUCKET,
		DROP_BUCKET_WITH_ALL_CONTENT
	}
	public enum ObjectAction implements Action {
		READ_EXISTING_OBJECT,
		WRITE_EXISTING_OBJECT
	}
	public enum TopicAction implements Action {
		SUBSCRIBE_TO_TOPIC,
		SEND_MESSAGE_TO_TOPIC
	}
	public static class Subject implements Comparable<Subject> {
		public static final String PREFIX_USER_ID = "UserID";
		public static final String PREFIX_GROUP_ID = "GroupID";
		public static final String PREFIX_THING_ID = "ThingID";
		public static final String ANONYMOUS_USER_ID = "ANONYMOUS_USER";
		public static final String ANY_AUTHENTICATED_USER_ID = "ANY_AUTHENTICATED_USER";
		public static final Subject ANONYMOUS_USER = new Subject("UserID", ANONYMOUS_USER_ID);
		public static final Subject ANY_AUTHENTICATED_USER = new Subject("UserID", ANY_AUTHENTICATED_USER_ID);
		private final String prefix;
		private final String identifier;
		private Subject(String prefix, String identifier) {
			this.prefix = StringUtils.capitalize(prefix);
			this.identifier = identifier;
		}
		public static Subject user(KiiUser user) {
			return user(user.getUserID());
		}
		public static Subject user(String userID) {
			if (ANONYMOUS_USER_ID.equals(userID)) {
				return ANONYMOUS_USER;
			}
			if (ANY_AUTHENTICATED_USER_ID.equals(userID)) {
				return ANY_AUTHENTICATED_USER;
			}
			return new Subject(PREFIX_USER_ID, userID);
		}
		public static Subject group(KiiGroup group) {
			return group(group.getGroupID());
		}
		public static Subject group(String groupID) {
			return new Subject(PREFIX_GROUP_ID, groupID);
		}
		public static Subject thing(KiiThing thing) {
			return thing(thing.getThingID());
		}
		public static Subject thing(String thingID) {
			return new Subject(PREFIX_THING_ID, thingID);
		}
		public static Subject fromJson(JsonObject json) {
			Set<Entry<String, JsonElement>> entrySet = json.entrySet();
			if (entrySet.size() != 1) {
				throw new IllegalArgumentException(json + " is not subject");
			}
			Entry<String, JsonElement> entry = entrySet.iterator().next();
			if (PREFIX_USER_ID.equals(StringUtils.capitalize(entry.getKey()))) {
				if (ANONYMOUS_USER_ID.equals(entry.getValue().toString())) {
					return ANONYMOUS_USER;
				}
				if (ANY_AUTHENTICATED_USER_ID.equals(entry.getValue().toString())) {
					return ANY_AUTHENTICATED_USER;
				}
			}
			return new Subject(entry.getKey(), entry.getValue().getAsString());
		}
		public boolean isUser() {
			return this.prefix.equals(PREFIX_USER_ID);
		}
		public boolean isGroup() {
			return this.prefix.equals(PREFIX_GROUP_ID);
		}
		public boolean isThing() {
			return this.prefix.equals(PREFIX_THING_ID);
		}
		public String getID() {
			return this.identifier;
		}
		public String toString() {
			return this.prefix + ":" + this.identifier;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((identifier == null) ? 0 : identifier.hashCode());
			result = prime * result
					+ ((prefix == null) ? 0 : prefix.hashCode());
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
			Subject other = (Subject) obj;
			if (identifier == null) {
				if (other.identifier != null)
					return false;
			} else if (!identifier.equals(other.identifier))
				return false;
			if (prefix == null) {
				if (other.prefix != null)
					return false;
			} else if (!prefix.equals(other.prefix))
				return false;
			return true;
		}
		@Override
		public int compareTo(Subject o) {
			int c = this.prefix.compareTo(o.prefix);
			if (c != 0) {
				return c;
			}
			c = this.identifier.length() - o.identifier.length();
			if (c != 0) {
				return c;
			}
			return this.identifier.compareTo(o.identifier);
		}
	}
}
