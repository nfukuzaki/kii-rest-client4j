package com.kii.cloud.rest.client.resource.push;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.push.KiiPushInstallation;
import com.kii.cloud.rest.client.model.storage.KiiThing;
import com.kii.cloud.rest.client.model.storage.KiiUser;
import com.kii.cloud.rest.client.resource.KiiAppResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.kii.cloud.rest.client.util.GsonUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

/**
 * Represents the push installation resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/installations
 * </ul>
 */
public class KiiPushInstallationsResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/installations";
	
	public static final MediaType MEDIA_TYPE_INSTALLATION_CREATION_REQUEST = MediaType.parse("application/vnd.kii.InstallationCreationRequest+json");
	
	public KiiPushInstallationsResource(KiiAppResource parent) {
		super(parent);
	}
	
	/**
	 * @param installation
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/push-notification/preparation/
	 */
	public void register(KiiPushInstallation installation) throws KiiRestException {
		if (installation == null) {
			throw new IllegalArgumentException("installation is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MEDIA_TYPE_INSTALLATION_CREATION_REQUEST, installation.getJsonObject());
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			installation.setInstallationID(KiiPushInstallation.PROPERTY_INSTALLATION_ID.get(responseBody));
			installation.setInstallationRegistrationID(KiiPushInstallation.PROPERTY_INSTALLATION_REGISTRATION_ID.get(responseBody));
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/push-notification/preparation/
	 */
	public List<KiiPushInstallation> list() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			JsonArray array = GsonUtils.getJsonArray(responseBody, "installations");
			List<KiiPushInstallation> installations = new ArrayList<KiiPushInstallation>();
			for (int i = 0; i < array.size(); i++) {
				installations.add(new KiiPushInstallation(array.get(i).getAsJsonObject()));
			}
			return installations;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param user
	 * @return
	 * @throws KiiRestException
	 */
	public List<KiiPushInstallation> list(KiiUser user) throws KiiRestException {
		if (user == null) {
			throw new IllegalArgumentException("user is null");
		}
		return this.listByUser(user.getUserID());
	}
	/**
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/push-notification/preparation/
	 */
	public List<KiiPushInstallation> listByUser(String userID) throws KiiRestException {
		if (userID == null) {
			throw new IllegalArgumentException("userID is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("?userID=" + userID), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			JsonArray array = GsonUtils.getJsonArray(responseBody, "installations");
			List<KiiPushInstallation> installations = new ArrayList<KiiPushInstallation>();
			for (int i = 0; i < array.size(); i++) {
				installations.add(new KiiPushInstallation(array.get(i).getAsJsonObject()));
			}
			return installations;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param thing
	 * @return
	 * @throws KiiRestException
	 */
	public List<KiiPushInstallation> list(KiiThing thing) throws KiiRestException {
		if (thing == null) {
			throw new IllegalArgumentException("thing is null");
		}
		return this.listByThing(thing.getThingID());
	}
	/**
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/push-notification/preparation/
	 */
	public List<KiiPushInstallation> listByThing(String thingID) throws KiiRestException {
		if (thingID == null) {
			throw new IllegalArgumentException("thingID is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("?thingID=" + thingID), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			JsonArray array = GsonUtils.getJsonArray(responseBody, "installations");
			List<KiiPushInstallation> installations = new ArrayList<KiiPushInstallation>();
			for (int i = 0; i < array.size(); i++) {
				installations.add(new KiiPushInstallation(array.get(i).getAsJsonObject()));
			}
			return installations;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
