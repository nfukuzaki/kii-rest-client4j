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

}
