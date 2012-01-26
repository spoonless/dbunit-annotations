package org.dbunit.ext.annotation.factory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.annotation.MethodDataSet;

public class MethodDataSetFactory extends AbstractDataSetFactory<MethodDataSet> {

	protected IDataSet[] createDataSets(MethodDataSet annotation, Method method, Integer parameterPosition, Object target) throws Exception {
		List<IDataSet> results = new ArrayList<IDataSet>() ;
		for (String methodName : annotation.methods()) {
			Method dataSetMethod = target.getClass().getMethod(methodName) ;
			if (!IDataSet.class.isAssignableFrom(dataSetMethod.getReturnType())) {
				throw new IllegalArgumentException("Method " + dataSetMethod.toString() + " must return an " + IDataSet.class.getName() + " compatible instance!") ;
			}
			results.add((IDataSet) dataSetMethod.invoke(target)) ;
		}
		return results.toArray(new IDataSet[results.size()]);
	}
}
