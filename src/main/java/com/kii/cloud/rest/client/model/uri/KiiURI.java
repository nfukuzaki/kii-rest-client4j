package com.kii.cloud.rest.client.model.uri;

import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.util.StringUtils;

/**
 * Represents the Kii URI
 * 
 */
public abstract class KiiURI {
	public static final String SCHEME = "kiicloud://";
	public static final String SEGMENT_USERS = "users";
	public static final String SEGMENT_GROUPS = "groups";
	public static final String SEGMENT_THINGS = "things";
	public static final String SEGMENT_BUCKETS = "buckets";
	public static final String SEGMENT_OBJECTS = "objects";
	public static final String SEGMENT_TOPICS = "topics";
	public static final String UNKNOWN_APP_ID = "unknown";
	
	public abstract KiiScope getScope();
	public abstract String toUriString();
	
	public static KiiURI parse(String str) {
		if (StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException("str is null or empty");
		}
		if (!str.startsWith(SCHEME)) {
			throw new IllegalArgumentException("URI should start with 'kiicloud://'");
		}
		String[] segments = str.replace(SCHEME, "").split("/");
		if (isNewFormat(segments)) {
			return parseNewFormat(segments);
		} else if (isOldFormat(segments)) {
			return parseOldFormat(segments);
		} else {
			throw new IllegalArgumentException("invalid URI : " + str);
		}
	}
	/**
	 * @param segments
	 * @return
	 */
	private static KiiURI parseNewFormat(String[] segments) {
		if (segments.length == 3) {
			if (StringUtils.equals(SEGMENT_BUCKETS, segments[1])) {
				// App scope bucket
				return new KiiBucketURI(new KiiAppURI(segments[0]), segments[2]);
			} else if (StringUtils.equals(SEGMENT_USERS, segments[1])) {
				// User
				return new KiiUserURI(new KiiAppURI(segments[0]), segments[2]);
			} else if (StringUtils.equals(SEGMENT_GROUPS, segments[1])) {
				// Group
				return new KiiGroupURI(new KiiAppURI(segments[0]), segments[2]);
			} else if (StringUtils.equals(SEGMENT_THINGS, segments[1])) {
				// Thing
				return new KiiThingURI(new KiiAppURI(segments[0]), segments[2]);
			} else if (StringUtils.equals(SEGMENT_TOPICS, segments[1])) {
				// App scope topic
				return new KiiTopicURI(new KiiAppURI(segments[0]), segments[2]);
			}
		} else if (segments.length == 5) {
			if (StringUtils.equals(SEGMENT_BUCKETS, segments[1])) {
				// App scope object
				return new KiiObjectURI(new KiiBucketURI(new KiiAppURI(segments[0]), segments[2]), segments[4]);
			} else if (StringUtils.equals(SEGMENT_USERS, segments[1])) {
				if (StringUtils.equals(SEGMENT_BUCKETS, segments[3])) {
					// User scope bucket
					return new KiiBucketURI(new KiiUserURI(new KiiAppURI(segments[0]), segments[2]), segments[4]);
				} else if (StringUtils.equals(SEGMENT_TOPICS, segments[3])) {
					// User scope topic
					return new KiiTopicURI(new KiiUserURI(new KiiAppURI(segments[0]), segments[2]), segments[4]);
				}
			} else if (StringUtils.equals(SEGMENT_GROUPS, segments[1])) {
				if (StringUtils.equals(SEGMENT_BUCKETS, segments[3])) {
					// Group scope bucket
					return new KiiBucketURI(new KiiGroupURI(new KiiAppURI(segments[0]), segments[2]), segments[4]);
				} else if (StringUtils.equals(SEGMENT_TOPICS, segments[3])) {
					// Group scope topic
					return new KiiTopicURI(new KiiGroupURI(new KiiAppURI(segments[0]), segments[2]), segments[4]);					
				}
			} else if (StringUtils.equals(SEGMENT_THINGS, segments[1])) {
				if (StringUtils.equals(SEGMENT_BUCKETS, segments[3])) {
					// Thing scope bucket
					return new KiiBucketURI(new KiiThingURI(new KiiAppURI(segments[0]), segments[2]), segments[4]);
				} else if (StringUtils.equals(SEGMENT_TOPICS, segments[3])) {
					// Thing scope topic
					return new KiiTopicURI(new KiiThingURI(new KiiAppURI(segments[0]), segments[2]), segments[4]);
				}
			}
		} else if (segments.length == 7) {
			if (StringUtils.equals(SEGMENT_USERS, segments[1])) {
				// User scope object
				return new KiiObjectURI(new KiiBucketURI(new KiiUserURI(new KiiAppURI(segments[0]), segments[2]), segments[4]), segments[6]);
			} else if (StringUtils.equals(SEGMENT_GROUPS, segments[1])) {
				// Group scope object
				return new KiiObjectURI(new KiiBucketURI(new KiiGroupURI(new KiiAppURI(segments[0]), segments[2]), segments[4]), segments[6]);
			} else if (StringUtils.equals(SEGMENT_THINGS, segments[1])) {
				// Thing scope object
				return new KiiObjectURI(new KiiBucketURI(new KiiThingURI(new KiiAppURI(segments[0]), segments[2]), segments[4]), segments[6]);
			}
		}
		throw new IllegalArgumentException("invalid URI : " + SCHEME + StringUtils.join(segments, "/"));
	}
	/**
	 * @param segments
	 * @return
	 */
	private static KiiURI parseOldFormat(String[] segments) {
		if (segments.length == 2) {
			if (StringUtils.equals(SEGMENT_USERS, segments[0])) {
				return new KiiUserURI(new KiiAppURI(UNKNOWN_APP_ID), segments[1]);
			} else if (StringUtils.equals(SEGMENT_GROUPS, segments[0])) {
				return new KiiGroupURI(new KiiAppURI(UNKNOWN_APP_ID), segments[1]);
			} else if (StringUtils.equals(SEGMENT_THINGS, segments[0])) {
				return new KiiThingURI(new KiiAppURI(UNKNOWN_APP_ID), segments[1]);
			}
		} else if (segments.length == 4) {
			if (StringUtils.equals(SEGMENT_BUCKETS, segments[0]) && StringUtils.equals(SEGMENT_OBJECTS, segments[2])) {
				return new KiiObjectURI(new KiiBucketURI(new KiiAppURI(UNKNOWN_APP_ID), segments[1]), segments[3]);
			}
		} else if (segments.length == 5) {
			if (StringUtils.equals(SEGMENT_BUCKETS, segments[2]) && StringUtils.equals(SEGMENT_OBJECTS, segments[4])) {
				if (StringUtils.equals(SEGMENT_USERS, segments[0])) {
					return new KiiObjectURI(new KiiBucketURI(new KiiUserURI(new KiiAppURI(UNKNOWN_APP_ID), segments[1]), segments[3]), segments[5]);
				} else if (StringUtils.equals(SEGMENT_GROUPS, segments[0])) {
					return new KiiObjectURI(new KiiBucketURI(new KiiGroupURI(new KiiAppURI(UNKNOWN_APP_ID), segments[1]), segments[3]), segments[5]);
				} else if (StringUtils.equals(SEGMENT_THINGS, segments[0])) {
					return new KiiObjectURI(new KiiBucketURI(new KiiThingURI(new KiiAppURI(UNKNOWN_APP_ID), segments[1]), segments[3]), segments[5]);
				}
			}
		}
		throw new IllegalArgumentException("invalid URI : " + SCHEME + StringUtils.join(segments, "/"));
	}
	/**
	 * @param segments
	 * @return
	 */
	private static boolean isNewFormat(String[] segments) {
		return segments.length == 3 || segments.length == 5 || segments.length == 7;
	}
	/**
	 * @param segments
	 * @return
	 * @see http://documentation.kii.com/en/starts/cloudsdk/cloudoverview/idanduri/
	 */
	private static boolean isOldFormat(String[] segments) {
		return segments.length == 2 || segments.length == 4 || segments.length == 6;
	}
}
