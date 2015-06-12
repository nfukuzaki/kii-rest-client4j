package com.kii.cloud.rest.client.resource.social;

import com.kii.cloud.rest.client.model.social.KiiSocialProvider;
import com.kii.cloud.rest.client.resource.KiiAppResource;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.util.StringUtils;

/**
 * Represents the server side auth resource like following URI:
 * 
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/integration/webauth
 * </ul>
 */
public class KiiWebAuthSocialIntegrationResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/integration/webauth";
	
	public KiiWebAuthSocialIntegrationResource(KiiAppResource parent) {
		super(parent);
	}
	
	/**
	 * Returns URL to integrate the social account with the kii account.
	 * You need to access this URL through the web browser.
	 * 
	 * @param provider
	 * @return
	 */
	public String getSocialIntegrationUrl(KiiSocialProvider provider) {
		if (provider == null) {
			throw new IllegalArgumentException("provider is null");
		}
		return this.getUrl("/connect?id=" + StringUtils.urlEncode(provider.getID()));
	}
	
	
	// FIXME:Link/Unlink URL does not work
//	/**
//	 * Returns URL to link the social account with the kii account.
//	 * You need to access this URL through the web browser.
//	 * 
//	 * @param provider
//	 * @return
//	 */
//	public String getSocialLinkUrl(KiiSocialProvider provider) {
//		if (provider == null) {
//			throw new IllegalArgumentException("provider is null");
//		}
//		return this.getUrl("/link?id=" + provider.getID());
//	}
//	/**
//	 * Returns URL to unlink the social account with the kii account.
//	 * You need to access this URL through the web browser.
//	 * 
//	 * @param provider
//	 * @return
//	 */
//	public String getSocialUnLinkUrl(KiiSocialProvider provider) {
//		if (provider == null) {
//			throw new IllegalArgumentException("provider is null");
//		}
//		return this.getUrl("/unlink?id=" + provider.getID());
//	}
	
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
