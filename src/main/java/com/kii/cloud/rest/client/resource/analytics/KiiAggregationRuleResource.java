package com.kii.cloud.rest.client.resource.analytics;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.annotation.AdminAPI;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.analytics.KiiAggregationRule;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.squareup.okhttp.Response;

/**
 * Represents the aggregation rule resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/aggregation-rules/{AGGREGATION_RULE_ID}
 * </ul>
 */
public class KiiAggregationRuleResource extends KiiRestSubResource {
	
	private final Long aggregationRuleID;
	
	public KiiAggregationRuleResource(KiiAggregationRulesResource parent, Long aggregationRuleID) {
		super(parent);
		if (aggregationRuleID == null) {
			throw new IllegalArgumentException("aggregationRuleID is null");
		}
		this.aggregationRuleID = aggregationRuleID;
	}
	
	@AdminAPI
	public KiiAggregationRule get() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiAggregationRule(responseBody);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@AdminAPI
	public void delete() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.DELETE, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	
	@Override
	public String getPath() {
		return "/" + this.aggregationRuleID;
	}

}
