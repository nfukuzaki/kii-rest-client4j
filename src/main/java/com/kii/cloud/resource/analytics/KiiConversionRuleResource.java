package com.kii.cloud.resource.analytics;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.annotation.AdminAPI;
import com.kii.cloud.model.analytics.KiiConversionRule;
import com.kii.cloud.resource.KiiRestRequest;
import com.kii.cloud.resource.KiiRestSubResource;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.kii.cloud.util.StringUtils;
import com.squareup.okhttp.Response;

/**
 * Represents the conversion rule resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/conversion-rules/{CONVERSION_RULE_ID}
 * </ul>
 */
public class KiiConversionRuleResource extends KiiRestSubResource {
	
	private final String conversionRuleID;
	
	public KiiConversionRuleResource(KiiConversionRulesResource parent, String conversionRuleID) {
		super(parent);
		if (StringUtils.isEmpty(conversionRuleID)) {
			throw new IllegalArgumentException("conversionRuleID is null or empty");
		}
		this.conversionRuleID = conversionRuleID;
	}
	
	@AdminAPI
	public KiiConversionRule get() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiConversionRule(responseBody);
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
		return "/" + this.conversionRuleID;
	}
}
