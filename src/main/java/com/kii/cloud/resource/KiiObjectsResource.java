package com.kii.cloud.resource;

import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiObject;
import com.kii.cloud.util.GsonUtils;
import com.squareup.okhttp.MediaType;

public class KiiObjectsResource extends KiiRestSubResource {
	public static final String BASE_PATH = "/objects";
	public KiiObjectsResource(KiiBucketResource parent) {
		super(parent);
	}
	public void register(KiiObject object) throws KiiRestException {
		this.register("application/json", object);
	}
	public KiiObject register(String contentType, KiiObject object) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject response = this.executePost(headers, MediaType.parse(contentType), object.getJsonObject());
		String objectID = GsonUtils.getString(response, KiiObject.PROPERTY_OBJECT_ID);
		return object.setObjectID(objectID);
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
