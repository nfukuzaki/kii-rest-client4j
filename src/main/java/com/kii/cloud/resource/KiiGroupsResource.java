package com.kii.cloud.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiGroup;
import com.kii.cloud.model.KiiGroupMembers;
import com.kii.cloud.util.GsonUtils;
import com.squareup.okhttp.MediaType;

public class KiiGroupsResource extends KiiRestSubResource {
	
	public static final MediaType MEDIA_TYPE_GROUP_CREATION_REQUEST = MediaType.parse("application/vnd.kii.GroupCreationRequest+json");
	
	public static final String BASE_PATH = "/groups";
	
	public KiiGroupsResource(KiiAppResource parent) {
		super(parent);
	}
	/**
	 * @param group
	 * @param members
	 * @return not found users.
	 * @throws KiiRestException
	 */
	public List<String> save(KiiGroup group, KiiGroupMembers members) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject request = GsonUtils.clone(group.getJsonObject());
		if (members != null) {
			request.add(KiiGroupMembers.PROPERTY_MEMBERS.getName(), members.toJsonArrayAsRequest());
		}
		JsonObject response = this.executePost(headers, MEDIA_TYPE_GROUP_CREATION_REQUEST, request);
		String groupID = KiiGroup.PROPERTY_GROUP_ID.getString(response);
		group.setGroupID(groupID);
		List<String> notFoundusers = new ArrayList<String>();
		JsonArray array = KiiGroup.PROPERTY_NOT_FOUND_USERS.getJsonArray(response);
		for (int i = 0; i < array.size(); i++) {
			notFoundusers.add(array.get(i).getAsString());
		}
		return notFoundusers;
	}
	public List<KiiGroup> getOwnGroups(String userID) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		Map<String, String> params = new HashMap<String, String>();
		params.put("owner", userID);
		JsonObject response = this.executeGet(headers, params);
		JsonArray array = response.getAsJsonArray("groups");
		List<KiiGroup> groups = new ArrayList<KiiGroup>();
		for (int i = 0; i < array.size(); i++) {
			groups.add(new KiiGroup(array.get(i).getAsJsonObject()));
		}
		return groups;
	}
	public List<KiiGroup> getBelongGroups(String userID) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		Map<String, String> params = new HashMap<String, String>();
		params.put("is_member", userID);
		JsonObject response = this.executeGet(headers, params);
		JsonArray array = response.getAsJsonArray("groups");
		List<KiiGroup> groups = new ArrayList<KiiGroup>();
		for (int i = 0; i < array.size(); i++) {
			groups.add(new KiiGroup(array.get(i).getAsJsonObject()));
		}
		return groups;
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
