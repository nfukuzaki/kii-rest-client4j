package com.kii.cloud.resource;

public class KiiAclResource extends KiiRestSubResource {
	public KiiAclResource(KiiBucketResource parent) {
		super(parent);
	}
	public KiiAclResource(KiiObjectResource parent) {
		super(parent);
	}
	public KiiAclResource(KiiTopicResource parent) {
		super(parent);
	}
	public void get() {
	}
	public void delete() {
	}
	@Override
	public String getPath() {
		return null;
	}
}
