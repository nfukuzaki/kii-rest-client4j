package com.kii.cloud.rest.client.resource.push;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.KiiRestException;
import com.kii.cloud.rest.client.model.push.KiiPushMessage;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.kii.cloud.rest.client.util.GsonUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

/**
 * Represents the push message resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/topics/{TOPIC_NAME}/push/messages
 * <li>https://hostname/api/apps/{APP_ID}/users/{USER_IDENTIFIER}/topics/{TOPIC_NAME}/push/messages
 * <li>https://hostname/api/apps/{APP_ID}/groups/{GROUP_ID}/topics/{TOPIC_NAME}/push/messages
 * <li>https://hostname/api/apps/{APP_ID}/things/{THING_ID}/topics/{TOPIC_NAME}/push/messages
 * </ul>
 *
 */
public class KiiPushMessageResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/push/messages";
	
	public static final MediaType MEDIA_TYPE_SEND_PUSH_MESSAGE_REQUEST = MediaType.parse("application/vnd.kii.SendPushMessageRequest+json");
	
	public KiiPushMessageResource(KiiTopicResource parent) {
		super(parent);
	}
	
	/**
	 * @param message
	 * @return pushMessageID
	 * @throws KiiRestException
	 */
	public String send(KiiPushMessage message) throws KiiRestException {
		if (message == null) {
			throw new IllegalArgumentException("message is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MEDIA_TYPE_SEND_PUSH_MESSAGE_REQUEST, message.toJson());
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return GsonUtils.getString(responseBody, "pushMessageID");
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
