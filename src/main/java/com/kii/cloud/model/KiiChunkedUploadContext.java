package com.kii.cloud.model;

import com.squareup.okhttp.MediaType;

public class KiiChunkedUploadContext {
	private final MediaType contentType;
	private final String uploadID;
	private final long dataSize;
	private long uploadedSize;
	public KiiChunkedUploadContext(String contentType, String uploadID, long dataSize) {
		this.contentType = MediaType.parse(contentType);
		this.uploadID = uploadID;
		this.dataSize = dataSize;
		this.uploadedSize = 0;
	}
	public KiiChunkedUploadContext increaseUploadedSize(long size) {
		this.uploadedSize += size;
		return this;
	}
	public boolean isCompleted() {
		return this.dataSize == uploadedSize;
	}
	public long getRestSize() {
		return this.dataSize - this.uploadedSize;
	}
	public MediaType getContentType() {
		return contentType;
	}
	public String getUploadID() {
		return this.uploadID;
	}
	public long getDataSize() {
		return this.dataSize;
	}
	public long getUploadedSize() {
		return this.uploadedSize;
	}
}
