package com.kii.cloud.resource.analytics;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.annotation.AnonymousAPI;
import com.kii.cloud.model.analytics.KiiEvent;
import com.kii.cloud.resource.KiiAppResource;
import com.kii.cloud.resource.KiiRestRequest;
import com.kii.cloud.resource.KiiRestSubResource;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

/**
 * Represents the event resource for analytics like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/events
 * </ul>
 */
public class KiiEventsResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/events";
	
	public static final MediaType MEDIA_TYPE_EVENT_RECORD = MediaType.parse("application/vnd.kii.EventRecord+json");
	
	public KiiEventsResource(KiiAppResource parent) {
		super(parent);
	}
	
	/**
	 * @param event
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-analytics/flex-analytics/analyze-event-data/
	 */
	@AnonymousAPI
	public void upload(KiiEvent event) throws KiiRestException {
		Map<String, String> headers = this.newAppHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MEDIA_TYPE_EVENT_RECORD, event.getJsonObject());
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param events
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-analytics/flex-analytics/analyze-event-data/
	 */
	@AnonymousAPI
	public void upload(List<KiiEvent> events) throws KiiRestException {
		Map<String, String> headers = this.newAppHeaders();
		JsonArray requestBody = new JsonArray();
		for (KiiEvent event : events) {
			requestBody.add(event.getJsonObject());
		}
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MEDIA_TYPE_EVENT_RECORD, requestBody);
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
