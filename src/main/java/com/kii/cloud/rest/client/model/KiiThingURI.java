package com.kii.cloud.rest.client.model;

import com.kii.cloud.rest.client.util.StringUtils;

/**
 * Represents the Thing URI.
 * 
 * @see http://documentation.kii.com/en/starts/cloudsdk/cloudoverview/idanduri/
 */
public class KiiThingURI extends KiiScopeURI {
	
	public static KiiThingURI create(String str) {
		if (StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException("str is null or empty");
		}
		if (!str.startsWith(SCHEME)) {
			throw new IllegalArgumentException("Thing URI should start with 'kiicloud://'");
		}
		String[] segments = str.replace(SCHEME, "").split("/");
		if (segments.length == 2) {
			if (!StringUtils.equals(KiiScope.THING.getCollectionName(), segments[0])) {
				throw new IllegalArgumentException("'" + str + "' is not Kii Thing URI");
			}
			return new KiiThingURI(segments[1]);
		} else {
			throw new IllegalArgumentException("'" + str + "' is not Kii Thing URI");
		}
	}

	private KiiThingURI(String thingID) {
		super(KiiScope.THING, thingID);
	}

}
