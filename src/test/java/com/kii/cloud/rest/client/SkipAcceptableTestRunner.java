package com.kii.cloud.rest.client;

import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class SkipAcceptableTestRunner extends BlockJUnit4ClassRunner {
	public SkipAcceptableTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}
	@Override
	protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
		Description description = describeChild(method);
		if (isIgnored(method)) {
			notifier.fireTestIgnored(description);
		} else {
			overridedRunLeaf(methodBlock(method), description, notifier);
		}
	}
	/**
	 * @param statement
	 * @param description
	 * @param notifier
	 * @see org.junit.runners.BlockJUnit4ClassRunner#runLeaf(Statement, Description, RunNotifier)
	 */
	private void overridedRunLeaf(Statement statement, Description description, RunNotifier notifier) {
		EachTestNotifier eachNotifier = new EachTestNotifier(notifier, description);
		eachNotifier.fireTestStarted();
		try {
			statement.evaluate();
		} catch (TestAppNotFoundException e) {
			System.out.println("[WARN] " + description.getClassName() + "." + description.getMethodName() + " is skipped. " + e.getMessage());
			eachNotifier.fireTestIgnored();
		} catch (AssumptionViolatedException e) {
			eachNotifier.addFailedAssumption(e);
		} catch (Throwable e) {
			eachNotifier.addFailure(e);
		} finally {
			eachNotifier.fireTestFinished();
		}
	}
}
