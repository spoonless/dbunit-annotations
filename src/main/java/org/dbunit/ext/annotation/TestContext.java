package org.dbunit.ext.annotation;

import java.lang.reflect.Method;

import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;

public class TestContext {
	
	private final DatabaseTesterResolver databaseTesterResolver ;
	private final Object target ;
	private final Method method ;
	private IDatabaseTester databaseTester ;
	private IDatabaseConnection connection ;
	
	public TestContext(DatabaseTesterResolver databaseTesterResolver, Object target, Method method) {
		if (databaseTesterResolver == null) {
			throw new IllegalArgumentException("databaseTesterResolver cannot be null!") ;
		}
		this.databaseTesterResolver = databaseTesterResolver ;

		if (target == null) {
			throw new IllegalArgumentException("target cannot be null!") ;
		}
		this.target = target ;

		if (method == null) {
			throw new IllegalArgumentException("method cannot be null!") ;
		}
		this.method = method ;
	}
	
	public IDatabaseTester getDatabaseTester() throws Exception {
		if (databaseTester == null) {
			databaseTester = databaseTesterResolver.createDatabaseTester(target) ;
		}
		return databaseTester;
	}
	
	public IDatabaseConnection getDatabaseConnection() throws Exception {
		if (connection == null) {
			connection = getDatabaseTester().getConnection() ;
		}
		return connection ;
	}
	
	public Object getTarget() {
		return target;
	}
	
	public Method getMethod() {
		return method;
	}

	public void destroy() throws Exception {
		databaseTester = null ;
		if (connection != null) {
			try {
				connection.close() ;
			}
			finally {
				connection = null ;
			}
		}
	}
}
