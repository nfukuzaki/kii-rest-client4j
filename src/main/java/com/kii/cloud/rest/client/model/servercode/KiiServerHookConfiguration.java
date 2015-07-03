package com.kii.cloud.rest.client.model.servercode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.util.GsonUtils;

public class KiiServerHookConfiguration {
	
	private String versionID;
	private Boolean current;
	private final List<TriggerConfiguration> triggerConfigurations = new ArrayList<TriggerConfiguration>();
	private final List<SchedulerConfiguration> schedulerConfigurations = new ArrayList<SchedulerConfiguration>();
	
	public KiiServerHookConfiguration() {
	}
	public KiiServerHookConfiguration(JsonObject json) {
		for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
			if ("kiicloud://scheduler".equals(entry.getKey())) {
				for (Map.Entry<String, JsonElement> schedulerEntry : ((JsonObject)entry.getValue()).entrySet()) {
					schedulerConfigurations.add(new SchedulerConfiguration(schedulerEntry.getKey(), (JsonObject)schedulerEntry.getValue()));
				}
			} else {
				triggerConfigurations.add(new TriggerConfiguration(Path.parse(entry.getKey()), (JsonArray)entry.getValue()));
			}
		}
	}
	public KiiServerHookConfiguration addTriggerConfiguration(TriggerConfiguration triggerConfiguration) {
		this.triggerConfigurations.add(triggerConfiguration);
		return this;
	}
	public KiiServerHookConfiguration addSchedulerConfiguration(SchedulerConfiguration schedulerConfiguration) {
		this.schedulerConfigurations.add(schedulerConfiguration);
		return this;
	}
	@Override
	public String toString() {
		JsonObject json = new JsonObject();
		
		if (this.versionID == null) {
			json.add("versionID", JsonNull.INSTANCE);
		} else {
			json.addProperty("versionID", this.versionID);
		}
		if (this.current == null) {
			json.add("current", JsonNull.INSTANCE);
		} else {
			json.addProperty("current", this.current);
		}
		json.add("hook", this.toJson());
		return json.toString();
	}
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		for (TriggerConfiguration triggerConfiguration : this.triggerConfigurations) {
			json.add(triggerConfiguration.path.toString(), triggerConfiguration.toJson());
		}
		if (this.schedulerConfigurations.size() > 0) {
			JsonObject scheduler = new JsonObject();
			for (SchedulerConfiguration schedulerConfiguration : this.schedulerConfigurations) {
				scheduler.add(schedulerConfiguration.jobName, schedulerConfiguration.toJson());
			}
			json.add("kiicloud://scheduler", scheduler);
		}
		return json;
	}
	public String getVersionID() {
		return versionID;
	}
	public void setVersionID(String versionID) {
		this.versionID = versionID;
	}
	public Boolean getCurrent() {
		return current;
	}
	public void setCurrent(Boolean current) {
		this.current = current;
	}
	public enum When {
		DATA_OBJECT_CREATED,
		DATA_OBJECT_DELETED,
		DATA_OBJECT_UPDATED,
		DATA_OBJECT_BODY_DELETED,
		DATA_OBJECT_BODY_UPDATED,
		DATA_OBJECT_BODY_PUBLISHED,
		USER_CREATED,
		USER_EMAIL_VERIFIED,
		USER_PHONE_VERIFIED,
		USER_PASSWORD_RESET_COMPLETED,
		USER_PASSWORD_CHANGED,
		USER_DELETED,
		USER_UPDATED,
		GROUP_CREATED,
		GROUP_DELETED,
		GROUP_MEMBERS_ADDED,
		GROUP_MEMBERS_REMOVED,
		INSTALLATION_CREATED,
		INSTALLATION_DELETED
	}
	public static class Path {
		protected static final String PREFIX = "kiicloud://";
		private final String scope;
		private final String bucketID;
		private Path(String scope) {
			this.scope = scope;
			this.bucketID = null;
		}
		private Path(String scope, String bucketID) {
			this.scope = scope;
			this.bucketID = bucketID;
		}
		@Override
		public String toString() {
			if (this.bucketID == null) {
				return PREFIX + this.scope;
			} else {
				if (this.scope == null) {
					return PREFIX + "buckets/" + this.bucketID;
				} else {
					return PREFIX + this.scope + "/*/buckets/" + this.bucketID;
				}
			}
		}
		public static Path Installation() {
			return new Path("installations");
		}
		public static Path app(String bucketID) {
			return new Path(null, bucketID);
		}
		public static Path group() {
			return new Path("groups");
		}
		public static Path group(String bucketID) {
			return new Path("groups", bucketID);
		}
		public static Path user() {
			return new Path("users");
		}
		public static Path user(String bucketID) {
			return new Path("users", bucketID);
		}
		public static Path thing() {
			return new Path("things");
		}
		public static Path thing(String bucketID) {
			return new Path("things", bucketID);
		}
		public static Path parse(String path) {
			String p = path.replace(PREFIX, "");
			String[] pp = p.split("/");
			if (pp.length == 1) {
				return new Path(pp[0]);
			} else {
				if ("buckets".equals(pp[0])) {
					return new Path(null, pp[pp.length - 1]);
				} else {
					return new Path(pp[0], pp[pp.length - 1]);
				}
			}
		}
	}
	public static class TriggerConfiguration {
		private final Path path;
		private final List<TriggerAction> triggerActions = new ArrayList<TriggerAction>();
		public TriggerConfiguration(Path path) {
			this.path = path;
		}
		public TriggerConfiguration(Path path, JsonArray array) {
			this.path = path;
			for (int i = 0; i < array.size(); i++) {
				this.triggerActions.add(new TriggerAction(array.get(i).getAsJsonObject()));
			}
		}
		public TriggerConfiguration addTriggerAction(TriggerAction triggerAction) {
			this.triggerActions.add(triggerAction);
			return this;
		}
		public JsonArray toJson() {
			JsonArray array = new JsonArray();
			for (TriggerAction triggerAction : this.triggerActions) {
				array.add(triggerAction.toJson());
			}
			return array;
		}
	}
	public static class TriggerAction {
		private final When when;
		private final String endpoint;
		public TriggerAction(When when, String endpoint) {
			this.when = when;
			this.endpoint = endpoint;
		}
		public TriggerAction(JsonObject json) {
			this.when = Enum.valueOf(When.class, GsonUtils.getString(json, "when"));
			this.endpoint = GsonUtils.getString(json, "endpoint");
		}
		public JsonObject toJson() {
			JsonObject json = new JsonObject();
			json.addProperty("when", this.when.toString());
			json.addProperty("what", "EXECUTE_SERVER_CODE");
			json.addProperty("endpoint", this.endpoint);
			return json;
		}
	}
	public static class SchedulerConfiguration {
		private final String jobName;
		private final String cronExpression;
		private final String endpoint;
		private final JsonObject parameters;
		public SchedulerConfiguration(String jobName, String cronExpression, String endpoint, JsonObject parameters) {
			this.jobName = jobName;
			this.cronExpression = cronExpression;
			this.endpoint = endpoint;
			if (parameters == null) {
				this.parameters = new JsonObject();
			} else {
				this.parameters = parameters;
			}
		}
		public SchedulerConfiguration(String jobName, JsonObject json) {
			this.jobName = jobName;
			this.cronExpression = GsonUtils.getString(json, "cron");
			this.endpoint = GsonUtils.getString(json, "endpoint");
			if (json.has("parameters")) {
				this.parameters = GsonUtils.getJsonObject(json, "parameters");
			} else {
				this.parameters = new JsonObject();
			}
		}
		public JsonObject toJson() {
			JsonObject json = new JsonObject();
			json.addProperty("what", "EXECUTE_SERVER_CODE");
			json.addProperty("cron", this.cronExpression);
			json.addProperty("endpoint", this.endpoint);
			json.add("parameters", this.parameters);
			return json;
		}
	}
}
