/**
 * Created on 13-Dec-07
 * 
 * 
 * Title: TTStructureEntitySolver.java
 *
 * Copyright (c) 2001 by rgr.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information
 * of rgr. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with rgr.
 *
 * 
 * 
 */

package dInternal.dTimeTable;

import java.io.FileReader;
import java.io.IOException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TTStructureEntitySolver implements EntityResolver {
	//to study the parser behavior put to true
	private  boolean PRINT_MESSAGE = true;

	    String schemaURI = 
	        "http://www.sauria.com/schemas/apache-xml-book/book.xsd";
	@Override
	public InputSource resolveEntity(@SuppressWarnings("unused")
	String publicId, String systemId)
			throws SAXException, IOException {
//		publicId += ""; // to avoid warning
		if (PRINT_MESSAGE)
			System.out.println("JREntitySolver: resolveEntity");
        if (systemId.equals(schemaURI)) {
            FileReader r = new FileReader("D://book.xsd");
            return new InputSource(r);
        } 
		if(false) // to avoid warning
			throw new SAXException();
		return null;
	}
}
