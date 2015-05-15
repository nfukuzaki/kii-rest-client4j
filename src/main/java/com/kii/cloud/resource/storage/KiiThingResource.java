package com.kii.cloud.resource.storage;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.storage.KiiThing;
import com.kii.cloud.resource.KiiRestRequest;
import com.kii.cloud.resource.KiiRestSubResource;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.kii.cloud.resource.push.KiiTopicResource;
import com.kii.cloud.util.GsonUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

/**
 * Represents the thing resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/things/{THING_ID}
 * </ul>
 */
public class KiiThingResource extends KiiRestSubResource {
	
	public static final MediaType MEDIA_TYPE_THING_UPDATE_REQUEST = MediaType.parse("application/vnd.kii.ThingUpdateRequest+json");
	public static final MediaType MEDIA_TYPE_THING_STATUS_UPDATE_REQUEST = MediaType.parse("application/vnd.kii.ThingStatusUpdateRequest+json");
	
	private final String thingID;
	private final String vendorThingID;
	
	public KiiThingResource(KiiThingsResource parent, String identifier) {
		super(parent);
		if (KiiThing.THING_ID_PATTERN.matcher(identifier).matches()) {
			this.thingID = identifier;
			this.vendorThingID = null;
		} else {
			this.thingID = null;
			this.vendorThingID = identifier;
		}
	}
	public KiiScopeAclResource acl() {
		return new KiiScopeAclResource(this);
	}
	public KiiThingOwnerResource owner() {
		return new KiiThingOwnerResource(this);
	}
	public KiiBucketResource buckets(String name) {
		return new KiiBucketResource(this, name);
	}
	public KiiTopicResource topics(String name) {
		return new KiiTopicResource(this, name);
	}
	
	/**
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/management/
	 */
	public boolean exists() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.HEAD, headers);
		try {
			Response response = this.execute(request);
			return response.isSuccessful();
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/management/
	 */
	public KiiThing get() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiThing(responseBody);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param thing
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/management/
	 */
	public void update(KiiThing thing) throws KiiRestException {
		if (thing == null) {
			throw new IllegalArgumentException("thing is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		headers.put("X-HTTP-Method-Override", "PATCH");
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MEDIA_TYPE_THING_UPDATE_REQUEST, thing.getJsonObject());
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/management/
	 */
	public boolean isDisabled() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/status"), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return GsonUtils.getBoolean(responseBody, "disabled");
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/management/
	 */
	public void enable() throws KiiRestException {
		this.changeStatus(false);
	}
	/**
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/management/
	 */
	public void disable() throws KiiRestException {
		this.changeStatus(true);
	}
	private void changeStatus(boolean disabled) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("disabled", disabled);
		KiiRestRequest request = new KiiRestRequest(getUrl("/status"), Method.PUT, headers, MEDIA_TYPE_THING_STATUS_UPDATE_REQUEST, requestBody);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/management/
	 */
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
		if (this.thingID != null) {
			return "/" + this.thingID;
		}
		return "/VENDOR_THING_ID:" + this.vendorThingID;
	}
}
