package org.dbunit.ext.annotation.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.IDataSet;

/**
 * Basic implementation of {@link IDataSetFactory}.
 * This implementation creates a {@link CompositeDataSet} if
 * several data sets should be created.
 *
 * @author David Gayerie
 * @version 1.0
 * @since 2010
 */
public abstract class AbstractDataSetFactory<A extends Annotation> implements IDataSetFactory<A> {
	
	/* (non-Javadoc)
	 * @see org.dbunit.ext.annotation.factory.IDataSetFactory#create(java.lang.annotation.Annotation, java.lang.reflect.Method, java.lang.Integer, java.lang.Object)
	 */
	public final IDataSet create(A annotation, Method method, Integer parameterPosition, Object target) throws Exception {
		IDataSet result = null ;
		IDataSet[] dataSets = createDataSets(annotation, method, parameterPosition, target);
		if (dataSets.length == 0) {
			result = new DefaultDataSet() ;
		}
		else if (dataSets.length == 1) {
			result = dataSets[0] ;
		}
		else {
			result = new CompositeDataSet(dataSets) ;
		}
		return result ;
	}
	
	protected abstract IDataSet[] createDataSets(A annotation, Method method, Integer parameterPosition, Object target) throws Exception ;

	protected URL getResourceURL (String location, Object target) throws Exception {
		if (location.length() == 0) {
			throw new IllegalArgumentException ("Data set location cannot be empty!") ;
		}
		URL locationURL = target.getClass().getResource(location) ;
		if (locationURL == null) {
			File locationFile = new File(location) ;
			if (! locationFile.exists()) {
				throw new FileNotFoundException("Cannot find " + location + " in classpath or in the file system!") ;
			}
			locationURL = locationFile.toURI().toURL() ;
		}
		return locationURL ;
	}
}
