package org.dbunit.ext.annotation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstractSampleTestCase {
	

	@BeforeClass
	public static void createTable() throws Exception {
		executeStatement("CREATE TABLE fellowship (firstName CHAR(50), lastName CHAR(50), betrayer BOOLEAN)");
	}

	@AfterClass
	public static void dropTable() throws Exception {
		executeStatement("DROP TABLE fellowship");
	}

	private static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(StaticDatabaseTester.JDBC_DRIVER);
		Connection c = DriverManager.getConnection(StaticDatabaseTester.JDBC_URL, StaticDatabaseTester.JDBC_LOGIN, StaticDatabaseTester.JDBC_PASSWORD);
		return c;
	}
	
	protected static void executeStatement (String sql) throws Exception {
		Connection connection = getConnection();
		Statement stmt = connection.createStatement();
		stmt.execute(sql);
		stmt.close();
		connection.commit();
		connection.close();
	}
	
}
