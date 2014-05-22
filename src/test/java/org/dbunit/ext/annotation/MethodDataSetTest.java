package org.dbunit.ext.annotation;

import java.io.InputStream;

import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.ext.annotation.junit4.DbUnitRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DbUnitRunner.class)
@DatabaseOperationAfter(DatabaseOperation.DELETE_ALL)
public class MethodDataSetTest extends AbstractSampleTestCase {
	
	@DatabaseTester
	public IDatabaseTester getDatabaseTester() throws ClassNotFoundException {
		return StaticDatabaseTester.getDatabaseTester() ;
	}
	
	public IDataSet createDataSet() throws Exception {
		InputStream inputStream = this.getClass().getResourceAsStream("/org/dbunit/ext/annotation/xmlDataSet.xml");
		XmlDataSet dataSet = new XmlDataSet(inputStream);
		inputStream.close();
		return dataSet;
	}

	@MethodDataSet(methods = "createDataSet")
	@Test
	public void testWhenMethodDataSetInserted(IDatabaseConnection connection) throws Exception {
		ITable table = connection.createTable("fellowship");
		
		Assert.assertEquals(3, table.getRowCount());
	}

	@MethodDataSet(methods = {"createDataSet","createDataSet"})
	@Test
	public void testWhenMultipleMethodDataSetInserted(IDatabaseConnection connection) throws Exception {
		ITable table = connection.createTable("fellowship");
		
		Assert.assertEquals(6, table.getRowCount());
	}
}
