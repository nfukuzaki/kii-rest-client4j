package com.kii.cloud.resource.servercode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import okio.Buffer;
import okio.BufferedSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.OkHttpClientFactory;
import com.kii.cloud.model.KiiCredentialsContainer;
import com.kii.cloud.model.servercode.KiiDevlog;
import com.kii.cloud.model.servercode.KiiDevlogFilter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ws.WebSocket;
import com.squareup.okhttp.ws.WebSocket.PayloadType;
import com.squareup.okhttp.ws.WebSocketCall;
import com.squareup.okhttp.ws.WebSocketListener;

public class KiiDevlogResource {
	
	public interface TailListener {
		public void onTail(List<KiiDevlog> logs, TailContext context);
		public void onFailure(Exception e);
	}
	public interface TailContext {
		public void close();
	}
	
	private final String appID;
	private final String appKey;
	private final String endpoint;
	private final KiiCredentialsContainer credentials;
	private static final OkHttpClient client = OkHttpClientFactory.newInstance();
	private WebSocketCall call;
	
	public KiiDevlogResource(String appID, String appKey, String endpoint, KiiCredentialsContainer credentials) {
		this.appID = appID;
		this.appKey = appKey;
		this.endpoint = endpoint;
		this.credentials = credentials;
	}
	@Override
	protected void finalize() throws Throwable {
		try {
			if (this.call != null) {
				this.call.cancel();
			}
		} catch (Throwable ignore) {
		}
		super.finalize();
	}
	/**
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/commandlinetools/devlog/
	 */
	public List<KiiDevlog> cat() throws KiiRestException {
		return this.cat(new KiiDevlogFilter());
	}
	/**
	 * @param filter
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/commandlinetools/devlog/
	 */
	public List<KiiDevlog> cat(KiiDevlogFilter filter) throws KiiRestException {
		if (this.endpoint == null) {
			throw new IllegalStateException("Devlog endpoint address not set.");
		}
		if (this.credentials == null || !this.credentials.isAdmin()) {
			throw new IllegalStateException("Admin credentials not set.");
		}
		final JsonObject requestBody = filter.getJsonObject();
		requestBody.addProperty("appID", this.appID);
		requestBody.addProperty("appKey", this.appKey);
		requestBody.addProperty("token", this.credentials.getAccessToken());
		requestBody.addProperty("command", "cat");
		
		final CountDownLatch latch = new CountDownLatch(1);
		final AtomicReference<JsonArray> logs = new AtomicReference<JsonArray>();
		final AtomicReference<Exception> exception = new AtomicReference<Exception>();
		
		Request request = new Request.Builder()
			.url(this.endpoint)
			.get()
			.build();
		this.call = WebSocketCall.create(client, request);
		this.call.enqueue(new WebSocketListener() {
			@Override
			public void onOpen(WebSocket webSocket, Request request, Response response) throws IOException {
				Buffer buffer = new Buffer();
				try {
					buffer.writeUtf8(requestBody.toString());
					webSocket.sendMessage(PayloadType.TEXT, buffer);
				} finally {
					buffer.close();
				}
			}
			@Override
			public void onClose(int code, String reason) {
				latch.countDown();
			}
			@Override
			public void onFailure(IOException e) {
				exception.set(e);
				latch.countDown();
			}
			@Override
			public void onMessage(BufferedSource payload, PayloadType type) throws IOException {
				try {
					logs.set((JsonArray)new JsonParser().parse(payload.readUtf8()));;
					payload.close();
				} catch (Exception e) {
					exception.set(e);
				} finally {
					latch.countDown();
				}
			}
			@Override
			public void onPong(Buffer payload) {
			}
		});
		try {
			if (!latch.await(10, TimeUnit.SECONDS)) {
				throw new KiiRestException("Timed out waiting for receiving devlog.");
			}
		} catch (InterruptedException e) {
			throw new KiiRestException("Timed out waiting for receiving devlog.", e);
		}
		List<KiiDevlog> results = new ArrayList<KiiDevlog>();
		for (int i = 0; i < logs.get().size(); i++) {
			results.add(new KiiDevlog(logs.get().get(i).getAsJsonObject()));
		}
		Collections.sort(results);
		return results;
	}
	public void tail(TailListener listener) throws Exception {
		this.tail(new KiiDevlogFilter(), listener);
	}
	public void tail(KiiDevlogFilter filter, final TailListener listener) throws Exception {
		if (this.endpoint == null) {
			throw new IllegalStateException("Devlog endpoint address not set.");
		}
		if (this.credentials == null || !this.credentials.isAdmin()) {
			throw new IllegalStateException("Admin credentials not set.");
		}
		final JsonObject requestBody = filter.getJsonObject();
		requestBody.addProperty("appID", this.appID);
		requestBody.addProperty("appKey", this.appKey);
		requestBody.addProperty("token", this.credentials.getAccessToken());
		requestBody.addProperty("command", "tail");
		
		Request request = new Request.Builder()
		.url(this.endpoint)
		.get()
		.build();
	this.call = WebSocketCall.create(client, request);
	this.call.enqueue(new WebSocketListener() {
		@Override
		public void onOpen(WebSocket webSocket, Request request, Response response) throws IOException {
			Buffer buffer = new Buffer();
			try {
				buffer.writeUtf8(requestBody.toString());
				webSocket.sendMessage(PayloadType.TEXT, buffer);
			} finally {
				buffer.close();
			}
		}
		@Override
		public void onClose(int code, String reason) {
		}
		@Override
		public void onFailure(IOException e) {
			listener.onFailure(e);
		}
		@Override
		public void onMessage(BufferedSource payload, PayloadType type) throws IOException {
			try {
				System.out.println("##### onMessage");
				JsonArray logs = (JsonArray)new JsonParser().parse(payload.readUtf8());
				System.out.println(logs.toString());
				payload.close();
				List<KiiDevlog> results = new ArrayList<KiiDevlog>();
				for (int i = 0; i < logs.size(); i++) {
					results.add(new KiiDevlog(logs.get(i).getAsJsonObject()));
				}
				Collections.sort(results);
				listener.onTail(results, new TailContext() {
					@Override
					public void close() {
						try {
							call.cancel();
						} catch (Exception ignore) {
						}
					}
				});
			} catch (Exception e) {
				listener.onFailure(e);
			}
		}
		@Override
		public void onPong(Buffer payload) {
		}
	});
	}
}
