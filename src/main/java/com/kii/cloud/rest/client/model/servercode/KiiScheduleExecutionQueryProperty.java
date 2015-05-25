package com.kii.cloud.rest.client.model.servercode;

import com.kii.cloud.rest.client.model.KiiJsonProperty;
import com.kii.cloud.rest.client.model.validation.KiiJsonPropertyValidator;

/**
 * 
 * @param <T>
 */
public class KiiScheduleExecutionQueryProperty<T> extends KiiJsonProperty<T> {
	public KiiScheduleExecutionQueryProperty(Class<T> propertyType, String name, KiiJsonPropertyValidator... validators) {
		super(propertyType, name, null, validators);
	}
	public KiiScheduleExecutionQueryProperty(Class<T> propertyType, String name, String alias, KiiJsonPropertyValidator... validators) {
		super(propertyType, name, alias, validators);
	}
}
