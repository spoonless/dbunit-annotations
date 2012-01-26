package org.dbunit.ext.annotation.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.annotation.DataSetFactory;

/**
 * The {@link IDataSet} factory.
 * Class implementing this interface is associated with an annotation
 * thanks to the meta-annotation {@link DataSetFactory &#064;DataSetFactory}.
 *
 * @author David Gayerie
 * @version 1.0
 * @since 2010
 * @see DataSetFactory
 */
public interface IDataSetFactory<A extends Annotation> {
	
	/**
	 * Method called to create a data set from the declaration of an 
	 * annotation.
	 * 
	 * @param annotation The annotation associated with this factory.
	 * @param method The test method for which the annotation is found
	 * (even if the annotation is declared at the class level).
	 * @param parameterPosition The position of the parameter for which
	 * the annotation is declared. Null if the annotation is declared at
	 * the class level or at the method level.
	 * @param target The instance of the test case.
	 * @return The data set
	 * @throws Exception for any errors.
	 */
	IDataSet create (A annotation, Method method, Integer parameterPosition, Object target) throws Exception ;

}
