package com.kii.cloud.rest.client.resource.analytics;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestAppFilter;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.model.KiiCredentials;
import com.kii.cloud.rest.client.model.analytics.FieldType;
import com.kii.cloud.rest.client.model.analytics.KiiAggregationAggregateRule;
import com.kii.cloud.rest.client.model.analytics.KiiAggregationGroupByRule;
import com.kii.cloud.rest.client.model.analytics.KiiAggregationRule;
import com.kii.cloud.rest.client.model.analytics.KiiAggregationAggregateRule.AggregationFunction;
import com.kii.cloud.rest.client.model.analytics.KiiAggregationRule.Source;
import com.kii.cloud.rest.client.model.analytics.KiiConversionMappingRule;
import com.kii.cloud.rest.client.model.analytics.KiiConversionRule;
import com.kii.cloud.rest.client.model.analytics.KiiConversionRule.Target;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiAggregationRuleResourceTest {
	@Test
	public void test() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		// listing conversion rules
		List<KiiAggregationRule> existingAggregationRules = rest.api().aggregationRules().list();
		
		// creating new conversion rule
		KiiConversionRule conversionRule = new KiiConversionRule();
		conversionRule.setBucket("app_bucket");
		conversionRule.setScope(KiiScope.APP);
		conversionRule.setTarget(Target.OBJECT);
		conversionRule.addMappingRule(new KiiConversionMappingRule().setName("id").setSource("_id").setType(FieldType.INT));
		conversionRule.addMappingRule(new KiiConversionMappingRule().setName("name").setSource("_name").setType(FieldType.STRING));
		Long conversionRuleID = rest.api().conversionRules().create(conversionRule);
		// creating new aggregation rule
		KiiAggregationRule newAggregationRule = new KiiAggregationRule();
		newAggregationRule.setName("Count objects");
		newAggregationRule.setSource(Source.APP);
		newAggregationRule.setConversionRuleID(conversionRuleID);
		newAggregationRule.setAggregate(new KiiAggregationAggregateRule().setValueOf("*").setWith(AggregationFunction.COUNT).setType(FieldType.INT));
		newAggregationRule.addGroupBy(new KiiAggregationGroupByRule().setName("user_id").setType(FieldType.STRING).setLabel("UserID"));
		rest.api().aggregationRules().create(newAggregationRule);
		
		// listing conversion rules
		List<KiiAggregationRule> aggregationRules = rest.api().aggregationRules().list();
		assertEquals(1, aggregationRules.size() - existingAggregationRules.size());
		
		// getting aggregation rule
		KiiAggregationRule aggregationRule = rest.api().aggregationRules(newAggregationRule).get();
		assertEquals(newAggregationRule.getID(), aggregationRule.getID());
		
		// deleting aggregation rule
		rest.api().aggregationRules(aggregationRule).delete();
		aggregationRules = rest.api().aggregationRules().list();
		assertEquals(aggregationRules.size(), existingAggregationRules.size());
	}
}
