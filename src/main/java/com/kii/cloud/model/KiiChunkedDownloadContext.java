package com.kii.cloud.model;

import com.squareup.okhttp.MediaType;

public class KiiChunkedDownloadContext {
	public static final long DEFAULT_CHUNK_SIZE = 1024 * 1024;
	private final long chunkSize;
	private long downloadedSize = 0;
	private MediaType contentType = null;
	private String eTag = null;
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
}
