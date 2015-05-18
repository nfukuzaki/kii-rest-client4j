package com.kii.cloud.rest.client.model.storage;

import com.kii.cloud.rest.client.model.HttpContentRange;
import com.squareup.okhttp.MediaType;

public class KiiChunkedDownloadContext {
	public static final long DEFAULT_CHUNK_SIZE = 1024 * 1024;
	private final long chunkSize;
	private long downloadedSize = 0;
	private MediaType contentType = null;
	private String eTag = null;
	private HttpContentRange contentRange = null;
	public KiiChunkedDownloadContext() {
		this(DEFAULT_CHUNK_SIZE);
	}
	public KiiChunkedDownloadContext(long chunkSize) {
		this.chunkSize = chunkSize;
	}
	public long getChunkSize() {
		return chunkSize;
	}
	public KiiChunkedDownloadContext increaseDownloadedSize(long size) {
		this.downloadedSize += size;
		return this;
	}
	public long getDownloadedSize() {
		return this.downloadedSize;
	}
	public MediaType getContentType() {
		return this.contentType;
	}
	public void setContentType(MediaType contentType) {
		this.contentType = contentType;
	}
	public String getETag() {
		return this.eTag;
	}
	public void setETag(String eTag) {
		this.eTag = eTag;
	}
	public HttpContentRange getContentRange() {
		return this.contentRange;
	}
	public void setContentRange(HttpContentRange contentRange) {
		this.contentRange = contentRange;
	}
	public boolean isCompleted() {
		if (this.contentRange == null) {
			return false;
		}
		return this.contentRange.getTotal() - 1 == this.contentRange.getTo();
	}
}
