package com.kii.cloud.rest.client.logging;

public class KiiEmptyLogger implements KiiLogger {
	public static KiiEmptyLogger INSTANCE = new KiiEmptyLogger();
	private KiiEmptyLogger() {
	}
	@Override
	public void debug(String msg) {
	}
	@Override
	public void debug(String msg, Throwable e) {
	}
	@Override
	public void info(String msg) {
	}
	@Override
	public void info(String msg, Throwable e) {
	}
	@Override
	public void warn(String msg) {
	}
	@Override
	public void warn(String msg, Throwable e) {
	}
	@Override
	public void error(String msg) {
	}
	@Override
	public void error(String msg, Throwable e) {
	}
}
