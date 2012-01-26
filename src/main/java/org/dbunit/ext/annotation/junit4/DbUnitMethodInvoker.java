package org.dbunit.ext.annotation.junit4;

import org.dbunit.ext.annotation.AnnotatedParameterProcessor;
import org.dbunit.ext.annotation.TestContext;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class DbUnitMethodInvoker extends Statement {
	
	private FrameworkMethod method;
	private DbUnitContext dbUnitContext;

	public DbUnitMethodInvoker(FrameworkMethod method, DbUnitContext dbUnitContext) {
		this.method = method;
		this.dbUnitContext = dbUnitContext;
	}

	@Override
	public void evaluate() throws Throwable {
		TestContext testContext = dbUnitContext.getTestContext();
		AnnotatedParameterProcessor processor = new AnnotatedParameterProcessor(testContext);
		method.invokeExplosively(testContext.getTarget(), processor.getParameters());
	}

}
