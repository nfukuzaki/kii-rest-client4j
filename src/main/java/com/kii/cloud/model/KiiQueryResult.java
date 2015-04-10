package com.kii.cloud.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class KiiQueryResult extends KiiJsonModel {
	public static final KiiJsonProperty<JsonArray> PROPERTY_RESULTS = new KiiJsonProperty<JsonArray>(JsonArray.class, "results");
	public static final KiiJsonProperty<String> PROPERTY_NEXT_PAGINATION_KEY = new KiiJsonProperty<String>(String.class, "nextPaginationKey");
	public static final KiiJsonProperty<String> PROPERTY_QUERY_DESCRIPTION = new KiiJsonProperty<String>(String.class, "queryDescription");
	
	private final KiiQuery query;
	
	public KiiQueryResult(KiiQuery query, JsonObject result) {
		super(result);
		this.query = query;
	}
	public boolean hasNext() {
		return PROPERTY_NEXT_PAGINATION_KEY.has(this.json);
	}
	public List<KiiObject> getResults() {
		List<KiiObject> results = new ArrayList<KiiObject>();
		JsonArray objects = PROPERTY_RESULTS.get(this.json);
		for (int i = 0; i < objects.size(); i++) {
			JsonObject object = objects.get(i).getAsJsonObject();
			results.add(new KiiObject(object));
		}
		return results;
	}
	public KiiQuery getNextQuery() {
		if (!hasNext()) {
			return null;
		}
		KiiQuery query = this.query.clone();
		query.setNextPaginationKey(PROPERTY_NEXT_PAGINATION_KEY.get(this.json));
		return query;
	}
}
