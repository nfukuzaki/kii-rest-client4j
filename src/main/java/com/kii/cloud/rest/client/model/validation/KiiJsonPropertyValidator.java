package com.kii.cloud.rest.client.model.validation;

import com.kii.cloud.rest.client.model.KiiJsonProperty;

public interface KiiJsonPropertyValidator {
	public void validate(KiiJsonProperty<?> property, Object value) throws KiiInvalidPropertyException;
}
