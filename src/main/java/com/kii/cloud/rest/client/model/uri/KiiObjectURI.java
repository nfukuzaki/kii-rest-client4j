package com.kii.cloud.rest.client.model.uri;

import com.kii.cloud.rest.client.model.KiiScope;

/**
 * Represents the XXXX URI like following URIs:
 * 
 * <ul>
 * <li>kiicloud://{AppID}/bukcets/{BucketID}/objects/{ObjectID}
 * <li>kiicloud://{AppID}/users/{UserID}/bukcets/{BucketID}/objects/{ObjectID}
 * <li>kiicloud://{AppID}/things/{ThingsID}/bukcets/{BucketID}/objects/{ObjectID}
 * <li>kiicloud://{AppID}/group/{GroupID}/bukcets/{BucketID}/objects/{ObjectID}
 * </ul>
 */
public class KiiObjectURI extends KiiURI {
	
	private final KiiBucketURI parent;
	private final String objectID;
	
	protected KiiObjectURI(KiiBucketURI parent, String objectID) {
		this.parent = parent;
		this.objectID = objectID;
	}
	public String getScopeID() {
		return this.parent.getScopeID();
	}
	public String getBucketID() {
		return this.parent.getBucketID();
	}
	public String getObjectID() {
		return this.objectID;
	}
	@Override
	public KiiScope getScope() {
		return this.parent.getScope();
	}
	@Override
	public String toUriString() {
		return this.parent.toUriString() + "/" + SEGMENT_OBJECTS + "/" + this.objectID;
	}

}
