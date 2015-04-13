package com.kii.cloud.model.storage;

import java.util.regex.Pattern;

import com.google.gson.JsonObject;
import com.kii.cloud.model.KiiCredentialsContainer;
import com.kii.cloud.model.KiiCustomableJsonModel;
import com.kii.cloud.model.KiiJsonProperty;

public class KiiThing extends KiiCustomableJsonModel<KiiThing> implements KiiCredentialsContainer {
	
	public static final Pattern THING_ID_PATTERN = Pattern.compile("^th\\.[a-z0-9]{12}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{8}$");
	public static final Pattern VENDOR_THING_ID_PATTERN = Pattern.compile("[a-zA-Z0-9-_\\.]{1,200}");
	public static final Pattern THING_PASSWORD_PATTERN = Pattern.compile("^\\p{Print}{1,200}$");
	public static final Pattern THING_GENERIC_FIELD_PATTERN = Pattern.compile("[a-zA-Z0-9-_\\. ]{0,100}");

	public static final KiiJsonProperty<String> PROPERTY_ACCESS_TOKEN = new KiiJsonProperty<String>(String.class, "_accessToken");
	public static final KiiJsonProperty<String> PROPERTY_REFRESH_TOKEN = new KiiJsonProperty<String>(String.class, "_refreshToken");
	public static final KiiJsonProperty<String> PROPERTY_THING_ID = new KiiJsonProperty<String>(String.class, "_thingID");
	public static final KiiJsonProperty<String> PROPERTY_VENDOR_THING_ID = new KiiJsonProperty<String>(String.class, "_vendorThingID");
	public static final KiiJsonProperty<String> PROPERTY_PASSWORD = new KiiJsonProperty<String>(String.class, "_password");
	public static final KiiJsonProperty<String> PROPERTY_THING_TYPE = new KiiJsonProperty<String>(String.class, "_thingType");
	public static final KiiJsonProperty<String> PROPERTY_VENDOR = new KiiJsonProperty<String>(String.class, "_vendor");
	public static final KiiJsonProperty<String> PROPERTY_FIRMWARE_VERSION = new KiiJsonProperty<String>(String.class, "_firmwareVersion");
	public static final KiiJsonProperty<String> PROPERTY_PRODUCT_NAME = new KiiJsonProperty<String>(String.class, "_productName");
	public static final KiiJsonProperty<String> PROPERTY_LOT = new KiiJsonProperty<String>(String.class, "_lot");
	public static final KiiJsonProperty<String> PROPERTY_STRING_FIELD_1 = new KiiJsonProperty<String>(String.class, "_stringField1");
	public static final KiiJsonProperty<String> PROPERTY_STRING_FIELD_2 = new KiiJsonProperty<String>(String.class, "_stringField2");
	public static final KiiJsonProperty<String> PROPERTY_STRING_FIELD_3 = new KiiJsonProperty<String>(String.class, "_stringField3");
	public static final KiiJsonProperty<String> PROPERTY_STRING_FIELD_4 = new KiiJsonProperty<String>(String.class, "_stringField4");
	public static final KiiJsonProperty<String> PROPERTY_STRING_FIELD_5 = new KiiJsonProperty<String>(String.class, "_stringField5");
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
	public String getAccessToken() {
		return PROPERTY_ACCESS_TOKEN.get(this.credentials);
	}
	public KiiThing setAccessToken(String accessToken) {
		credentials.addProperty(PROPERTY_ACCESS_TOKEN.getName(), accessToken);
		return this;
	}
	@Override
	public String getRefreshToken() {
		return PROPERTY_REFRESH_TOKEN.get(this.credentials);
	}
	public KiiThing setRefreshToken(String refreshToken) {
		credentials.addProperty(PROPERTY_REFRESH_TOKEN.getName(), refreshToken);
		return this;
	}
	public Long getCreated() {
		return PROPERTY_CREATED.get(this.json);
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
		return PROPERTY_THING_ID.get(this.json);
	}
	public KiiThing setThingID(String thingID) {
		this.json.addProperty(PROPERTY_THING_ID.getName(), thingID);
		return this;
	}
	
	public String getVendorThingID() {
		return PROPERTY_VENDOR_THING_ID.get(this.json);
	}
	public KiiThing setVendorThingID(String vendorThingID) {
		this.json.addProperty(PROPERTY_VENDOR_THING_ID.getName(), vendorThingID);
		return this;
	}
	public String getPassword() {
		return PROPERTY_PASSWORD.get(this.json);
	}
	public KiiThing setPassword(String password) {
		this.json.addProperty(PROPERTY_PASSWORD.getName(), password);
		return this;
	}
	public String getThingType() {
		return PROPERTY_THING_TYPE.get(this.json);
	}
	public KiiThing setThingType(String thingType) {
		this.json.addProperty(PROPERTY_THING_TYPE.getName(), thingType);
		return this;
	}
	public String getVendor() {
		return PROPERTY_VENDOR.get(this.json);
	}
	public KiiThing setVendor(String vendor) {
		this.json.addProperty(PROPERTY_VENDOR.getName(), vendor);
		return this;
	}
	public String getFirmwareVersion() {
		return PROPERTY_FIRMWARE_VERSION.get(this.json);
	}
	public KiiThing setFirmwareVersion(String firmwareVersion) {
		this.json.addProperty(PROPERTY_FIRMWARE_VERSION.getName(), firmwareVersion);
		return this;
	}
	public String getProductName() {
		return PROPERTY_PRODUCT_NAME.get(this.json);
	}
	public KiiThing setProductName(String productName) {
		this.json.addProperty(PROPERTY_PRODUCT_NAME.getName(), productName);
		return this;
	}
	public String getLog() {
		return PROPERTY_LOT.get(this.json);
	}
	public KiiThing setLog(String lot) {
		this.json.addProperty(PROPERTY_LOT.getName(), lot);
		return this;
	}
	public String getStringField1() {
		return PROPERTY_STRING_FIELD_1.get(this.json);
	}
	public KiiThing setStringField1(String stringField) {
		this.json.addProperty(PROPERTY_STRING_FIELD_1.getName(), stringField);
		return this;
	}
	public String getStringField2() {
		return PROPERTY_STRING_FIELD_2.get(this.json);
	}
	public KiiThing setStringField2(String stringField) {
		this.json.addProperty(PROPERTY_STRING_FIELD_2.getName(), stringField);
		return this;
	}
	public String getStringField3() {
		return PROPERTY_STRING_FIELD_3.get(this.json);
	}
	public KiiThing setStringField3(String stringField) {
		this.json.addProperty(PROPERTY_STRING_FIELD_3.getName(), stringField);
		return this;
	}
	public String getStringField4() {
		return PROPERTY_STRING_FIELD_4.get(this.json);
	}
	public KiiThing setStringField4(String stringField) {
		this.json.addProperty(PROPERTY_STRING_FIELD_4.getName(), stringField);
		return this;
	}
	public String getStringField5() {
		return PROPERTY_STRING_FIELD_5.get(this.json);
	}
	public KiiThing setStringField5(String stringField) {
		this.json.addProperty(PROPERTY_STRING_FIELD_5.getName(), stringField);
		return this;
	}
	public Long getNumberField1() {
		return PROPERTY_NUMBER_FIELD_1.get(this.json);
	}
	public KiiThing setNumberField1(String numberField) {
		this.json.addProperty(PROPERTY_NUMBER_FIELD_1.getName(), numberField);
		return this;
	}
	public Long getNumberField2() {
		return PROPERTY_NUMBER_FIELD_2.get(this.json);
	}
	public KiiThing setNumberField2(String numberField) {
		this.json.addProperty(PROPERTY_NUMBER_FIELD_2.getName(), numberField);
		return this;
	}
	public Long getNumberField3() {
		return PROPERTY_NUMBER_FIELD_3.get(this.json);
	}
	public KiiThing setNumberField3(String numberField) {
		this.json.addProperty(PROPERTY_NUMBER_FIELD_3.getName(), numberField);
		return this;
	}
	public Long getNumberField4() {
		return PROPERTY_NUMBER_FIELD_4.get(this.json);
	}
	public KiiThing setNumberField4(String numberField) {
		this.json.addProperty(PROPERTY_NUMBER_FIELD_4.getName(), numberField);
		return this;
	}
	public Long getNumberField5() {
		return PROPERTY_NUMBER_FIELD_5.get(this.json);
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
