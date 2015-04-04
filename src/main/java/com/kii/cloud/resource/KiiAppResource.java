package com.kii.cloud.resource;

import java.util.Map;

import com.kii.cloud.model.KiiRestContext;
import com.kii.cloud.model.KiiUser;

public class KiiAppResource extends KiiRestResource {
	public static final String BASE_PATH = "/apps";
	
	private final String appID;
	private final String appKey;
	private final String endpoint;
	private final KiiRestContext context;
	
	public KiiAppResource(String appID, String appKey, String endpoint, KiiRestContext context) {
		this.appID = appID;
		this.appKey = appKey;
		this.endpoint = endpoint;
		this.context = context;
	}
	public KiiOAuthResource oauth() {
		return new KiiOAuthResource(this);
	}
	public KiiUsersResource user() {
		return new KiiUsersResource(this);
	}
	public KiiUserResource user(String identifier) {
		return new KiiUserResource(this, identifier);
	}
	public KiiUserResource user(KiiUser user) {
		return new KiiUserResource(this, user.getUserID());
	}
	public KiiGroupsResource group() {
		return new KiiGroupsResource(this);
	}
	public KiiGroupResource group(String groupID) {
		return new KiiGroupResource(this, groupID);
	}
	public KiiTopicsResource topic() {
		return new KiiTopicsResource(this);
	}
	public KiiTopicResource topic(String name) {
		return new KiiTopicResource(this, name);
	}
	public KiiServerCodeResource servercode() {
		return new KiiServerCodeResource(this);
	}
	public KiiServerCodeResource servercode(String version) {
		return new KiiServerCodeResource(this, version);
	}
	@Override
	public String getPath() {
		return this.endpoint + BASE_PATH + "/" + this.appID;
	}
	@Override
	protected KiiRestResource getParent() {
		// This class is root resource
		return null;
	}
	@Override
	protected void setAppHeader(Map<String, String> headers) {
		headers.put("X-Kii-AppID", this.appID);
		headers.put("X-Kii-AppKey", this.appKey);
	}
	@Override
	protected void setAuthorizationHeader(Map<String, String> headers) {
		if (this.context != null) {
			headers.put("Authorization", "Bearer " + this.context.getAccessToken());
		}
	}
}
