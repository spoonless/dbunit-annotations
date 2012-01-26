
package org.dbunit.ext.annotation.factory;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.annotation.FlatXmlDataSet;

public class FlatXmlDataSetFactory extends AbstractDataSetFactory<FlatXmlDataSet> {

	@Override
	protected IDataSet[] createDataSets(FlatXmlDataSet annotation, Method method, Integer parameterPosition, Object target) throws Exception {
		FlatXmlDataSetBuilder builder = createBuilder(annotation, target);
		String[] locations = annotation.location();
		IDataSet[] dataSets = new IDataSet[locations.length] ;
		for(int i = 0 ; i < locations.length ; i++) {
			URL resourceURL = getResourceURL(locations[i], target) ;
			dataSets[i] = builder.build(resourceURL) ;
		}
		return dataSets;
	}

	private FlatXmlDataSetBuilder createBuilder(FlatXmlDataSet annotation, Object target) throws Exception {
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder() ;
		builder.setCaseSensitiveTableNames(annotation.caseSensitive()) ;
		builder.setDtdMetadata(annotation.useDtd()) ;
		builder.setColumnSensing(annotation.columnSensing()) ;
		URLConnection dtdConnection = null ;
		if (annotation.dtd().length() > 0) {
			URL dtdURL = getResourceURL(annotation.dtd(), target) ;
			// TODO should we close the connection ?
			dtdConnection = dtdURL.openConnection() ;
			builder.setMetaDataSetFromDtd(dtdConnection.getInputStream()) ;
		}
		return builder;
	}
}
