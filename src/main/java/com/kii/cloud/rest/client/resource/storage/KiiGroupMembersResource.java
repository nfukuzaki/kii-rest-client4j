package com.kii.cloud.rest.client.resource.storage;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.KiiRestException;
import com.kii.cloud.rest.client.model.storage.KiiGroupMembers;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.squareup.okhttp.Response;

/**
 * Represents the group members resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/groups/{GROUP_ID}/members
 * </ul>
 */
public class KiiGroupMembersResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/members";
	
	public KiiGroupMembersResource(KiiGroupResource parent) {
		super(parent);
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
	/**
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-groups/listing-group-member/
	 */
	public KiiGroupMembers list() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiGroupMembers(responseBody);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
}
