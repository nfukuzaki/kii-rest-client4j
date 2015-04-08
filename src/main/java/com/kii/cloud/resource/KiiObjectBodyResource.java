package com.kii.cloud.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.HttpContentRange;
import com.kii.cloud.model.KiiChunkedDownloadContext;
import com.kii.cloud.model.KiiChunkedUploadContext;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.kii.cloud.util.GsonUtils;
import com.kii.cloud.util.IOUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

public class KiiObjectBodyResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/body";
	
	public static final MediaType MEDIA_TYPE_START_OBJECT_BODY_UPDATE_REQUEST = MediaType.parse("application/vnd.kii.StartObjectBodyUploadRequest+json");
	public static final MediaType MEDIA_TYPE_OBJECT_BODY_PUBLICATION_REQUEST = MediaType.parse("application/vnd.kii.ObjectBodyPublicationRequest+json");
	public static final MediaType MEDIA_TYPE_START_OBJECT_BODY_UPLOAD_REQUEST = MediaType.parse("application/vnd.kii.StartObjectBodyUploadRequest+json");
	
	public KiiObjectBodyResource(KiiObjectResource parent) {
		super(parent);
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
	/**
	 * 
	 * NOTE:This feature has not documented yet.
	 * @return
	 * @throws KiiRestException
	 */
	public boolean exists() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.HEAD, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
			return response.isSuccessful();
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param stream
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/downloading/
	 */
	public void download(OutputStream stream) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			InputStream responseBody = this.parseResponseAsInputStream(request, response);
			try {
				IOUtils.copy(responseBody, stream);
			} catch (IOException e) {
				throw new KiiRestException("", e);
			}
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param context
	 * @param stream
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/downloading/
	 */
	public KiiChunkedDownloadContext downloadByChunk(KiiChunkedDownloadContext context, OutputStream stream) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		if (context.getETag() != null) {
			headers.put("If-Match", context.getETag());
		}
		headers.put("Range", "bytes=" + context.getDownloadedSize() + "-" + (context.getDownloadedSize() + context.getChunkSize() - 1));
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.GET, headers);
		try {
			Response response = this.execute(request);
			InputStream responseBody = this.parseResponseAsInputStream(request, response);
			try {
				long downloadedBytes = IOUtils.copy(responseBody, stream);
				context.setETag(response.header("ETag"));
				context.setContentType(MediaType.parse(response.header("Content-Type")));
				context.setContentRange(HttpContentRange.fromHeader(response.header("Content-Range")));
				context.increaseDownloadedSize(downloadedBytes);
				return context;
			} catch (IOException e) {
				throw new KiiRestException("", e);
			}
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param contentType
	 * @param stream
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/uploading/
	 */
	public void upload(String contentType, InputStream stream) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.PUT, headers, MediaType.parse(contentType), stream);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param contentType
	 * @param dataSize
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/uploading/
	 */
	public KiiChunkedUploadContext beginChunkedUpload(String contentType, long dataSize) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/uploads"), Method.POST, headers, MEDIA_TYPE_START_OBJECT_BODY_UPLOAD_REQUEST, new JsonObject());
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiChunkedUploadContext(contentType, GsonUtils.getString(responseBody, "uploadID"), dataSize);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param context
	 * @param chunk
	 * @return
	 * @throws KiiRestException
	 */
	public KiiChunkedUploadContext uploadByChunk(KiiChunkedUploadContext context, byte[] chunk) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		headers.put("Content-Range", "bytes=" + context.getUploadedSize() +
				"-" + (context.getUploadedSize() + chunk.length - 1) +
				"/" + context.getDataSize());
		KiiRestRequest request = new KiiRestRequest(getUrl("/uploads/" + context.getUploadID() + "/data"), Method.PUT, headers, context.getContentType(), chunk);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
			context.increaseUploadedSize(chunk.length);
			return context;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param context
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/uploading/
	 */
	public void commitChunkedUpload(KiiChunkedUploadContext context) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/uploads/" + context.getUploadID() + "/status/committed"), Method.POST, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param context
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/uploading/
	 */
	public void cancelChunkedUpload(KiiChunkedUploadContext context) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl("/uploads/" + context.getUploadID() + "/status/cancelled"), Method.POST, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/publishingbody/
	 */
	public String publish() throws KiiRestException {
		return this.publish(0);
	}
	/**
	 * @param expiresAt
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/publishingbody/
	 */
	public String publish(long expiresAt) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject requestBody = new JsonObject();
		if (expiresAt > 0) {
			requestBody.addProperty("expiresAt", expiresAt);
		}
		KiiRestRequest request = new KiiRestRequest(getUrl("/publish"), Method.POST, headers, MEDIA_TYPE_OBJECT_BODY_PUBLICATION_REQUEST, requestBody);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return GsonUtils.getString(responseBody, "url");
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/deletingbody/
	 */
	public void delete() throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.DELETE, headers);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
}
