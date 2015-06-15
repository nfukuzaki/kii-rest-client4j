package com.kii.cloud.rest.client.model.uri;

import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.util.StringUtils;

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
	
	public static KiiBucketURI parse(String str) {
		if (StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException("str is null or empty");
		}
		if (!str.startsWith(SCHEME)) {
			throw new IllegalArgumentException("URI should start with 'kiicloud://'");
		}
		String[] segments = str.replace(SCHEME, "").split("/");
		if (segments.length == 3) {
			if (StringUtils.equals(SEGMENT_BUCKETS, segments[1])) {
				return new KiiBucketURI(new KiiAppURI(segments[0]), segments[2]);
			}
		} else if (segments.length == 5) {
			if (StringUtils.equals(SEGMENT_BUCKETS, segments[3])) {
				if (StringUtils.equals(SEGMENT_USERS, segments[1])) {
					return new KiiBucketURI(new KiiUserURI(new KiiAppURI(segments[0]), segments[2]), segments[4]);
				} else if (StringUtils.equals(SEGMENT_GROUPS, segments[1])) {
					return new KiiBucketURI(new KiiGroupURI(new KiiAppURI(segments[0]), segments[2]), segments[4]);
				} else if (StringUtils.equals(SEGMENT_THINGS, segments[1])) {
					return new KiiBucketURI(new KiiThingURI(new KiiAppURI(segments[0]), segments[2]), segments[4]);
				}
			}
		}
		throw new IllegalArgumentException("invalid URI : " + str);
	}
	
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
