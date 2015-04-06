package com.kii.cloud.resource;

import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiGroupMembers;

public class KiiGroupMembersResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/members";
	
	public KiiGroupMembersResource(KiiGroupResource parent) {
		super(parent);
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
	public KiiGroupMembers list() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject response = this.executeGet(headers);
		return new KiiGroupMembers(response);
	}
}
