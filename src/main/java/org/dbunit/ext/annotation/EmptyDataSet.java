package org.dbunit.ext.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.annotation.factory.EmptyDataSetFactory;

/**
 * Allows to add empty table in the data set.
 * This annotation is useful if you use {@link DatabaseOperation#DELETE_ALL}
 * or {@link DatabaseOperation#TRUNCATE} as operation after the test. 
 * The tables declared by this annotation will be automatically cleaned from 
 * any data inserted by the test. 
 *
 * @author David Gayerie
 * @version 1.0
 * @since 2010
 * @see FlatXmlDataSet
 * @see FlatXmlDataSetBuilder
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Documented
@Inherited
@DataSetFactory(EmptyDataSetFactory.class)
public @interface EmptyDataSet {
	/**
	 * @return The methods name which must be called to get the data set.
	 */
	String[] tables() ;
}
