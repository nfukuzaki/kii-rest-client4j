package com.kii.cloud.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.push.KiiPushInstallation;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.kii.cloud.util.GsonUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

public class KiiPushInstallationsResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/installations";
	
	public static final MediaType MEDIA_TYPE_INSTALLATION_CREATION_REQUEST = MediaType.parse("application/vnd.kii.InstallationCreationRequest+json");
	
	public KiiPushInstallationsResource(KiiAppResource parent) {
		super(parent);
	}
	
	/**
	 * @param installation
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/push-notification/preparation/
	 */
	public KiiPushInstallation register(KiiPushInstallation installation) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MEDIA_TYPE_INSTALLATION_CREATION_REQUEST, installation.getJsonObject());
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiPushInstallation(responseBody);
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
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/push-notification/preparation/
	 */
	public List<KiiPushInstallation> listByUser(String userID) throws KiiRestException {
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
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/push-notification/preparation/
	 */
	public List<KiiPushInstallation> listByThing(String thingID) throws KiiRestException {
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
