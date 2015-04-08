package com.kii.cloud.resource;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiObject;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

public class KiiObjectsResource extends KiiRestSubResource {
	public static final String BASE_PATH = "/objects";
	public KiiObjectsResource(KiiBucketResource parent) {
		super(parent);
	}
	public void save(KiiObject object) throws KiiRestException {
		this.save("application/json", object);
	}
	/**
	 * @param contentType
	 * @param object
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/creating/
	 */
	public KiiObject save(String contentType, KiiObject object) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MediaType.parse(contentType), object.getJsonObject());
		try {
			Response response = this.execute(request);
			String version = response.header("ETag");
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			String objectID = KiiObject.PROPERTY_OBJECT_ID.getString(responseBody);
			return object.setObjectID(objectID).setVersion(version);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
