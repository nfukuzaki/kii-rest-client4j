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
		return sb.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((scopeID == null) ? 0 : scopeID.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KiiScopeURI other = (KiiScopeURI) obj;
		if (scopeID == null) {
			if (other.scopeID != null)
				return false;
		} else if (!scopeID.equals(other.scopeID))
			return false;
		return true;
	}
}
