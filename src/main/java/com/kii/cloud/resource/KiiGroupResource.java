package com.kii.cloud.resource;

import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiGroup;

public class KiiGroupResource extends KiiRestSubResource {
	private final String groupID;
	public KiiGroupResource(KiiGroupsResource parent, String groupID) {
		super(parent);
		this.groupID = groupID;
	}
	public KiiGroupMembersResource members() {
		return new KiiGroupMembersResource(this);
	}
	public KiiGroupMemberResource members(String userID) {
		return new KiiGroupMemberResource(members(), userID);
	}
	public KiiGroup get() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject response = this.executeGet(headers);
		return new KiiGroup(response);
	}
	public void changeOwner() throws KiiRestException {
	}
	public void update(KiiGroup group) throws KiiRestException {
	}
	public void delete() throws KiiRestException {
	}
	public KiiBucketResource buckets(String name) {
		return new KiiBucketResource(this, name);
	}
	public KiiTopicsResource topics() {
		return new KiiTopicsResource(this);
	}
	public KiiTopicResource topics(String name) {
		return new KiiTopicResource(this.topics(), name);
	}
	@Override
	public String getPath() {
		return "/" + groupID;
	}
}
