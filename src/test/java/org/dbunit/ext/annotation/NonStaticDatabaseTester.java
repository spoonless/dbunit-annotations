package org.dbunit.ext.annotation;

import org.dbunit.IDatabaseTester;
import org.junit.Ignore;

@Ignore
public class NonStaticDatabaseTester {
	
	@DatabaseTester
	public IDatabaseTester getDatabaseTester() throws Exception {
		return StaticDatabaseTester.getDatabaseTester();
	}
}
