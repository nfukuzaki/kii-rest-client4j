package com.kii.cloud.resource;

import com.kii.cloud.KiiRestException;

public class KiiGroupMemberResource extends KiiRestSubResource {
	
	private final String userID;
	
	public KiiGroupMemberResource(KiiGroupMembersResource parent, String userID) {
		super(parent);
		this.userID = userID;
	}
	@Override
	public String getPath() {
		return "/" + this.userID;
	}
	public void add(String userID) throws KiiRestException {
	}
	public void remove(String userID) throws KiiRestException {
	}
}
