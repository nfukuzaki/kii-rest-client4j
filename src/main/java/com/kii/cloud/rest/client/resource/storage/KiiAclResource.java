package com.kii.cloud.rest.client.resource.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.storage.KiiAcl;
import com.kii.cloud.rest.client.model.storage.KiiAcl.Action;
import com.kii.cloud.rest.client.model.storage.KiiAcl.Subject;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestResource;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.squareup.okhttp.Response;

/**
 * This class is base class for the ACL resource classes.
 */
public abstract class KiiAclResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/acl";
	
	public KiiAclResource(KiiRestResource parent) {
		super(parent);
	}
	public Map<Action,List<Subject>> list() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			Map<Action,List<Subject>> results = new HashMap<Action, List<Subject>>();
			for (Entry<String, JsonElement> entry : responseBody.entrySet()) {
				Action action = KiiAcl.parseAction(entry.getKey());
				List<Subject> subjects = new ArrayList<Subject>();
				JsonArray array = (JsonArray)entry.getValue();
				for (int i = 0; i < array.size(); i++) {
					subjects.add(Subject.fromJson(array.get(i).getAsJsonObject()));
				}
				Collections.sort(subjects);
				results.put(action, subjects);
			}
			return results;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	protected List<Subject> list(Action action) throws KiiRestException {
		if (action == null) {
			throw new IllegalArgumentException("action is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/" + action), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonArray responseBody = this.parseResponseAsJsonArray(request, response);
			List<Subject> subjects = new ArrayList<Subject>();
			for (int i = 0; i < responseBody.size(); i++) {
				subjects.add(Subject.fromJson(responseBody.get(i).getAsJsonObject()));
			}
			Collections.sort(subjects);
			return subjects;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	protected Subject get(Action action, Subject subject) throws KiiRestException {
		if (action == null) {
			throw new IllegalArgumentException("action is null");
		}
		if (subject == null) {
			throw new IllegalArgumentException("subject is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/%s/%s", action, subject), Method.GET, headers);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return Subject.fromJson(responseBody);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	protected void grant(Action action, Subject subject) throws KiiRestException {
		if (action == null) {
			throw new IllegalArgumentException("action is null");
		}
		if (subject == null) {
			throw new IllegalArgumentException("subject is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/%s/%s", action, subject), Method.PUT, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	protected void revok(Action action, Subject subject) throws KiiRestException {
		if (action == null) {
			throw new IllegalArgumentException("action is null");
		}
		if (subject == null) {
			throw new IllegalArgumentException("subject is null");
		}
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/%s/%s", action, subject), Method.DELETE, headers);
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
