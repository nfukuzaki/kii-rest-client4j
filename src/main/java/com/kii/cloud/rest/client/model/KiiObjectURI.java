package com.kii.cloud.rest.client.model;

import com.kii.cloud.rest.client.util.StringUtils;

/**
 * Represents the Object URI.
 * <p>
 * <table>
 * <tr><th>Scope</th><th>URI</th></tr>
 * <tr><td>APP</td><td>kiicloud://buckets/{BucketID}/objects/{OBJECT_ID}</td></tr>
 * <tr><td>Group</td><td>kiicloud://groups/{GROUP_ID}/buckets/{BucketID}/objects/{OBJECT_ID}</td></tr>
 * <tr><td>User</td><td>kiicloud://users/{USER_ID}/buckets/{BucketID}/objects/{OBJECT_ID}</td></tr>
 * <tr><td>Thing</td><td>kiicloud://things/{THING_ID}/buckets/{BucketID}/objects/{OBJECT_ID}</td></tr>
 * </table>
 * 
 * @see http://documentation.kii.com/en/starts/cloudsdk/cloudoverview/idanduri/
 */
public class KiiObjectURI {
	
	public static final String SCHEME = "kiicloud://";
	
	public static KiiObjectURI create(String str) {
		if (StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException("str is null or empty");
		}
		if (!str.startsWith(SCHEME)) {
			throw new IllegalArgumentException("Object URI should start with 'kiicloud://'");
		}
		String[] segments = str.replace(SCHEME, "").split("/");
		KiiScope scope = null;
		String scopeID = null;
		String bucketName = null;
		String objectID = null;
		if (segments.length == 4) {
			// App scope object URI
			if (!StringUtils.equals("buckets", segments[0]) || !StringUtils.equals("objects", segments[2])) {
				throw new IllegalArgumentException("'" + str + "' is not Kii Object URI");
			}
			scope = KiiScope.APP;
			bucketName = segments[1];
			objectID = segments[3];
		} else if (segments.length == 6) {
			// Group or User or Thing scope object URI
			scope = KiiScope.fromString(segments[0]);
			if (scope == null || scope == KiiScope.APP) {
				throw new IllegalArgumentException("'" + str + "' is not Kii Object URI");
			}
			if (!StringUtils.equals("buckets", segments[2]) || !StringUtils.equals("objects", segments[4])) {
				throw new IllegalArgumentException("'" + str + "' is not Kii Object URI");
			}
			scopeID = segments[1];
			bucketName = segments[3];
			objectID = segments[5];
		} else {
			throw new IllegalArgumentException("'" + str + "' is not Kii Object URI");
		}
		return new KiiObjectURI(scope, scopeID, bucketName, objectID);
	}
	
	private final KiiScope scope;
	private final String scopeID;
	private final String bucketName;
	private final String objectID;
	
	private KiiObjectURI(KiiScope scope, String scopeID, String bucketName, String objectID) {
		this.scope = scope;
		this.scopeID = scopeID;
		this.bucketName = bucketName;
		this.objectID = objectID;
	}
	public KiiScope getScope() {
		return scope;
	}
	public String getScopeID() {
		return scopeID;
	}
	public String getBucketName() {
		return bucketName;
	}
	public String getObjectID() {
		return objectID;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(SCHEME);
		if (this.scope != KiiScope.APP) {
			sb.append(this.scope.getCollectionName());
			sb.append("/");
			sb.append(this.scopeID);
			sb.append("/");
		}
		sb.append("buckets");
		sb.append("/");
		sb.append(this.bucketName);
		sb.append("/");
		sb.append("objects");
		sb.append("/");
		sb.append(this.objectID);
		return sb.toString();
	}
}
