package com.kii.cloud.rest.client.model;

public interface KiiCredentialsContainer {
	public String getID();
	public String getAccessToken();
	public String getRefreshToken();
	public boolean hasCredentials();
	public boolean isAdmin();
}
