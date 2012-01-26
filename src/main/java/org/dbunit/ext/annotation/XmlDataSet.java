package org.dbunit.ext.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.dbunit.ext.annotation.factory.XmlDataSetFactory;

/**
 * Loads a data set based on one or several {@link org.dbunit.dataset.xml.XmlDataSet XML data sets}.
 *
 * @author David Gayerie
 * @version 1.0
 * @since 2010
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Documented
@Inherited
@DataSetFactory(XmlDataSetFactory.class)
public @interface XmlDataSet {
	/**
	 * One or several locations of XML data sets.
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
}
