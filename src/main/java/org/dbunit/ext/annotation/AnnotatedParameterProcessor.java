package org.dbunit.ext.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;

public class AnnotatedParameterProcessor extends AbstractAnnotationProcessor {

	public AnnotatedParameterProcessor(TestContext testContext) {
		super(testContext);
	}
	
	public Object[] getParameters() throws Exception {
		Method method = testContext.getMethod() ;
		Class<?>[] parameterTypes = method.getParameterTypes();
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		Object[] parameters = new Object[parameterTypes.length];
		
		for (int i = 0 ; i < parameterTypes.length ; i++) {
			Class<?> parameterClass = parameterTypes[i];
			if (parameterClass.equals(IDataSet.class)) {
				parameters[i] = getDataSetFromParameter(parameterAnnotations[i], i);
			}
			else if (parameterClass.equals(IDatabaseConnection.class)) {
				parameters[i] = testContext.getDatabaseConnection();
			}
			else if (parameterClass.equals(Connection.class)) {
				parameters[i] = testContext.getDatabaseConnection().getConnection() ;
			}
			else {
				// TODO throws exception?
			}
		}
		return parameters;
	}

	private IDataSet getDataSetFromParameter(Annotation[] annotations, int parameterPosition) throws Exception {
		IDataSet dataSet = null ;
		List<IDataSet> dataSets = getDataSet(annotations, parameterPosition);
		if (dataSets.size() == 1) {
			dataSet = dataSets.get(0);
		}
		else {
			dataSet = new CompositeDataSet(dataSets.toArray(new IDataSet[dataSets.size()]));
		}
		return dataSet;
	}
}
