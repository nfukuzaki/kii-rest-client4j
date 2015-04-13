package com.kii.cloud.resource.push;

import com.kii.cloud.resource.KiiRestSubResource;

public class KiiPushMessageResource extends KiiRestSubResource {
	public KiiPushMessageResource(KiiTopicResource parent) {
		super(parent);
	}

	@Override
	public String getPath() {
		return null;
	}
}
