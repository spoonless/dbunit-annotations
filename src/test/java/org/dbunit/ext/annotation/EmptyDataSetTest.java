package org.dbunit.ext.annotation;

import junit.framework.Assert;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.annotation.junit4.DbUnitRunner;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DbUnitRunner.class)
@DatabaseOperationAfter(DatabaseOperation.DELETE_ALL)
@DatabaseTesterProvider(StaticDatabaseTester.class)
public class EmptyDataSetTest extends AbstractSampleTestCase {

	@Test
	@EmptyDataSet(tables="fellowship")
	public void testEmptyDataSet(IDatabaseConnection connection) throws Exception {
		// creates a test side effect by inserting a new record
		executeStatement("INSERT INTO fellowship VALUES ('frodo', 'baggins', false)");
		
		Assert.assertEquals(1, connection.getRowCount("fellowship"));
	}
	
	@AfterClass
	public static void checksTableContentHasBeenDeleted() throws Exception {
		IDatabaseConnection connection = StaticDatabaseTester.getDatabaseTester().getConnection();
		int rowCount = connection.getRowCount("fellowship");
		connection.close();
		Assert.assertEquals(0, rowCount);
	}

}
