package com.kii.cloud.rest.client.logging;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.SkipAcceptableTestRunner;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiDefaultLoggerTest {
	@Test
	public void test() throws Exception {
		KiiLogger logger = KiiDefaultLogger.INSTANCE;
		logger.debug("debug");
		logger.debug("debug with exception", new RuntimeException());
		logger.info("info");
		logger.info("info with exception", new RuntimeException());
		logger.warn("warn");
		logger.warn("warn with exception", new RuntimeException());
		logger.error("error");
		logger.error("error with exception", new RuntimeException());
	}
}
