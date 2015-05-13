package com.kii.cloud.model.conf;

import com.google.gson.JsonObject;
import com.kii.cloud.model.KiiJsonModel;
import com.kii.cloud.model.KiiJsonProperty;

public class KiiThingTypeConfiguration extends KiiJsonModel {
	
	public static final KiiJsonProperty<String> PROPERTY_THINGTYPE = new KiiJsonProperty<String>(String.class, "thingType");
	public static final KiiJsonProperty<Boolean> PROPERTY_SIMPLE_FLOW = new KiiJsonProperty<Boolean>(Boolean.class, "simpleFlow");
	public static final KiiJsonProperty<Boolean> PROPERTY_VERIFICATION_CODE_FLOW_STARTED_BY_USER = new KiiJsonProperty<Boolean>(Boolean.class, "verificationCodeFlowStartedByUser");
	public static final KiiJsonProperty<Boolean> PROPERTY_VERIFICATION_CODE_FLOW_STARTED_BY_THING = new KiiJsonProperty<Boolean>(Boolean.class, "verificationCodeFlowStartedByThing");
	
	public static final KiiJsonProperty<Long> PROPERTY_VERIFICATION_CODE_LENGTH = new KiiJsonProperty<Long>(Long.class, "verificationCodeLength");
	public static final KiiJsonProperty<Long> PROPERTY_VERIFICATION_CODE_TIMEOUT = new KiiJsonProperty<Long>(Long.class, "verificationCodeTimeout");
	
	public KiiThingTypeConfiguration() {
	}
	public KiiThingTypeConfiguration(JsonObject json) {
		super(json);
	}
	
	public boolean has(KiiJsonProperty<?> property) {
		return this.json.has(property.getName());
	}
	public String getThingType() {
		return PROPERTY_THINGTYPE.get(this.json);
	}
	public KiiThingTypeConfiguration setThingType(String thingType) {
		PROPERTY_THINGTYPE.set(this.json, thingType);
		return this;
	}
	public Boolean isSimpleFlow() {
		return PROPERTY_SIMPLE_FLOW.get(this.json);
	}
	public KiiThingTypeConfiguration setSimpleFlow(boolean isSimpleFlow) {
		PROPERTY_SIMPLE_FLOW.set(this.json, isSimpleFlow);
		return this;
	}
	public Boolean isVerificationCodeFlowStartedByUser() {
		return PROPERTY_VERIFICATION_CODE_FLOW_STARTED_BY_USER.get(this.json);
	}
	public KiiThingTypeConfiguration setVerificationCodeFlowStartedByUser(boolean isVerificationCodeFlowStartedByUser) {
		PROPERTY_VERIFICATION_CODE_FLOW_STARTED_BY_USER.set(this.json, isVerificationCodeFlowStartedByUser);
		return this;
	}
	public Boolean isVerificationCodeFlowStartedByThing() {
		return PROPERTY_VERIFICATION_CODE_FLOW_STARTED_BY_THING.get(this.json);
	}
	public KiiThingTypeConfiguration setVerificationCodeFlowStartedByThing(boolean isVerificationCodeFlowStartedByThing) {
		PROPERTY_VERIFICATION_CODE_FLOW_STARTED_BY_THING.set(this.json, isVerificationCodeFlowStartedByThing);
		return this;
	}
	
	public Long getVerificationCodeLength() {
		return PROPERTY_VERIFICATION_CODE_LENGTH.get(this.json);
	}
	public KiiThingTypeConfiguration setVerificationCodeLength(Long verificationCodeLength) {
		PROPERTY_VERIFICATION_CODE_LENGTH.set(this.json, verificationCodeLength);
		return this;
	}
	public Long getVerificationCodeTimeout() {
		return PROPERTY_VERIFICATION_CODE_TIMEOUT.get(this.json);
	}
	public KiiThingTypeConfiguration setVerificationCodeTimeout(Long verificationCodeTimeout) {
		PROPERTY_VERIFICATION_CODE_TIMEOUT.set(this.json, verificationCodeTimeout);
		return this;
	}
}
