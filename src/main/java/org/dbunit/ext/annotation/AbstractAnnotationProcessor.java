package org.dbunit.ext.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.annotation.factory.IDataSetFactory;

public abstract class AbstractAnnotationProcessor {

	protected TestContext testContext ;

	public AbstractAnnotationProcessor(TestContext testContext) {
		if (testContext == null) {
			throw new IllegalArgumentException("testContext cannot be null!") ;
		}
		this.testContext = testContext ;
	}
	
	protected List<IDataSet> getDataSet(Annotation[] annotations) throws DbUnitAnnotationException {
		return getDataSet(annotations, null);
	}

	protected List<IDataSet> getDataSet(Annotation[] annotations, Integer parameterPosition) throws DbUnitAnnotationException {
		List<IDataSet> result = new ArrayList<IDataSet>() ;
		for (Annotation annotation : annotations) {
			IDataSet dataSet = getDataSet(annotation, parameterPosition);
			if (dataSet != null) {
				result.add(dataSet);
			}
		}
		return result ;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected IDataSet getDataSet(Annotation annotation, Integer parameterPosition) throws DbUnitAnnotationException {
		try {
			IDataSet result = null ;
			DataSetFactory dataSetFactoryAnnotation = annotation.annotationType().getAnnotation(DataSetFactory.class) ;
			if (dataSetFactoryAnnotation != null) {
				IDataSetFactory dataSetFactory = dataSetFactoryAnnotation.value().newInstance() ;
				result = dataSetFactory.create(annotation, testContext.getMethod(), parameterPosition, testContext.getTarget()) ;
			}
			return result ;
		}
		catch (Exception e) {
			Throwable t = e ;
			if (e instanceof InvocationTargetException) {
				t = ((InvocationTargetException) e).getCause();
			}
			String message = null ;
			if (parameterPosition == null) {
				message = "Exception when creating data set from annotation @" 
					+ annotation.annotationType().getSimpleName() + ": " + t.getMessage();
			}
			else {
				message = "Exception when creating data set from annotation @" 
					+ annotation.annotationType().getSimpleName() + " for parameter at position " 
					+ parameterPosition + ": " + t.getMessage();
			}
			throw new DbUnitAnnotationException(message, t);
		}
	}

}