package org.dbunit.ext.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies an external class which declares a method annotated with 
 * {@link DatabaseTester}.
 * This is a convenient way to create a factory for the database tester.
 * <p>
 * The class referenced by this annotation must contain a public method with no
 * argument annotated with {@link DatabaseTester}. If this method is not static,
 * this class must have a zero argument constructor and will be instantiated 
 * for each test method.
 * <p>
 * The usage of this annotation is strongly recommended to improve cohesion in 
 * the design of test classes.  
 *
 * @author David Gayerie
 * @version 1.0
 * @since 2010
 * @see DatabaseTester
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Inherited
public @interface DatabaseTesterProvider {
	Class<?> value() ;
}
