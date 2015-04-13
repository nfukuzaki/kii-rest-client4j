package com.kii.cloud.model;

import com.google.gson.JsonObject;

public class KiiScheduleExecutionResult extends KiiJsonModel {
	
	public enum Status {
		SUCCESS,
		FAILED,
		RUNNING
	}
	
	public static final KiiJsonProperty<String> PROPERTY_SCHEDULE_EXECUTION_ID = new KiiJsonProperty<String>(String.class, "scheduleExecutionID");
	public static final KiiJsonProperty<String> PROPERTY_JOB_NAME = new KiiJsonProperty<String>(String.class, "name");
	public static final KiiJsonProperty<String> PROPERTY_STATUS = new KiiJsonProperty<String>(String.class, "status");
	public static final KiiJsonProperty<Long> PROPERTY_STARTED_AT = new KiiJsonProperty<Long>(Long.class, "startedAt");
	public static final KiiJsonProperty<Long> PROPERTY_FINISHED_AT = new KiiJsonProperty<Long>(Long.class, "finishedAt");
	public static final KiiJsonProperty<String> PROPERTY_RESULT = new KiiJsonProperty<String>(String.class, "result");
	
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
		return Enum.valueOf(Status.class, PROPERTY_STATUS.get(this.json));
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
