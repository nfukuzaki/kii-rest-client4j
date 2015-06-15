package com.kii.cloud.rest.client.resource.storage;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.model.push.KiiTopic;
import com.kii.cloud.rest.client.model.storage.KiiThing;
import com.kii.cloud.rest.client.model.storage.KiiThingIdentifierType;
import com.kii.cloud.rest.client.model.uri.KiiThingURI;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.kii.cloud.rest.client.resource.KiiScopedResource;
import com.kii.cloud.rest.client.resource.push.KiiTopicResource;
import com.kii.cloud.rest.client.resource.push.KiiTopicsResource;
import com.kii.cloud.rest.client.util.GsonUtils;
import com.kii.cloud.rest.client.util.StringUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

/**
 * Represents the thing resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/things/{THING_ID}
 * </ul>
 */
public class KiiThingResource extends KiiRestSubResource implements KiiScopedResource {
	
	public static final MediaType MEDIA_TYPE_THING_UPDATE_REQUEST = MediaType.parse("application/vnd.kii.ThingUpdateRequest+json");
	public static final MediaType MEDIA_TYPE_THING_STATUS_UPDATE_REQUEST = MediaType.parse("application/vnd.kii.ThingStatusUpdateRequest+json");
	
	private final KiiThingIdentifierType identifierType;
	private final String identifier;
	
	public KiiThingResource(KiiThingsResource parent, String identifier) {
		super(parent);
		if (StringUtils.isEmpty(identifier)) {
			throw new IllegalArgumentException("identifier is null or empty");
		}
		String[] array = identifier.split(":");
		if (array.length > 1) {
			this.identifierType = KiiThingIdentifierType.fromString(array[0]);
			this.identifier = array[1];
		} else {
			this.identifierType = KiiThingIdentifierType.parseIdentifier(identifier);
			this.identifier = identifier;
		}
	}
	public KiiThingResource(KiiThingsResource parent, KiiThingIdentifierType identifierType, String identifier) {
		super(parent);
		this.identifierType = identifierType;
		this.identifier = identifier;
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
	public KiiTopicsResource topics() {
		return new KiiTopicsResource(this);
	}
	public KiiTopicResource topics(KiiTopic topic) {
		if (topic == null) {
			throw new IllegalArgumentException("topic is null"); 
		}
		if (topic.getURI() != null && topic.getURI().getScope() != KiiScope.THING) {
			throw new IllegalArgumentException("topic scope is not Thing");
		}
		return new KiiTopicResource(this.topics(), topic.getTopicID());
	}
	public KiiTopicResource topics(String topicID) {
		return new KiiTopicResource(this.topics(), topicID);
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
			this.logResponse(request, response);
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
			return new KiiThing(responseBody).setURI(this.getURI());
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
			thing.setURI(this.getURI());
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
		return "/" + this.identifierType.getFullyQualifiedIdentifier(this.identifier);
	}
	@Override
	public KiiScope getScope() {
		return ((KiiThingsResource)this.parent).getScope();
	}
	public String getScopeIdentifier() {
		return this.identifierType.getFullyQualifiedIdentifier(this.identifier);
	}
	public KiiThingURI getURI() {
		return KiiThingURI.newURI(this.getAppID(), this.identifier);
	}
}
