package org.dbunit.ext.annotation;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.ext.annotation.junit4.DbUnitRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DbUnitRunner.class)
@DatabaseOperationAfter(DatabaseOperation.DELETE_ALL)
public class XmlDataSetTest extends AbstractSampleTestCase {
	
	@DatabaseTester
	public IDatabaseTester getDatabaseTester() throws ClassNotFoundException {
		return StaticDatabaseTester.getDatabaseTester() ;
	}
	
	@Test
	@XmlDataSet(location = "xmlDataSet.xml")
	public void testWhenXmlDataSetInsertedWithRelativeClassPath(IDatabaseConnection connection) throws Exception {
		ITable table = connection.createTable("fellowship");
		
		Assert.assertEquals(3, table.getRowCount());		
	}

	@Test
	@XmlDataSet(location = "src/test/resources/org/dbunit/ext/annotation/xmlDataSet.xml")
	public void testWhenXmlDataSetInsertedWithRelativeFilePath(IDatabaseConnection connection) throws Exception {
		ITable table = connection.createTable("fellowship");
		
		Assert.assertEquals(3, table.getRowCount());
	}

	@Test
	@XmlDataSet(location = "/org/dbunit/ext/annotation/xmlDataSet.xml")
	public void testWhenXmlDataSetInsertedWithAbsoluteClassPath(IDatabaseConnection connection) throws Exception {
		ITable table = connection.createTable("fellowship");
		
		Assert.assertEquals(3, table.getRowCount());
	}

	@Test
	@XmlDataSet(location = {"/org/dbunit/ext/annotation/xmlDataSet.xml", "xmlDataSet2.xml"})
	public void testWhenMultipleXmlDataSetsInserted(IDatabaseConnection connection) throws Exception {
		ITable table = connection.createTable("fellowship");
		
		Assert.assertEquals(5, table.getRowCount());
	}

	@Test
	public void testWhenEmptyTable(IDatabaseConnection connection) throws Exception {
		ITable table = connection.createTable("fellowship");
		
		Assert.assertEquals(0, table.getRowCount());
	}
	
	@Test
	@XmlDataSet(location = "xmlDataSet.xml")
	public void testWhenXmlDataSetGivenAsParameter(@XmlDataSet(location = "xmlDataSet.xml") IDataSet expectedDataSet, IDatabaseConnection connection) throws Exception {
		ITable table = connection.createTable("fellowship");
		
		Assert.assertEquals(3, table.getRowCount());
		Assertion.assertEquals(expectedDataSet.getTable("fellowship"), table);
	}
}
