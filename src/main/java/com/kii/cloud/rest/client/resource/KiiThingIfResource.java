package com.kii.cloud.rest.client.resource;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.logging.KiiLogger;
import com.kii.cloud.rest.client.model.KiiCredentialsContainer;
import com.kii.cloud.rest.client.model.storage.KiiGroup;
import com.kii.cloud.rest.client.model.storage.KiiThing;
import com.kii.cloud.rest.client.model.storage.KiiUser;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.kii.cloud.rest.client.resource.thingif.KiiThingIfTargetResource;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

public class KiiThingIfResource extends KiiRestResource {
	
	private static final MediaType MEDIA_TYPE_ONBOARDING_WITH_THING_ID_BY_OWNER_REQUEST = MediaType.parse("application/vnd.kii.OnboardingWithThingIDByOwner+json");
	private static final MediaType MEDIA_TYPE_ONBOARDING_WITH_VENDOR_THING_ID_BY_OWNER_REQUEST = MediaType.parse("application/vnd.kii.OnboardingWithVendorThingIDByOwner+json");
	
	public enum Prefix {
		USER("user:"),
		GROUP("group:"),
		THING("thing:");
		private final String value;
		private Prefix(String value) {
			this.value = value;
		}
		public String getValue() {
			return this.value;
		}
		public static Prefix fromString(String s) {
			for (Prefix prefix : values()) {
				if (prefix.value.equals(s)) {
					return prefix;
				}
			}
			return null;
		}
	}
	
	public static final String BASE_PATH = "/apps";
	
	private final String appID;
	private final String appKey;
	private final String endpoint;
	private final KiiCredentialsContainer credentials;
	private final KiiLogger logger;
	
	public KiiThingIfResource(String appID, String appKey, String endpoint, KiiCredentialsContainer credentials, KiiLogger logger) {
		this.appID = appID;
		this.appKey = appKey;
		this.endpoint = endpoint;
		this.credentials = credentials;
		this.logger = logger;
	}
	
	@Override
	public String getAppID() {
		return this.appID;
	}
	public String getAppKey() {
		return this.appKey;
	}
	public String getEndpoint() {
		return this.endpoint;
	}
	public KiiCredentialsContainer getCredentials() {
		return this.credentials;
	}
	
	public KiiThingIfTargetResource targets(KiiUser user) {
		return new KiiThingIfTargetResource(this, Prefix.USER.getValue() + user.getID());
	}
	public KiiThingIfTargetResource targets(KiiGroup group) {
		return new KiiThingIfTargetResource(this, Prefix.GROUP.getValue() + group.getGroupID());
	}
	public KiiThingIfTargetResource targets(KiiThing thing) {
		return new KiiThingIfTargetResource(this, Prefix.THING.getValue() + thing.getID());
	}
	public KiiThing onboard(String vendorThingID, String thingPassword, String thingType, JsonObject thingProperties) throws KiiRestException {
		if (!(getCredentials() instanceof KiiUser)) {
			throw new IllegalStateException("Credentials must be KiiUser who will become a owner");
		}
		if (vendorThingID == null) {
			throw new IllegalArgumentException("vendorThingID is null");
		}
		if (thingPassword == null) {
			throw new IllegalArgumentException("thingPassword is null");
		}
		
		Map<String, String> headers = this.newAppHeaders();
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("vendorThingID", vendorThingID);
		requestBody.addProperty("thingPassword", thingPassword);
		if (thingType != null) {
			requestBody.addProperty("thingType", thingType);
		}
		if (thingProperties != null) {
			requestBody.add("thingProperties", thingProperties);
		}
		requestBody.addProperty("owner", Prefix.USER.getValue() + getCredentials().getID());
		
		KiiRestRequest request = new KiiRestRequest(getUrl("/onboardings"), Method.POST, headers, MEDIA_TYPE_ONBOARDING_WITH_VENDOR_THING_ID_BY_OWNER_REQUEST, requestBody);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiThing(responseBody);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	public KiiThing onboard(String thingID, String thingPassword) throws KiiRestException {
		if (!(getCredentials() instanceof KiiUser)) {
			throw new IllegalStateException("Credentials must be KiiUser who will become a owner");
		}
		if (thingID == null) {
			throw new IllegalArgumentException("thingID is null");
		}
		if (thingPassword == null) {
			throw new IllegalArgumentException("thingPassword is null");
		}
		
		Map<String, String> headers = this.newAppHeaders();
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("thingID", thingID);
		requestBody.addProperty("thingPassword", thingPassword);
		requestBody.addProperty("owner", Prefix.USER.getValue() + getCredentials().getID());
		
		KiiRestRequest request = new KiiRestRequest(getUrl("/onboardings"), Method.POST, headers, MEDIA_TYPE_ONBOARDING_WITH_THING_ID_BY_OWNER_REQUEST, requestBody);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiThing(responseBody);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	
	@Override
	protected KiiRestResource getParent() {
		return null;
	}
	@Override
	public String getPath() {
		return this.endpoint + BASE_PATH + "/" + this.appID;
	}
	@Override
	protected KiiLogger getLogger() {
		return this.logger;
	}
}
