package org.dbunit.ext.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sets the {@link DatabaseOperation} to call when ending the test. 
 * This annotation can be declared at the class level and will be applied for each
 * test method. Otherwise, the annotation can also be declared at the method
 * level. In this case, the class level declaration (if any) is overridden.
 * 
 * @author David Gayerie
 * @version 1.0
 * @since 2010
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE, ElementType.METHOD })
@Documented
@Inherited
public @interface DatabaseOperationAfter {
	DatabaseOperation value();
}
