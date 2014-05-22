package org.dbunit.ext.annotation.junit4;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.annotation.AnnotatedMethodProcessor;
import org.dbunit.ext.annotation.DatabaseTesterResolver;
import org.dbunit.ext.annotation.DbUnitAnnotationException;
import org.dbunit.ext.annotation.TestContext;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

/**
 * A JUnit4 rule for DbUnit integration.
 * To integrate DbUnit in your test classes, all you have to
 * do is to declare a public non static attribute of this class
 * annotated with {@link Rule &#064;Rule}: 
 * <p>
 * <pre>
 * import org.junit.Rule;
 * import org.dbunit.ext.junit4.DbUnitContext;
 * 
 * public class MyTestClass {
 *  &#064;Rule
 *  public DbUnitContext dbUnitContext = new DbUnitContext();
 *  ...
 * }
 * </pre>
 * <p>
 * All test classes using an attribute of this type as a rule support
 * DbUnit annotations at the method level and at the class level. However,
 * you cannot declare parameters on test method.
 * <p>
 * A more convenient way to support DbUnit annotations is to use the
 * {@link DbUnitRunner}. Declaring a rule in a test class is useful only
 * if you want to use an alternate Junit4 runner.
 *
 * @author David Gayerie
 * @version 1.0
 * @since 2010
 * @see DbUnitRunner
 */
public class DbUnitContext implements MethodRule {
	
	private DatabaseTesterResolver databaseTesterResolver ;
	private TestContext testContext ;
	
	public Statement apply(final Statement base, final FrameworkMethod method, final Object target) {
		return new Statement() {
			
			@Override
			public void evaluate() throws Throwable {
				createTestContext(method.getMethod(), target) ;
				AnnotatedMethodProcessor annotatedMethodProcessor = new AnnotatedMethodProcessor(testContext) ;
				annotatedMethodProcessor.onSetup() ;
				List<Throwable> errors = new ArrayList<Throwable>();
				try {
					if (base != null) {
						base.evaluate() ;
					}
				}
				catch (Exception e) {
					errors.add(e) ;
				}
				try {
					annotatedMethodProcessor.onTearDown() ;
				}
				catch (Exception e) {
					errors.add(e) ;
				}
				try {
					deleteTestContext() ;
				}
				catch (Exception e) {
					errors.add(e) ;
				}
				MultipleFailureException.assertEmpty(errors) ;
			}
		};
	}
	
	private DatabaseTesterResolver getDatabaseTesterResolver(Class<?> targetClass) throws DbUnitAnnotationException {
		if (databaseTesterResolver == null || ! targetClass.equals(databaseTesterResolver.getTargetClass())) {
			databaseTesterResolver = new DatabaseTesterResolver(targetClass) ;
		}
		return databaseTesterResolver;
	}
	
	private void createTestContext(Method method, Object target) throws DbUnitAnnotationException {
		this.testContext = new TestContext(getDatabaseTesterResolver(target.getClass()), target, method) ;
	}
	
	private void deleteTestContext() throws Exception {
		if (testContext != null) {
			try {
				testContext.destroy() ;
			}
			finally {
				testContext = null ;
			}
		}
	}

	/**
	 * @return an {@link IDatabaseConnection}
	 * @throws Exception
	 */
	public IDatabaseConnection getDatabaseConnection() throws Exception {
		IDatabaseConnection databaseConnection = null ;
		if (testContext != null) {
			databaseConnection = testContext.getDatabaseConnection() ;
		}
		return databaseConnection;
	}
	
	protected TestContext getTestContext() {
		return testContext;
	}
}
