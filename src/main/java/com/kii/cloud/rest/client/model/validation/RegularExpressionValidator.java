package com.kii.cloud.rest.client.model.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.kii.cloud.rest.client.model.KiiJsonProperty;

public class RegularExpressionValidator implements KiiJsonPropertyValidator {
	private final Pattern[] patterns;
	public RegularExpressionValidator(String regex, String... alternatives) {
		List<Pattern> patterns = new ArrayList<Pattern>();
		patterns.add(Pattern.compile(regex));
		for (String r : alternatives) {
			patterns.add(Pattern.compile(r));
		}
		this.patterns = patterns.toArray(new Pattern[patterns.size()]);
	}
	public RegularExpressionValidator(Pattern pattern, Pattern... alternatives) {
		List<Pattern> patterns = new ArrayList<Pattern>();
		patterns.add(pattern);
		for (Pattern p : alternatives) {
			patterns.add(p);
		}
		this.patterns = patterns.toArray(new Pattern[patterns.size()]);
	}
	public void validate(KiiJsonProperty<?> property, Object value) throws KiiInvalidPropertyException {
		if (value instanceof String) {
			for (Pattern pattern : this.patterns) {
				if (pattern.matcher((String)value).matches()) {
					return;
				}
			}
			throw new KiiInvalidPropertyException(property.getName() + " does not match pattern: " + getRegexList());
		}
	}
	private String getRegexList() {
		StringBuilder sb = new StringBuilder();
		for (Pattern pattern : this.patterns) {
			if (sb.length() > 0) {
				sb.append("  or  ");
			}
			sb.append(pattern.pattern());
		}
		return sb.toString();
	}
}
