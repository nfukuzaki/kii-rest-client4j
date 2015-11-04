package com.kii.cloud.rest.client.resource.conf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.conf.KiiThingTypeConfiguration;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.squareup.okhttp.Response;

/**
 * Represents the things types configuration resource like following URI:
 * 
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/configuration/thing-types
 * </ul>
 */
public class KiiThingTypesConfigurationResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/thing-types";
	
	public KiiThingTypesConfigurationResource(KiiAppConfigurationResource parent) {
		super(parent);
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
	public List<KiiThingTypeConfiguration> list() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonArray thingTypes = this.parseResponseAsJsonArray(request, response);
			List<KiiThingTypeConfiguration> result = new ArrayList<KiiThingTypeConfiguration>();
			for (int i = 0; i < thingTypes.size(); i++) {
				result.add(new KiiThingTypeConfiguration(thingTypes.get(i).getAsJsonObject()));
			}
			return result;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
}
