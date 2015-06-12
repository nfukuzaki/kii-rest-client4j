package com.kii.cloud.rest.client.model.uri;

import com.kii.cloud.rest.client.model.KiiScope;

/**
 * Represents the XXXX URI like following URIs:
 * 
 * <ul>
 * <li>kiicloud://{AppID}/bukcets/{BucketID}
 * <li>kiicloud://{AppID}/users/{UserID}/bukcets/{BucketID}
 * <li>kiicloud://{AppID}/group/{GroupID}/bukcets/{BucketID}
 * <li>kiicloud://{AppID}/things/{ThingsID}/bukcets/{BucketID}
 * </ul>
 */
public class KiiBucketURI extends KiiURI {
	
	private final KiiURI parent;
	private final String bucketID;
	
	protected KiiBucketURI(KiiAppURI parent, String bucketID) {
		this.parent = parent;
		this.bucketID = bucketID;
	}
	protected KiiBucketURI(KiiUserURI parent, String bucketID) {
		this.parent = parent;
		this.bucketID = bucketID;
	}
	protected KiiBucketURI(KiiGroupURI parent, String bucketID) {
		this.parent = parent;
		this.bucketID = bucketID;
	}
	protected KiiBucketURI(KiiThingURI parent, String bucketID) {
		this.parent = parent;
		this.bucketID = bucketID;
	}
	public String getScopeID() {
		if (this.parent instanceof KiiUserURI) {
			KiiUserURI userURI = (KiiUserURI)this.parent;
			return userURI.getAccountType().getFullyQualifiedIdentifier(userURI.getIdentifier());
		} else if (this.parent instanceof KiiGroupURI) {
			return ((KiiGroupURI)this.parent).getGroupID();
		} else if (this.parent instanceof KiiThingURI) {
			KiiThingURI thingURI = (KiiThingURI)this.parent;
			return thingURI.getIdentifierType().getFullyQualifiedIdentifier(thingURI.getIdentifier());
		}
		return null;
	}
	public String getBucketID() {
		return this.bucketID;
	}
	@Override
	public KiiScope getScope() {
		return this.parent.getScope();
	}
	@Override
	public String toUriString() {
		return this.parent.toUriString() + "/" + SEGMENT_BUCKETS + "/" + this.bucketID;
	}

}
