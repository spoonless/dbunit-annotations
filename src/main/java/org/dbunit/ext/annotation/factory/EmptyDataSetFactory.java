package org.dbunit.ext.annotation.factory;

import java.lang.reflect.Method;

import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.ext.annotation.EmptyDataSet;

public class EmptyDataSetFactory implements IDataSetFactory<EmptyDataSet> {

	public IDataSet create(EmptyDataSet annotation, Method method, Integer parameterPosition, Object target) throws Exception {
		ITable[] tables = new ITable[annotation.tables().length];
		for(int i = 0 ; i < tables.length ; i++) {
			tables[i] = new DefaultTable(annotation.tables()[i]);
		}
		return new DefaultDataSet(tables);
	}
}
