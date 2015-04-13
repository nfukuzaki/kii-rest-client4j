package com.kii.cloud.resource.analytics;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.annotation.AdminAPI;
import com.kii.cloud.model.analytics.KiiAggregationRule;
import com.kii.cloud.resource.KiiRestRequest;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.kii.cloud.resource.KiiRestSubResource;
import com.squareup.okhttp.Response;

/**
 * Represents the aggregation rule resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/aggregation-rules/{AGGREGATION_RULE_ID}
 * </ul>
 */
public class KiiAggregationRuleResource extends KiiRestSubResource {
	
	private final String aggregationRuleID;
	
	public KiiAggregationRuleResource(KiiAggregationRulesResource parent, String aggregationRuleID) {
		super(parent);
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
