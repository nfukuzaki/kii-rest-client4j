package com.kii.cloud.resource;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiObject;
import com.kii.cloud.model.KiiQuery;

public class KiiObjectResource extends KiiRestSubResource {
	
	private final String objectID;
	
	public KiiObjectResource(KiiBucketResource parent, String objectID) {
		super(parent);
		this.objectID = objectID;
	}
	public KiiObject get() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject response = this.executeGet(headers);
		return new KiiObject(response);
	}
	public List<KiiObject> query(KiiQuery query) {
		return null;
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
	public void partialUpdate(KiiObject object) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		headers.put("X-HTTP-Method-Override", "PATCH");
		JsonObject response = this.executePost(headers, null, object.getJsonObject());
		Long modifiedAt = KiiObject.PROPERTY_MODIFIED_AT.getLong(response);
		String owner = KiiObject.PROPERTY_OWNER.getString(response);
		String version = KiiObject.PROPERTY_VERSION.getString(response);
		object.setModifiedAt(modifiedAt).setOwner(owner).setVersion(version);
	}
	@Override
	public String getPath() {
		return KiiObjectsResource.BASE_PATH + "/" + this.objectID;
	}
}
