package org.dbunit.ext.annotation;

import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.AbstractDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.hsqldb.HsqldbConnection;
import org.junit.Ignore;

@Ignore
public class StaticDatabaseTester {
	
	public static final String JDBC_DRIVER = "org.hsqldb.jdbcDriver";
	public static final String JDBC_URL = "jdbc:hsqldb:mem:testdb";
	public static final String JDBC_LOGIN = "sa";
	public static final String JDBC_PASSWORD = "";

	private StaticDatabaseTester() {
		// private constructor. Just to be sure the class
		// cannot be instantiated.
	}

	@DatabaseTester
	public static IDatabaseTester getDatabaseTester() throws ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
		return new AbstractDatabaseTester() {
			
			public IDatabaseConnection getConnection() throws Exception {
				Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_LOGIN, JDBC_PASSWORD);
				return new HsqldbConnection(connection, getSchema());
			}
		};
	}
}
