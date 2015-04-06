package com.kii.cloud.resource;

import java.util.List;

public class KiiGroupMembersResource extends KiiRestSubResource {

	public KiiGroupMembersResource(KiiGroupResource parent) {
		super(parent);
	}
	@Override
	public String getPath() {
		return "/members";
	}
	public List<String> list() {
		return null;
	}
}
