package com.kii.cloud.rest.client.model;

public abstract class KiiScopeURI extends KiiURI {
	
	protected final String scopeID;
	
	public KiiScopeURI(KiiScope scope, String scopeID) {
		super(scope);
		this.scopeID = scopeID;
	}
	public String getScopeID() {
		return scopeID;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(SCHEME);
		sb.append(this.scope.getCollectionName());
		sb.append("/");
		sb.append(this.scopeID);
		sb.append("/");
		return sb.toString();
	}
}
