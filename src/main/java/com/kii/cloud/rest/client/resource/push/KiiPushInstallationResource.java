package com.kii.cloud.rest.client.resource.push;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.exception.KiiServiceUnavailableException;
import com.kii.cloud.rest.client.model.push.KiiMqttEndpoint;
import com.kii.cloud.rest.client.model.push.KiiPushInstallation;
import com.kii.cloud.rest.client.model.push.KiiPushInstallation.InstallationType;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.kii.cloud.rest.client.util.StringUtils;
import com.squareup.okhttp.Response;

/**
 * Represents the specified push installation resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/installations/{INSTALLATION_ID}
 * <li>https://hostname/api/apps/{APP_ID}/installations/{INSTALLATION_TYPE}:{INSTALLATION_REGISTRATION_ID}
 * </ul>
 */
public class KiiPushInstallationResource extends KiiRestSubResource {
	
	private final InstallationType installationType;
	private final String installationRegistrationID;
	private final String installationID;
	
	public KiiPushInstallationResource(KiiPushInstallationsResource parent, InstallationType installationType, String installationRegistrationID) {
		super(parent);
		if (installationType == null) {
			throw new IllegalArgumentException("installationType is null");
		}
		if (StringUtils.isEmpty(installationRegistrationID)) {
			throw new IllegalArgumentException("installationRegistrationID is null or empty");
		}
		this.installationType = installationType;
		this.installationRegistrationID = installationRegistrationID;
		this.installationID = null;
	}
	public KiiPushInstallationResource(KiiPushInstallationsResource parent, String installationID) {
		super(parent);
		if (StringUtils.isEmpty(installationID)) {
			throw new IllegalArgumentException("installationID is null or empty");
		}
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
		return this.getMqttEndpoint(false);
	}
	/**
	 * @param retry
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/thing/thing-rest/push-notification/preparation/
	 */
	public KiiMqttEndpoint getMqttEndpoint(boolean retry) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/mqtt-endpoint"), Method.GET, headers);
		try {
			while (true) {
				try {
					Response response = this.execute(request);
					JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
					return new KiiMqttEndpoint(responseBody);
				} catch (KiiServiceUnavailableException e) {
					if (retry) {
						retry = false;
						String waitTime = e.getHttpHeaders().get("Retry-After");
						try {
							if (waitTime != null) {
								Thread.sleep(1000 * Integer.parseInt(waitTime));
							} else {
								Thread.sleep(1000 * 3);
							}
						} catch (InterruptedException ignore) {
						}
					} else {
						throw e;
					}
				}
			}
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
