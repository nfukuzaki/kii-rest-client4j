package com.kii.cloud.resource.conf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.conf.KiiThingTypeConfiguration;
import com.kii.cloud.resource.KiiRestRequest;
import com.kii.cloud.resource.KiiRestSubResource;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.squareup.okhttp.Response;

/**
 * 
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
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			JsonArray thingTypes = responseBody.getAsJsonArray("thingTypes");
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
