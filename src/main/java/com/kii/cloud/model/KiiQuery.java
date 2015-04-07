package com.kii.cloud.model;

import com.google.gson.JsonObject;
import com.kii.cloud.util.GsonUtils;
import com.kii.cloud.util.StringUtils;

public class KiiQuery {
	
	private String nextPaginationKey;
	private int limit;
	private JsonObject json;
	
	public KiiQuery() {
		this(null);
	}
	public KiiQuery(KiiClause clause) {
		JsonObject clauseJson = new JsonObject();
		if (clause == null) {
			clauseJson.add("clause", KiiClause.all().getJson());
		} else {
			clauseJson.add("clause", clause.getJson());
		}
		this.json = clauseJson;
	}
	public KiiQuery sortByAsc(String key) {
		this.json.addProperty("orderBy", key);
		this.json.addProperty("descending", false);
		return this;
	}
	public KiiQuery sortByDesc(String key) {
		this.json.addProperty("orderBy", key);
		this.json.addProperty("descending", true);
		return this;
	}
	public KiiQuery setNextPaginationKey(String nextPaginationKey) {
		this.nextPaginationKey = nextPaginationKey;
		return this;
	}
	public KiiQuery setLimit(int limit) {
		this.limit = limit;
		return this;
	}
	public JsonObject toJson() {
		JsonObject query = new JsonObject();
		query.add("bucketQuery", this.json);
		if (!StringUtils.isEmpty(this.nextPaginationKey)) {
			query.addProperty("paginationKey", this.nextPaginationKey);
		}
		if (this.limit > 0) {
			query.addProperty("bestEffortLimit", this.limit);
		}
		return query;
	}
	public KiiQuery clone() {
		KiiQuery clone = new KiiQuery();
		clone.nextPaginationKey = this.nextPaginationKey;
		clone.limit = this.limit;
		clone.json = GsonUtils.clone(this.json);
		return clone;
	}
}
