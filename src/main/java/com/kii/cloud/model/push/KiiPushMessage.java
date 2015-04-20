package com.kii.cloud.model.push;

import com.google.gson.JsonObject;
import com.kii.cloud.util.GsonUtils;

public class KiiPushMessage {
	
	private final JsonObject messageMeta = new JsonObject();
	private final JsonObject messageBody;
	private APNSMessage apns = null;
	private GCMMessage gcm = null;
	private JPushMessage jpush = null;
	private MqttMessage mqtt = null;
	
	public KiiPushMessage(JsonObject messageBody) {
		if (messageBody == null) {
			throw new IllegalArgumentException("messageBody is null");
		}
		this.messageBody = messageBody;
	}
	public KiiPushMessage setAPNS(APNSMessage apns) {
		this.apns = apns;
		return this;
	}
	public KiiPushMessage setGCM(GCMMessage gcm) {
		this.gcm = gcm;
		return this;
	}
	public KiiPushMessage setJPush(JPushMessage jpush) {
		this.jpush = jpush;
		return this;
	}
	public KiiPushMessage setMqtt(MqttMessage mqtt) {
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
			this.apns = new APNSMessage();
		}
		result.add("apns", this.apns.toJson());
		if (this.gcm == null) {
			this.gcm = new GCMMessage();
		}
		result.add("gcm", this.gcm.toJson());
		if (this.jpush == null) {
			this.jpush = new JPushMessage();
		}
		result.add("jpush", this.jpush.toJson());
		if (this.mqtt == null) {
			this.mqtt = new MqttMessage();
		}
		result.add("mqtt", this.mqtt.toJson());
		return result;
	}
}
