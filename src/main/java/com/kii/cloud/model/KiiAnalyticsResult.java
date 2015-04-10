package com.kii.cloud.model;

public interface KiiAnalyticsResult {
	public enum ResultType {
		GroupedResult("application/vnd.kii.GroupedAnalyticResult+json"),
		TabularResult("application/vnd.kii.TabularAnalyticResult+json");
		private final String contentType;
		private ResultType(String contentType) {
			this.contentType = contentType;
		}
		public String getContentType() {
			return contentType;
		}
	}
	public ResultType getResultType();
}
