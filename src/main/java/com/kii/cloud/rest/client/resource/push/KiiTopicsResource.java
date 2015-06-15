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
import com.kii.cloud.rest.client.model.push.KiiTopic;
import com.kii.cloud.rest.client.model.uri.KiiTopicURI;
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
	public String getScopeIdentifier() {
		switch (this.getScope()) {
			case APP:
				return null;
			case USER:
				return ((KiiUserResource)this.parent).getScopeIdentifier();
			case GROUP:
				return ((KiiGroupResource)this.parent).getScopeIdentifier();
			case THING:
				return ((KiiThingResource)this.parent).getScopeIdentifier();
		}
		throw new AssertionError("This Topics has unexpected scope.");
	}
	/**
	 * @param topic
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/creating-topic/
	 */
	public KiiTopic create(KiiTopic topic) throws KiiRestException {
		return this.create(topic.getTopicID());
	}
	/**
	 * @param topicID
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/creating-topic/
	 */
	public KiiTopic create(String topicID) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl() + "/" + topicID, Method.PUT, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
			return new KiiTopic(topicID).setURI(this.createTopicURI(topicID));
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * Gets list of topics.
	 * 
	 * @return
	 * @throws KiiRestException
	 */
	public KiiListResult<KiiTopic> list() throws KiiRestException {
		return this.list(null);
	}
	/**
	 * @param paginationKey
	 * @return
	 * @throws KiiRestException
	 */
	public KiiListResult<KiiTopic> list(String paginationKey) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		String param = StringUtils.isEmpty(paginationKey) ? "" : "?paginationKey=" + StringUtils.urlEncode(paginationKey);
		KiiRestRequest request = new KiiRestRequest(getUrl() + param, Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			List<KiiTopic> result = new ArrayList<KiiTopic>();
			JsonArray topics = GsonUtils.getJsonArray(responseBody, "topics");
			for (int i = 0; i < topics.size(); i++) {
				KiiTopic topic = new KiiTopic(GsonUtils.getString(topics.get(i).getAsJsonObject(), "topicID"));
				topic.setURI(this.createTopicURI(topic.getTopicID()));
				result.add(topic);
			}
			String newPaginationKey = GsonUtils.getString(responseBody, "paginationKey");
			return new KiiListResult<KiiTopic>(result, newPaginationKey);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	private KiiTopicURI createTopicURI(String topicID) {
		switch (this.getScope()) {
			case APP:
				return KiiTopicURI.newAppScopeURI(this.getAppID(), topicID);
			case USER:
				return KiiTopicURI.newUserScopeURI(this.getAppID(), ((KiiUserResource)this.parent).getScopeIdentifier(), topicID);
			case GROUP:
				return KiiTopicURI.newGroupScopeURI(this.getAppID(), ((KiiGroupResource)this.parent).getScopeIdentifier(), topicID);
			case THING:
				return KiiTopicURI.newThingScopeURI(this.getAppID(), ((KiiThingResource)this.parent).getScopeIdentifier(), topicID);
		}
		throw new AssertionError("This Topic has unexpected scope.");
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
