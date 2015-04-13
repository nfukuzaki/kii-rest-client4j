package com.kii.cloud.resource;

import java.util.Map;

import com.kii.cloud.model.KiiCredentialsContainer;
import com.kii.cloud.model.push.KiiPushInstallation.InstallationType;
import com.kii.cloud.model.storage.KiiGroup;
import com.kii.cloud.model.storage.KiiThing;
import com.kii.cloud.model.storage.KiiUser;

public class KiiAppResource extends KiiRestResource {
	public static final String BASE_PATH = "/apps";
	
	private final String appID;
	private final String appKey;
	private final String endpoint;
	private final KiiCredentialsContainer credentials;
	
	public KiiAppResource(String appID, String appKey, String endpoint, KiiCredentialsContainer credentials) {
		this.appID = appID;
		this.appKey = appKey;
		this.endpoint = endpoint;
		this.credentials = credentials;
	}
	public KiiOAuthResource oauth() {
		return new KiiOAuthResource(this);
	}
	public KiiAppConfigurationResource configuration() {
		return new KiiAppConfigurationResource(this);
	}
	public KiiUsersResource users() {
		return new KiiUsersResource(this);
	}
	public KiiUserResource users(String identifier) {
		return new KiiUserResource(this.users(), identifier);
	}
	public KiiUserResource users(KiiUser user) {
		return new KiiUserResource(this.users(), user.getUserID());
	}
	public KiiThingsResource things() {
		return new KiiThingsResource(this);
	}
	public KiiThingResource things(KiiThing thing) {
		return new KiiThingResource(this.things(), thing.getIdentifier());
	}
	public KiiThingResource things(String identifier) {
		return new KiiThingResource(this.things(), identifier);
	}
	public KiiGroupsResource groups() {
		return new KiiGroupsResource(this);
	}
	public KiiGroupResource groups(KiiGroup group) {
		return groups(group.getGroupID());
	}
	public KiiGroupResource groups(String groupID) {
		return new KiiGroupResource(this.groups(), groupID);
	}
	public KiiBucketResource buckets(String name) {
		return new KiiBucketResource(this, name);
	}
	public KiiEncryptedBucketResource encryptedBuckets(String name) {
		return new KiiEncryptedBucketResource(this, name);
	}
	public KiiTopicResource topics(String name) {
		return new KiiTopicResource(this, name);
	}
	public KiiScopeAclResource acl() {
		return new KiiScopeAclResource(this);
	}
	public KiiPushInstallationsResource installations() {
		return new KiiPushInstallationsResource(this);
	}
	public KiiPushInstallationResource installations(String installationID) {
		return new KiiPushInstallationResource(this.installations(), installationID);
	}
	public KiiPushInstallationResource installations(InstallationType installationType, String installationRegistrationID) {
		return new KiiPushInstallationResource(this.installations(), installationType, installationRegistrationID);
	}
	public KiiServerCodesResource servercode() {
		return new KiiServerCodesResource(this);
	}
	public KiiServerCodeResource servercode(String version) {
		return new KiiServerCodeResource(servercode(), version);
	}
	public KiiServerCodeHooksResource hooks() {
		return new KiiServerCodeHooksResource(this);
	}
	public KiiServerCodeHookResource hooks(String version) {
		return new KiiServerCodeHookResource(this.hooks(), version);
	}
	
	public KiiEventsResource events() {
		return new KiiEventsResource(this);
	}
	public KiiAnalyticsResource analytics(String aggregationRuleID) {
		return new KiiAnalyticsResource(this, aggregationRuleID);
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
		if (this.credentials != null) {
			headers.put("Authorization", "Bearer " + this.credentials.getAccessToken());
		}
	}
}
