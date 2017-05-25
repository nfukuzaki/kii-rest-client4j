package com.kii.cloud.rest.client.resource.storage;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.model.storage.KiiObject;
import com.kii.cloud.rest.client.model.uri.KiiObjectURI;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiScopedResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.kii.cloud.rest.client.util.Path;
import com.kii.cloud.rest.client.util.StringUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

/**
 * Represents the objects resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/buckets/{BUCKET_ID}/objects
 * <li>https://hostname/api/apps/{APP_ID}/users/{USER_IDENTIFIER}/buckets/{BUCKET_ID}/objects
 * <li>https://hostname/api/apps/{APP_ID}/groups/{GROUP_ID}/buckets/{BUCKET_ID}/objects
 * <li>https://hostname/api/apps/{APP_ID}/things/{THING_ID}/buckets/{BUCKET_ID}/objects
 * </ul>
 *
 */
public class KiiObjectsResource extends KiiRestSubResource implements KiiScopedResource {
	public static final String BASE_PATH = "/objects";
	public KiiObjectsResource(KiiBucketResource parent) {
		super(parent);
	}
	@Override
	public KiiScope getScope() {
		return ((KiiBucketResource)this.parent).getScope();
	}
	/**
	 * @param object
	 * @throws KiiRestException
	 */
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
			return object.setObjectID(objectID)
					.setVersion(version)
					.setURI(KiiObjectURI.newURI(((KiiBucketResource)parent).getURI(), objectID));
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}

	/**
	 * Save object specifying its ID.
	 * @param contentType If null application/json is applied.
	 * @param objectID ID of the object.
	 * @param object Object to be stored.
	 * @return Stored object.
	 * @throws KiiRestException
	 * @see <a href="http://docs.kii.com/en/guides/cloudsdk/rest/managing-data/object-storages/creating/#creating-an-object-with-id">creating-an-object-with-id</a>
	 */
	public KiiObject save(String contentType, String objectID, KiiObject object) throws KiiRestException {
		if (contentType == null) {
			contentType = "application/json";
		}
		if (object == null) {
			throw new IllegalArgumentException("object is null");
		}
		if (StringUtils.isEmpty(objectID)) {
			throw new IllegalArgumentException("object ID is empty.");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		String url = Path.combine(getUrl(), objectID);
		KiiRestRequest request = new KiiRestRequest(url, Method.PUT, headers, MediaType.parse(contentType), object.getJsonObject());
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			String version = response.headers().get("ETag");
			return object.setObjectID(objectID)
					.setVersion(version)
					.setURI(KiiObjectURI.newURI(((KiiBucketResource)parent).getURI(), objectID));
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
