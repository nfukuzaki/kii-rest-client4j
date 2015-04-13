package com.kii.cloud.model.analytics;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kii.cloud.model.analytics.KiiAnalyticsResult.ResultType;
import com.kii.cloud.util.StringUtils;

public class KiiAnalyticsQuery {
	private final ResultType resultType;
	private String groupingKey;
	private SimpleDate startDate;
	private SimpleDate endDate;
	private Map<String, String> filters = new LinkedHashMap<String, String>();
	private boolean cumulative;
	
	public KiiAnalyticsQuery(ResultType resultType) {
		this.resultType = resultType;
	}
	public KiiAnalyticsQuery setGrouping(String groupingKey) {
		this.groupingKey = groupingKey;
		return this;
	}
	public KiiAnalyticsQuery setStartDate(SimpleDate startDate) {
		this.startDate = startDate;
		return this;
	}
	public KiiAnalyticsQuery setEndDate(SimpleDate endDate) {
		this.endDate = endDate;
		return this;
	}
	public KiiAnalyticsQuery addFilter(String key, String value) {
		this.filters.put(key, value);
		return this;
	}
	public KiiAnalyticsQuery setCumulative(boolean cumulative) {
		this.cumulative = cumulative;
		return this;
	}
	public String toQueryString() {
		StringBuilder queryString = new StringBuilder();
		if (!StringUtils.isEmpty(this.groupingKey)) {
			queryString.append("group=" + this.groupingKey + "&");
		}
		if (this.startDate != null) {
			queryString.append("startDate=" + this.startDate.toString() + "&");
		}
		if (this.endDate != null) {
			queryString.append("endDate=" + this.endDate.toString() + "&");
		}
		int i = 1;
		for (Map.Entry<String, String> filter : this.filters.entrySet()) {
			queryString.append("filter" + i + ".name=" + filter.getKey() + "&");
			queryString.append("filter" + i + ".value=" + filter.getValue() + "&");
			i++;
		}
		if (this.cumulative) {
			queryString.append("cumulative=true&");
		}
		if (queryString.length() == 0) {
			return "";
		} else {
			queryString.deleteCharAt(queryString.length() - 1);
		}
		return "?" + queryString.toString();
	}
	public ResultType getResultType() {
		return this.resultType;
	}
	
	public static class SimpleDate {
		private final int year;
		private final int month;
		private final int day;
		public SimpleDate(int year, int month, int day) {
			if(!(year > 0 && year <= 9999)) {
				throw new IllegalArgumentException("Year is not valid, [1-9999]");
			}
			if(!(month >= 1 && month <= 12)) {
				throw new IllegalArgumentException("Month is not valid, [1-12]");
			}
			if(!(day >= 1 && month <= 31)) {
				throw new IllegalArgumentException("day is not valid, [1-31]");
			}
			this.year = year;
			this.month = month;
			this.day = day;
			
		}
		@Override
		public String toString() {
			return String.format("%04d-%02d-%02d", this.year, this.month, this.day);
		}
	}
}
