package org.dbunit.ext.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.dbunit.IDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;

/**
 * Annotates a method which returns an {@link IDatabaseTester}. This
 * method must be public, have no argument and return an instance compatible 
 * with {@link IDatabaseTester}. The annotated method can be declared in the 
 * test class or in another class referenced by the annotation
 * {@link DatabaseTesterProvider}.
 * <p>
 * If this annotation is not present, the test framework will fall back to the
 * default behavior: it will try to create a
 * {@link PropertiesBasedJdbcDatabaseTester} and configure it with a set
 * of keys declared as system properties.
 * <p>
 * If more than one method in the same class are annotated with DatabaseTester, 
 * the behavior is undefined. 
 * 
 * @author David Gayerie
 * @version 1.0
 * @since 2010
 * @see DatabaseTesterProvider
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DatabaseTester {
}
