package org.dbunit.ext.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.annotation.factory.FlatXmlDataSetFactory;

/**
 * Loads a data set based on one or several {@link org.dbunit.dataset.xml.FlatXmlDataSet flat XML data sets}.
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
@DataSetFactory(FlatXmlDataSetFactory.class)
public @interface FlatXmlDataSet {
	/**
	 * One or several locations of flat XML data sets.
	 * <p>
	 * Firstly, a file will be located from the class path.
	 * If it cannot be found, the path will be regarded as
	 * a file system path.
	 * If you provide a relative path, it will be resolved as
	 * follows:
	 * <ul>
	 * 	<li>Class path: from the package of the annotated class (i.e. the test class)</li>
	 * 	<li>File system: from the working directory</li>
	 * </ul>
	 * <p>
	 * If the XML file referenced a DTD, the path of this DTD
	 * is supposed to be relative to the XML file itself.
	 */
	String [] location() ;

	/**
	 * Location of the DTD to validate XML files.
	 * This document supersedes any DTD declared in the XML files. 
	 * <p>
	 * Firstly, the DTD will be located from the class path.
	 * If it cannot be found, the path will be regarded as
	 * a file system path.
	 * If you provide a relative path, it will be resolved as
	 * follows:
	 * <ul>
	 * 	<li>Class path: from the package of the annotated class (i.e. the test class)</li>
	 * 	<li>File system: from the working directory</li>
	 * </ul>
	 */
	String dtd() default "";
	
	/**
	 * Whether or not the created data set should use case sensitive table names.
	 * 
	 * @See FlatXmlDataSetBuilder
	 */
	boolean caseSensitive() default false ;
	
	/**
	 * Since DBUnit 2.3.0 there is a functionality called "column sensing" which 
	 * basically reads in the whole XML into a buffer and dynamically adds 
	 * new columns as they appear.
	 * 
	 * @See FlatXmlDataSetBuilder
	 */
	boolean columnSensing() default false ;
	
	/**
	 * Whether or not the XML files should be validated
	 *  
	 * @See FlatXmlDataSetBuilder
	 */
	boolean useDtd() default true ;
}
