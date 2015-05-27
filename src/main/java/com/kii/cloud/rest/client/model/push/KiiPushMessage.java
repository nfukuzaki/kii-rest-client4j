package com.kii.cloud.rest.client.model.push;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.util.GsonUtils;

public class KiiPushMessage {
	
	private final JsonObject messageMeta = new JsonObject();
	private final JsonObject messageBody;
	private KiiAPNSMessage apns = null;
	private KiiGCMMessage gcm = null;
	private KiiJPushMessage jpush = null;
	private KiiMqttMessage mqtt = null;
	
	public KiiPushMessage(JsonObject messageBody) {
		if (messageBody == null) {
			throw new IllegalArgumentException("messageBody is null");
		}
		this.messageBody = messageBody;
	}
	public KiiPushMessage setAPNS(KiiAPNSMessage apns) {
		if (apns != null) {
			apns.setEnable(true);
		}
		this.apns = apns;
		return this;
	}
	public KiiPushMessage setGCM(KiiGCMMessage gcm) {
		if (gcm != null) {
			gcm.setEnable(true);
		}
		this.gcm = gcm;
		return this;
	}
	public KiiPushMessage setJPush(KiiJPushMessage jpush) {
		if (jpush != null) {
			jpush.setEnable(true);
		}
		this.jpush = jpush;
		return this;
	}
	public KiiPushMessage setMqtt(KiiMqttMessage mqtt) {
		if (mqtt != null) {
			mqtt.setEnable(true);
		}
		this.mqtt = mqtt;
		return this;
	}
	
	public KiiPushMessage setPushMessageType(String pushMessageType) {
		this.messageMeta.addProperty("pushMessageType", pushMessageType);
		return this;
	}
	public KiiPushMessage setSendAppID(boolean isSendAppId) {
		this.messageMeta.addProperty("sendAppID", isSendAppId);
		return this;
	}
	public KiiPushMessage setSendSender(boolean sendSender) {
		this.messageMeta.addProperty("sendSender", sendSender);
		return this;
	}
	public KiiPushMessage setSendWhen(boolean sendWhen) {
		this.messageMeta.addProperty("sendWhen", sendWhen);
		return this;
	}
	public KiiPushMessage setSendOrigin(boolean sendOrigin) {
		this.messageMeta.addProperty("sendOrigin", sendOrigin);
		return this;
	}
	public KiiPushMessage setSendObjectScope(boolean sendObjectScope) {
		this.messageMeta.addProperty("sendObjectScope", sendObjectScope);
		return this;
	}
	public KiiPushMessage setSendTopicID(boolean sendTopicID) {
		this.messageMeta.addProperty("sendTopicID", sendTopicID);
		return this;
	}
	public KiiPushMessage setSendToProduction(boolean sendToProduction) {
		this.messageMeta.addProperty("sendToProduction", sendToProduction);
		return this;
	}
	public KiiPushMessage setSendToDevelopment(boolean sendToDevelopment) {
		this.messageMeta.addProperty("sendToDevelopment", sendToDevelopment);
		return this;
	}
	public JsonObject toJson() {
		JsonObject result = GsonUtils.clone(this.messageMeta);
		result.add("data", GsonUtils.clone(this.messageBody));
		if (this.apns == null) {
			this.apns = new KiiAPNSMessage();
		}
		result.add("apns", this.apns.toJson());
		if (this.gcm == null) {
			this.gcm = new KiiGCMMessage();
		}
		result.add("gcm", this.gcm.toJson());
		if (this.jpush == null) {
			this.jpush = new KiiJPushMessage();
		}
		result.add("jpush", this.jpush.toJson());
		if (this.mqtt == null) {
			this.mqtt = new KiiMqttMessage();
		}
		result.add("mqtt", this.mqtt.toJson());
		return result;
	}
}
