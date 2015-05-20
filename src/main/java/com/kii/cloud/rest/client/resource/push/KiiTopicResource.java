package com.kii.cloud.rest.client.resource.push;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.model.push.KiiPushMessage;
import com.kii.cloud.rest.client.model.storage.KiiThing;
import com.kii.cloud.rest.client.model.storage.KiiUser;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiScopedResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.kii.cloud.rest.client.util.GsonUtils;
import com.kii.cloud.rest.client.util.StringUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

/**
 * Represents the topic resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/topics/{TOPIC_NAME}
 * <li>https://hostname/api/apps/{APP_ID}/users/{USER_IDENTIFIER}/topics/{TOPIC_NAME}
 * <li>https://hostname/api/apps/{APP_ID}/groups/{GROUP_ID}/topics/{TOPIC_NAME}
 * <li>https://hostname/api/apps/{APP_ID}/things/{THING_ID}/topics/{TOPIC_NAME}
 * </ul>
 *
 */
public class KiiTopicResource extends KiiRestSubResource implements KiiScopedResource {
	
	public static final MediaType MEDIA_SEND_PUSH_MESSAGE_REQUEST = MediaType.parse("application/vnd.kii.SendPushMessageRequest+json");
	
	private final String name;
	public KiiTopicResource(KiiTopicsResource parent, String name) {
		super(parent);
		if (StringUtils.isEmpty(name)) {
			throw new IllegalArgumentException("name is null or empty");
		}
		this.name = name;
	}
	@Override
	public KiiScope getScope() {
		return ((KiiTopicsResource)this.parent).getScope();
	}
	/**
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/creating-topic/
	 */
	public void create() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.PUT, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * NOTE:This feature has not documented yet.
	 * @return
	 * @throws KiiRestException
	 */
	public boolean exists() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.HEAD, headers);
		try {
			Response response = this.execute(request);
			return response.isSuccessful();
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/deleting-topic/
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
	 * @param user
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/subscribing-topic/
	 */
	public void subscribe(KiiUser user) throws KiiRestException {
		if (user == null) {
			throw new IllegalArgumentException("user is null");
		}
		this.subscribeByUser(user.getUserID());
	}
	/**
	 * @param userID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/subscribing-topic/
	 */
	public void subscribeByUser(String userID) throws KiiRestException {
		if (userID == null) {
			throw new IllegalArgumentException("userID is null");
		}
		this.subscribe("users", userID);
	}
	/**
	 * @param thing
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/subscribing-topic/
	 */
	public void subscribe(KiiThing thing) throws KiiRestException {
		if (thing == null) {
			throw new IllegalArgumentException("thing is null");
		}
		this.subscribeByThing(thing.getThingID());
	}
	/**
	 * @param thingID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/subscribing-topic/
	 */
	public void subscribeByThing(String thingID) throws KiiRestException {
		if (thingID == null) {
			throw new IllegalArgumentException("thingID is null");
		}
		this.subscribe("things", thingID);
	}
	private void subscribe(String subscriberType, String id) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/push/subscriptions/%s/%s", subscriberType, id), Method.PUT, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param user
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/subscribing-topic/
	 */
	public void unsubscribe(KiiUser user) throws KiiRestException {
		if (user == null) {
			throw new IllegalArgumentException("user is null");
		}
		this.unsubscribeByUser(user.getUserID());
	}
	/**
	 * @param userID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/subscribing-topic/
	 */
	public void unsubscribeByUser(String userID) throws KiiRestException {
		if (userID == null) {
			throw new IllegalArgumentException("userID is null");
		}
		this.unsubscribe("users", userID);
	}
	/**
	 * @param thing
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/subscribing-topic/
	 */
	public void unsubscribe(KiiThing thing) throws KiiRestException {
		if (thing == null) {
			throw new IllegalArgumentException("thing is null");
		}
		this.unsubscribeByThing(thing.getThingID());
	}
	/**
	 * @param thingID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/subscribing-topic/
	 */
	public void unsubscribeByThing(String thingID) throws KiiRestException {
		if (thingID == null) {
			throw new IllegalArgumentException("thingID is null");
		}
		this.unsubscribe("things", thingID);
	}
	private void unsubscribe(String unsubscriberType, String id) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/push/subscriptions/%s/%s", unsubscriberType, id), Method.DELETE, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param user
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/checking-subscription/
	 */
	public boolean isSubscribed(KiiUser user) throws KiiRestException {
		if (user == null) {
			throw new IllegalArgumentException("user is null");
		}
		return this.isSubscribedByUser(user.getUserID());
	}
	/**
	 * @param userID
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/checking-subscription/
	 */
	public boolean isSubscribedByUser(String userID) throws KiiRestException {
		if (userID == null) {
			throw new IllegalArgumentException("userID is null");
		}
		return this.isSubscribed("users", userID);
	}
	/**
	 * @param thing
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/checking-subscription/
	 */
	public boolean isSubscribed(KiiThing thing) throws KiiRestException {
		if (thing == null) {
			throw new IllegalArgumentException("thing is null");
		}
		return this.isSubscribedByThing(thing.getThingID());
	}
	/**
	 * @param thingID
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/checking-subscription/
	 */
	public boolean isSubscribedByThing(String thingID) throws KiiRestException {
		if (thingID == null) {
			throw new IllegalArgumentException("thingID is null");
		}
		return this.isSubscribed("things", thingID);
	}
	private boolean isSubscribed(String subscriberType, String id) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/push/subscriptions/%s/%s", subscriberType, id), Method.GET, headers);
		try {
			Response response = this.execute(request);
			return response.isSuccessful();
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param message
	 * @return pushMessageID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/sending-messages/
	 */
	public String send(KiiPushMessage message) throws KiiRestException {
		if (message == null) {
			throw new IllegalArgumentException("message is null");
		}
		return this.send(message.toJson());
	}
	/**
	 * @param message
	 * @return pushMessageID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/sending-messages/
	 */
	public String send(JsonObject message) throws KiiRestException {
		if (message == null) {
			throw new IllegalArgumentException("message is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/push/messages"), Method.POST, headers, MEDIA_SEND_PUSH_MESSAGE_REQUEST, message);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return GsonUtils.getString(responseBody, "pushMessageID");
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	public KiiTopicAclResource acl() {
		return new KiiTopicAclResource(this);
	}
	@Override
	public String getPath() {
		return "/" + this.name;
	}
}
