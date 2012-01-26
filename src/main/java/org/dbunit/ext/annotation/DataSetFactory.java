package org.dbunit.ext.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.annotation.factory.IDataSetFactory;

/**
 * This is a meta-annotation to identify annotations which declare {@link IDataSet}.
 * <p>
 * For a given test method, the test framework scans all annotations declared
 * at the class level and at the method level. For each annotation annotated
 * with DataSetFactory, the framework instantiates the class referenced by this annotation
 * and calls {@link IDataSetFactory#create(java.lang.annotation.Annotation, java.lang.reflect.Method, Object)}.
 * <p>
 * With such mechanism, you can extend easily the framework by creating your
 * own annotations to create a specific {@link IDataSet}.
 *
 * @author David Gayerie
 * @version 1.0
 * @since 2010
 * @see IDataSetFactory
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Documented
public @interface DataSetFactory {
	/**
	 * Identifies the class which will handle the creation of the {@link IDataSet}.
	 * This class must be concrete and must declare a zero argument constructor.
	 */
	@SuppressWarnings("rawtypes")
	Class<? extends IDataSetFactory> value();
}
