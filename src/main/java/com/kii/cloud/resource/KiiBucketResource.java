package com.kii.cloud.resource;

import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiObject;
import com.kii.cloud.model.KiiQuery;
import com.kii.cloud.model.KiiQueryResult;
import com.squareup.okhttp.MediaType;

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
	public void create() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject request = new JsonObject();
		request.addProperty("bucketType", this.getBucketType());
		this.executePut(headers, MEDIA_TYPE_BUCKET_CREATION_REQUEST, request);
	}
	public KiiQueryResult query(KiiQuery query) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject response = this.executePost("/query", headers, MEDIA_TYPE_QUERY_REQUEST, query.toJson());
		return new KiiQueryResult(query, response);
	}
	public void delete() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		this.executeDelete(headers);
	}
	public KiiAclResource acl() {
		return new KiiAclResource(this);
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
