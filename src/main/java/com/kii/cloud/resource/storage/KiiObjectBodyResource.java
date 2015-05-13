package com.kii.cloud.resource.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.annotation.AdminAPI;
import com.kii.cloud.model.HttpContentRange;
import com.kii.cloud.model.storage.KiiChunkedDownloadContext;
import com.kii.cloud.model.storage.KiiChunkedUploadContext;
import com.kii.cloud.model.storage.KiiGroup;
import com.kii.cloud.model.storage.KiiThing;
import com.kii.cloud.model.storage.KiiUser;
import com.kii.cloud.resource.KiiRestRequest;
import com.kii.cloud.resource.KiiRestSubResource;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.kii.cloud.util.GsonUtils;
import com.kii.cloud.util.IOUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

/**
 * Represents the object body resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/buckets/{BUCKET_NAME}/objects/{OBJECT_ID}/body
 * <li>https://hostname/api/apps/{APP_ID}/users/{USER_IDENTIFIER}/buckets/{BUCKET_NAME}/objects/{OBJECT_ID}/body
 * <li>https://hostname/api/apps/{APP_ID}/groups/{GROUP_ID}/buckets/{BUCKET_NAME}/objects/{OBJECT_ID}/body
 * <li>https://hostname/api/apps/{APP_ID}/things/{THING_ID}/buckets/{BUCKET_NAME}/objects/{OBJECT_ID}/body
 * </ul>
 *
 */
public class KiiObjectBodyResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/body";
	
	public static final MediaType MEDIA_TYPE_START_OBJECT_BODY_UPDATE_REQUEST = MediaType.parse("application/vnd.kii.StartObjectBodyUploadRequest+json");
	public static final MediaType MEDIA_TYPE_OBJECT_BODY_PUBLICATION_REQUEST = MediaType.parse("application/vnd.kii.ObjectBodyPublicationRequest+json");
	public static final MediaType MEDIA_TYPE_START_OBJECT_BODY_UPLOAD_REQUEST = MediaType.parse("application/vnd.kii.StartObjectBodyUploadRequest+json");
	public static final MediaType MEDIA_TYPE_OBJECT_BODY_MOVE_REQUEST = MediaType.parse("application/vnd.kii.ObjectBodyMoveRequest+json");
	
	public KiiObjectBodyResource(KiiObjectResource parent) {
		super(parent);
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
	/**
	 * @param bucketName
	 * @param objectID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/movingbody/
	 */
	@AdminAPI
	public void moveToAppScope(String bucketName, String objectID) throws KiiRestException {
		JsonObject targetObjectScope = new JsonObject();
		targetObjectScope.addProperty("appID", getRootResource().getAppID());
		targetObjectScope.addProperty("type", "APP");
		this.moveObjectBody(targetObjectScope, bucketName, objectID);
	}
	/**
	 * @param user
	 * @param bucketName
	 * @param objectID
	 * @throws KiiRestException
	 */
	public void moveToUserScope(KiiUser user, String bucketName, String objectID) throws KiiRestException {
		this.moveToUserScope(user.getUserID(), bucketName, objectID);
	}
	/**
	 * @param userID
	 * @param bucketName
	 * @param objectID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/movingbody/
	 */
	@AdminAPI
	public void moveToUserScope(String userID, String bucketName, String objectID) throws KiiRestException {
		JsonObject targetObjectScope = new JsonObject();
		targetObjectScope.addProperty("appID", getRootResource().getAppID());
		targetObjectScope.addProperty("userID", userID);
		targetObjectScope.addProperty("type", "APP_AND_USER");
		this.moveObjectBody(targetObjectScope, bucketName, objectID);
	}
	/**
	 * @param group
	 * @param bucketName
	 * @param objectID
	 * @throws KiiRestException
	 */
	@AdminAPI
	public void moveToGroupScope(KiiGroup group, String bucketName, String objectID) throws KiiRestException {
		this.moveToGroupScope(group.getGroupID(), bucketName, objectID);
	}
	/**
	 * @param groupID
	 * @param bucketName
	 * @param objectID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/movingbody/
	 */
	@AdminAPI
	public void moveToGroupScope(String groupID, String bucketName, String objectID) throws KiiRestException {
		JsonObject targetObjectScope = new JsonObject();
		targetObjectScope.addProperty("appID", getRootResource().getAppID());
		targetObjectScope.addProperty("groupID", groupID);
		targetObjectScope.addProperty("type", "APP_AND_GROUP");
		this.moveObjectBody(targetObjectScope, bucketName, objectID);
	}
	/**
	 * @param thing
	 * @param bucketName
	 * @param objectID
	 * @throws KiiRestException
	 */
	public void moveToThingScope(KiiThing thing, String bucketName, String objectID) throws KiiRestException {
		this.moveToThingScope(thing.getThingID(), bucketName, objectID);
	}
	/**
	 * @param thingID
	 * @param bucketName
	 * @param objectID
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-data/object-storages/movingbody/
	 */
	@AdminAPI
	public void moveToThingScope(String thingID, String bucketName, String objectID) throws KiiRestException {
		JsonObject targetObjectScope = new JsonObject();
		targetObjectScope.addProperty("appID", getRootResource().getAppID());
		targetObjectScope.addProperty("thingID", thingID);
		targetObjectScope.addProperty("type", "APP_AND_THING");
		this.moveObjectBody(targetObjectScope, bucketName, objectID);
	}
	private void moveObjectBody(JsonObject targetObjectScope, String bucketName, String objectID) throws KiiRestException {
		Map<String, String> headers = this.newAuthorizedHeaders();
		JsonObject requestBody = new JsonObject();
		requestBody.add("targetObjectScope", targetObjectScope);
		requestBody.addProperty("targetBucketID", bucketName);
		requestBody.addProperty("targetObjectID", objectID);
		
		KiiRestRequest request = new KiiRestRequest(getUrl("/move"), Method.POST, headers, MEDIA_TYPE_OBJECT_BODY_MOVE_REQUEST, requestBody);
		try {
			Response response = this.execute(request);
			this.parseResponse(request, response);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
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
	 * @param file
	 * @throws KiiRestException
	 * @throws IOException
	 */
	public void upload(String contentType, File file) throws KiiRestException, IOException {
		InputStream stream = new FileInputStream(file);
		try {
			this.upload(contentType, stream);
		} finally {
			stream.close();
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
		KiiRestRequest request = new KiiRestRequest(getUrl("/uploads/%s/data", context.getUploadID()), Method.PUT, headers, context.getContentType(), chunk);
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
		KiiRestRequest request = new KiiRestRequest(getUrl("/uploads/%s/status/committed", context.getUploadID()), Method.POST, headers);
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
		KiiRestRequest request = new KiiRestRequest(getUrl("/uploads/%s/status/cancelled", context.getUploadID()), Method.POST, headers);
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
