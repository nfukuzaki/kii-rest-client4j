package com.kii.cloud.rest.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.JavaDocTag;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaDocSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.junit.Test;

import com.kii.cloud.rest.client.OkHttpClientFactory;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class JavadocTest {
	@Test
	public void resourceClassesSeeLinkTest() throws Exception {
		File srcDir = new File("src/main/java/com/kii/cloud/rest/client/resource");
		List<File> javaFiles = new ArrayList<File>();
		searchJavaFiles(srcDir, javaFiles);
		
		Set<String> seeLinks = new HashSet<String>();
		for (File javaFile : javaFiles) {
			JavaClassSource javaClass = Roaster.parse(JavaClassSource.class, javaFile);
			JavaDocSource<JavaClassSource> classJavaDoc = javaClass.getJavaDoc();
			for (JavaDocTag tag : classJavaDoc.getTags("@see")) {
				seeLinks.add(tag.getValue());
			}
			for (MethodSource<JavaClassSource> method : javaClass.getMethods()) {
				JavaDocSource<MethodSource<JavaClassSource>> methodJavaDoc = method.getJavaDoc();
				for (JavaDocTag tag : methodJavaDoc.getTags("@see")) {
					if (tag.getValue().startsWith("http")) {
						seeLinks.add(tag.getValue());
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
	private void searchJavaFiles(File dir, List<File> javaFiles) {
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				searchJavaFiles(f, javaFiles);
			} else if (f.getName().endsWith(".java")) {
				javaFiles.add(f);
			}
		}
	}
}
