package com.tidesofwaronline.Exodus.Config;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class XMLLoader extends DefaultHandler {
	
	public XMLLoader() throws Exception {
		super();
		
		XMLReader xr = XMLReaderFactory.createXMLReader();
		XMLLoader handler = new XMLLoader();
		xr.setContentHandler(handler);
		xr.setErrorHandler(handler);
	}
}
