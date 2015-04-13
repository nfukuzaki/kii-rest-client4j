package com.kii.cloud.resource;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.annotation.AnonymousAPI;
import com.kii.cloud.model.analytics.KiiAnalyticsQuery;
import com.kii.cloud.model.analytics.KiiAnalyticsResult;
import com.kii.cloud.model.analytics.KiiGroupedAnalyticsResult;
import com.kii.cloud.model.analytics.KiiTabularAnalyticsResult;
import com.kii.cloud.model.analytics.KiiAnalyticsResult.ResultType;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.squareup.okhttp.Response;

public class KiiAnalyticsResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/analytics";
	
	
	private final String aggregationRuleID;
	
	public KiiAnalyticsResource(KiiAppResource parent, String aggregationRuleID) {
		super(parent);
		this.aggregationRuleID = aggregationRuleID;
	}
	@AnonymousAPI
	public KiiAnalyticsResult getResult(KiiAnalyticsQuery query) throws KiiRestException {
		Map<String, String> headers = this.newAppHeaders();
		headers.put("Accept", query.getResultType().getContentType());
		KiiRestRequest request = new KiiRestRequest(getUrl("/data"), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			if (query.getResultType() == ResultType.GroupedResult) {
				return new KiiGroupedAnalyticsResult(responseBody);
			}
			return new KiiTabularAnalyticsResult(responseBody);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@Override
	public String getPath() {
		return BASE_PATH + "/" + this.aggregationRuleID;
	}
}
