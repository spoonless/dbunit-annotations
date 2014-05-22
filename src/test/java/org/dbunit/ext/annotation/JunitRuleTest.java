package org.dbunit.ext.annotation;

import org.dbunit.dataset.ITable;
import org.dbunit.ext.annotation.junit4.DbUnitContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

@DatabaseTesterProvider(StaticDatabaseTester.class)
@DatabaseOperationAfter(DatabaseOperation.DELETE_ALL)
public class JunitRuleTest extends AbstractSampleTestCase {
	
	@Rule
	public DbUnitContext dbUnitContext = new DbUnitContext() ;

	@Test
	@XmlDataSet(location = "xmlDataSet.xml")
	public void testWhenXmlDataSetInsertedWithRelativeClassPath() throws Exception {
		ITable table = dbUnitContext.getDatabaseConnection().createTable("fellowship");
		
		Assert.assertEquals(3, table.getRowCount());		
	}
	
	@Before
	@After
	public void checkConnectionIsNotNull() throws Exception {
		Assert.assertNotNull(dbUnitContext.getDatabaseConnection());
	}

}
