package com.kii.cloud.rest.client.model.push;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.model.KiiJsonModel;
import com.kii.cloud.rest.client.model.KiiJsonProperty;

public class KiiTopic extends KiiJsonModel {
	
	public static final KiiJsonProperty<String> PROPERTY_TOPIC_ID = new KiiJsonProperty<String>(String.class, "topicID");
	
	public KiiTopic() {
	}
	public KiiTopic(String topicID) {
		PROPERTY_TOPIC_ID.set(this.json, topicID);
	}
	public KiiTopic(JsonObject json) {
		super(json);
	}
	
	public String getTopicID() {
		return PROPERTY_TOPIC_ID.get(this.json);
	}
	public KiiTopic setTopicID(String topicID) {
		PROPERTY_TOPIC_ID.set(this.json, topicID);
		return this;
	}

}
