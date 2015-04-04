package com.kii.cloud;

/**
 * 
 * 
 * @param <T>
 */
public interface KiiCallback<T> {
	public void onCompleted(T response, Exception e);
}
