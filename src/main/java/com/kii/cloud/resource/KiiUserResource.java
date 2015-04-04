package com.kii.cloud.resource;

import com.kii.cloud.model.KiiPseudoUser;
import com.kii.cloud.model.KiiUser;

public class KiiUserResource extends KiiRestSubResource {
	private final String identifier;
	public KiiUserResource(KiiAppResource parent, String identifier) {
		super(parent);
		this.identifier = identifier;
	}
	public KiiPseudoUser get() {
		return null;
	}
	public KiiPseudoUser update(KiiPseudoUser user) {
		return null;
	}
	public void delete() {
	}
	public KiiBucketResource bucket(String name) {
		return new KiiBucketResource(this, name);
	}
	public KiiTopicsResource topic() {
		return null;
	}
	public KiiTopicResource topic(String name) {
		return null;
	}
	@Override
	public String getPath() {
		return KiiUsersResource.BASE_PATH + "/" + KiiUser.getAccountType(this.identifier) + this.identifier;
	}
}
