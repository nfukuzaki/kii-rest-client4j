package com.kii.cloud.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

public class KiiServerHookConfiguration {
	
	private final List<TriggerConfiguration> triggerConfigurations = new ArrayList<TriggerConfiguration>();
	private final List<SchedulerConfiguration> schedulerConfigurations = new ArrayList<SchedulerConfiguration>();
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		for (TriggerConfiguration triggerConfiguration : this.triggerConfigurations) {
			json.add(triggerConfiguration.path, triggerConfiguration.toJson());
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
	public enum When {
		DATA_OBJECT_CREATED,
		DATA_OBJECT_DELETED,
		DATA_OBJECT_UPDATED
	}
	public static class TriggerConfiguration {
		private final String path;
		private final When when;
		private final String endpoint;
		public TriggerConfiguration(String path, When when, String endpoint) {
			this.path = path;
			this.when = when;
			this.endpoint = endpoint;
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
