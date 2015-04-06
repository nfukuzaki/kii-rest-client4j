package com.kii.cloud.resource;

import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiGroup;

public class KiiGroupResource extends KiiRestSubResource {
	private final String groupID;
	public KiiGroupResource(KiiGroupsResource parent, String groupID) {
		super(parent);
		this.groupID = groupID;
	}
	public KiiGroupMemberResource member() {
		return new KiiGroupMemberResource(this);
	}
	public KiiGroup get() throws KiiRestException {
		return null;
	}
	public void changeOwner() throws KiiRestException {
	}
	public void update(KiiGroup group) throws KiiRestException {
	}
	public void delete() throws KiiRestException {
	}
	public KiiBucketResource bucket(String name) {
		return null;
	}
	public KiiTopicsResource topic() {
		return new KiiTopicsResource(this);
	}
	public KiiTopicResource topic(String name) {
		return new KiiTopicResource(this.topic(), name);
	}
	@Override
	public String getPath() {
		return groupID;
	}
}
