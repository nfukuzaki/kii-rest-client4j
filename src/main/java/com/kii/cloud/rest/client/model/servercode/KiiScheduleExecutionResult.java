package com.kii.cloud.rest.client.model.servercode;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.model.KiiJsonModel;

public class KiiScheduleExecutionResult extends KiiJsonModel {
	
	public enum Status {
		SUCCESS,
		FAILED,
		RUNNING
	}
	
	public static final KiiScheduleExecutionQueryProperty<String> PROPERTY_SCHEDULE_EXECUTION_ID = new KiiScheduleExecutionQueryProperty<String>(String.class, "scheduleExecutionID");
	public static final KiiScheduleExecutionQueryProperty<String> PROPERTY_JOB_NAME = new KiiScheduleExecutionQueryProperty<String>(String.class, "name");
	public static final KiiScheduleExecutionQueryProperty<String> PROPERTY_STATUS = new KiiScheduleExecutionQueryProperty<String>(String.class, "status");
	public static final KiiScheduleExecutionQueryProperty<Long> PROPERTY_STARTED_AT = new KiiScheduleExecutionQueryProperty<Long>(Long.class, "startedAt");
	public static final KiiScheduleExecutionQueryProperty<Long> PROPERTY_FINISHED_AT = new KiiScheduleExecutionQueryProperty<Long>(Long.class, "finishedAt");
	public static final KiiScheduleExecutionQueryProperty<String> PROPERTY_RESULT = new KiiScheduleExecutionQueryProperty<String>(String.class, "result");
	
	public KiiScheduleExecutionResult(JsonObject json) {
		super(json);
	}
	
	public String getScheduleExecutionID() {
		return PROPERTY_SCHEDULE_EXECUTION_ID.get(this.json);
	}
	public String getJobName() {
		return PROPERTY_JOB_NAME.get(this.json);
	}
	public Status getStatus() {
		if (PROPERTY_STATUS.has(this.json)) {
			return Enum.valueOf(Status.class, PROPERTY_STATUS.get(this.json));
		}
		return null;
	}
	public Long getStartedAt() {
		return PROPERTY_STARTED_AT.get(this.json);
	}
	public Long getFinishedAt() {
		return PROPERTY_FINISHED_AT.get(this.json);
	}
	public String getResult() {
		return PROPERTY_RESULT.get(this.json);
	}
}
