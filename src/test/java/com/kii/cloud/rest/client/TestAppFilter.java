package com.kii.cloud.rest.client;

import com.kii.cloud.rest.client.KiiRest.Site;
import com.kii.cloud.rest.client.util.StringUtils;

public class TestAppFilter {
	
	private Site site = null;
	private Boolean hasAppAdminCredentials = null;
	private Boolean enableRefreshToken = null;
	private Boolean enableEmailVerification = null;
	private Boolean enablePhoneVerification = null;
	private Boolean enableExposeFullUserData = null;
	private Boolean enableBucketEncryption = null;
	private Boolean enableGCM = null;
	private Boolean enableAPNS = null;
	private Boolean enableJPush = null;
	private Boolean enableGoogle = null;
	private Boolean enableYahoo = null;
	private Boolean enableTwitter = null;
	private Boolean enableFacebook = null;
	private Boolean enableLive = null;
	private Boolean enableLinkedIn = null;
	private Boolean enableDropBox = null;
	private Boolean enableBox = null;
	private Boolean enableRenRen = null;
	private Boolean enableSina = null;
	private Boolean enableQQ = null;
	private Long aggregationRuleID = null;
	
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
	public TestAppFilter disableRefreshToken() {
		this.enableRefreshToken = false;
		return this;
	}
	public TestAppFilter enableEmailVerification() {
		this.enableEmailVerification = true;
		return this;
	}
	public TestAppFilter disableEmailVerification() {
		this.enableEmailVerification = false;
		return this;
	}
	public TestAppFilter enablePhoneVerification() {
		this.enablePhoneVerification = true;
		return this;
	}
	public TestAppFilter disablePhoneVerification() {
		this.enablePhoneVerification = false;
		return this;
	}
	public TestAppFilter enableExposeFullUserData() {
		this.enableExposeFullUserData = true;
		return this;
	}
	public TestAppFilter disableExposeFullUserData() {
		this.enableExposeFullUserData = false;
		return this;
	}
	public TestAppFilter enableBucketEncryption() {
		this.enableBucketEncryption = true;
		return this;
	}
	public TestAppFilter disableBucketEncryption() {
		this.enableBucketEncryption = false;
		return this;
	}
	public TestAppFilter aggregationRuleID(Long aggregationRuleID) {
		this.aggregationRuleID = aggregationRuleID;
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
		if (this.hasAppAdminCredentials == Boolean.TRUE && (StringUtils.isEmpty(app.getClientID()) || StringUtils.isEmpty(app.getClientSecret()))) {
			return false;
		}
		if (!this.accept(this.enableRefreshToken, app.getFlag("RefreshToken"))) {
			return false;
		}
		if (!this.accept(this.enableEmailVerification, app.getFlag("EmailVerification"))) {
			return false;
		}
		if (!this.accept(this.enablePhoneVerification, app.getFlag("PhoneVerification"))) {
			return false;
		}
		if (!this.accept(this.enableExposeFullUserData, app.getFlag("Privacy"))) {
			return false;
		}
		if (!this.accept(this.enableBucketEncryption, app.getFlag("EncryptEnabled"))) {
			return false;
		}
		if (this.aggregationRuleID != null && !this.aggregationRuleID.equals(app.getAggregationRuleID())) {
			return false;
		}
		if (!this.accept(this.enableGCM, app.isEnabledPush("GCM"))) {
			return false;
		}
		if (!this.accept(this.enableAPNS, app.isEnabledPush("APNS"))) {
			return false;
		}
		if (!this.accept(this.enableJPush, app.isEnabledPush("JPush"))) {
			return false;
		}
		if (!this.accept(this.enableGoogle, app.isEnabledSNS("Google"))) {
			return false;
		}
		if (!this.accept(this.enableYahoo, app.isEnabledSNS("Yahoo"))) {
			return false;
		}
		if (!this.accept(this.enableTwitter, app.isEnabledSNS("Twitter"))) {
			return false;
		}
		if (!this.accept(this.enableFacebook, app.isEnabledSNS("Facebook"))) {
			return false;
		}
		if (!this.accept(this.enableLive, app.isEnabledSNS("Live"))) {
			return false;
		}
		if (!this.accept(this.enableLinkedIn, app.isEnabledSNS("LinkedIn"))) {
			return false;
		}
		if (!this.accept(this.enableDropBox, app.isEnabledSNS("DropBox"))) {
			return false;
		}
		if (!this.accept(this.enableBox, app.isEnabledSNS("Box"))) {
			return false;
		}
		if (!this.accept(this.enableRenRen, app.isEnabledSNS("RenRen"))) {
			return false;
		}
		if (!this.accept(this.enableSina, app.isEnabledSNS("Sina"))) {
			return false;
		}
		if (!this.accept(this.enableQQ, app.isEnabledSNS("QQ"))) {
			return false;
		}
		return true;
	}
	private boolean accept(Boolean filterCondition, Boolean value) {
		if (filterCondition == null) {
			return true;
		}
		return filterCondition == value;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (this.site != null) {
			sb.append("site=" + site.toString() + " ");
		}
		if (this.hasAppAdminCredentials != null) {
			sb.append("hasAppAdminCredentials=" + hasAppAdminCredentials + " ");
		}
		if (this.enableRefreshToken != null) {
			sb.append("enableRefreshToken=" + enableRefreshToken + " ");
		}
		if (this.enableEmailVerification != null) {
			sb.append("enableEmailVerification=" + enableEmailVerification + " ");
		}
		if (this.enablePhoneVerification != null) {
			sb.append("enablePhoneVerification=" + enablePhoneVerification + " ");
		}
		if (this.enableExposeFullUserData != null) {
			sb.append("enableExposeFullUserData=" + enableExposeFullUserData + " ");
		}
		if (this.enableBucketEncryption != null) {
			sb.append("enableBucketEncryption=" + enableBucketEncryption + " ");
		}
		if (this.enableGCM != null) {
			sb.append("enableGCM=" + enableGCM + " ");
		}
		if (this.enableAPNS != null) {
			sb.append("enableAPNS=" + enableAPNS + " ");
		}
		if (this.enableJPush != null) {
			sb.append("enableJPush=" + enableJPush + " ");
		}
		if (this.enableGoogle != null) {
			sb.append("enableGoogle=" + enableGoogle + " ");
		}
		if (this.enableYahoo != null) {
			sb.append("enableYahoo=" + enableYahoo + " ");
		}
		if (this.enableTwitter != null) {
			sb.append("enableTwitter=" + enableTwitter + " ");
		}
		if (this.enableFacebook != null) {
			sb.append("enableFacebook=" + enableFacebook + " ");
		}
		if (this.enableLive != null) {
			sb.append("enableLive=" + enableLive + " ");
		}
		if (this.enableLinkedIn != null) {
			sb.append("enableLinkedIn=" + enableLinkedIn + " ");
		}
		if (this.enableDropBox != null) {
			sb.append("enableDropBox=" + enableDropBox + " ");
		}
		if (this.enableBox != null) {
			sb.append("enableBox=" + enableBox + " ");
		}
		if (this.enableRenRen != null) {
			sb.append("enableRenRen=" + enableRenRen + " ");
		}
		if (this.enableSina != null) {
			sb.append("enableSina=" + enableSina + " ");
		}
		if (this.enableQQ != null) {
			sb.append("enableQQ=" + enableQQ + " ");
		}
		if (this.aggregationRuleID != null) {
			sb.append("aggregationRuleID=" + aggregationRuleID + " ");
		}
		return sb.toString();
	}
}
