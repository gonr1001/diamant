/**
 *
 * Title: WriteXMLElement $Revision: 1.3 $  $Date: 2005-06-23 20:10:34 $
 * Description: WriteXMLElement is a class used to build the tree of
 *              dom elements
 *
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
 * @version $Revision: 1.3 $
 * @author  $Author: durp1901 $
 * @since JDK1.3
 */

package eLib.exit.xml.output;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * Description: WriteXMLElement is a class used used to build the tree of
 *              dom elements, normally the tree will be written in a file
 */
public class XMLWriter {
	private DocumentBuilder _builder;

	private Document _doc;

	/**
	 * Construct an item list builder
	 * 
	 * 
	 *
	 */
	public XMLWriter() throws ParserConfigurationException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			_builder = factory.newDocumentBuilder();
			_doc = _builder.newDocument();
		} catch (FactoryConfigurationError e) {
			System.out.println("Factory: " + e);//debug
		}
	}

	public Document getDocument() {
		return _doc;
	}

	public Document getNewDocument() {
		return _builder.newDocument();
	}

	/***
	 * Builds a Document org.w3c.dom.Document for
	 *  an element tree of items
	 * @param doc a DOM document describing the items
	 * @param elementList Element, the item list
	 * @return doc a DOM document describing the items
	 * */
	public Document buildDocument(Document doc, Element elementList) {
		doc.appendChild(elementList);
		return doc;
	}

	/**
	 * builds a Document org.w3c.dom.Document for for an item
	 * @param doc a DOM document describing the items
	 * @param tagName a String
	 * @return an element Element
	 * */
	public Element createElement(Document doc, String tagName) {
		return doc.createElement(tagName);
	}

	/**
	 * builds a dom element for an item
	 * @param doc a DOM document describing the items
	 * @param tagName a String
	 * @param value a String  
	 * @return an element Element
	 * */
	public Element createElement(Document doc, String tagName, String value) {
		Element element = doc.createElement(tagName);
		Text val = doc.createTextNode(value);
		element.appendChild(val);
		return element;
	}

	/**
	 * add a child to an element
	 * @param element an Element where we will add a child
	 * @param child an Element to be added
	 * @return element an Element the result of the operation
	 * 
	 * */
	public Element appendChildInElement(Element element, Element child) {
		element.appendChild(child);
		return element;
	}

} /* end XMLWriter */