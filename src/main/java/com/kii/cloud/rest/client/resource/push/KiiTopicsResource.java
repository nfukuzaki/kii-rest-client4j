package com.kii.cloud.rest.client.resource.push;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.KiiListResult;
import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.resource.KiiAppResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiScopedResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.kii.cloud.rest.client.resource.storage.KiiGroupResource;
import com.kii.cloud.rest.client.resource.storage.KiiThingResource;
import com.kii.cloud.rest.client.resource.storage.KiiUserResource;
import com.kii.cloud.rest.client.util.GsonUtils;
import com.kii.cloud.rest.client.util.StringUtils;
import com.squareup.okhttp.Response;

/**
 * Represents the topics resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/topics
 * <li>https://hostname/api/apps/{APP_ID}/users/{USER_IDENTIFIER}/topics
 * <li>https://hostname/api/apps/{APP_ID}/groups/{GROUP_ID}/topics
 * <li>https://hostname/api/apps/{APP_ID}/things/{THING_ID}/topics
 * </ul>
 *
 */
public class KiiTopicsResource extends KiiRestSubResource implements KiiScopedResource {
	public static final String BASE_PATH = "/topics";
	public KiiTopicsResource(KiiAppResource parent) {
		super(parent);
	}
	public KiiTopicsResource(KiiUserResource parent) {
		super(parent);
	}
	public KiiTopicsResource(KiiGroupResource parent) {
		super(parent);
	}
	public KiiTopicsResource(KiiThingResource parent) {
		super(parent);
	}
	@Override
	public KiiScope getScope() {
		return ((KiiScopedResource)this.parent).getScope();
	}
	/**
	 * Gets list of topic names.
	 * 
	 * @return
	 * @throws KiiRestException
	 */
	public KiiListResult<String> list() throws KiiRestException {
		return this.list(null);
	}
	/**
	 * @param paginationKey
	 * @return
	 * @throws KiiRestException
	 */
	public KiiListResult<String> list(String paginationKey) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		String param = StringUtils.isEmpty(paginationKey) ? "" : "?paginationKey=" + StringUtils.urlEncode(paginationKey);
		KiiRestRequest request = new KiiRestRequest(getUrl() + param, Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			List<String> result = new ArrayList<String>();
			JsonArray topics = GsonUtils.getJsonArray(responseBody, "topics");
			for (int i = 0; i < topics.size(); i++) {
				result.add(GsonUtils.getString(topics.get(i).getAsJsonObject(), "topicID"));
			}
			String newPaginationKey = GsonUtils.getString(responseBody, "paginationKey");
			return new KiiListResult<String>(result, newPaginationKey);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
