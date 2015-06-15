package com.kii.cloud.rest.client.resource.storage;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.model.push.KiiTopic;
import com.kii.cloud.rest.client.model.storage.KiiGroup;
import com.kii.cloud.rest.client.model.storage.KiiUser;
import com.kii.cloud.rest.client.model.uri.KiiGroupURI;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiScopedResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.kii.cloud.rest.client.resource.push.KiiTopicResource;
import com.kii.cloud.rest.client.resource.push.KiiTopicsResource;
import com.kii.cloud.rest.client.util.StringUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

/**
 * Represents the group resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/groups/{GROUP_ID}
 * </ul>
 */
public class KiiGroupResource extends KiiRestSubResource implements KiiScopedResource {
	
	public static final MediaType MEDIA_TYPE_GROUP_OWNER_CHANGE_REQUEST = MediaType.parse("application/vnd.kii.GroupOwnerChangeRequest+json");
	
	private final String groupID;
	public KiiGroupResource(KiiGroupsResource parent, String groupID) {
		super(parent);
		if (StringUtils.isEmpty(groupID)) {
			throw new IllegalArgumentException("groupID is null or empty");
		}
		this.groupID = groupID;
	}
	public KiiScopeAclResource acl() {
		return new KiiScopeAclResource(this);
	}
	public KiiGroupMembersResource members() {
		return new KiiGroupMembersResource(this);
	}
	public KiiGroupMemberResource members(KiiUser user) {
		return this.members(user.getUserID());
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
			KiiGroup group = new KiiGroup(responseBody);
			group.setURI(this.getURI());
			return group;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param user
	 * @throws KiiRestException
	 */
	public void changeOwner(KiiUser user) throws KiiRestException {
		if (user == null) {
			throw new IllegalArgumentException("user is null");
		}
		this.changeOwner(user.getUserID());
	}
	/**
	 * @param userID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-groups/changing-a-group-owner/
	 */
	public void changeOwner(String userID) throws KiiRestException {
		if (userID == null) {
			throw new IllegalArgumentException("userID is null");
		}
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
		if (name == null) {
			throw new IllegalArgumentException("name is null");
		}
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
	public KiiTopicResource topics(KiiTopic topic) {
		if (topic == null) {
			throw new IllegalArgumentException("topic is null"); 
		}
		if (topic.getURI() != null && topic.getURI().getScope() != KiiScope.GROUP) {
			throw new IllegalArgumentException("topic scope is not Group");
		}
		return new KiiTopicResource(this.topics(), topic.getTopicID());
	}
	public KiiTopicResource topics(String topicID) {
		return new KiiTopicResource(this.topics(), topicID);
	}
	@Override
	public String getPath() {
		return "/" + groupID;
	}
	@Override
	public KiiScope getScope() {
		return ((KiiGroupsResource)this.parent).getScope();
	}
	public String getScopeIdentifier() {
		return this.groupID;
	}
	public KiiGroupURI getURI() {
		return KiiGroupURI.newURI(this.getAppID(), this.groupID);
	}
}
