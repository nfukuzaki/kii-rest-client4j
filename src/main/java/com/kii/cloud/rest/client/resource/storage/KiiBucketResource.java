package com.kii.cloud.rest.client.resource.storage;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.model.storage.KiiBucket;
import com.kii.cloud.rest.client.model.storage.KiiCountingQuery;
import com.kii.cloud.rest.client.model.storage.KiiObject;
import com.kii.cloud.rest.client.model.storage.KiiQuery;
import com.kii.cloud.rest.client.model.storage.KiiQueryResult;
import com.kii.cloud.rest.client.model.storage.KiiThing;
import com.kii.cloud.rest.client.model.storage.KiiUser;
import com.kii.cloud.rest.client.resource.KiiAppResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiScopedResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.kii.cloud.rest.client.util.GsonUtils;
import com.kii.cloud.rest.client.util.StringUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

/**
 * Represents the bucket resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/buckets/{BUCKET_NAME}
 * <li>https://hostname/api/apps/{APP_ID}/users/{USER_IDENTIFIER}/buckets/{BUCKET_NAME}
 * <li>https://hostname/api/apps/{APP_ID}/groups/{GROUP_ID}/buckets/{BUCKET_NAME}
 * <li>https://hostname/api/apps/{APP_ID}/things/{THING_ID}/buckets/{BUCKET_NAME}
 * </ul>
 *
 */
public class KiiBucketResource extends KiiRestSubResource implements KiiScopedResource {
	
	public static final MediaType MEDIA_TYPE_BUCKET_CREATION_REQUEST = MediaType.parse("application/vnd.kii.BucketCreationRequest+json");
	public static final MediaType MEDIA_TYPE_QUERY_REQUEST = MediaType.parse("application/vnd.kii.QueryRequest+json");
	
	public static final String BASE_PATH = "/buckets";
	
	protected final String name;
	
	public KiiBucketResource(KiiAppResource parent, String name) {
		super(parent);
		if (StringUtils.isEmpty(name)) {
			throw new IllegalArgumentException("name is null or empty");
		}
		this.name = name;
	}
	public KiiBucketResource(KiiUserResource parent, String name) {
		super(parent);
		if (StringUtils.isEmpty(name)) {
			throw new IllegalArgumentException("name is null or empty");
		}
		this.name = name;
	}
	public KiiBucketResource(KiiGroupResource parent, String name) {
		super(parent);
		if (StringUtils.isEmpty(name)) {
			throw new IllegalArgumentException("name is null or empty");
		}
		this.name = name;
	}
	public KiiBucketResource(KiiThingResource parent, String name) {
		super(parent);
		if (StringUtils.isEmpty(name)) {
			throw new IllegalArgumentException("name is null or empty");
		}
		this.name = name;
	}
	@Override
	public KiiScope getScope() {
		return ((KiiScopedResource)this.parent).getScope();
	}
	public KiiBucketAclResource acl() {
		return new KiiBucketAclResource(this);
	}
	public KiiObjectsResource objects() {
		return new KiiObjectsResource(this);
	}
	public KiiObjectResource objects(KiiObject object) {
		return objects(object.getObjectID());
	}
	public KiiObjectResource objects(String objectID) {
		return new KiiObjectResource(this, objectID);
	}
	
	/**
	 * 
	 * NOTE:This feature has not documented yet.
	 * @throws KiiRestException
	 */
	public void create() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("bucketType", this.getBucketType());
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.PUT, headers, MEDIA_TYPE_BUCKET_CREATION_REQUEST, requestBody);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * 
	 * NOTE:This feature has not documented yet.
	 * @throws KiiRestException
	 */
	public KiiBucket get() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiBucket(responseBody);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/querying/
	 */
	public int count() throws KiiRestException {
		return this.count(new KiiQuery());
	}
	/**
	 * @param query
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/querying/
	 */
	public int count(KiiQuery query) throws KiiRestException {
		if (query == null) {
			throw new IllegalArgumentException("query is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiCountingQuery countingQuery = new KiiCountingQuery(query);
		KiiRestRequest request = new KiiRestRequest(getUrl("/query"), Method.POST, headers, MEDIA_TYPE_QUERY_REQUEST, countingQuery.toJson());
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return GsonUtils.getInt(responseBody.getAsJsonObject("aggregations"), "count_field") ;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param query
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/querying/
	 */
	public KiiQueryResult query(KiiQuery query) throws KiiRestException {
		if (query == null) {
			throw new IllegalArgumentException("query is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/query"), Method.POST, headers, MEDIA_TYPE_QUERY_REQUEST, query.toJson());
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiQueryResult(query, responseBody);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/buckets/deleting/
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
	
	/**
	 * @param user
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/push/
	 */
	public void subscribe(KiiUser user) throws KiiRestException {
		if (user == null) {
			throw new IllegalArgumentException("user is null");
		}
		this.subscribeByUser(user.getUserID());
	}
	/**
	 * @param userID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/push/
	 */
	public void subscribeByUser(String userID) throws KiiRestException {
		if (userID == null) {
			throw new IllegalArgumentException("userID is null");
		}
		this.subscribe("users", userID);
	}
	/**
	 * @param thingID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/push/
	 */
	public void subscribe(KiiThing thing) throws KiiRestException {
		if (thing == null) {
			throw new IllegalArgumentException("thing is null");
		}
		this.subscribeByThing(thing.getThingID());
	}
	/**
	 * @param thingID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/push/
	 */
	public void subscribeByThing(String thingID) throws KiiRestException {
		if (thingID == null) {
			throw new IllegalArgumentException("thingID is null");
		}
		this.subscribe("things", thingID);
	}
	private void subscribe(String subscriberType, String id) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/filters/all/push/subscriptions/%s/%s", subscriberType, id), Method.PUT, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param user
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/push/
	 */
	public void unsubscribe(KiiUser user) throws KiiRestException {
		if (user == null) {
			throw new IllegalArgumentException("user is null");
		}
		this.unsubscribeByUser(user.getUserID());
	}
	/**
	 * @param userID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/push/
	 */
	public void unsubscribeByUser(String userID) throws KiiRestException {
		if (userID == null) {
			throw new IllegalArgumentException("userID is null");
		}
		this.unsubscribe("users", userID);
	}
	/**
	 * @param userID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/push/
	 */
	public void unsubscribe(KiiThing thing) throws KiiRestException {
		if (thing == null) {
			throw new IllegalArgumentException("thing is null");
		}
		this.unsubscribeByThing(thing.getThingID());
	}
	/**
	 * @param userID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/push/
	 */
	public void unsubscribeByThing(String thingID) throws KiiRestException {
		if (thingID == null) {
			throw new IllegalArgumentException("thingID is null");
		}
		this.unsubscribe("things", thingID);
	}
	private void unsubscribe(String unsubscriberType, String id) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/filters/all/push/subscriptions/%s/%s", unsubscriberType, id), Method.DELETE, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param user
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/push/
	 */
	public boolean isSubscribed(KiiUser user) throws KiiRestException {
		if (user == null) {
			throw new IllegalArgumentException("user is null");
		}
		return this.isSubscribedByUser(user.getUserID());
	}
	/**
	 * @param userID
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/push/
	 */
	public boolean isSubscribedByUser(String userID) throws KiiRestException {
		if (userID == null) {
			throw new IllegalArgumentException("userID is null");
		}
		return this.isSubscribed("users", userID);
	}
	/**
	 * @param thing
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/push/
	 */
	public boolean isSubscribed(KiiThing thing) throws KiiRestException {
		if (thing == null) {
			throw new IllegalArgumentException("thing is null");
		}
		return this.isSubscribedByThing(thing.getThingID());
	}
	/**
	 * @param thingID
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/push/
	 */
	public boolean isSubscribedByThing(String thingID) throws KiiRestException {
		if (thingID == null) {
			throw new IllegalArgumentException("thingID is null");
		}
		return this.isSubscribed("things", thingID);
	}
	private boolean isSubscribed(String subscriberType, String id) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/filters/all/push/subscriptions/%s/%s", subscriberType, id), Method.GET, headers);
		try {
			Response response = this.execute(request);
			return response.isSuccessful();
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	protected String getBucketType() {
		return "rw";
	}
	@Override
	public String getPath() {
		return BASE_PATH + "/" + this.name;
	}
}
