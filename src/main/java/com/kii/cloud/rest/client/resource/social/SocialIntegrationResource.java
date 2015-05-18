package com.kii.cloud.rest.client.resource.social;

import com.kii.cloud.rest.client.model.social.SocialProvider;
import com.kii.cloud.rest.client.resource.KiiAppResource;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;

/**
 * 
 */
public class SocialIntegrationResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/integration/webauth";
	
	public SocialIntegrationResource(KiiAppResource parent) {
		super(parent);
	}
	
	/**
	 * Returns URL to integrate the social account with the kii account.
	 * You need to access this URL through the web browser.
	 * 
	 * @param provider
	 * @return
	 */
	public String getSocialIntegrationUrl(SocialProvider provider) {
		if (provider == null) {
			throw new IllegalArgumentException("provider is null");
		}
		return this.getUrl("/connect?id=" + provider.getID());
	}
	/**
	 * Returns URL to link the social account with the kii account.
	 * You need to access this URL through the web browser.
	 * 
	 * @param provider
	 * @return
	 */
	public String getSocialLinkUrl(SocialProvider provider) {
		if (provider == null) {
			throw new IllegalArgumentException("provider is null");
		}
		return this.getUrl("/link?id=" + provider.getID());
	}
	/**
	 * Returns URL to unlink the social account with the kii account.
	 * You need to access this URL through the web browser.
	 * 
	 * @param provider
	 * @return
	 */
	public String getSocialUnLinkUrl(SocialProvider provider) {
		if (provider == null) {
			throw new IllegalArgumentException("provider is null");
		}
		return this.getUrl("/unlink?id=" + provider.getID());
	}
	
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
