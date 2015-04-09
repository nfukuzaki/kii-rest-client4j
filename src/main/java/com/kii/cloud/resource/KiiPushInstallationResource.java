package com.kii.cloud.resource;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiMqttEndpoint;
import com.kii.cloud.model.KiiPushInstallation;
import com.kii.cloud.model.KiiPushInstallation.InstallationType;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.squareup.okhttp.Response;

public class KiiPushInstallationResource extends KiiRestSubResource {
	
	private final InstallationType installationType;
	private final String installationRegistrationID;
	private final String installationID;
	
	public KiiPushInstallationResource(KiiPushInstallationsResource parent, InstallationType installationType, String installationRegistrationID) {
		super(parent);
		this.installationType = installationType;
		this.installationRegistrationID = installationRegistrationID;
		this.installationID = null;
	}
	public KiiPushInstallationResource(KiiPushInstallationsResource parent, String installationID) {
		super(parent);
		this.installationType = null;
		this.installationRegistrationID = null;
		this.installationID = installationID;
	}
	
	/**
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/push-notification/preparation/
	 */
	public KiiPushInstallation get() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiPushInstallation(responseBody);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/push-notification/preparation/
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
	/**
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/push-notification/preparation/
	 */
	public KiiMqttEndpoint getMqttEndpoint() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/mqtt-endpoint"), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiMqttEndpoint(responseBody);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@Override
	public String getPath() {
		if (this.installationID != null) {
			return "/" + this.installationID;
		}
		return "/" + this.installationType.name() + ":" + this.installationRegistrationID;
	}
}
