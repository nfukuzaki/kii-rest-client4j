package com.kii.cloud.resource.analytics;

import com.kii.cloud.resource.KiiRestSubResource;

public class KiiAggregationRuleResource extends KiiRestSubResource {
	
	private final String aggregationRuleID;
	
	public KiiAggregationRuleResource(KiiAggregationRulesResource parent, String aggregationRuleID) {
		super(parent);
		this.aggregationRuleID = aggregationRuleID;
	}
	
	
	@Override
	public String getPath() {
		return "/" + this.aggregationRuleID;
	}

}
