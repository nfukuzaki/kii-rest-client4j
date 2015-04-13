package com.kii.cloud.model.servercode;

import com.google.gson.JsonObject;
import com.kii.cloud.model.KiiJsonProperty;
import com.kii.cloud.util.GsonUtils;
import com.kii.cloud.util.StringUtils;

public class KiiScheduleExecutionQuery {
	
	private String nextPaginationKey;
	private int limit;
	private JsonObject json;
	
	
	public KiiScheduleExecutionQuery() {
		this(null);
	}
	public KiiScheduleExecutionQuery(KiiScheduleExecutionQueryClause clause) {
		JsonObject clauseJson = new JsonObject();
		if (clause == null) {
			clauseJson.add("clause", KiiScheduleExecutionQueryClause.all().getJson());
		} else {
			clauseJson.add("clause", clause.getJson());
		}
		this.json = clauseJson;
	}
	public KiiScheduleExecutionQuery sortByAsc(KiiJsonProperty<?> key) {
		this.json.addProperty("orderBy", key.getName());
		this.json.addProperty("descending", false);
		return this;
	}
	public KiiScheduleExecutionQuery sortByDesc(KiiJsonProperty<?> key) {
		this.json.addProperty("orderBy", key.getName());
		this.json.addProperty("descending", true);
		return this;
	}
	public KiiScheduleExecutionQuery setNextPaginationKey(String nextPaginationKey) {
		this.nextPaginationKey = nextPaginationKey;
		return this;
	}
	public KiiScheduleExecutionQuery setLimit(int limit) {
		this.limit = limit;
		return this;
	}
	public JsonObject toJson() {
		JsonObject query = new JsonObject();
		query.add("scheduleExecutionQuery", this.json);
		if (!StringUtils.isEmpty(this.nextPaginationKey)) {
			query.addProperty("paginationKey", this.nextPaginationKey);
		}
		if (this.limit > 0) {
			query.addProperty("bestEffortLimit", this.limit);
		}
		return query;
	}

	public KiiScheduleExecutionQuery clone() {
		KiiScheduleExecutionQuery clone = new KiiScheduleExecutionQuery();
		clone.nextPaginationKey = this.nextPaginationKey;
		clone.limit = this.limit;
		clone.json = GsonUtils.clone(this.json);
		return clone;
	}

}
