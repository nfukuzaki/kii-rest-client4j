package com.kii.cloud.resource;

import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiGroup;
import com.squareup.okhttp.MediaType;

public class KiiGroupResource extends KiiRestSubResource {
	
	public static final MediaType MEDIA_TYPE_GROUP_OWNER_CHANGE_REQUEST = MediaType.parse("application/vnd.kii.GroupOwnerChangeRequest+json");
	
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
	public void changeOwner(String userID) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject request = new JsonObject();
		request.addProperty("owner", userID);
		this.executePut("/owner", headers, MEDIA_TYPE_GROUP_OWNER_CHANGE_REQUEST, request);
	}
	public void changeName(String name) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		this.executePut("/name", headers, MEDIA_TYPE_TEXT_PLAIN, name);
	}
	public void delete() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		this.executeDelete(headers);
	}
	public KiiBucketResource buckets(String name) {
		return new KiiBucketResource(this, name);
	}
	public KiiEncryptedBucketResource encryptedBuckets(String name) {
		return new KiiEncryptedBucketResource(this, name);
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
