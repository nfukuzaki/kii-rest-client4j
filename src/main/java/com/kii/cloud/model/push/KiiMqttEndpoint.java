package com.kii.cloud.model.push;

import com.google.gson.JsonObject;
import com.kii.cloud.model.KiiJsonModel;
import com.kii.cloud.model.KiiJsonProperty;

public class KiiMqttEndpoint extends KiiJsonModel {
	
	public static final KiiJsonProperty<String> PROPERTY_INSTALLATION_ID = new KiiJsonProperty<String>(String.class, "installationID");
	public static final KiiJsonProperty<String> PROPERTY_USERNAME = new KiiJsonProperty<String>(String.class, "username");
	public static final KiiJsonProperty<String> PROPERTY_PASSWORD = new KiiJsonProperty<String>(String.class, "password");
	public static final KiiJsonProperty<String> PROPERTY_MQTT_TOPIC = new KiiJsonProperty<String>(String.class, "mqttTopic");
	public static final KiiJsonProperty<String> PROPERTY_HOST = new KiiJsonProperty<String>(String.class, "host");
	public static final KiiJsonProperty<Integer> PROPERTY_PORT_TCP = new KiiJsonProperty<Integer>(Integer.class, "portTCP");
	public static final KiiJsonProperty<Integer> PROPERTY_PORT_SSL = new KiiJsonProperty<Integer>(Integer.class, "portSSL");
	public static final KiiJsonProperty<Integer> PROPERTY_X_MQTT_TTL = new KiiJsonProperty<Integer>(Integer.class, "X-MQTT-TTL");
	
	public KiiMqttEndpoint() {
	}
	public KiiMqttEndpoint(JsonObject json) {
		super(json);
	}
	public String getInstallationID() {
		return PROPERTY_INSTALLATION_ID.get(this.json);
	}
	public KiiMqttEndpoint setInstallationID(String installationID) {
		this.json.addProperty(PROPERTY_INSTALLATION_ID.getName(), installationID);
		return this;
	}
	public String getUsername() {
		return PROPERTY_USERNAME.get(this.json);
	}
	public KiiMqttEndpoint setUsername(String username) {
		this.json.addProperty(PROPERTY_USERNAME.getName(), username);
		return this;
	}
	public String getPassword() {
		return PROPERTY_PASSWORD.get(this.json);
	}
	public KiiMqttEndpoint setPassword(String password) {
		this.json.addProperty(PROPERTY_PASSWORD.getName(), password);
		return this;
	}
	public String getMqttTopic() {
		return PROPERTY_MQTT_TOPIC.get(this.json);
	}
	public KiiMqttEndpoint setMqttTopic(String mqttTopic) {
		this.json.addProperty(PROPERTY_MQTT_TOPIC.getName(), mqttTopic);
		return this;
	}
	public String getHost() {
		return PROPERTY_HOST.get(this.json);
	}
	public KiiMqttEndpoint setHost(String host) {
		this.json.addProperty(PROPERTY_HOST.getName(), host);
		return this;
	}
	public int getPortTcp() {
		return PROPERTY_PORT_TCP.get(this.json);
	}
	public KiiMqttEndpoint setPortTcp(int portTcp) {
		this.json.addProperty(PROPERTY_PORT_TCP.getName(), portTcp);
		return this;
	}
	public int getPortSsl() {
		return PROPERTY_PORT_SSL.get(this.json);
	}
	public KiiMqttEndpoint setPortSsl(int portSsl) {
		this.json.addProperty(PROPERTY_PORT_SSL.getName(), portSsl);
		return this;
	}
	public int getMqttTtl() {
		return PROPERTY_X_MQTT_TTL.get(this.json);
	}
	public KiiMqttEndpoint setMqttTtl(int mqttTtl) {
		this.json.addProperty(PROPERTY_X_MQTT_TTL.getName(), mqttTtl);
		return this;
	}
}
