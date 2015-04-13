package com.kii.cloud.model.push;

import com.google.gson.JsonObject;
import com.kii.cloud.util.GsonUtils;

public class KiiPushMessage {
	
	private JsonObject message;
	private JsonObject data;
	private APNSMessage apns;
	private GCMMessage gcm;
	private JPushMessage jpush;
	private MqttMessage mqtt;
	
	public JsonObject toJson() {
		JsonObject result = GsonUtils.clone(this.message);
		result.add("data", GsonUtils.clone(this.data));
		result.add("apns", this.apns.toJson());
		result.add("gcm", this.gcm.toJson());
		result.add("jpush", this.jpush.toJson());
		result.add("mqtt", this.mqtt.toJson());
		return result;
	}
	
	public static class APNSMessage {
		private JsonObject message;
		private JsonObject data;
		private JsonObject alert;
		public JsonObject toJson() {
			JsonObject result = GsonUtils.clone(this.message);
			if (this.data != null) {
				result.add("data", GsonUtils.clone(this.data));
			}
			if (this.alert != null) {
				result.add("alert", GsonUtils.clone(this.alert));
			}
			return result;
		}
	}
	public static class GCMMessage {
		private JsonObject message;
		private JsonObject data;
		public JsonObject toJson() {
			JsonObject result = GsonUtils.clone(this.message);
			if (this.data != null) {
				result.add("data", GsonUtils.clone(this.data));
			}
			return result;
		}
	}
	public static class JPushMessage {
		private JsonObject message;
		private JsonObject data;
		public JsonObject toJson() {
			JsonObject result = GsonUtils.clone(this.message);
			if (this.data != null) {
				result.add("data", GsonUtils.clone(this.data));
			}
			return result;
		}
	}
	public static class MqttMessage {
		private JsonObject message;
		private JsonObject data;
		public JsonObject toJson() {
			JsonObject result = GsonUtils.clone(this.message);
			if (this.data != null) {
				result.add("data", GsonUtils.clone(this.data));
			}
			return result;
		}
	}
}
