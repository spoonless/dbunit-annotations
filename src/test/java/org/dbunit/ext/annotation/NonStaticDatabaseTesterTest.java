package org.dbunit.ext.annotation;

import junit.framework.Assert;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.ITable;
import org.dbunit.ext.annotation.junit4.DbUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DbUnitRunner.class)
@DatabaseTesterProvider(NonStaticDatabaseTester.class)
@DatabaseOperationAfter(DatabaseOperation.DELETE_ALL)
public class NonStaticDatabaseTesterTest extends AbstractSampleTestCase {
	
	@Test
	public void testWhenEmptyTable(IDatabaseConnection connection) throws Exception {
		ITable table = connection.createTable("fellowship");
		
		Assert.assertEquals(0, table.getRowCount());		
	}
}
