package com.kii.cloud.rest.client.resource.servercode;

import java.util.ArrayList;
import java.util.List;

import com.kii.cloud.rest.client.annotation.AdminAPI;
import com.kii.cloud.rest.client.annotation.MultipleAPICalls;
import com.kii.cloud.rest.client.exception.KiiNotFoundException;
import com.kii.cloud.rest.client.model.servercode.KiiServerCodeVersion;
import com.kii.cloud.rest.client.model.servercode.KiiServerHookConfiguration;
import com.kii.cloud.rest.client.resource.KiiAppResource;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;

/**
 * Represents the server hook resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/hooks
 * </ul>
 */
public class KiiServerCodeHooksResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/hooks";
	
	public KiiServerCodeHooksResource(KiiAppResource parent) {
		super(parent);
	}
	public KiiServerCodeHookExecutionsResource executions() {
		return new KiiServerCodeHookExecutionsResource(this);
	}
	@AdminAPI
	@MultipleAPICalls
	public List<KiiServerHookConfiguration> list() throws Exception {
		KiiAppResource rest = ((KiiAppResource)this.parent);
		List<KiiServerHookConfiguration> hooks = new ArrayList<KiiServerHookConfiguration>();
		List<KiiServerCodeVersion> versions = rest.servercode().list();
		for (KiiServerCodeVersion version : versions) {
			try {
				KiiServerHookConfiguration hook = rest.hooks(version.getVersionID()).get();
				hook.setCurrent(version.isCurrent());
				hook.setVersionID(version.getVersionID());
				hooks.add(hook);
			} catch (KiiNotFoundException e) {
			}
		}
		return hooks;
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
