package com.kii.cloud.rest.client.model.servercode;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.model.KiiJsonModel;
import com.kii.cloud.rest.client.model.KiiJsonProperty;

public class KiiScheduleExecutionQueryResult extends KiiJsonModel {
	public static final KiiJsonProperty<JsonArray> PROPERTY_RESULTS = new KiiJsonProperty<JsonArray>(JsonArray.class, "results");
	public static final KiiJsonProperty<String> PROPERTY_NEXT_PAGINATION_KEY = new KiiJsonProperty<String>(String.class, "nextPaginationKey");
	
	private final KiiScheduleExecutionQuery query;
	
	public KiiScheduleExecutionQueryResult(KiiScheduleExecutionQuery query, JsonObject result) {
		super(result);
		this.query = query;
	}
	public boolean hasNext() {
		return PROPERTY_NEXT_PAGINATION_KEY.has(this.json);
	}
	public List<KiiScheduleExecutionResult> getResults() {
		List<KiiScheduleExecutionResult> results = new ArrayList<KiiScheduleExecutionResult>();
		JsonArray objects = PROPERTY_RESULTS.get(this.json);
		for (int i = 0; i < objects.size(); i++) {
			JsonObject object = objects.get(i).getAsJsonObject();
			results.add(new KiiScheduleExecutionResult(object));
		}
		return results;
	}
	public KiiScheduleExecutionQuery getNextQuery() {
		if (!hasNext()) {
			return null;
		}
		KiiScheduleExecutionQuery query = this.query.clone();
		query.setNextPaginationKey(PROPERTY_NEXT_PAGINATION_KEY.get(this.json));
		return query;
	}
}
