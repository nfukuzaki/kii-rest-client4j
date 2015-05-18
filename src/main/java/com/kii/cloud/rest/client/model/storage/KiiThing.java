package com.kii.cloud.rest.client.model.storage;

import java.util.regex.Pattern;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.model.KiiCredentialsContainer;
import com.kii.cloud.rest.client.model.KiiCustomableJsonModel;
import com.kii.cloud.rest.client.model.KiiJsonProperty;
import com.kii.cloud.rest.client.model.validation.RegularExpressionValidator;
import com.kii.cloud.rest.client.util.StringUtils;

public class KiiThing extends KiiCustomableJsonModel<KiiThing> implements KiiCredentialsContainer {
	
	public static final Pattern THING_ID_PATTERN = Pattern.compile("^th\\.[a-z0-9]{12}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{8}$");
	public static final Pattern VENDOR_THING_ID_PATTERN = Pattern.compile("[a-zA-Z0-9-_\\.]{1,200}");
	public static final Pattern THING_PASSWORD_PATTERN = Pattern.compile("^\\p{Print}{1,200}$");
	public static final Pattern THING_GENERIC_FIELD_PATTERN = Pattern.compile("[a-zA-Z0-9-_\\. ]{0,100}");

	public static final KiiJsonProperty<String> PROPERTY_ACCESS_TOKEN = new KiiJsonProperty<String>(String.class, "_accessToken");
	public static final KiiJsonProperty<String> PROPERTY_REFRESH_TOKEN = new KiiJsonProperty<String>(String.class, "_refreshToken");
	public static final KiiJsonProperty<String> PROPERTY_THING_ID = new KiiJsonProperty<String>(String.class, "_thingID");
	public static final KiiJsonProperty<String> PROPERTY_VENDOR_THING_ID = new KiiJsonProperty<String>(String.class, "_vendorThingID", new RegularExpressionValidator(VENDOR_THING_ID_PATTERN));
	public static final KiiJsonProperty<String> PROPERTY_PASSWORD = new KiiJsonProperty<String>(String.class, "_password", new RegularExpressionValidator(THING_PASSWORD_PATTERN));
	public static final KiiJsonProperty<String> PROPERTY_THING_TYPE = new KiiJsonProperty<String>(String.class, "_thingType", new RegularExpressionValidator(THING_GENERIC_FIELD_PATTERN));
	public static final KiiJsonProperty<String> PROPERTY_VENDOR = new KiiJsonProperty<String>(String.class, "_vendor", new RegularExpressionValidator(THING_GENERIC_FIELD_PATTERN));
	public static final KiiJsonProperty<String> PROPERTY_FIRMWARE_VERSION = new KiiJsonProperty<String>(String.class, "_firmwareVersion", new RegularExpressionValidator(THING_GENERIC_FIELD_PATTERN));
	public static final KiiJsonProperty<String> PROPERTY_PRODUCT_NAME = new KiiJsonProperty<String>(String.class, "_productName", new RegularExpressionValidator(THING_GENERIC_FIELD_PATTERN));
	public static final KiiJsonProperty<String> PROPERTY_LOT = new KiiJsonProperty<String>(String.class, "_lot", new RegularExpressionValidator(THING_GENERIC_FIELD_PATTERN));
	public static final KiiJsonProperty<String> PROPERTY_STRING_FIELD_1 = new KiiJsonProperty<String>(String.class, "_stringField1", new RegularExpressionValidator(THING_GENERIC_FIELD_PATTERN));
	public static final KiiJsonProperty<String> PROPERTY_STRING_FIELD_2 = new KiiJsonProperty<String>(String.class, "_stringField2", new RegularExpressionValidator(THING_GENERIC_FIELD_PATTERN));
	public static final KiiJsonProperty<String> PROPERTY_STRING_FIELD_3 = new KiiJsonProperty<String>(String.class, "_stringField3", new RegularExpressionValidator(THING_GENERIC_FIELD_PATTERN));
	public static final KiiJsonProperty<String> PROPERTY_STRING_FIELD_4 = new KiiJsonProperty<String>(String.class, "_stringField4", new RegularExpressionValidator(THING_GENERIC_FIELD_PATTERN));
	public static final KiiJsonProperty<String> PROPERTY_STRING_FIELD_5 = new KiiJsonProperty<String>(String.class, "_stringField5", new RegularExpressionValidator(THING_GENERIC_FIELD_PATTERN));
	public static final KiiJsonProperty<Long> PROPERTY_NUMBER_FIELD_1 = new KiiJsonProperty<Long>(Long.class, "_numberField1");
	public static final KiiJsonProperty<Long> PROPERTY_NUMBER_FIELD_2 = new KiiJsonProperty<Long>(Long.class, "_numberField2");
	public static final KiiJsonProperty<Long> PROPERTY_NUMBER_FIELD_3 = new KiiJsonProperty<Long>(Long.class, "_numberField3");
	public static final KiiJsonProperty<Long> PROPERTY_NUMBER_FIELD_4 = new KiiJsonProperty<Long>(Long.class, "_numberField4");
	public static final KiiJsonProperty<Long> PROPERTY_NUMBER_FIELD_5 = new KiiJsonProperty<Long>(Long.class, "_numberField5");
	public static final KiiJsonProperty<Long> PROPERTY_CREATED = new KiiJsonProperty<Long>(Long.class, "_created");
	public static final KiiJsonProperty<Boolean> PROPERTY_DISABLED = new KiiJsonProperty<Boolean>(Boolean.class, "_disabled");

	protected final JsonObject credentials = new JsonObject();
	
	public KiiThing() {
	}
	public KiiThing(JsonObject json) {
		super(json);
	}
	
	@Override
	public String getID() {
		return this.getThingID();
	}
	@Override
	public boolean hasCredentials() {
		return !StringUtils.isEmpty(this.getAccessToken());
	}
	@Override
	public String getAccessToken() {
		return PROPERTY_ACCESS_TOKEN.get(this.credentials);
	}
	public KiiThing setAccessToken(String accessToken) {
		PROPERTY_ACCESS_TOKEN.set(this.credentials, accessToken);
		return this;
	}
	@Override
	public String getRefreshToken() {
		return PROPERTY_REFRESH_TOKEN.get(this.credentials);
	}
	public KiiThing setRefreshToken(String refreshToken) {
		PROPERTY_REFRESH_TOKEN.set(this.credentials, refreshToken);
		return this;
	}
	public Long getCreated() {
		return PROPERTY_CREATED.get(this.json);
	}
	public KiiThing setCreated(Long created) {
		PROPERTY_CREATED.set(this.json, created);
		return this;
	}
	@Override
	public boolean isAdmin() {
		return false;
	}
	
	public String getThingID() {
		return PROPERTY_THING_ID.get(this.json);
	}
	public KiiThing setThingID(String thingID) {
		PROPERTY_THING_ID.set(this.json, thingID);
		return this;
	}
	
	public String getVendorThingID() {
		return PROPERTY_VENDOR_THING_ID.get(this.json);
	}
	public KiiThing setVendorThingID(String vendorThingID) {
		PROPERTY_VENDOR_THING_ID.set(this.json, vendorThingID);
		return this;
	}
	public String getPassword() {
		return PROPERTY_PASSWORD.get(this.json);
	}
	public KiiThing setPassword(String password) {
		PROPERTY_PASSWORD.set(this.json, password);
		return this;
	}
	public String getThingType() {
		return PROPERTY_THING_TYPE.get(this.json);
	}
	public KiiThing setThingType(String thingType) {
		PROPERTY_THING_TYPE.set(this.json, thingType);
		return this;
	}
	public String getVendor() {
		return PROPERTY_VENDOR.get(this.json);
	}
	public KiiThing setVendor(String vendor) {
		PROPERTY_VENDOR.set(this.json, vendor);
		return this;
	}
	public String getFirmwareVersion() {
		return PROPERTY_FIRMWARE_VERSION.get(this.json);
	}
	public KiiThing setFirmwareVersion(String firmwareVersion) {
		PROPERTY_FIRMWARE_VERSION.set(this.json, firmwareVersion);
		return this;
	}
	public String getProductName() {
		return PROPERTY_PRODUCT_NAME.get(this.json);
	}
	public KiiThing setProductName(String productName) {
		PROPERTY_PRODUCT_NAME.set(this.json, productName);
		return this;
	}
	public String getLog() {
		return PROPERTY_LOT.get(this.json);
	}
	public KiiThing setLog(String lot) {
		PROPERTY_LOT.set(this.json, lot);
		return this;
	}
	public String getStringField1() {
		return PROPERTY_STRING_FIELD_1.get(this.json);
	}
	public KiiThing setStringField1(String stringField) {
		PROPERTY_STRING_FIELD_1.set(this.json, stringField);
		return this;
	}
	public String getStringField2() {
		return PROPERTY_STRING_FIELD_2.get(this.json);
	}
	public KiiThing setStringField2(String stringField) {
		PROPERTY_STRING_FIELD_2.set(this.json, stringField);
		return this;
	}
	public String getStringField3() {
		return PROPERTY_STRING_FIELD_3.get(this.json);
	}
	public KiiThing setStringField3(String stringField) {
		PROPERTY_STRING_FIELD_3.set(this.json, stringField);
		return this;
	}
	public String getStringField4() {
		return PROPERTY_STRING_FIELD_4.get(this.json);
	}
	public KiiThing setStringField4(String stringField) {
		PROPERTY_STRING_FIELD_4.set(this.json, stringField);
		return this;
	}
	public String getStringField5() {
		return PROPERTY_STRING_FIELD_5.get(this.json);
	}
	public KiiThing setStringField5(String stringField) {
		PROPERTY_STRING_FIELD_5.set(this.json, stringField);
		return this;
	}
	public Long getNumberField1() {
		return PROPERTY_NUMBER_FIELD_1.get(this.json);
	}
	public KiiThing setNumberField1(Long numberField) {
		PROPERTY_NUMBER_FIELD_1.set(this.json, numberField);
		return this;
	}
	public Long getNumberField2() {
		return PROPERTY_NUMBER_FIELD_2.get(this.json);
	}
	public KiiThing setNumberField2(Long numberField) {
		PROPERTY_NUMBER_FIELD_2.set(this.json, numberField);
		return this;
	}
	public Long getNumberField3() {
		return PROPERTY_NUMBER_FIELD_3.get(this.json);
	}
	public KiiThing setNumberField3(Long numberField) {
		PROPERTY_NUMBER_FIELD_3.set(this.json, numberField);
		return this;
	}
	public Long getNumberField4() {
		return PROPERTY_NUMBER_FIELD_4.get(this.json);
	}
	public KiiThing setNumberField4(Long numberField) {
		PROPERTY_NUMBER_FIELD_4.set(this.json, numberField);
		return this;
	}
	public Long getNumberField5() {
		return PROPERTY_NUMBER_FIELD_5.get(this.json);
	}
	public KiiThing setNumberField5(Long numberField) {
		PROPERTY_NUMBER_FIELD_5.set(this.json, numberField);
		return this;
	}
	public String getIdentifier() {
		if (PROPERTY_THING_ID.has(this.json)) {
			return this.getThingID();
		}
		if (PROPERTY_VENDOR_THING_ID.has(this.json)) {
			return this.getVendorThingID();
		}
		return null;
	}
}
