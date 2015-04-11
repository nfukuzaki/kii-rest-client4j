package com.kii.cloud.resource;

public class KiiServerCodeHookResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/versions";
	private final String version;
	
	public KiiServerCodeHookResource(KiiServerCodeHooksResource parent) {
		super(parent);
		this.version = "current";
	}
	public KiiServerCodeHookResource(KiiServerCodeHooksResource parent, String version) {
		super(parent);
		this.version = version;
	}
	@Override
	public String getPath() {
		return BASE_PATH + "/" + this.version;
	}

}
