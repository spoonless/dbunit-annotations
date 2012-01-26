package org.dbunit.ext.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.annotation.factory.MethodDataSetFactory;

/**
 * Gets a data set from a method call.
 * <p>
 * This is the simplest way to add a data set for a given test method.
 * The referenced methods must be declared in the same class and 
 * have the following signature:
 * <ul>
 * <li>No argument</li>
 * <li>The returned value must be type compatible with {@link IDataSet}</li>
 * </ul>
 *
 * @author David Gayerie
 * @version 1.0
 * @since 2010
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Documented
@Inherited
@DataSetFactory(MethodDataSetFactory.class)
public @interface MethodDataSet {
	/**
	 * @return The methods name which must be called to get the data set.
	 */
	String[] methods() ;
}
