package com.kii.cloud.resource;

import com.kii.cloud.model.KiiGroup;

public class KiiGroupResource extends KiiRestSubResource {
	private final String id;
	public KiiGroupResource(KiiAppResource parent, String id) {
		super(parent);
		this.id = id;
	}
	public KiiGroup get() {
		return null;
	}
	public KiiGroup update(KiiGroup group) {
		return null;
	}
	public void delete() {
	}
	public KiiBucketResource bucket(String name) {
		return null;
	}
	public KiiTopicsResource topic() {
		return null;
	}
	public KiiTopicResource topic(String name) {
		return null;
	}
	@Override
	public String getPath() {
		return null;
	}
}
