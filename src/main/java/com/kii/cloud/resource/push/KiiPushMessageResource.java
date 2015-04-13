package com.kii.cloud.resource.push;

import com.kii.cloud.resource.KiiRestSubResource;

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
	public KiiPushMessageResource(KiiTopicResource parent) {
		super(parent);
	}

	@Override
	public String getPath() {
		return null;
	}
}
