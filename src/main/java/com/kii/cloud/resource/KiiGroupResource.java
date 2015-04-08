package com.kii.cloud.resource;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiGroup;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

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
	/**
	 * NOTE:This feature has not documented yet.
	 * @return
	 * @throws KiiRestException
	 */
	public KiiGroup get() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiGroup(responseBody);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param userID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-groups/changing-a-group-owner/
	 */
	public void changeOwner(String userID) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("owner", userID);
		KiiRestRequest request = new KiiRestRequest(getUrl("/owner"), Method.PUT, headers, MEDIA_TYPE_GROUP_OWNER_CHANGE_REQUEST, requestBody);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param name
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-groups/changing-a-group-name/
	 */
	public void changeName(String name) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/name"), Method.PUT, headers, MEDIA_TYPE_TEXT_PLAIN, name);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-groups/deleting-a-group/
	 */
	public void delete() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.DELETE, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
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
