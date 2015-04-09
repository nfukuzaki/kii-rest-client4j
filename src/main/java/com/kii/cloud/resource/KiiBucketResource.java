package com.kii.cloud.resource;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiBucket;
import com.kii.cloud.model.KiiCountingQuery;
import com.kii.cloud.model.KiiObject;
import com.kii.cloud.model.KiiQuery;
import com.kii.cloud.model.KiiQueryResult;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.kii.cloud.util.GsonUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

public class KiiBucketResource extends KiiRestSubResource {
	
	public static final MediaType MEDIA_TYPE_BUCKET_CREATION_REQUEST = MediaType.parse("application/vnd.kii.BucketCreationRequest+json");
	public static final MediaType MEDIA_TYPE_QUERY_REQUEST = MediaType.parse("application/vnd.kii.QueryRequest+json");
	
	public static final String BASE_PATH = "/buckets";
	
	protected final String name;
	
	public KiiBucketResource(KiiAppResource parent, String name) {
		super(parent);
		this.name = name;
	}
	public KiiBucketResource(KiiUserResource parent, String name) {
		super(parent);
		this.name = name;
	}
	public KiiBucketResource(KiiGroupResource parent, String name) {
		super(parent);
		this.name = name;
	}
	public KiiBucketResource(KiiThingResource parent, String name) {
		super(parent);
		this.name = name;
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
	 * @param userID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/push/
	 */
	public void subscribe(String userID) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/filters/all/push/subscriptions/users/" + userID), Method.PUT, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param userID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/push/
	 */
	public void unsubscribe(String userID) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/filters/all/push/subscriptions/users/" + userID), Method.DELETE, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param userID
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/push/
	 */
	public boolean isSubscribed(String userID) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/filters/all/push/subscriptions/users/" + userID), Method.GET, headers);
		try {
			Response response = this.execute(request);
			return response.isSuccessful();
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
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
	protected String getBucketType() {
		return "rw";
	}
	@Override
	public String getPath() {
		return BASE_PATH + "/" + this.name;
	}
}
