package com.kii.cloud.model;

import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kii.cloud.util.StringUtils;

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
		CREATE_NEW_BUCKET
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
	public static class Subject {
		public static final Subject ANONYMOUS_USER = new Subject("UserID", "ANONYMOUS_USER");
		public static final Subject ANY_AUTHENTICATED_USER = new Subject("UserID", "ANY_AUTHENTICATED_USER");
		private final String prefix;
		private final String identifier;
		private Subject(String prefix, String identifier) {
			this.prefix = StringUtils.capitalize(prefix);
			this.identifier = identifier;
		}
		public static Subject user(String userID) {
			return new Subject("UserID", userID);
		}
		public static Subject group(String groupID) {
			return new Subject("GroupID", groupID);
		}
		public static Subject thing(String thingID) {
			return new Subject("ThingID", thingID);
		}
		public static Subject fromJson(JsonObject json) {
			Set<Entry<String, JsonElement>> entrySet = json.entrySet();
			if (entrySet.size() != 1) {
				throw new IllegalArgumentException(json + " is not subject");
			}
			Entry<String, JsonElement> entry = entrySet.iterator().next();
			return new Subject(entry.getKey(), entry.getValue().toString());
		}
		public String toString() {
			return this.prefix + ":" + this.identifier;
		}
	}
}
