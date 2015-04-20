package com.kii.cloud.model.validation;

import com.kii.cloud.model.KiiJsonProperty;

public class RangeLengthValidator implements KiiJsonPropertyValidator {
	
	private final int minLength;
	private final int maxLength;
	
	public RangeLengthValidator(int minLength, int maxLength) {
		this.minLength = minLength;
		this.maxLength = maxLength;
	}
	
	
	@Override
	public void validate(KiiJsonProperty<?> property, Object value) throws KiiInvalidPropertyException {
		if (value instanceof String) {
			if (((String)value).length() < this.minLength || ((String)value).length() > this.maxLength) {
				throw new KiiInvalidPropertyException(property.getName() + " length must be between " + this.minLength + " and " + this.maxLength);
			}
		}
	}

}
