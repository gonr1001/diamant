/**
 * Created on 13-Dec-07
 * 
 * 
 * Title: TTStructureSAXContentHandler.java
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class TTStructureSAXContentHandler implements ContentHandler {
	
	private static boolean PRINT_MESSAGE = false;
	
	private DxTTStructure _dxTTStructure;
	
	private StringBuffer _currentText;

	
	/** Store URI to prefix mappings 
	 * @associates String*/
	private Map<String, String> namespaceMappings;

	public TTStructureSAXContentHandler(DxTTStructure dxTTStructure) {
		if (PRINT_MESSAGE)
			System.out.println("TTStructureSAXContentHandler: constructor");
		_dxTTStructure = dxTTStructure;
		this.namespaceMappings = new HashMap<String, String>();
		_currentText = null;
	}
	
	public DxTTStructure getdxTTStructure() {
		return _dxTTStructure;
	}

	
	/**
	 * <p>
	 *   This reports character data (within an element).
	 * </p>
	 *
	 * @param ch <code>char[]</code> character array with character data
	 * @param start <code>int</code> index in array where data starts.
	 * @param length <code>int</code> index in array where data ends.
	 * @throws <code>SAXException</code> when things go wrong
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (PRINT_MESSAGE)
			System.out.println("TTStructureSAXContentHandler: characters");
		_currentText.append(ch, start, length);
		/*
		 String s = new String(ch, start, length);
		 DefaultMutableTreeNode data =
		 new DefaultMutableTreeNode("Character Data: '" + s + "'");
		 current.add(data);*/
	}


	/**
	 * <p>
	 *  This indicates the end of a Document parse-this occurs after
	 *    all callbacks in all SAX Handlers.</code>.
	 * </p>
	 *
	 * @throws <code>SAXException</code> when things go wrong
	 */
	@Override
	public void endDocument() throws SAXException {
		if (PRINT_MESSAGE)
			System.out.println("TTStructureSAXContentHandler: endDocument");
		// No visual events occur here
	}


	/**
	 * <p>
	 *   Indicates the end of an element
	 *     (<code>&lt;/[element name]&gt;</code>) is reached. Note that
	 *     the parser does not distinguish between empty
	 *     elements and non-empty elements, so this occurs uniformly.
	 * </p>
	 *
	 * @param namespaceURI <code>String</code> URI of namespace this
	 *                element is associated with
	 * @param localName <code>String</code> name of element without prefix
	 * @param qName <code>String</code> name of element in XML 1.0 form
	 * @throws <code>SAXException</code> when things go wrong
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (PRINT_MESSAGE)
			System.out.println("TTStructureSAXContentHandler: endElement");
		uri += ""; //to avoid warning
		qName += ""; //to avoid warning

		if (localName.equals("DxTimeTable")) {
			;
		} else if (localName.equals("TTcycle")) {
			;
		} else if (localName.equals("TTdays")) {
			;
		} else if (localName.equals("cycleID")) {
			;
		} else if (localName.equals("pLength")) {
			;
		} else if (localName.equals("TTday")) {
			;
		} else if (localName.equals("TTsequences")) {
			;
		} else if (localName.equals("dayID")) { 
			;
		} else if (localName.equals("dayRef")) {
			;
		} else if (localName.equals("TTsequence")) {
			;
		} else if (localName.equals("sequenceID")) {
			;
		} else if (localName.equals("TTperiods")) {
			;
		} else if (localName.equals("TTperiod")) {
			;
		} else if (localName.equals("BeginTime")) { 
			;
		} else if (localName.equals("EndTime")) {
			;
		} else if (localName.equals("priority")) {
			;
		} else if (localName.equals("periodID")) {
			;
		} else {
			throw new SAXException("Unknown element in XML ttStructure");
		}
	}


	/**
	 * <p>
	 *   This indicates the end of a prefix mapping, when the namespace
	 *     reported in a <code>{@link #startPrefixMapping}</code> callback
	 *     is no longer available.
	 * </p>
	 *
	 * @param prefix <code>String</code> of namespace being reported
	 * @throws <code>SAXException</code> when things go wrong
	 */
	public void endPrefixMapping(String prefix) throws SAXException {
		if (PRINT_MESSAGE)
			System.out.println("TTStructureSAXContentHandler: endPrefixMapping");
		// No visual events occur here.
		for (Iterator i = namespaceMappings.keySet().iterator(); i.hasNext();) {

			String uri = (String) i.next();
			String thisPrefix = (String) namespaceMappings.get(uri);
			if (prefix.equals(thisPrefix)) {
				namespaceMappings.remove(uri);
				break;
			}
		}
	}


	/**
	 * <p>
	 * This reports whitespace that can be ignored in the
	 * originating document. This is typically invoked only when
	 * validation is ocurring in the parsing process.
	 * </p>
	 *
	 * @param ch <code>char[]</code> character array with character data
	 * @param start <code>int</code> index in array where data starts.
	 * @param end <code>int</code> index in array where data ends.
	 * @throws <code>SAXException</code> when things go wrong
	 */
	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		if (PRINT_MESSAGE)
			System.out.println("TTStructureSAXContentHandler: ignorableWhitespace");
		ch.toString(); //to avoid warning
		start = start + 0; //to avoid warning
		length = length + 0; //to avoid warning
		// This is ignorable, so don't display it
	}

	
	



	/**
	 * <p>
	 *   This indicates that a processing instruction (other than
	 *     the XML declaration) has been encountered.
	 * </p>
	 *
	 * @param target <code>String</code> target of PI
	 * @param data <code>String</code containing all data sent to the PI.
	 *               This typically looks like one or more attribute value
	 *               pairs.
	 * @throws <code>SAXException</code> when things go wrong
	 */
	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
		if (PRINT_MESSAGE)
			System.out
					.println("TTStructureSAXContentHandler: processingInstruction");
		target += ""; //to avid warning
		data += ""; //to avoid warning
	}


	/**
	 * <p>
	 *  Provide reference to <code>Locator</code> which provides+
	 *    information about where in a document callbacks occur.
	 * </p>
	 *
	 * @param locator <code>Locator</code> object tied to callback
	 *        process
	 */
	@Override
	public void setDocumentLocator(Locator locator) {
		if (PRINT_MESSAGE)
			System.out.println("TTStructureSAXContentHandler: setDocumentLocator");
		// Save this for later use
		//_locator = locator;
		locator.toString(); //to avoid warning
	}



	
	/**
	 * <p>
	 *   This reports an entity that is skipped by the parser. This
	 *     should only occur for non-validating parsers, and then is still
	 *     implementation-dependent behavior.
	 * </p>
	 *
	 * @param name <code>String</code> name of entity being skipped
	 * @throws <code>SAXException</code> when things go wrong
	 */
	@SuppressWarnings("unused")
	@Override
	public void skippedEntity(String name) throws SAXException {
		if (PRINT_MESSAGE)
			System.out.println("TTStructureSAXContentHandler: skippedEntity");
	}

	/**
	 * <p>
	 *  This indicates the start of a Document parse-this precedes
	 *    all callbacks in all SAX Handlers with the sole exception
	 *    of <code>{@link #setDocumentLocator}</code>.
	 * </p>
	 *
	 * @throws <code>SAXException</code> when things go wrong
	 */
	@Override
	public void startDocument() throws SAXException {
		if (PRINT_MESSAGE)
			System.out.println("TTStructureSAXContentHandler: startDocument");
		// No visual events occur here
	}

	

	/**
	 * <p>
	 *   This reports the occurrence of an actual element. It includes
	 *     the element's attributes, with the exception of XML vocabulary
	 *     specific attributes, such as
	 *     <code>xmlns:[namespace prefix]</code> and
	 *     <code>xsi:schemaLocation</code>.
	 * </p>
	 *
	 * @param namespaceURI <code>String</code> namespace URI this element
	 *               is associated with, or an empty <code>String</code>
	 * @param localName <code>String</code> name of element (with no
	 *               namespace prefix, if one is present)
	 * @param qName <code>String</code> XML 1.0 version of element name:
	 *                [namespace prefix]:[localName]
	 * @param atts <code>Attributes</code> list for this element
	 * @throws <code>SAXException</code> when things go wrong
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (PRINT_MESSAGE)
			System.out.println("TTStructureSAXContentHandler: startElement");
		uri += ""; //to avoid warning
		qName += ""; //to avoid warning
		_currentText = new StringBuffer();
		//_textStack.push(_currentText);
		//_elementStack.push(localName);
		if (localName.equals("OthelloTag.O_ROOT")) {
			String version = attributes.getValue("", "version");
			if (version != null && !version.equals("OthelloTag.VERSION"))
				throw new SAXException("Incorrect code version");
			//_code = new JRCode();
			//_book = new Book();
		}

	}
	



	/**
	 * <p>
	 *   This indicates the beginning of an XML Namespace prefix
	 *     mapping. Although this typically occurs within the root element
	 *     of an XML document, it can occur at any point within the
	 *     document. Note that a prefix mapping on an element triggers
	 *     this callback <i>before</i> the callback for the actual element
	 *     itself (<code>{@link #startElement}</code>) occurs.
	 * </p>
	 *
	 * @param prefix <code>String</code> prefix used for the namespace
	 *                being reported
	 * @param uri <code>String</code> URI for the namespace
	 *               being reported
	 * @throws <code>SAXException</code> when things go wrong
	 */
	@Override
	public void startPrefixMapping(String prefix, String uri) {
		if (PRINT_MESSAGE)
			System.out.println("TTStructureSAXContentHandler: startPrefixMapping");
		// No visual events occur here.
		namespaceMappings.put(uri, prefix);
	}

}
