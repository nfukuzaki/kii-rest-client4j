package com.kii.cloud;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Options;
import com.sun.tools.javadoc.JavadocTool;
import com.sun.tools.javadoc.Messager;
import com.sun.tools.javadoc.ModifierFilter;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.SeeTag;

public class JavadocTest {
	@Test
	public void resourceClassesSeeLinkTest() throws Exception {
		Context context = new Context();
		Options compOpts = Options.instance(context);
		System.out.println(new File("src/main/java").getAbsolutePath());
		compOpts.put("-sourcepath", new File("src/main/java").getAbsolutePath());
		
		new PublicMessager(
				context,
				"JavadocTest",
				new PrintWriter(new LogWriter(), true),
				new PrintWriter(new LogWriter(), true),
				new PrintWriter(new LogWriter(), true)
		);
		
		JavadocTool javadocTool = JavadocTool.make0(context);
		ListBuffer<String> subPackages = new ListBuffer<String>();
		subPackages.add("com.kii.cloud.resource");
		RootDoc rootDoc = javadocTool.getRootDocImpl(
				"",
				"utf-8",
				new ModifierFilter(ModifierFilter.ALL_ACCESS),
				new ListBuffer<String>().toList(),
				new ListBuffer<String[]>().toList(),
				false,
				subPackages.toList(),
				new ListBuffer<String>().toList(),
				false,
				false,
				false);
		Set<String> seeLinks = new HashSet<String>();
		for (ClassDoc classDoc : rootDoc.classes()) {
			for (MethodDoc methodDoc : classDoc.methods()) {
				for (SeeTag seeTag : methodDoc.seeTags()) {
					if (seeTag.text().startsWith("http://")) {
						seeLinks.add(seeTag.text());
					}
				}
			}
		}
		Set<String> brokenLinks = new HashSet<String>();
		OkHttpClient client = OkHttpClientFactory.newInstance();
		for (String url : seeLinks) {
			Request request = new Request.Builder().url(url).get().build();
			System.out.print("Checking link " + url + "  ... ");
			try {
				Response response = client.newCall(request).execute();
				if (!response.isSuccessful()) {
					brokenLinks.add(url);
					System.out.println("NG");
				} else {
					System.out.println("OK");
				}
			} catch (IOException e) {
				brokenLinks.add(url);
				System.out.println("NG");
			}
		}
		assertEquals(0, brokenLinks.size());
	}
	public class PublicMessager extends Messager {
		public PublicMessager(Context context, String s) {
			super(context, s);
		}
		public PublicMessager(Context context, String s, PrintWriter printWriter, PrintWriter printWriter1, PrintWriter printWriter2) {
			super(context, s, printWriter, printWriter1, printWriter2);
		}
	}
	protected class LogWriter extends Writer {
		public LogWriter() {}
		public void write(char[] chars, int offset, int length) throws IOException {}
		public void flush() throws IOException {}
		public void close() throws IOException {}
	}
}
