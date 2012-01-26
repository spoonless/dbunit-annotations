package org.dbunit.ext.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.dbunit.IDatabaseTester;

/**
 * Specifies the data set related annotations at the class level 
 * <u>and</u> at the method level must apply.
 * <p>
 * For instance, if a given class is annotated by 
 * {@link XmlDataSet &#064;XmlDataSet} and a given test method declared 
 * in this class is also annotated by {@link XmlDataSet &#064;XmlDataSet}, 
 * the default behavior is to override and the class level
 * declaration is ignored. If you add &#064;MergeDataSet in the method
 * signature, the test framework will inject data set files declared at
 * the class level then data set files declared at the method level.
 * <p>
 * The {@link IDatabaseTester} provided to the test framework can also
 * contain a data set. For example you can create your own 
 * {@link IDatabaseTester} with a method annotated by 
 * {@link DatabaseTester &#064;DatabaseTester} and you can programmatically 
 * set the default data. The default behavior is to ignore already defined 
 * data set in the {@link IDatabaseTester} unless this annotation is added 
 * to the test method signature.
 *
 * @author David Gayerie
 * @version 1.0
 * @since 2010
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface MergeDataSet {

}
