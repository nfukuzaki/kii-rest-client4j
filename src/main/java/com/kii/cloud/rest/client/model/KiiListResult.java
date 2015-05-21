package com.kii.cloud.rest.client.model;

import java.util.List;

public class KiiListResult<T> {
	private final List<T> result;
	private final String paginationKey;

	public KiiListResult(List<T> result) {
		this(result, null);
	}
	public KiiListResult(List<T> result, String paginationKey) {
		this.result = result;
		this.paginationKey = paginationKey;
	}
	public int size() {
		if (this.result == null) {
			return 0;
		}
		return this.result.size();
	}
	public List<T> getResult() {
		return this.result;
	}
	public String getPaginationKey() {
		return this.paginationKey;
	}
	public boolean hasNext() {
		return this.paginationKey != null;
	}
}
