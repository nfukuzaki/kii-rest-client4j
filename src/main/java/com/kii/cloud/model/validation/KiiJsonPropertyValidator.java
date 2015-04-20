package com.kii.cloud.model.validation;

import com.kii.cloud.model.KiiJsonProperty;

public interface KiiJsonPropertyValidator {
	public void validate(KiiJsonProperty<?> property, Object value) throws KiiInvalidPropertyException;
}
