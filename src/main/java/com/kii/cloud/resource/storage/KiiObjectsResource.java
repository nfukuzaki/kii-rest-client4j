package com.kii.cloud.resource.storage;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.storage.KiiObject;
import com.kii.cloud.resource.KiiRestRequest;
import com.kii.cloud.resource.KiiRestSubResource;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

/**
 * Represents the objects resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/buckets/{BUCKET_NAME}/objects
 * <li>https://hostname/api/apps/{APP_ID}/users/{USER_IDENTIFIER}/buckets/{BUCKET_NAME}/objects
 * <li>https://hostname/api/apps/{APP_ID}/groups/{GROUP_ID}/buckets/{BUCKET_NAME}/objects
 * <li>https://hostname/api/apps/{APP_ID}/things/{THING_ID}/buckets/{BUCKET_NAME}/objects
 * </ul>
 *
 */
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
		if (contentType == null) {
			contentType = "application/json";
		}
		if (object == null) {
			throw new IllegalArgumentException("object is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MediaType.parse(contentType), object.getJsonObject());
		try {
			Response response = this.execute(request);
			String version = response.header("ETag");
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			String objectID = KiiObject.PROPERTY_OBJECT_ID.get(responseBody);
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
