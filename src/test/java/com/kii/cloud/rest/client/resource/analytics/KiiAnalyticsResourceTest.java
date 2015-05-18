package com.kii.cloud.rest.client.resource.analytics;

import static org.junit.Assert.assertTrue;

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
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiObject;
import com.kii.cloud.rest.client.resource.storage.KiiBucketResource;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiAnalyticsResourceTest {
	@Test
	public void groupedResultTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAggregationRuleID());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiAnalyticsQuery query = new KiiAnalyticsQuery(ResultType.GroupedResult);
		
		String aggregationRuleID = testApp.getAggregationRuleID().toString();
		KiiAnalyticsResult result = rest.api().analytics(aggregationRuleID).getResult(query);
		
		assertTrue(result instanceof KiiGroupedAnalyticsResult);
		KiiGroupedAnalyticsResult groupedResult = (KiiGroupedAnalyticsResult)result;
		// TODO
	}
	@Test
	public void tabularResultTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAggregationRuleID());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiAnalyticsQuery query = new KiiAnalyticsQuery(ResultType.TabularResult);
		
		String aggregationRuleID = testApp.getAggregationRuleID().toString();
		KiiAnalyticsResult result = rest.api().analytics(aggregationRuleID).getResult(query);
		
		assertTrue(result instanceof KiiTabularAnalyticsResult);
		KiiTabularAnalyticsResult tabularResult = (KiiTabularAnalyticsResult)result;
		// TODO
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
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAggregationRuleID().hasAppAdminCredentials());
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
