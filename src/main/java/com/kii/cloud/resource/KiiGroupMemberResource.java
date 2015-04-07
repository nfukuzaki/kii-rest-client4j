package com.kii.cloud.resource;

import java.util.Map;

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
	public void add() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		this.executePut(headers);
	}
	public void remove() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		this.executeDelete(headers);
	}
}
