package com.kii.cloud.rest.client.logging;

public class KiiDefaultLogger implements KiiLogger {
	public static KiiDefaultLogger INSTANCE = new KiiDefaultLogger();
	private KiiDefaultLogger() {
	}
	@Override
	public void debug(String msg) {
		this.debug(msg, null);
	}
	@Override
	public void debug(String msg, Throwable e) {
		System.out.println(msg);
		if (e != null) {
			System.out.println(e.toString());
		}
	}
	@Override
	public void info(String msg) {
		this.info(msg, null);
	}
	@Override
	public void info(String msg, Throwable e) {
		System.out.println(msg);
		if (e != null) {
			System.out.println(e.toString());
		}
	}
	@Override
	public void warn(String msg) {
		this.warn(msg, null);
	}
	@Override
	public void warn(String msg, Throwable e) {
		System.out.println(msg);
		if (e != null) {
			System.out.println(e.toString());
		}
	}
	@Override
	public void error(String msg) {
		this.error(msg, null);
	}
	@Override
	public void error(String msg, Throwable e) {
		System.out.println(msg);
		if (e != null) {
			System.out.println(e.toString());
		}
	}
}
