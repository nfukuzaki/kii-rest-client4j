package com.kii.cloud;

import java.util.Random;

public class TestEnvironments {
	
	public static final TestApp JP1 = new TestApp("kii-rest-client4j-test-jp-1", "9a15048a", "b16f770032fdef36677ed39b0457c867", KiiRest.Site.JP);
	public static final TestApp US1 = new TestApp("kii-rest-client4j-test-us-1", "9a30c24b", "eaaf7c57316124a29a564943d7c11ea8", KiiRest.Site.US);
	public static final TestApp SG1 = new TestApp("kii-rest-client4j-test-sg-1", "3f22addf", "7fac180c653b2fda0724fa5b7c888fdb", KiiRest.Site.SG);
	public static final TestApp CN1 = new TestApp("kii-rest-client4j-test-cn-1", "1c3c0e50", "e1544a070cc0089d2885a03ea3d38dfb", KiiRest.Site.CN);
	// TODO: Needs to set APP_ID, APP_KEY, CLIENT_ID and CLIENT_SECRET if you want to test the Admin feature
	public static final TestApp ADMIN = new TestApp("kii-rest-client4j-test-admin", "{APP_ID}", "{APP_KEY}", "{CLIENT_ID}", "{CLIENT_SECRET}", KiiRest.Site.US);
	
	public static final TestApp[] ALL = {JP1, US1, SG1, CN1};
	public static final TestApp[] JP_ALL = {JP1};
	public static final TestApp[] US_ALL = {US1};
	public static final TestApp[] SG_ALL = {SG1};
	public static final TestApp[] CN_ALL = {CN1};
	
	public static TestApp random() {
		Random random = new Random(System.currentTimeMillis());
		int index = random.nextInt(ALL.length);
		return ALL[index];
	}
	public static TestApp randomJP() {
		Random random = new Random(System.currentTimeMillis());
		int index = random.nextInt(JP_ALL.length);
		return JP_ALL[index];
	}
	public static TestApp randomUS() {
		Random random = new Random(System.currentTimeMillis());
		int index = random.nextInt(US_ALL.length);
		return US_ALL[index];
	}
	public static TestApp randomSG() {
		Random random = new Random(System.currentTimeMillis());
		int index = random.nextInt(SG_ALL.length);
		return SG_ALL[index];
	}
	public static TestApp randomCN() {
		Random random = new Random(System.currentTimeMillis());
		int index = random.nextInt(CN_ALL.length);
		return CN_ALL[index];
	}
}
