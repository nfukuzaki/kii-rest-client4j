package com.kii.cloud.resource.analytics;

import com.kii.cloud.resource.KiiAppResource;
import com.kii.cloud.resource.KiiRestSubResource;

public class KiiAggregationRulesResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/aggregation-rules";
	
	public KiiAggregationRulesResource(KiiAppResource parent) {
		super(parent);
	}
	
	
	
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
