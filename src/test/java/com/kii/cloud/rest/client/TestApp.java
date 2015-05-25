package com.kii.cloud.rest.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.KiiRest.Site;
import com.kii.cloud.rest.client.util.GsonUtils;

public class TestApp {
	private final JsonObject json;
	public TestApp(JsonObject json) {
		this.json = json;
	}
	public String getAppID() {
		return GsonUtils.getString(this.json, "AppID");
	}
	public String getAppKey() {
		return GsonUtils.getString(this.json, "AppKey");
	}
	public Site getSite() {
		return Enum.valueOf(Site.class, GsonUtils.getString(this.json, "Site"));
	}
	public String getClientID() {
		return GsonUtils.getString(this.json, "ClientID");
	}
	public void setClientID(String clientID) {
		this.json.addProperty("ClientID", clientID);
	}
	public String getClientSecret() {
		return GsonUtils.getString(this.json, "ClientSecret");
	}
	public void setClientSecret(String clientSecret) {
		this.json.addProperty("ClientSecret", clientSecret);
	}
	public boolean hasAppAdminCredentials() {
		return this.getClientID() != null && this.getClientSecret() != null;
	}
	public Long getAggregationRuleID() {
		return GsonUtils.getLong(this.json, "AggregationRuleID");
	}
	public boolean getFlag(String name) {
		return GsonUtils.getBoolean(this.json, name);
	}
	public boolean isEnabledPush(String name) {
		JsonArray array = GsonUtils.getJsonArray(this.json, "PushEnabled");
		for (int i = 0; i < array.size(); i++) {
			if (GsonUtils.getBoolean(array.get(i).getAsJsonObject(), name) == Boolean.TRUE) {
				return true;
			}
		}
		return false;
	}
	public boolean isEnabledSNS(String name) {
		JsonArray array = GsonUtils.getJsonArray(this.json, "SNSEnabled");
		for (int i = 0; i < array.size(); i++) {
			if (GsonUtils.getBoolean(array.get(i).getAsJsonObject(), name) == Boolean.TRUE) {
				return true;
			}
		}
		return false;
	}
}
