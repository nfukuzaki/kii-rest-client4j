package com.kii.cloud.rest.client.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpContentRange {
	private static final String REGEX = "^bytes[=|\\s](\\d+)-(\\d+)/(\\d+)$";
	private static final Pattern PATTERN = Pattern.compile(REGEX);

	private final long from;
	private final long to;
	private final long total;

	public Long getFrom() {
		return from;
	}

	public Long getTo() {
		return to;
	}

	public Long getTotal() {
		return total;
	}

	public HttpContentRange(long from, long to, long total) {
		this.from = from;
		this.to = to;
		this.total = total;
	}

	/**
	 * Build a HttpRange from the given http header param: content-range
	 * 
	 * @param header
	 * @return
	 */
	public static HttpContentRange fromHeader(String header) {
		Matcher matcher = PATTERN.matcher(header);
		if (!matcher.matches())
			throw new IllegalArgumentException(
					"The supplied header ["
							+ header
							+ "] is not a valid range header. Valid range headers must macth the pattern:"
							+ REGEX);
		Long from = matcher.group(1) != null ? Long.parseLong(matcher.group(1))
				: null;
		Long to = matcher.group(2) != null ? Long.parseLong(matcher.group(2))
				: null;
		Long total = matcher.group(3) != null ? Long
				.parseLong(matcher.group(3)) : null;

		if (from == null || to == null || total == null)
			throw new IllegalArgumentException("The supplied header [" + header
					+ "] is not a valid range header. No null values allowed.");

		return new HttpContentRange(from, to, total);
	}
	/**
	 * Return the http header representation of the HttpRange: 'bytes=from-to'
	 * 
	 * @return
	 */
	public String toHeader() {
		StringBuilder header = new StringBuilder("bytes ").append(this.from)
				.append("-").append(this.to).append("/").append(this.total);
		return header.toString();
	}

}
