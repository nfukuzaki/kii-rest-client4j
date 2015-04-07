package com.kii.cloud.resource;

import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiObject;
import com.squareup.okhttp.Response;

public class KiiObjectResource extends KiiRestSubResource {
	
	private final String objectID;
	
	public KiiObjectResource(KiiBucketResource parent, String objectID) {
		super(parent);
		this.objectID = objectID;
	}
	public KiiObjectBodyResource body() {
		return new KiiObjectBodyResource(this);
	}
	public KiiAclResource acl() {
		return new KiiAclResource(this);
	}
	public boolean exists() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		Response response = this.executeHead(headers);
		return response.isSuccessful();
	}
	public KiiObject get() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject response = this.executeGet(headers);
		return new KiiObject(response);
	}
	public void delete() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		this.executeDelete(headers);
	}
	public void update(KiiObject object) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject response = this.executePut(headers, null, object.getJsonObject());
		Long modifiedAt = KiiObject.PROPERTY_MODIFIED_AT.getLong(response);
		object.setModifiedAt(modifiedAt);
	}
	public void updateWithOptimisticLock(KiiObject object) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		headers.put("If-Match", object.getVersion());
		JsonObject response = this.executePut(headers, null, object.getJsonObject());
		Long modifiedAt = KiiObject.PROPERTY_MODIFIED_AT.getLong(response);
		object.setModifiedAt(modifiedAt);
	}
	public void partialUpdate(KiiObject object) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		headers.put("X-HTTP-Method-Override", "PATCH");
		JsonObject response = this.executePost(headers, null, object.getJsonObject());
		object.setJsonObject(response);
	}
	public void partialUpdateWithOptimisticLock(KiiObject object) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		headers.put("If-Match", object.getVersion());
		headers.put("X-HTTP-Method-Override", "PATCH");
		JsonObject response = this.executePost(headers, null, object.getJsonObject());
		object.setJsonObject(response);
	}
	@Override
	public String getPath() {
		return KiiObjectsResource.BASE_PATH + "/" + this.objectID;
	}
}
