package com.kii.cloud.model.validation;

import java.util.regex.Pattern;

import com.kii.cloud.model.KiiJsonProperty;

public class RegularExpressionValidator implements KiiJsonPropertyValidator {
	private final Pattern pattern;
	public RegularExpressionValidator(String regex) {
		this.pattern = Pattern.compile(regex);
	}
	public void validate(KiiJsonProperty<?> property, Object value) throws KiiInvalidPropertyException {
		if (value instanceof String) {
			if (!this.pattern.matcher((String)value).matches()) {
				throw new KiiInvalidPropertyException(property.getName() + " does not match pattern: " + this.pattern.pattern());
			}
		}
	}
}
