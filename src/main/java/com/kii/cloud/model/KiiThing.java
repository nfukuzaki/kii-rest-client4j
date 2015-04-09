package com.kii.cloud.model;

import java.util.regex.Pattern;

import com.google.gson.JsonObject;

public class KiiThing extends KiiCustomableJsonModel<KiiThing> implements KiiCredentialsContainer {
	
	public static final Pattern THING_ID_PATTERN = Pattern.compile("^th\\.[a-z0-9]{12}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{8}$");
	public static final Pattern VENDOR_THING_ID_PATTERN = Pattern.compile("[a-zA-Z0-9-_\\.]{1,200}");
	public static final Pattern THING_PASSWORD_PATTERN = Pattern.compile("^\\p{Print}{1,200}$");
	public static final Pattern THING_GENERIC_FIELD_PATTERN = Pattern.compile("[a-zA-Z0-9-_\\. ]{0,100}");

	public static final KiiJsonProperty PROPERTY_ACCESS_TOKEN = new KiiJsonProperty("_accessToken");
	public static final KiiJsonProperty PROPERTY_REFRESH_TOKEN = new KiiJsonProperty("_refreshToken");
	public static final KiiJsonProperty PROPERTY_THING_ID = new KiiJsonProperty("_thingID");
	public static final KiiJsonProperty PROPERTY_VENDOR_THING_ID = new KiiJsonProperty("_vendorThingID");
	public static final KiiJsonProperty PROPERTY_PASSWORD = new KiiJsonProperty("_password");
	public static final KiiJsonProperty PROPERTY_THING_TYPE = new KiiJsonProperty("_thingType");
	public static final KiiJsonProperty PROPERTY_VENDOR = new KiiJsonProperty("_vendor");
	public static final KiiJsonProperty PROPERTY_FIRMWARE_VERSION = new KiiJsonProperty("_firmwareVersion");
	public static final KiiJsonProperty PROPERTY_PRODUCT_NAME = new KiiJsonProperty("_productName");
	public static final KiiJsonProperty PROPERTY_LOT = new KiiJsonProperty("_lot");
	public static final KiiJsonProperty PROPERTY_STRING_FIELD_1 = new KiiJsonProperty("_stringField1");
	public static final KiiJsonProperty PROPERTY_STRING_FIELD_2 = new KiiJsonProperty("_stringField2");
	public static final KiiJsonProperty PROPERTY_STRING_FIELD_3 = new KiiJsonProperty("_stringField3");
	public static final KiiJsonProperty PROPERTY_STRING_FIELD_4 = new KiiJsonProperty("_stringField4");
	public static final KiiJsonProperty PROPERTY_STRING_FIELD_5 = new KiiJsonProperty("_stringField5");
	public static final KiiJsonProperty PROPERTY_NUMBER_FIELD_1 = new KiiJsonProperty("_numberField1");
	public static final KiiJsonProperty PROPERTY_NUMBER_FIELD_2 = new KiiJsonProperty("_numberField2");
	public static final KiiJsonProperty PROPERTY_NUMBER_FIELD_3 = new KiiJsonProperty("_numberField3");
	public static final KiiJsonProperty PROPERTY_NUMBER_FIELD_4 = new KiiJsonProperty("_numberField4");
	public static final KiiJsonProperty PROPERTY_NUMBER_FIELD_5 = new KiiJsonProperty("_numberField5");
	public static final KiiJsonProperty PROPERTY_CREATED = new KiiJsonProperty("_created");
	public static final KiiJsonProperty PROPERTY_DISABLED = new KiiJsonProperty("_disabled");

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
	public String getAccessToken() {
		return PROPERTY_ACCESS_TOKEN.getString(this.credentials);
	}
	public KiiThing setAccessToken(String accessToken) {
		credentials.addProperty(PROPERTY_ACCESS_TOKEN.getName(), accessToken);
		return this;
	}
	@Override
	public String getRefreshToken() {
		return PROPERTY_REFRESH_TOKEN.getString(this.credentials);
	}
	public KiiThing setRefreshToken(String refreshToken) {
		credentials.addProperty(PROPERTY_REFRESH_TOKEN.getName(), refreshToken);
		return this;
	}
	public Long getCreated() {
		return PROPERTY_CREATED.getLong(this.json);
	}
	public KiiThing setCreated(Long created) {
		this.json.addProperty(PROPERTY_CREATED.getName(), created);
		return this;
	}
	@Override
	public boolean isAdmin() {
		return false;
	}
	
	public String getThingID() {
		return PROPERTY_THING_ID.getString(this.json);
	}
	public KiiThing setThingID(String thingID) {
		this.json.addProperty(PROPERTY_THING_ID.getName(), thingID);
		return this;
	}
	
	public String getVendorThingID() {
		return PROPERTY_VENDOR_THING_ID.getString(this.json);
	}
	public KiiThing setVendorThingID(String vendorThingID) {
		this.json.addProperty(PROPERTY_VENDOR_THING_ID.getName(), vendorThingID);
		return this;
	}
	public String getPassword() {
		return PROPERTY_PASSWORD.getString(this.json);
	}
	public KiiThing setPassword(String password) {
		this.json.addProperty(PROPERTY_PASSWORD.getName(), password);
		return this;
	}
	public String getThingType() {
		return PROPERTY_THING_TYPE.getString(this.json);
	}
	public KiiThing setThingType(String thingType) {
		this.json.addProperty(PROPERTY_THING_TYPE.getName(), thingType);
		return this;
	}
	public String getVendor() {
		return PROPERTY_VENDOR.getString(this.json);
	}
	public KiiThing setVendor(String vendor) {
		this.json.addProperty(PROPERTY_VENDOR.getName(), vendor);
		return this;
	}
	public String getFirmwareVersion() {
		return PROPERTY_FIRMWARE_VERSION.getString(this.json);
	}
	public KiiThing setFirmwareVersion(String firmwareVersion) {
		this.json.addProperty(PROPERTY_FIRMWARE_VERSION.getName(), firmwareVersion);
		return this;
	}
	public String getProductName() {
		return PROPERTY_PRODUCT_NAME.getString(this.json);
	}
	public KiiThing setProductName(String productName) {
		this.json.addProperty(PROPERTY_PRODUCT_NAME.getName(), productName);
		return this;
	}
	public String getLog() {
		return PROPERTY_LOT.getString(this.json);
	}
	public KiiThing setLog(String lot) {
		this.json.addProperty(PROPERTY_LOT.getName(), lot);
		return this;
	}
	public String getStringField1() {
		return PROPERTY_STRING_FIELD_1.getString(this.json);
	}
	public KiiThing setStringField1(String stringField) {
		this.json.addProperty(PROPERTY_STRING_FIELD_1.getName(), stringField);
		return this;
	}
	public String getStringField2() {
		return PROPERTY_STRING_FIELD_2.getString(this.json);
	}
	public KiiThing setStringField2(String stringField) {
		this.json.addProperty(PROPERTY_STRING_FIELD_2.getName(), stringField);
		return this;
	}
	public String getStringField3() {
		return PROPERTY_STRING_FIELD_3.getString(this.json);
	}
	public KiiThing setStringField3(String stringField) {
		this.json.addProperty(PROPERTY_STRING_FIELD_3.getName(), stringField);
		return this;
	}
	public String getStringField4() {
		return PROPERTY_STRING_FIELD_4.getString(this.json);
	}
	public KiiThing setStringField4(String stringField) {
		this.json.addProperty(PROPERTY_STRING_FIELD_4.getName(), stringField);
		return this;
	}
	public String getStringField5() {
		return PROPERTY_STRING_FIELD_5.getString(this.json);
	}
	public KiiThing setStringField5(String stringField) {
		this.json.addProperty(PROPERTY_STRING_FIELD_5.getName(), stringField);
		return this;
	}
	public String getNumberField1() {
		return PROPERTY_NUMBER_FIELD_1.getString(this.json);
	}
	public KiiThing setNumberField1(String numberField) {
		this.json.addProperty(PROPERTY_NUMBER_FIELD_1.getName(), numberField);
		return this;
	}
	public String getNumberField2() {
		return PROPERTY_NUMBER_FIELD_2.getString(this.json);
	}
	public KiiThing setNumberField2(String numberField) {
		this.json.addProperty(PROPERTY_NUMBER_FIELD_2.getName(), numberField);
		return this;
	}
	public String getNumberField3() {
		return PROPERTY_NUMBER_FIELD_3.getString(this.json);
	}
	public KiiThing setNumberField3(String numberField) {
		this.json.addProperty(PROPERTY_NUMBER_FIELD_3.getName(), numberField);
		return this;
	}
	public String getNumberField4() {
		return PROPERTY_NUMBER_FIELD_4.getString(this.json);
	}
	public KiiThing setNumberField4(String numberField) {
		this.json.addProperty(PROPERTY_NUMBER_FIELD_4.getName(), numberField);
		return this;
	}
	public String getNumberField5() {
		return PROPERTY_NUMBER_FIELD_5.getString(this.json);
	}
	public KiiThing setNumberField5(String numberField) {
		this.json.addProperty(PROPERTY_NUMBER_FIELD_5.getName(), numberField);
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
