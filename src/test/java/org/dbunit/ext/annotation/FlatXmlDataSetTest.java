package org.dbunit.ext.annotation;

import junit.framework.Assert;

import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.ITable;
import org.dbunit.ext.annotation.junit4.DbUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DbUnitRunner.class)
@DatabaseOperationAfter(DatabaseOperation.DELETE_ALL)
public class FlatXmlDataSetTest extends AbstractSampleTestCase {
	
	@DatabaseTester
	public IDatabaseTester getDatabaseTester() throws ClassNotFoundException {
		return StaticDatabaseTester.getDatabaseTester() ;
	}
	
	@Test
	@FlatXmlDataSet(location = "flatXmlDataSet.xml")
	public void testWhenFlatXmlDataSetInsertedWithRelativeClassPath(IDatabaseConnection connection) throws Exception {
		ITable table = connection.createTable("fellowship");
		
		Assert.assertEquals(3, table.getRowCount());
		// checks that default value from DTD was found
		Assert.assertNotNull(table.getValue(0, "betrayer"));
	}

	@Test
	@FlatXmlDataSet(location = "src/test/resources/org/dbunit/ext/annotation/flatXmlDataSet.xml")
	public void testWhenFlatXmlDataSetInsertedWithRelativeFilePath(IDatabaseConnection connection) throws Exception {
		ITable table = connection.createTable("fellowship");
		
		Assert.assertEquals(3, table.getRowCount());
	}

	@Test
	@FlatXmlDataSet(location = "/org/dbunit/ext/annotation/flatXmlDataSet.xml")
	public void testWhenFlatXmlDataSetInsertedWithAbsoluteClassPath(IDatabaseConnection connection) throws Exception {
		ITable table = connection.createTable("fellowship");
		
		Assert.assertEquals(3, table.getRowCount());
	}

	@Test
	@FlatXmlDataSet(location = "/flatXmlDataSet.xml")
	public void testWhenFlatXmlDataSetInsertedWithAbsoluteClassPathFromRoot(IDatabaseConnection connection) throws Exception {
		ITable table = connection.createTable("fellowship");
		
		Assert.assertEquals(1, table.getRowCount());
	}

	@Test
	@FlatXmlDataSet(location = "flatXmlDataSet.xml")
	@XmlDataSet(location = "xmlDataSet2.xml")
	public void testWhenMixedDataSetsInserted(IDatabaseConnection connection) throws Exception {
		ITable table = connection.createTable("fellowship");
		
		Assert.assertEquals(5, table.getRowCount());
	}

	@Test
	@FlatXmlDataSet(location="flatXmlDataSet.xml", useDtd=false)
	public void testWhenFlatXmlDataSetInsertedWithoutDtdValidation(IDatabaseConnection connection) throws Exception {
		ITable table = connection.createTable("fellowship");
		
		Assert.assertEquals(3, table.getRowCount());
		// checks that default value from DTD was not found because
		// DTD usage is deactivated for this test
		Assert.assertNull(table.getValue(0, "betrayer"));
		// checks the value was not found because
		// DTD usage is deactivated for this test and column sensing is not activated
		Assert.assertNull(table.getValue(2, "betrayer"));
	}

	@Test
	@FlatXmlDataSet(location="flatXmlDataSet.xml", useDtd=false, columnSensing=true)
	public void testWhenFlatXmlDataSetInsertedWithoutDtdValidationAndWithColumnSensing(IDatabaseConnection connection) throws Exception {
		ITable table = connection.createTable("fellowship");
		
		Assert.assertEquals(3, table.getRowCount());
		// checks that default value from DTD was not found because
		// DTD usage is deactivated for this test
		Assert.assertNull(table.getValue(0, "betrayer"));
		// checks the value was found because
		// DTD usage is deactivated for this test but column sensing is activated
		Assert.assertEquals(Boolean.TRUE, table.getValue(2, "betrayer"));
	}

	@Test
	@FlatXmlDataSet(location="flatXmlDataSetWithoutDtd.xml")
	public void testWhenFlatXmlDataSetWithoutDtdInserted(IDatabaseConnection connection) throws Exception {
		ITable table = connection.createTable("fellowship");
		
		Assert.assertEquals(3, table.getRowCount());
		// checks that default value from DTD was not found because
		// DTD usage is deactivated for this test
		Assert.assertNull(table.getValue(0, "betrayer"));
		// checks the value was found because
		// column sensing is activated
		Assert.assertNull(table.getValue(2, "betrayer"));
	}

	@Test
	@FlatXmlDataSet(location="flatXmlDataSetWithoutDtd.xml", columnSensing=true)
	public void testWhenFlatXmlDataSetWithoutDtdInsertedAndColumnSensing(IDatabaseConnection connection) throws Exception {
		ITable table = connection.createTable("fellowship");
		
		Assert.assertEquals(3, table.getRowCount());
		// checks that default value from DTD was not found because
		// DTD usage is deactivated for this test
		Assert.assertNull(table.getValue(0, "betrayer"));
		// checks the value was found because
		// column sensing is activated
		Assert.assertEquals(Boolean.TRUE, table.getValue(2, "betrayer"));
	}

	@Test
	@FlatXmlDataSet(location="flatXmlDataSetWithoutDtd.xml", dtd="fellowship.dtd")
	public void testWhenFlatXmlDataSetWithoutDtdInsertedButDtdSpecified(IDatabaseConnection connection) throws Exception {
		ITable table = connection.createTable("fellowship");
		
		Assert.assertEquals(3, table.getRowCount());
		// checks that default value from DTD was found because
		// DTD is specified
		// TODO DbUnit bug ?
		// Assert.assertEquals(Boolean.FALSE, table.getValue(0, "betrayer"));
		Assert.assertEquals(Boolean.TRUE, table.getValue(2, "betrayer"));
	}
}
