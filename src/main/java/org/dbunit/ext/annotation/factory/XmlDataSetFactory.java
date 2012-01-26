package org.dbunit.ext.annotation.factory;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;

import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.annotation.XmlDataSet;

public class XmlDataSetFactory extends AbstractDataSetFactory<XmlDataSet> {
	
	@Override
	protected IDataSet[] createDataSets(XmlDataSet annotation, Method method, Integer parameterPosition, Object target) throws Exception {
		String[] locations = annotation.location() ;
		IDataSet[] dataSets = new IDataSet[locations.length] ;
		for (int i = 0 ; i < locations.length ; i++) {
			URL locationURL = getResourceURL(locations[i], target) ;
			// TODO Should we close the connection ?
			URLConnection locationConnection = locationURL.openConnection() ;
			dataSets[i] = new org.dbunit.dataset.xml.XmlDataSet(locationConnection.getInputStream()) ;
		}
		return dataSets ;
	}

}
