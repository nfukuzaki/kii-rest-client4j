package com.kii.cloud.rest.client.resource.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.model.storage.KiiGroup;
import com.kii.cloud.rest.client.model.storage.KiiGroupMembers;
import com.kii.cloud.rest.client.model.storage.KiiUser;
import com.kii.cloud.rest.client.model.uri.KiiGroupURI;
import com.kii.cloud.rest.client.resource.KiiAppResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiScopedResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.kii.cloud.rest.client.util.GsonUtils;
import com.kii.cloud.rest.client.util.StringUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

/**
 * Represents the groups resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/groups
 * </ul>
 */
public class KiiGroupsResource extends KiiRestSubResource implements KiiScopedResource {
	
	public static final MediaType MEDIA_TYPE_GROUP_CREATION_REQUEST = MediaType.parse("application/vnd.kii.GroupCreationRequest+json");
	
	public static final String BASE_PATH = "/groups";
	
	public KiiGroupsResource(KiiAppResource parent) {
		super(parent);
	}
	/**
	 * @param group
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-groups/creating-a-group/
	 */
	public List<String> save(KiiGroup group) throws KiiRestException {
		return this.save(group, null);
	}
	/**
	 * @param group
	 * @param members
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-groups/creating-a-group/
	 */
	public List<String> save(KiiGroup group, KiiGroupMembers members) throws KiiRestException {
		if (group == null) {
			throw new IllegalArgumentException("group is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject requestBody = GsonUtils.clone(group.getJsonObject());
		if (members != null) {
			KiiGroupMembers.PROPERTY_MEMBERS.set(requestBody, members.toJsonArrayAsRequest());
		}
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MEDIA_TYPE_GROUP_CREATION_REQUEST, requestBody);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			String groupID = KiiGroup.PROPERTY_GROUP_ID.get(responseBody);
			group.setGroupID(groupID);
			group.setURI(KiiGroupURI.newURI(getAppID(), groupID));
			List<String> notFoundusers = new ArrayList<String>();
			JsonArray array = KiiGroup.PROPERTY_NOT_FOUND_USERS.get(responseBody);
			for (int i = 0; i < array.size(); i++) {
				notFoundusers.add(array.get(i).getAsString());
			}
			return notFoundusers;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param user
	 * @return
	 * @throws KiiRestException
	 */
	public List<KiiGroup> getOwnGroups(KiiUser user) throws KiiRestException {
		if (user == null) {
			throw new IllegalArgumentException("user is null");
		}
		return this.getOwnGroups(user.getUserID());
	}
	/**
	 * 
	 * NOTE:This feature has not documented yet.
	 * @param userID
	 * @return
	 * @throws KiiRestException
	 */
	public List<KiiGroup> getOwnGroups(String userID) throws KiiRestException {
		if (userID == null) {
			throw new IllegalArgumentException("userID is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("?owner=" + StringUtils.urlEncode(userID)), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			JsonArray array = responseBody.getAsJsonArray("groups");
			List<KiiGroup> groups = new ArrayList<KiiGroup>();
			for (int i = 0; i < array.size(); i++) {
				KiiGroup group = new KiiGroup(array.get(i).getAsJsonObject());
				group.setURI(KiiGroupURI.newURI(this.getAppID(), group.getGroupID()));
				groups.add(group);
			}
			return groups;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param user
	 * @return
	 * @throws KiiRestException
	 */
	public List<KiiGroup> getBelongGroups(KiiUser user) throws KiiRestException {
		if (user == null) {
			throw new IllegalArgumentException("user is null");
		}
		return this.getBelongGroups(user.getUserID());
	}
	/**
	 * @param userID
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/ja/guides/rest/managing-groups/listing-groups/
	 */
	public List<KiiGroup> getBelongGroups(String userID) throws KiiRestException {
		if (userID == null) {
			throw new IllegalArgumentException("userID is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("?is_member=" + StringUtils.urlEncode(userID)), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			JsonArray array = responseBody.getAsJsonArray("groups");
			List<KiiGroup> groups = new ArrayList<KiiGroup>();
			for (int i = 0; i < array.size(); i++) {
				KiiGroup group = new KiiGroup(array.get(i).getAsJsonObject());
				group.setURI(KiiGroupURI.newURI(this.getAppID(), group.getGroupID()));
				groups.add(group);
			}
			return groups;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
	@Override
	public KiiScope getScope() {
		return KiiScope.GROUP;
	}
}
