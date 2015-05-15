package com.kii.cloud.resource.conf;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.annotation.AdminAPI;
import com.kii.cloud.model.conf.KiiThingTypeConfiguration;
import com.kii.cloud.resource.KiiRestRequest;
import com.kii.cloud.resource.KiiRestSubResource;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.kii.cloud.util.StringUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

/**
 * 
 */
public class KiiThingTypeConfigurationResource extends KiiRestSubResource {
	
	public static final MediaType MEDIA_TYPE_THING_TYPE_CONFIGURATION_REQUEST = MediaType.parse("application/vnd.kii.ThingTypeConfigurationRequest+json");
	
	private final String thingType;
	public KiiThingTypeConfigurationResource(KiiThingTypesConfigurationResource parent, String thingType) {
		super(parent);
		if (StringUtils.isEmpty(thingType)) {
			throw new IllegalArgumentException("thingType is null or empty");
		}
		this.thingType = thingType;
	}
	
	@AdminAPI
	public KiiThingTypeConfiguration get() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiThingTypeConfiguration(responseBody);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@AdminAPI
	public void save(KiiThingTypeConfiguration configuration) throws KiiRestException {
		if (configuration == null) {
			throw new IllegalArgumentException("configuration is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.PUT, headers, MEDIA_TYPE_THING_TYPE_CONFIGURATION_REQUEST, configuration.getJsonObject());
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@AdminAPI
	public void remove() throws KiiRestException {
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
		return "/" + this.thingType;
	}
}
