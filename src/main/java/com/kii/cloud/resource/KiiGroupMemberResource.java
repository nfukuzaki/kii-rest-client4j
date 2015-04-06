package com.kii.cloud.resource;

import com.kii.cloud.KiiRestException;

public class KiiGroupMemberResource extends KiiRestSubResource {
	
	
	public KiiGroupMemberResource(KiiGroupResource parent) {
		super(parent);
	}
	@Override
	public String getPath() {
		return null;
	}
	public void get() throws KiiRestException {
	}
	public void add(String userID) throws KiiRestException {
	}
	public void remove(String userID) throws KiiRestException {
	}
}
