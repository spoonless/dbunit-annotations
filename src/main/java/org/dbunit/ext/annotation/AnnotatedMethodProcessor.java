package org.dbunit.ext.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.IDataSet;

public class AnnotatedMethodProcessor extends AbstractAnnotationProcessor {
	
	private IDataSet originalDataSet ;
	private boolean isDatabaseTesterInitialized ;
	
	public AnnotatedMethodProcessor(TestContext testContext) {
		super(testContext) ;
	}
	
	public void onSetup () throws Exception {
		try {
			initDatabaseTester() ;
		}
		catch (Exception e) {
			clean() ;
			throw e ;
		}
	}
	
	public void onTearDown () throws Exception {
		// TODO merge onTearDown and clean
		try {
			if (isDatabaseTesterInitialized) {
				testContext.getDatabaseTester().onTearDown() ;
			}
		}
		finally {
			clean();
		}
	}

	private void clean() throws Exception {
		if (isDatabaseTesterInitialized) {
			testContext.getDatabaseTester().setDataSet(originalDataSet) ;
		}
		originalDataSet = null ;
	}
	
	private void initDatabaseTester () throws Exception {
		IDatabaseTester databaseTester = testContext.getDatabaseTester() ;
		DatabaseOperationBefore databaseOperationBefore = getAnnotation(DatabaseOperationBefore.class) ;
		if (databaseOperationBefore != null) {
			databaseTester.setSetUpOperation(databaseOperationBefore.value().convert()) ;
		}

		DatabaseOperationAfter databaseOperationAfter = getAnnotation(DatabaseOperationAfter.class) ;
		if (databaseOperationAfter != null) {
			databaseTester.setTearDownOperation(databaseOperationAfter.value().convert()) ;
		}
		
		originalDataSet = databaseTester.getDataSet();
		addDataSet (databaseTester) ;
		databaseTester.onSetup() ;
		isDatabaseTesterInitialized = true ;
	}
	
	private void addDataSet(IDatabaseTester databaseTester) throws Exception {
		Method method = testContext.getMethod() ;
		Object target = testContext.getTarget() ;
		
		List<IDataSet> dataSets = getDataSet(method.getAnnotations()) ;
		boolean shouldMerge = method.isAnnotationPresent(MergeDataSet.class);
		if (dataSets.isEmpty() || shouldMerge) {
			dataSets.addAll(0, getDataSet(target.getClass().getAnnotations())) ;
		}
		if (! dataSets.isEmpty()) {
			if (databaseTester.getDataSet() != null && shouldMerge) {
				dataSets.add(0, databaseTester.getDataSet()) ;
			}
			if (dataSets.size() == 1) {
				databaseTester.setDataSet(dataSets.get(0)) ;
			}
			else {
				databaseTester.setDataSet(new CompositeDataSet(dataSets.toArray(new IDataSet[dataSets.size()]))) ;
			}
		}
		else if (databaseTester.getDataSet() == null) {
			databaseTester.setDataSet(new DefaultDataSet()) ;
		}
	}
	
	private <T extends Annotation> T getAnnotation (Class<T> annotationType) {
		Method method = testContext.getMethod() ;
		Object target = testContext.getTarget() ;

		T annotation = null ;
		annotation = method.getAnnotation(annotationType) ;
		if(annotation == null) {
			annotation = target.getClass().getAnnotation(annotationType) ;
		}
		return annotation ;
	}
}
