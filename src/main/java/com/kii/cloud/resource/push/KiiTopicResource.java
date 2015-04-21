package com.kii.cloud.resource.push;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.push.KiiPushMessage;
import com.kii.cloud.model.storage.KiiThing;
import com.kii.cloud.model.storage.KiiUser;
import com.kii.cloud.resource.KiiAppResource;
import com.kii.cloud.resource.KiiRestRequest;
import com.kii.cloud.resource.KiiRestSubResource;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.kii.cloud.resource.storage.KiiGroupResource;
import com.kii.cloud.resource.storage.KiiThingResource;
import com.kii.cloud.resource.storage.KiiUserResource;
import com.kii.cloud.util.GsonUtils;
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
public class KiiTopicResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/topics";
	
	public static final MediaType MEDIA_SEND_PUSH_MESSAGE_REQUEST = MediaType.parse("application/vnd.kii.SendPushMessageRequest+json");
	
	private final String name;
	public KiiTopicResource(KiiAppResource parent, String name) {
		super(parent);
		this.name = name;
	}
	public KiiTopicResource(KiiUserResource parent, String name) {
		super(parent);
		this.name = name;
	}
	public KiiTopicResource(KiiGroupResource parent, String name) {
		super(parent);
		this.name = name;
	}
	public KiiTopicResource(KiiThingResource parent, String name) {
		super(parent);
		this.name = name;
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
		this.subscribeByUser(user.getUserID());
	}
	/**
	 * @param userID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/subscribing-topic/
	 */
	public void subscribeByUser(String userID) throws KiiRestException {
		this.subscribe("users", userID);
	}
	/**
	 * @param thing
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/subscribing-topic/
	 */
	public void subscribe(KiiThing thing) throws KiiRestException {
		this.subscribeByThing(thing.getThingID());
	}
	/**
	 * @param thingID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/subscribing-topic/
	 */
	public void subscribeByThing(String thingID) throws KiiRestException {
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
		this.unsubscribeByUser(user.getUserID());
	}
	/**
	 * @param userID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/subscribing-topic/
	 */
	public void unsubscribeByUser(String userID) throws KiiRestException {
		this.unsubscribe("users", userID);
	}
	/**
	 * @param thing
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/subscribing-topic/
	 */
	public void unsubscribe(KiiThing thing) throws KiiRestException {
		this.unsubscribeByThing(thing.getThingID());
	}
	/**
	 * @param thingID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/subscribing-topic/
	 */
	public void unsubscribeByThing(String thingID) throws KiiRestException {
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
		return this.isSubscribedByUser(user.getUserID());
	}
	/**
	 * @param userID
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/checking-subscription/
	 */
	public boolean isSubscribedByUser(String userID) throws KiiRestException {
		return this.isSubscribed("users", userID);
	}
	/**
	 * @param thing
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/checking-subscription/
	 */
	public boolean isSubscribed(KiiThing thing) throws KiiRestException {
		return this.isSubscribedByThing(thing.getThingID());
	}
	/**
	 * @param thingID
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/checking-subscription/
	 */
	public boolean isSubscribedByThing(String thingID) throws KiiRestException {
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
		return this.send(message.toJson());
	}
	/**
	 * @param message
	 * @return pushMessageID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-push-notification/push-to-user/sending-messages/
	 */
	public String send(JsonObject message) throws KiiRestException {
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
		return BASE_PATH + "/" + this.name;
	}
}
