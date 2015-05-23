package com.kii.cloud.rest.client.util;

import com.kii.cloud.rest.client.model.KiiObjectURI;
import com.kii.cloud.rest.client.model.KiiScope;

/**
 * 
 */
public class KiiURIUtils {
	public static KiiObjectURI createAppScopeObject(String bucketName, String objectID) {
		return new KiiObjectURI(KiiScope.APP, null, bucketName, objectID);
	}
	public static KiiObjectURI createGroupScopeObject(String groupID, String bucketName, String objectID) {
		return new KiiObjectURI(KiiScope.GROUP, groupID, bucketName, objectID);
	}
	public static KiiObjectURI createUserScopeObject(String userID, String bucketName, String objectID) {
		return new KiiObjectURI(KiiScope.USER, userID, bucketName, objectID);
	}
	public static KiiObjectURI createThingScopeObject(String thingID, String bucketName, String objectID) {
		return new KiiObjectURI(KiiScope.THING, thingID, bucketName, objectID);
	}
}
