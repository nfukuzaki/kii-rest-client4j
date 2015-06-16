package com.kii.cloud.rest.client.model.uri;

import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.util.StringUtils;

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
	
	public static KiiObjectURI newAppScopeURI(String appID, String bucketID, String objectID) {
		return new KiiObjectURI(new KiiBucketURI(new KiiAppURI(appID), bucketID), objectID);
	}
	public static KiiObjectURI newUserScopeURI(String appID, String userIdentifier, String bucketID, String objectID) {
		return new KiiObjectURI(new KiiBucketURI(new KiiUserURI(new KiiAppURI(appID), bucketID), userIdentifier), objectID);
	}
	public static KiiObjectURI newGroupScopeURI(String appID, String groupID, String bucketID, String objectID) {
		return new KiiObjectURI(new KiiBucketURI(new KiiGroupURI(new KiiAppURI(appID), bucketID), groupID), objectID);
	}
	public static KiiObjectURI newThingScopeURI(String appID, String thingIdentifier, String bucketID, String objectID) {
		return new KiiObjectURI(new KiiBucketURI(new KiiThingURI(new KiiAppURI(appID), bucketID), thingIdentifier), objectID);
	}
	public static KiiObjectURI newURI(KiiBucketURI parent, String objectID) {
		return new KiiObjectURI(parent, objectID);
	}

	public static KiiObjectURI parse(String str) {
		if (StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException("str is null or empty");
		}
		if (!str.startsWith(SCHEME)) {
			throw new IllegalArgumentException("URI should start with 'kiicloud://'");
		}
		String[] segments = str.replace(SCHEME, "").split("/");
		if (segments.length == 4) {
			if (StringUtils.equals(SEGMENT_BUCKETS, segments[0]) && StringUtils.equals(SEGMENT_OBJECTS, segments[2])) {
				return new KiiObjectURI(new KiiBucketURI(new KiiAppURI(UNKNOWN_APP_ID), segments[1]), segments[3]);
			}
		} else if (segments.length == 5) {
			if (StringUtils.equals(SEGMENT_BUCKETS, segments[1]) && StringUtils.equals(SEGMENT_OBJECTS, segments[3])) {
				return new KiiObjectURI(new KiiBucketURI(new KiiAppURI(segments[0]), segments[2]), segments[4]);
			}
		} else if (segments.length == 6) {
			if (StringUtils.equals(SEGMENT_BUCKETS, segments[2]) && StringUtils.equals(SEGMENT_OBJECTS, segments[4])) {
				if (StringUtils.equals(SEGMENT_USERS, segments[0])) {
					return new KiiObjectURI(new KiiBucketURI(new KiiUserURI(new KiiAppURI(UNKNOWN_APP_ID), segments[1]), segments[3]), segments[5]);
				} else if (StringUtils.equals(SEGMENT_GROUPS, segments[0])) {
					return new KiiObjectURI(new KiiBucketURI(new KiiGroupURI(new KiiAppURI(UNKNOWN_APP_ID), segments[1]), segments[3]), segments[5]);
				} else if (StringUtils.equals(SEGMENT_THINGS, segments[0])) {
					return new KiiObjectURI(new KiiBucketURI(new KiiThingURI(new KiiAppURI(UNKNOWN_APP_ID), segments[1]), segments[3]), segments[5]);
				}
			}
		} else if (segments.length == 7) {
			if (StringUtils.equals(SEGMENT_BUCKETS, segments[3]) && StringUtils.equals(SEGMENT_OBJECTS, segments[5])) {
				if (StringUtils.equals(SEGMENT_USERS, segments[1])) {
					return new KiiObjectURI(new KiiBucketURI(new KiiUserURI(new KiiAppURI(segments[0]), segments[2]), segments[4]), segments[6]);
				} else if (StringUtils.equals(SEGMENT_GROUPS, segments[1])) {
					return new KiiObjectURI(new KiiBucketURI(new KiiGroupURI(new KiiAppURI(segments[0]), segments[2]), segments[4]), segments[6]);
				} else if (StringUtils.equals(SEGMENT_THINGS, segments[1])) {
					return new KiiObjectURI(new KiiBucketURI(new KiiThingURI(new KiiAppURI(segments[0]), segments[2]), segments[4]), segments[6]);
				}
			}
		}
		throw new IllegalArgumentException("invalid URI : " + str);
	}
	
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
	@Override
	public String toString() {
		return this.toUriString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((objectID == null) ? 0 : objectID.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
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
		KiiObjectURI other = (KiiObjectURI) obj;
		if (objectID == null) {
			if (other.objectID != null)
				return false;
		} else if (!objectID.equals(other.objectID))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}
}
