package com.kii.cloud.resource.servercode;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.annotation.AdminAPI;
import com.kii.cloud.model.servercode.KiiScheduleExecutionQuery;
import com.kii.cloud.model.servercode.KiiScheduleExecutionQueryResult;
import com.kii.cloud.model.servercode.KiiScheduleExecutionResult;
import com.kii.cloud.resource.KiiRestRequest;
import com.kii.cloud.resource.KiiRestSubResource;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

public class KiiServerCodeHookExecutionsResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/executions";
	
	public static final MediaType MEDIA_TYPE_SCHEDULE_EXECUTION_QUERY_REQUEST = MediaType.parse("application/vnd.kii.ScheduleExecutionQueryRequest+json");
	
	public KiiServerCodeHookExecutionsResource(KiiServerCodeHooksResource parent) {
		super(parent);
	}
	@AdminAPI
	public KiiScheduleExecutionResult get(String scheduleExecutionID) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/" + scheduleExecutionID), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiScheduleExecutionResult(responseBody);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@AdminAPI
	public KiiScheduleExecutionQueryResult query(KiiScheduleExecutionQuery query) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/query"), Method.POST, headers, MEDIA_TYPE_SCHEDULE_EXECUTION_QUERY_REQUEST, query.toJson());
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiScheduleExecutionQueryResult(query, responseBody);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
