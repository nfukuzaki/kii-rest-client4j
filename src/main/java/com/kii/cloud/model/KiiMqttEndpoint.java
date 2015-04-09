package com.kii.cloud.model;

import com.google.gson.JsonObject;

public class KiiMqttEndpoint extends KiiJsonModel {
	
	public static final KiiJsonProperty PROPERTY_INSTALLATION_ID = new KiiJsonProperty("installationID");
	public static final KiiJsonProperty PROPERTY_USERNAME = new KiiJsonProperty("username");
	public static final KiiJsonProperty PROPERTY_PASSWORD = new KiiJsonProperty("password");
	public static final KiiJsonProperty PROPERTY_MQTT_TOPIC = new KiiJsonProperty("mqttTopic");
	public static final KiiJsonProperty PROPERTY_HOST = new KiiJsonProperty("host");
	public static final KiiJsonProperty PROPERTY_PORT_TCP = new KiiJsonProperty("portTCP");
	public static final KiiJsonProperty PROPERTY_PORT_SSL = new KiiJsonProperty("portSSL");
	public static final KiiJsonProperty PROPERTY_X_MQTT_TTL = new KiiJsonProperty("X-MQTT-TTL");
	
	public KiiMqttEndpoint() {
	}
	public KiiMqttEndpoint(JsonObject json) {
		super(json);
	}
	public String getInstallationID() {
		return PROPERTY_INSTALLATION_ID.getString(this.json);
	}
	public KiiMqttEndpoint setInstallationID(String installationID) {
		this.json.addProperty(PROPERTY_INSTALLATION_ID.getName(), installationID);
		return this;
	}
	public String getUsername() {
		return PROPERTY_USERNAME.getString(this.json);
	}
	public KiiMqttEndpoint setUsername(String username) {
		this.json.addProperty(PROPERTY_USERNAME.getName(), username);
		return this;
	}
	public String getPassword() {
		return PROPERTY_PASSWORD.getString(this.json);
	}
	public KiiMqttEndpoint setPassword(String password) {
		this.json.addProperty(PROPERTY_PASSWORD.getName(), password);
		return this;
	}
	public String getMqttTopic() {
		return PROPERTY_MQTT_TOPIC.getString(this.json);
	}
	public KiiMqttEndpoint setMqttTopic(String mqttTopic) {
		this.json.addProperty(PROPERTY_MQTT_TOPIC.getName(), mqttTopic);
		return this;
	}
	public String getHost() {
		return PROPERTY_HOST.getString(this.json);
	}
	public KiiMqttEndpoint setHost(String host) {
		this.json.addProperty(PROPERTY_HOST.getName(), host);
		return this;
	}
	public int getPortTcp() {
		return PROPERTY_PORT_TCP.getInt(this.json);
	}
	public KiiMqttEndpoint setPortTcp(int portTcp) {
		this.json.addProperty(PROPERTY_PORT_TCP.getName(), portTcp);
		return this;
	}
	public int getPortSsl() {
		return PROPERTY_PORT_SSL.getInt(this.json);
	}
	public KiiMqttEndpoint setPortSsl(int portSsl) {
		this.json.addProperty(PROPERTY_PORT_SSL.getName(), portSsl);
		return this;
	}
	public int getMqttTtl() {
		return PROPERTY_X_MQTT_TTL.getInt(this.json);
	}
	public KiiMqttEndpoint setMqttTtl(int mqttTtl) {
		this.json.addProperty(PROPERTY_X_MQTT_TTL.getName(), mqttTtl);
		return this;
	}
}
