package com.kii.cloud.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KiiJsonProperty {
	
	private final String requestPropertyName;
	private final String[] responsePropertyName;
	
	public KiiJsonProperty(String requestPropertyName, String responsePropertyName, String... responsePropertyAlias) {
		this.requestPropertyName = requestPropertyName;
		List<String> responsePropertyNames = new ArrayList<String>();
		responsePropertyNames.add(responsePropertyName);
		responsePropertyNames.addAll(Arrays.asList(responsePropertyAlias));
		this.responsePropertyName = responsePropertyNames.toArray(new String[responsePropertyName.length()]);
	}
}
