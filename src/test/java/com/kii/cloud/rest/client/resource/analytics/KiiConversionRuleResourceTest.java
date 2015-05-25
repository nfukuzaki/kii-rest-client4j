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
import com.kii.cloud.rest.client.model.KiiAdminCredentials;
import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.model.analytics.FieldType;
import com.kii.cloud.rest.client.model.analytics.KiiConversionMappingRule;
import com.kii.cloud.rest.client.model.analytics.KiiConversionRule;
import com.kii.cloud.rest.client.model.analytics.KiiConversionRule.Target;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiConversionRuleResourceTest {
	@Test
	public void test() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials());
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		KiiAdminCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		// listing conversion rules
		List<KiiConversionRule> existingConversionRules = rest.api().conversionRules().list();
		
		// creating new conversion rule
		KiiConversionRule newConversionRule = new KiiConversionRule();
		newConversionRule.setBucket("app_bucket");
		newConversionRule.setScope(KiiScope.APP);
		newConversionRule.setTarget(Target.OBJECT);
		newConversionRule.addMappingRule(new KiiConversionMappingRule().setName("id").setSource("_id").setType(FieldType.INT));
		newConversionRule.addMappingRule(new KiiConversionMappingRule().setName("name").setSource("_name").setType(FieldType.STRING));
		rest.api().conversionRules().create(newConversionRule);
		
		// listing conversion rules
		List<KiiConversionRule> conversionRules = rest.api().conversionRules().list();
		assertEquals(1, conversionRules.size() - existingConversionRules.size());
		
		// getting conversion rule
		KiiConversionRule conversionRule = rest.api().conversionRules(newConversionRule).get();
		assertEquals(newConversionRule.getID(), conversionRule.getID());
		
		// deleting conversion rule
		rest.api().conversionRules(conversionRule).delete();
		conversionRules = rest.api().conversionRules().list();
		assertEquals(conversionRules.size(), existingConversionRules.size());
	}
}
