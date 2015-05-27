package com.kii.cloud.rest.client;

import static org.junit.Assert.fail;

import java.util.List;

import com.kii.cloud.rest.client.model.storage.KiiAcl.Subject;

public class AssertUtils {
	public static void assertEqualsIgnoreOrder(Subject[] expected, List<Subject> actual) throws Exception {
		if (expected == null && actual == null) {
			return;
		} else if (expected == null || actual == null) {
			fail("expected=" + expected + " but actual=" + actual);
		} else if (expected.length != actual.size()) {
			fail("expected length=" + expected.length + " but actual.size=" + actual.size());
		}
		for (Subject e : expected) {
			boolean found = false;
			for (Subject a : actual) {
				if (e.equals(a)) {
					found = true;
					break;
				}
			}
			if (!found) {
				fail("expected " + e.toString() + " is not contained in actual");
			}
		}
	}
}
