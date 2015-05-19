package com.kii.cloud.rest.client.resource.analytics;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestAppFilter;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.KiiAdminCredentials;
import com.kii.cloud.rest.client.model.analytics.KiiAggregationRule;
import com.kii.cloud.rest.client.model.analytics.KiiAnalyticsQuery;
import com.kii.cloud.rest.client.model.analytics.KiiAnalyticsResult;
import com.kii.cloud.rest.client.model.analytics.KiiAnalyticsResult.ResultType;
import com.kii.cloud.rest.client.model.analytics.KiiConversionRule;
import com.kii.cloud.rest.client.model.analytics.KiiGroupedAnalyticsResult;
import com.kii.cloud.rest.client.model.analytics.KiiTabularAnalyticsResult;
import com.kii.cloud.rest.client.model.analytics.KiiTabularAnalyticsResult.Label;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiObject;
import com.kii.cloud.rest.client.resource.storage.KiiBucketResource;
import com.kii.cloud.rest.client.util.StringUtils;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiAnalyticsResourceTest {
	
	@Test
	public void groupedResultTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().aggregationRuleID(1388));
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiAnalyticsQuery query = new KiiAnalyticsQuery(ResultType.GroupedResult);
		
		String aggregationRuleID = testApp.getAggregationRuleID().toString();
		KiiAnalyticsResult result = rest.api().analytics(aggregationRuleID).getResult(query);
		
		assertTrue(result instanceof KiiGroupedAnalyticsResult);
		KiiGroupedAnalyticsResult groupedResult = (KiiGroupedAnalyticsResult)result;
		
		List<KiiGroupedAnalyticsResult.Snapshot> snapshots = groupedResult.getSnapshots();
		assertTrue(snapshots.size() > 0);
		for (KiiGroupedAnalyticsResult.Snapshot snapshot : snapshots) {
			assertTrue(!StringUtils.isEmpty(snapshot.getName()));
			assertTrue(snapshot.getData().size() > 0);
			assertTrue(snapshot.getPointStart() > 0);
			assertTrue(snapshot.getPointInterval() > 0);
		}
	}
	@Test
	public void tabularResultTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().aggregationRuleID(1388));
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiAnalyticsQuery query = new KiiAnalyticsQuery(ResultType.TabularResult);
		
		String aggregationRuleID = testApp.getAggregationRuleID().toString();
		KiiAnalyticsResult result = rest.api().analytics(aggregationRuleID).getResult(query);
		
		assertTrue(result instanceof KiiTabularAnalyticsResult);
		KiiTabularAnalyticsResult tabularResult = (KiiTabularAnalyticsResult)result;
		
		List<Label> labels = tabularResult.getLabels();
		assertEquals(2, labels.size());
		for (Label label : labels) {
			if ("user_id".equals(label.getLabel())) {
				assertEquals("DIMENSION", label.getType());
			} else if ("max".equals(label.getLabel())) {
				assertEquals("FACT", label.getType());
			} else {
				fail("unexpected tabular result");
			}
		}
		List<KiiTabularAnalyticsResult.Snapshot> snapshots = tabularResult.getSnapshots();
		assertTrue(snapshots.size() > 0);
		for (KiiTabularAnalyticsResult.Snapshot snapshot : snapshots) {
			assertTrue(snapshot.getCreatedAt() > 0);
			assertTrue(snapshot.getData().size() > 0);
		}
	}
	/**
	 * Creates test data for analytics.
	 * This method assumes that following rules are defined.
	 * 
	 * -- Aggregation Rule
	 *  {
	 *    "_id" : xxx,
	 *    "_createdAt" : 1431932079000,
	 *    "_modifiedAt" : 1431932079000,
	 *    "name" : "HighScores",
	 *    "aggregate" : {
	 *        "valueOf" : "score",
	 *        "with" : "max",
	 *        "type" : "int"
	 *    },
	 *    "groupBy" : [ {
	 *        "name" : "user_id",
	 *        "label" : "user_id",
	 *        "type" : "string"
	 *    } ],
	 *    "source" : "APP",
	 *    "conversionRuleID" : 644,
	 *    "status" : "ACTIVE"
	 *  }
	 * -- Conversion Rule
	 *  {
	 *    "_id" : xxx,
	 *    "_createdAt" : 1431932077000,
	 *    "_modifiedAt" : 1431932077000,
	 *    "scope" : "APP",
	 *    "bucket" : "score",
	 *    "derived" : [ {
	 *        "name" : "user_id",
	 *        "source" : "user_id",
	 *        "type" : "string"
	 *    }, {
	 *        "name" : "score",
	 *        "source" : "score",
	 *        "type" : "int"
	 *    } ],
	 *    "target" : "OBJECT",
	 *    "status" : "ACTIVE"
	 *  }
	 * 
	 * @throws Exception
	 */
	@Test
	public void createTestData() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().aggregationRuleID(1388).hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		KiiAdminCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		String aggregationRuleID = testApp.getAggregationRuleID().toString();
		KiiAggregationRule aggregationRule = rest.api().aggregationRules(aggregationRuleID).get();
		if (aggregationRule.getConversionRuleID() != null) {
			String conversionRuleID = aggregationRule.getConversionRuleID().toString();
			KiiConversionRule conversionRule = rest.api().conversionRules(conversionRuleID).get();
			
			List<KiiNormalUser> users = new ArrayList<KiiNormalUser>();
			for (int i = 0; i < 10; i++) {
				KiiNormalUser user = new KiiNormalUser().setUsername("test-" + System.currentTimeMillis());
				user = rest.api().users().register(user, "password");
				users.add(user);
			}
			KiiBucketResource bucketResource = rest.api().buckets(conversionRule.getBucket());
			for (KiiNormalUser user : users) {
				Random random = new Random(System.currentTimeMillis());
				for (int i = 0; i < 5; i++) {
					
					KiiObject object = new KiiObject()
						.set("user_id", user.getID())
						.set("score", random.nextInt(10000) * 100 + i);
					bucketResource.objects().save(object);
				}
			}
		}
	}
}
