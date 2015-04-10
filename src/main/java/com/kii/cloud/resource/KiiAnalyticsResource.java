package com.kii.cloud.resource;

public class KiiAnalyticsResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/analytics";
	public static final String CONTENT_TYPE_TABULAR_ANALYTIC_RESULT = "application/vnd.kii.TabularAnalyticResult+json";
	public static final String CONTENT_TYPE_GROUPED_ANALYTIC_RESULT = "application/vnd.kii.GroupedAnalyticResult+json";
	
	
	private final String aggregationRuleID;
	
	public KiiAnalyticsResource(KiiAppResource parent, String aggregationRuleID) {
		super(parent);
		this.aggregationRuleID = aggregationRuleID;
	}
	
	
	
	
	
	@Override
	public String getPath() {
		return BASE_PATH + "/" + this.aggregationRuleID;
	}
}
