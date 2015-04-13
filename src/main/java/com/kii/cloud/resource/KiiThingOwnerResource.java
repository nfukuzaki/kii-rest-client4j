package com.kii.cloud.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.storage.KiiThingOwner;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.kii.cloud.util.GsonUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

public class KiiThingOwnerResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/ownership";
	
	public static final MediaType MEDIA_TYPE_THING_OWNERSHIP_CONFIRMATION_REQUEST = MediaType.parse("application/vnd.kii.ThingOwnershipConfirmationRequest+json");
	
	public KiiThingOwnerResource(KiiThingResource parent) {
		super(parent);
	}
	/**
	 * @param owner
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/ownership/
	 */
	public void add(KiiThingOwner owner) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(owner.toString()), Method.PUT, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param owner
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/ownership/
	 */
	public String getPinCode(KiiThingOwner owner) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/request/" + owner.toString()), Method.POST, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return GsonUtils.getString(responseBody, "code");
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param pinCode
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/ownership/
	 */
	public void confirmPinCode(String pinCode) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("code", pinCode);
		KiiRestRequest request = new KiiRestRequest(getUrl("/cofirm"), Method.POST, headers, MEDIA_TYPE_THING_OWNERSHIP_CONFIRMATION_REQUEST, requestBody);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param owner
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/ownership/
	 */
	public boolean exists(KiiThingOwner owner) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(owner.toString()), Method.HEAD, headers);
		try {
			Response response = this.execute(request);
			return response.isSuccessful();
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/ownership/
	 */
	public List<KiiThingOwner> list() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			List<KiiThingOwner> owners = new ArrayList<KiiThingOwner>();
			JsonArray userOwners = GsonUtils.getJsonArray(responseBody, "users");
			for (int i = 0; i < userOwners.size(); i++) {
				owners.add(KiiThingOwner.user(userOwners.get(i).getAsString()));
			}
			JsonArray groupOwners = GsonUtils.getJsonArray(responseBody, "groups");
			for (int i = 0; i < groupOwners.size(); i++) {
				owners.add(KiiThingOwner.group(groupOwners.get(i).getAsString()));
			}
			return owners;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param owner
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/ownership/
	 */
	public void delete(KiiThingOwner owner) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(owner.toString()), Method.DELETE, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
