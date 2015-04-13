package com.kii.cloud.model.storage;

public class KiiThingOwner {
	
	public enum OwnerType {
		USER,
		GROUP;
		@Override
		public String toString() {
			return this.name().toLowerCase();
		}
	}
	
	private final OwnerType ownerType;
	private final String identifier;
	
	public static KiiThingOwner user(String userID) {
		return new KiiThingOwner(OwnerType.USER, userID);
	}
	public static KiiThingOwner group(String groupID) {
		return new KiiThingOwner(OwnerType.GROUP, groupID);
	}
	public KiiThingOwner(OwnerType ownerType, String identifier) {
		this.ownerType = ownerType;
		this.identifier = identifier;
	}
	public OwnerType getOwnerType() {
		return ownerType;
	}
	public String getID() {
		return identifier;
	}
	public boolean isUser() {
		return this.ownerType == OwnerType.USER;
	}
	public boolean isGroup() {
		return this.ownerType == OwnerType.GROUP;
	}
	@Override
	public String toString() {
		return this.ownerType.toString() + ":" + this.identifier;
	}
}
