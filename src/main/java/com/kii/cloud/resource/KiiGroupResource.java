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
	public KiiGroupMembersResource member() {
		return new KiiGroupMembersResource(this);
	}
	public KiiGroupMemberResource member(String userID) {
		return new KiiGroupMemberResource(member(), userID);
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
	public KiiBucketResource bucket(String name) {
		return null;
	}
	public KiiTopicsResource topic() {
		return new KiiTopicsResource(this);
	}
	public KiiTopicResource topic(String name) {
		return new KiiTopicResource(this.topic(), name);
	}
	@Override
	public String getPath() {
		return "/" + groupID;
	}
}
