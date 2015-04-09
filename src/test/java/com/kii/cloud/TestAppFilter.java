package com.kii.cloud;

import com.kii.cloud.KiiRest.Site;
import com.kii.cloud.util.StringUtils;

public class TestAppFilter {
	
	private Site site = null;
	private boolean hasAppAdminCredentials = false;
	private boolean enableRefreshToken = false;
	private boolean enableEmailVerification = false;
	private boolean enablePhoneVerification = false;
	private boolean enableExposeFullUserData = false;
	private boolean enableBucketEncryption = false;
	private boolean enableGCM = false;
	private boolean enableAPNS = false;
	private boolean enableJPush = false;
	private boolean enableGoogle = false;
	private boolean enableYahoo = false;
	private boolean enableTwitter = false;
	private boolean enableFacebook = false;
	private boolean enableLive = false;
	private boolean enableLinkedIn = false;
	private boolean enableDropBox = false;
	private boolean enableBox = false;
	private boolean enableRenRen = false;
	private boolean enableSina = false;
	private boolean enableQQ = false;
	
	public TestAppFilter site(Site site) {
		this.site = site;
		return this;
	}
	public TestAppFilter hasAppAdminCredentials() {
		this.hasAppAdminCredentials = true;
		return this;
	}
	public TestAppFilter enableRefreshToken() {
		this.enableRefreshToken = true;
		return this;
	}
	public TestAppFilter enableEmailVerification() {
		this.enableEmailVerification = true;
		return this;
	}
	public TestAppFilter enablePhoneVerification() {
		this.enablePhoneVerification = true;
		return this;
	}
	public TestAppFilter enableExposeFullUserData() {
		this.enableExposeFullUserData = true;
		return this;
	}
	public TestAppFilter enableBucketEncryption() {
		this.enableBucketEncryption = true;
		return this;
	}
	public TestAppFilter enableGCM() {
		this.enableGCM =  true;
		return this;
	}
	public TestAppFilter enableAPNS() {
		this.enableAPNS = true;
		return this;
	}
	public TestAppFilter enableJPush() {
		this.enableJPush = true;
		return this;
	}
	public TestAppFilter enableGoogle() {
		this.enableGoogle = true;
		return this;
	}
	public TestAppFilter enableYahoo() {
		this.enableYahoo = true;
		return this;
	}
	public TestAppFilter enableTwitter() {
		this.enableTwitter = true;
		return this;
	}
	public TestAppFilter enableFacebook() {
		this.enableFacebook = true;
		return this;
	}
	public TestAppFilter enableLive() {
		this.enableLive = true;
		return this;
	}
	public TestAppFilter enableLinkedIn() {
		this.enableLinkedIn = true;
		return this;
	}
	public TestAppFilter enableDropBox() {
		this.enableDropBox = true;
		return this;
	}
	public TestAppFilter enableBox() {
		this.enableBox = true;
		return this;
	}
	public TestAppFilter enableRenRen() {
		this.enableRenRen = true;
		return this;
	}
	public TestAppFilter enableSina() {
		this.enableSina = true;
		return this;
	}
	public TestAppFilter enableQQ() {
		this.enableQQ = true;
		return this;
	}
	public boolean accept(TestApp app) {
		if (this.site != null && app.getSite() != site) {
			return false;
		}
		if (this.hasAppAdminCredentials && (StringUtils.isEmpty(app.getClientID()) || StringUtils.isEmpty(app.getClientSecret()))) {
			return false;
		}
		if (this.enableRefreshToken && !app.getFlag("RefreshToken")) {
			return false;
		}
		if (this.enableEmailVerification && !app.getFlag("EmailVerification")) {
			return false;
		}
		if (this.enablePhoneVerification && !app.getFlag("PhoneVerification")) {
			return false;
		}
		if (this.enableExposeFullUserData && !app.getFlag("Privacy")) {
			return false;
		}
		if (this.enableBucketEncryption && !app.getFlag("EncryptEnabled")) {
			return false;
		}
		if (this.enableGCM && !app.isEnabledPush("GCM")) {
			return false;
		}
		if (this.enableAPNS && !app.isEnabledPush("APNS")) {
			return false;
		}
		if (this.enableJPush && !app.isEnabledPush("JPush")) {
			return false;
		}
		if (this.enableGoogle && !app.isEnabledSNS("Google")) {
			return false;
		}
		if (this.enableYahoo && !app.isEnabledSNS("Yahoo")) {
			return false;
		}
		if (this.enableTwitter && !app.isEnabledSNS("Twitter")) {
			return false;
		}
		if (this.enableFacebook && !app.isEnabledSNS("Facebook")) {
			return false;
		}
		if (this.enableLive && !app.isEnabledSNS("Live")) {
			return false;
		}
		if (this.enableLinkedIn && !app.isEnabledSNS("LinkedIn")) {
			return false;
		}
		if (this.enableDropBox && !app.isEnabledSNS("DropBox")) {
			return false;
		}
		if (this.enableBox && !app.isEnabledSNS("Box")) {
			return false;
		}
		if (this.enableRenRen && !app.isEnabledSNS("RenRen")) {
			return false;
		}
		if (this.enableSina && !app.isEnabledSNS("Sina")) {
			return false;
		}
		if (this.enableQQ && !app.isEnabledSNS("QQ")) {
			return false;
		}
		return true;
	}
}
