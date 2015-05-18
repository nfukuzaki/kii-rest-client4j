package com.kii.cloud.rest.client.resource.analytics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.KiiRestException;
import com.kii.cloud.rest.client.annotation.AdminAPI;
import com.kii.cloud.rest.client.model.analytics.KiiConversionRule;
import com.kii.cloud.rest.client.resource.KiiAppResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

/**
 * Represents the conversion rules resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/conversion-rules
 * </ul>
 */
public class KiiConversionRulesResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/conversion-rules";
	
	public static final MediaType MEDIA_TYPE_APP_DATA_CONVERSION_RULE = MediaType.parse("application/vnd.kii.AppDataConversionRule+json");
	
	public KiiConversionRulesResource(KiiAppResource parent) {
		super(parent);
	}
	/**
	 * @param conversionRule
	 * @return conversionRuleID
	 * @throws KiiRestException
	 */
	@AdminAPI
	public String create(KiiConversionRule conversionRule) throws KiiRestException {
		if (conversionRule == null) {
			throw new IllegalArgumentException("conversionRule is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MEDIA_TYPE_APP_DATA_CONVERSION_RULE, conversionRule.getJsonObject());
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return KiiConversionRule.PROPERTY_ID.get(responseBody);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @return
	 * @throws KiiRestException
	 */
	@AdminAPI
	public List<KiiConversionRule> list() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonArray responseBody = this.parseResponseAsJsonArray(request, response);
			List<KiiConversionRule> result = new ArrayList<KiiConversionRule>();
			for (int i = 0; i < responseBody.size(); i++) {
				result.add(new KiiConversionRule(responseBody.get(i).getAsJsonObject()));
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
