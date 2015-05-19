package com.kii.cloud.rest.client.resource.analytics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.annotation.AdminAPI;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.analytics.KiiAggregationRule;
import com.kii.cloud.rest.client.resource.KiiAppResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

/**
 * Represents the aggregation rules resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/aggregation-rules
 * </ul>
 */
public class KiiAggregationRulesResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/aggregation-rules";
	
	public static final MediaType MEDIA_TYPE_AGGREGATION_RULE = MediaType.parse("application/vnd.kii.AggregationRule+json");
	
	public KiiAggregationRulesResource(KiiAppResource parent) {
		super(parent);
	}
	
	@AdminAPI
	public Long create(KiiAggregationRule aggregationRule) throws KiiRestException {
		if (aggregationRule == null) {
			throw new IllegalArgumentException("aggregationRule is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MEDIA_TYPE_AGGREGATION_RULE, aggregationRule.getJsonObject());
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			Long id = KiiAggregationRule.PROPERTY_ID.get(responseBody);
			aggregationRule.setID(id);
			return id;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@AdminAPI
	public List<KiiAggregationRule> list() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonArray responseBody = this.parseResponseAsJsonArray(request, response);
			List<KiiAggregationRule> result = new ArrayList<KiiAggregationRule>();
			for (int i = 0; i < responseBody.size(); i++) {
				result.add(new KiiAggregationRule(responseBody.get(i).getAsJsonObject()));
			}
			return result;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
