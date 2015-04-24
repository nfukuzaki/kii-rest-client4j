package com.kii.cloud.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;

public class IOUtils {
	public static String readAsString(InputStream is) throws IOException {
		StringWriter writer = new StringWriter();
		InputStreamReader reader = new InputStreamReader(is, Charset.forName("UTF-8"));
		copy(reader, writer);
		return writer.toString();
	}
	public static long copy(final InputStream input, final OutputStream output) throws IOException {
		byte[] buffer = new byte[1024 * 4];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}
	public static long copy(Reader input, Writer output) throws IOException {
		char[] buffer = new char[1024 * 4];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}
}
