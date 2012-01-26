package org.dbunit.ext.annotation.junit4;

import java.util.List;

import org.dbunit.ext.annotation.DatabaseTester;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * The runner for Junit4 integration.
 * To integrate DbUnit in your test classes, all you have to
 * do is to add the following {@link RunWith &#064;RunWith} annotation:
 * <p>
 * <pre>
 * import org.junit.runner.RunWith;
 * import org.dbunit.ext.junit4.DbUnitRunner;
 * 
 * &#064;RunWith(DbUnitRunner.class)
 * public class MyTestClass {
 * 	...
 * }
 * </pre>
 * <p>
 * All test classes using the DbUnitRunner support DbUnit annotations.
 * <p>
 * If you want to use another JUnit4 compliant runner, you can just declare
 * a {@link DbUnitContext} as a {@link Rule}.
 * <p>
 * <b>Note on precedence order:</b> DbUnit annotations have precedence over 
 * Junit4 annotations since JUnit 4.8.
 * This implies, for instance, that a method annotated with
 * {@link Before &#064;Before} is called after a method annotated with
 * {@link DatabaseTester &#064;DatabaseTester}.  
 *
 * @author David Gayerie
 * @version 1.0
 * @since 2010
 * @see DbUnitContext
 */
public class DbUnitRunner extends BlockJUnit4ClassRunner {
	
	private DbUnitContext dbUnitcontext = new DbUnitContext() ;
	private Object test = null;

	public DbUnitRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}
	
	@Override
	protected Statement methodBlock(FrameworkMethod method) {
		// TODO handle the case when a DBUnitContext is also declared
		// as a Rule in the test class.
		Statement statement = super.methodBlock(method);
		statement = dbUnitcontext.apply(statement, method, test);
		test = null;
		return statement;
	}
	
	@Override
	protected Statement methodInvoker(FrameworkMethod method, Object test) {
		// We need to save the test object here because there is really no 
		// other clean way to capture it in JUnit 4.8.
		this.test = test;
		return new DbUnitMethodInvoker(method, dbUnitcontext);
	}
	
	@Override
	protected void validateTestMethods(List<Throwable> errors) {
		// TODO Checks methods with params
		// super.validateTestMethods(errors);
	}
	
}
