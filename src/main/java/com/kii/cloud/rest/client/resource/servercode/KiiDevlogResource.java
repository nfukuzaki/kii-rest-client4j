package com.kii.cloud.rest.client.resource.servercode;

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
import com.kii.cloud.rest.client.OkHttpClientFactory;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.logging.KiiLogger;
import com.kii.cloud.rest.client.model.KiiCredentialsContainer;
import com.kii.cloud.rest.client.model.servercode.KiiDevlog;
import com.kii.cloud.rest.client.model.servercode.KiiDevlogFilter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ws.WebSocket;
import com.squareup.okhttp.ws.WebSocket.PayloadType;
import com.squareup.okhttp.ws.WebSocketCall;
import com.squareup.okhttp.ws.WebSocketListener;

/**
 * Represents the devlog resource like following URI:
 * 
 * <ul>
 * <li>wss:hostname:443/logs
 * </ul>
 */
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
	private final KiiLogger logger;
	private static final OkHttpClient client = OkHttpClientFactory.newInstance();
	private WebSocketCall call;
	
	public KiiDevlogResource(String appID, String appKey, String endpoint, KiiCredentialsContainer credentials, KiiLogger logger) {
		this.appID = appID;
		this.appKey = appKey;
		this.endpoint = endpoint;
		this.credentials = credentials;
		this.logger = logger;
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
		return this.cat(null);
	}
	/**
	 * @param filter
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/commandlinetools/devlog/
	 */
	public List<KiiDevlog> cat(KiiDevlogFilter filter) throws KiiRestException {
		if (filter == null) {
			filter = new KiiDevlogFilter();
		}
		if (this.endpoint == null) {
			throw new IllegalStateException("Devlog endpoint address not set.");
		}
		if (this.credentials == null) {
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
		
		this.logger.info("connect to " + this.endpoint);
		Request request = new Request.Builder()
			.url(this.endpoint)
			.get()
			.build();
		this.call = WebSocketCall.create(client, request);
		this.call.enqueue(new WebSocketListener() {
			@Override
			public void onOpen(final WebSocket webSocket, Response response) {
				logger.info("WebSocketListener.onOpen");
				new Thread() {
					@Override
					public void run() {
						Buffer buffer = new Buffer();
						try {
							buffer.writeUtf8(requestBody.toString());
							webSocket.sendMessage(PayloadType.TEXT, buffer);
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							buffer.close();
						}
					}
				}.start();
			}
			@Override
			public void onClose(int code, String reason) {
				logger.info("WebSocketListener.onClose");
				latch.countDown();
			}
			@Override
			public void onFailure(IOException e, Response response) {
				logger.error("WebSocketListener.onFailure", e);
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
	/**
	 * @param listener
	 * @throws Exception
	 * @see http://documentation.kii.com/en/guides/commandlinetools/devlog/
	 */
	public void tail(TailListener listener) throws Exception {
		this.tail(null, listener);
	}
	/**
	 * @param filter
	 * @param listener
	 * @throws Exception
	 * @see http://documentation.kii.com/en/guides/commandlinetools/devlog/
	 */
	public void tail(KiiDevlogFilter filter, final TailListener listener) throws Exception {
		if (filter == null) {
			filter = new KiiDevlogFilter();
		}
		if (listener == null) {
			throw new IllegalArgumentException("listener is null");
		}
		if (this.endpoint == null) {
			throw new IllegalStateException("Devlog endpoint address not set.");
		}
		if (this.credentials == null) {
			throw new IllegalStateException("Admin credentials not set.");
		}
		final JsonObject requestBody = filter.getJsonObject();
		requestBody.addProperty("appID", this.appID);
		requestBody.addProperty("appKey", this.appKey);
		requestBody.addProperty("token", this.credentials.getAccessToken());
		requestBody.addProperty("command", "tail");
		
		this.logger.info("connect to " + this.endpoint);
		Request request = new Request.Builder()
			.url(this.endpoint)
			.get()
			.build();
		this.call = WebSocketCall.create(client, request);
		this.call.enqueue(new WebSocketListener() {
			@Override
			public void onOpen(final WebSocket webSocket, Response response) {
				logger.info("WebSocketListener.onOpen");
				new Thread() {
					@Override
					public void run() {
						Buffer buffer = new Buffer();
						try {
							buffer.writeUtf8(requestBody.toString());
							webSocket.sendMessage(PayloadType.TEXT, buffer);
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							buffer.close();
						}
					}
				}.start();
			}
			@Override
			public void onClose(int code, String reason) {
				logger.info("WebSocketListener.onClose");
			}
			@Override
			public void onFailure(IOException e, Response response) {
				logger.error("WebSocketListener.onFailure", e);
				listener.onFailure(e);
			}
			@Override
			public void onMessage(BufferedSource payload, PayloadType type) throws IOException {
				try {
					JsonArray logs = (JsonArray)new JsonParser().parse(payload.readUtf8());
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
