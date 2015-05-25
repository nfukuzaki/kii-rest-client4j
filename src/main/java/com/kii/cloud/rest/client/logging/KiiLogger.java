package com.kii.cloud.rest.client.logging;

public interface KiiLogger {
	public void debug(String msg);
	public void debug(String msg, Throwable e);
	public void info(String msg);
	public void info(String msg, Throwable e);
	public void warn(String msg);
	public void warn(String msg, Throwable e);
	public void error(String msg);
	public void error(String msg, Throwable e);
}
