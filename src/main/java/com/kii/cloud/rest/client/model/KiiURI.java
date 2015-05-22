package com.kii.cloud.rest.client.model;

import com.kii.cloud.rest.client.util.StringUtils;

public abstract class KiiURI {
	public static final String SCHEME = "kiicloud://";
	
	protected final KiiScope scope;
	
	public KiiURI(KiiScope scope) {
		this.scope = scope;
	}
	public KiiScope getScope() {
		return scope;
	}
	public static KiiURI parse(String str) {
		if (StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException("str is null or empty");
		}
		if (!str.startsWith(SCHEME)) {
			throw new IllegalArgumentException("URI should start with 'kiicloud://'");
		}
		String[] segments = str.replace(SCHEME, "").split("/");
		
		if (segments.length == 2) {
			if (StringUtils.equals(KiiScope.GROUP.getCollectionName(), segments[0])) {
				return new KiiGroupURI(segments[1]);
			} else if (StringUtils.equals(KiiScope.USER.getCollectionName(), segments[0])) {
				return new KiiUserURI(segments[1]);
			} else if (StringUtils.equals(KiiScope.THING.getCollectionName(), segments[0])) {
				return new KiiThingURI(segments[1]);
			} else {
				throw new IllegalArgumentException("'" + str + "' is not Kii URI");
			}
		} else if (segments.length == 4 || segments.length == 6) {
			return KiiObjectURI.create(str);
		} else {
			throw new IllegalArgumentException("'" + str + "' is not Kii URI");
		}
	}
}
