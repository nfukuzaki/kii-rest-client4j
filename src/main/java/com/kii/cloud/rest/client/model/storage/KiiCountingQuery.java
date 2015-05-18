package com.kii.cloud.rest.client.model.storage;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class KiiCountingQuery {
	private final KiiQuery query;
	public KiiCountingQuery(KiiQuery query) {
		this.query = query;
	}
	public JsonObject toJson() {
		JsonArray aggregations = new JsonArray();
		JsonObject element = new JsonObject();
		element.addProperty("type", "COUNT");
		element.addProperty("putAggregationInto", "count_field");
		aggregations.add(element);
		
		JsonObject query = this.query.clone().toJson();
		JsonObject bucketQuery = query.getAsJsonObject("bucketQuery");
		bucketQuery.add("aggregations", aggregations);
		query.add("bucketQuery", bucketQuery);
		return query;
	}
}
