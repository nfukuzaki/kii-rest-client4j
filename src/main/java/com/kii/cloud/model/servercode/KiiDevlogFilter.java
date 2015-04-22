package com.kii.cloud.model.servercode;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.kii.cloud.model.KiiJsonModel;
import com.kii.cloud.model.KiiJsonProperty;
import com.kii.cloud.model.servercode.KiiDevlog.LogLevel;

public class KiiDevlogFilter extends KiiJsonModel {
	
	public static final SimpleDateFormat FILTER_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
	public static final KiiJsonProperty<Integer> PROPERTY_LIMIT = new KiiJsonProperty<Integer>(Integer.class, "limit");
	public static final KiiJsonProperty<String> PROPERTY_USER_ID = new KiiJsonProperty<String>(String.class, "userID");
	public static final KiiJsonProperty<String> PROPERTY_LEVEL = new KiiJsonProperty<String>(String.class, "level");
	public static final KiiJsonProperty<String> PROPERTY_DATE_FROM = new KiiJsonProperty<String>(String.class, "dateFrom");
	public static final KiiJsonProperty<String> PROPERTY_DATE_TO = new KiiJsonProperty<String>(String.class, "dateTo");
	
	public KiiDevlogFilter limit(int limit) {
		PROPERTY_LIMIT.set(this.json, limit);
		return this;
	}
	public KiiDevlogFilter user(String userID) {
		PROPERTY_USER_ID.set(this.json, userID);
		return this;
	}
	public KiiDevlogFilter level(LogLevel level) {
		PROPERTY_LEVEL.set(this.json, level.name());
		return this;
	}
	public KiiDevlogFilter from(Date dateFrom) {
		PROPERTY_DATE_FROM.set(this.json, FILTER_DATE_FORMATTER.format(dateFrom));
		return this;
	}
	public KiiDevlogFilter to(Date dateTo) {
		PROPERTY_DATE_TO.set(this.json, FILTER_DATE_FORMATTER.format(dateTo));
		return this;
	}
}
