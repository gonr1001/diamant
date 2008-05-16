/**
 * Created on 13-Dec-07
 * 
 * 
 * Title: TTStructureSAXErrorHandler.java
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

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class TTStructureSAXErrorHandler implements ErrorHandler {
	//to study the parser behavior
	private static boolean PRINT_MESSAGE = true;


	/**
	 * <p>
	 *   This will report a warning that has occurred; this indicates
	 *   that while no XML rules were "broken", something appears
	 *   to be incorrect or missing.
	 * </p>
	 *
	 * @param exception <code>SAXParseException</code> that occurred.
	 * @throws <code>SAXException</code> when things go wrong 
	 */
	@Override
	public void warning(SAXParseException exception) throws SAXException {
		if (PRINT_MESSAGE)
			System.out.println("TTStructureSAXErrorHandler: warning");
		System.out.println("**Parsing Warning**\n" + getLocationString(exception) +
				": " + exception.getMessage());
		throw new SAXException("Warning encountered");
	}// end warning

	/**
	 * <p>
	 *   This will report an error that has occurred; this indicates
	 *   that a rule was broken, typically in validation, but that
	 *   parsing can reasonably continue.
	 * </p>
	 *
	 * @param exception <code>SAXParseException</code> that occurred.
	 * @throws <code>SAXException</code> when things go wrong 
	 */
	@Override
	public void error(SAXParseException exception) throws SAXException {
		if (PRINT_MESSAGE)
			System.out.println("TTStructureSAXErrorHandler: error");
		System.out.println("**Parsing Error**\n" + getLocationString(exception) +
				": " + exception.getMessage());
		throw new SAXException("Error encountered");
	}// end error


	/**
	 * <p>
	 *   This will report a fatal error that has occurred; this indicates
	 *   that a rule has been broken that makes continued parsing either
	 *   impossible or an almost certain waste of time.
	 * </p>
	 *
	 * @param exception <code>SAXParseException</code> that occurred.
	 * @throws <code>SAXException</code> when things go wrong 
	 */
	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		if (PRINT_MESSAGE)
			System.out.println("TTStructureSAXErrorHandler: fatalError");
		System.out.println("**Parsing Fatal Error**\n" + getLocationString(exception) +
				": " + exception.getMessage());
		throw new SAXException("Fatal Error encountered");
	}// end fatalError
	
	
	/* returns a string of the location problem */
	private String getLocationString(SAXParseException exception){
		StringBuffer sb = new StringBuffer();
		
		String systemId = exception.getSystemId();
		if (systemId != null) {
			int index = systemId.lastIndexOf('/');
			if (index != -1)
				systemId = systemId.substring(index +1);
			sb.append(systemId);
		}
		sb.append(" line : ");
		sb.append(exception.getLineNumber());
		sb.append(" column : ");
		sb.append(exception.getColumnNumber());
		
		return sb.toString();
	}

}
