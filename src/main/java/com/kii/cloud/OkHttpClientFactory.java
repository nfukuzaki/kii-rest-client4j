package com.kii.cloud;

import java.util.concurrent.TimeUnit;

import com.squareup.okhttp.OkHttpClient;

public class OkHttpClientFactory {
	public static OkHttpClient newInstance() {
		OkHttpClient client = new OkHttpClient();
		client.setConnectTimeout(10, TimeUnit.SECONDS);
		client.setWriteTimeout(10, TimeUnit.SECONDS);
		client.setReadTimeout(60, TimeUnit.SECONDS);
		return client;
	}
}
