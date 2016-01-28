package com.kii.cloud.rest.client.resource.servercode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.KiiRest;
import com.kii.cloud.rest.client.SkipAcceptableTestRunner;
import com.kii.cloud.rest.client.TestApp;
import com.kii.cloud.rest.client.TestAppFilter;
import com.kii.cloud.rest.client.TestEnvironments;
import com.kii.cloud.rest.client.KiiRest.Site;
import com.kii.cloud.rest.client.model.KiiCredentials;
import com.kii.cloud.rest.client.model.servercode.KiiScheduleExecutionQuery;
import com.kii.cloud.rest.client.model.servercode.KiiScheduleExecutionQueryClause;
import com.kii.cloud.rest.client.model.servercode.KiiScheduleExecutionQueryResult;
import com.kii.cloud.rest.client.model.servercode.KiiScheduleExecutionResult;
import com.kii.cloud.rest.client.model.servercode.KiiScheduleExecutionResult.Status;
import com.kii.cloud.rest.client.model.servercode.KiiServerHookConfiguration;
import com.kii.cloud.rest.client.model.servercode.KiiServerHookConfiguration.Path;
import com.kii.cloud.rest.client.model.servercode.KiiServerHookConfiguration.SchedulerConfiguration;
import com.kii.cloud.rest.client.model.servercode.KiiServerHookConfiguration.TriggerAction;
import com.kii.cloud.rest.client.model.servercode.KiiServerHookConfiguration.TriggerConfiguration;
import com.kii.cloud.rest.client.model.servercode.KiiServerHookConfiguration.When;
import com.kii.cloud.rest.client.util.GsonUtils;

@RunWith(SkipAcceptableTestRunner.class)
public class KiiServerCodeHookResourceTest {
	@Test
	public void deployTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials().site(Site.JP));
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		String versionID = rest.api().servercode().deploy(new File(getClass().getResource("get_server_time.js").getPath()));
		rest.api().servercode().setCurrentVersion(versionID);
		
		KiiServerHookConfiguration hookConfig = new KiiServerHookConfiguration();
		hookConfig.addTriggerConfiguration(
				new TriggerConfiguration(Path.user())
				.addTriggerAction(
						new TriggerAction(When.USER_CREATED, "user_created")
				)
		);
		hookConfig.addSchedulerConfiguration(new SchedulerConfiguration("1DayBatch", "1 0 * * *", "daily_batch", null));
		rest.api().hooks(versionID).deploy(hookConfig);
	}
	@Test
	public void scheduleBasedExecutionResultsTest() throws Exception {
		TestApp testApp = TestEnvironments.random(new TestAppFilter().hasAppAdminCredentials().site(Site.JP));
		KiiRest rest = new KiiRest(testApp.getAppID(), testApp.getAppKey(), testApp.getSite());
		
		KiiCredentials cred = rest.api().oauth().getAdminAccessToken(testApp.getClientID(), testApp.getClientSecret());
		rest.setCredentials(cred);
		
		String versionID = rest.api().servercode().deploy(new File(getClass().getResource("get_server_time.js").getPath()));
		rest.api().servercode().setCurrentVersion(versionID);
		
		JsonObject result = rest.api().servercode(versionID).execute("get_server_time", null);
		DateTime serverDateTime = new DateTime(GsonUtils.getLong(result, "returnedValue"), DateTimeZone.UTC);
		
		// Executes the server code after 2 minutes.
		KiiServerHookConfiguration hookConfig = new KiiServerHookConfiguration();
		serverDateTime = serverDateTime.plusMinutes(2);
		String cronExpression = String.format("%d %d * * *",
				serverDateTime.getMinuteOfHour(),
				serverDateTime.getHourOfDay()
				);
		String jobName = "TestJob-" + System.currentTimeMillis();
		hookConfig.addSchedulerConfiguration(new SchedulerConfiguration(jobName, cronExpression, "get_server_time", null));
		rest.api().hooks(versionID).deploy(hookConfig);
		
		// waiting for 2 minutes
		System.out.println("waiting for the scheduled server code to finish...");
		Thread.sleep(60 * 1000 * 2);
		
		// querying execution result
		KiiScheduleExecutionQueryClause clause = KiiScheduleExecutionQueryClause.prefix(KiiScheduleExecutionResult.PROPERTY_JOB_NAME, "TestJob-");
		KiiScheduleExecutionQuery query = new KiiScheduleExecutionQuery(clause);
		KiiScheduleExecutionQueryResult queryResult = rest.api().hooks().executions().query(query);
		
		List<KiiScheduleExecutionResult> executionResultList = new ArrayList<KiiScheduleExecutionResult>();
		executionResultList.addAll(queryResult.getResults());
		while (queryResult.hasNext()) {
			query = queryResult.getNextQuery();
			queryResult = rest.api().hooks().executions().query(query);
			executionResultList.addAll(queryResult.getResults());
		}
		
		assertTrue(executionResultList.size() > 0);
		String scheduleExecutionID = null;
		for (KiiScheduleExecutionResult executionResult : executionResultList) {
			if (jobName.equals(executionResult.getJobName())) {
				scheduleExecutionID = executionResult.getScheduleExecutionID();
				assertEquals(Status.SUCCESS, executionResult.getStatus());
			}
		}
		if (scheduleExecutionID == null) {
			fail("Result of " + jobName + " is not found");
		}
		
		// getting execution result
		KiiScheduleExecutionResult executionResult = rest.api().hooks().executions().get(scheduleExecutionID);
		assertEquals(scheduleExecutionID, executionResult.getScheduleExecutionID());
		assertEquals(Status.SUCCESS, executionResult.getStatus());
	}
}
