/**
 *
 * Title: XMLReader $Revision: 1.3 $  $Date: 2005-06-23 20:10:34 $
 * Description: XMLReader is a class used to read XML 
 *              elements from an XML file.
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

package eLib.exit.xml.input;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * Description: XMLReader is a class used to read XML 
 *              elements from an XML file.
 *              The pre-condition is to have an instace
 *              of a Document org.w3c.dom.Document
 *              typical use is :
 *              <p>XMLReader xmlR = new XMLReader(); </p>
 *              <p>root = xmlR.getRootElement(doc); </p>
 *              <p> ... </p>
 *              <p>x = xmlR.get... </p>
 */
public class XMLReader {
	/**
	 * XMLReader used to call methods
	 *
	 */
	public XMLReader() {
		//used to call methods
	}

	/**
	 * get the Root Element 
	 * @param  doc Document org.w3c.dom.Document, 
	 * @return the root Element containing all children elements in
	 *          Document org.w3c.dom.Document (the XML file)
	 *          
	 */
	public Element getRootElement(Document doc) {
		return doc.getDocumentElement();
	}

	/**
	 * get a child element from  a tree of Elements
	 * @param e a root of a tree; 
	 * @param tag a String tag name of the element
	 * @return an Element containing a tree or an item
	 * */
/*	public static Element getElement(Element e, String tag) {
		// get the item children
		NodeList children = e.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node childNode = children.item(i);
			if (childNode instanceof Element) {
				Element childElement = (Element) childNode;
				if (childElement.getTagName().equals(tag)) {
					return childElement;
				}// end if (childElement.getTagName().equals(ITEM1))
			}// end if (childNode instanceof Element)
		}// end for i
		return null;
	}
*/
	/**
	 * get size of a tree 
	 * @param e a root element; 
	 * @param tag a String tag name of the element
	 * @return size of a tree (number of elements)
	 *
	 **/
	public int getSize(Element e, String tag) {
		NodeList node = e.getChildNodes();
		int size = 0;
		for (int i = 0; i < node.getLength(); i++) {
			Node childNode = node.item(i);
			if (childNode instanceof Element) {
				Element childElement = (Element) childNode;
				if (childElement.getTagName().equals(tag)) {
					size++;
				}// end if childElement
			}// end if childnode
		}// end for i
		return size;
	}

	/**
	 * get the Element e corresponding to a tag and the index
	 * @param e a tree; 
	 * @param tag a String tag name of the element
	 * @param index element position
	 * @return an Element, an item 
	 **/
	public Element getElement(Element e, String tag, int index) {
		NodeList node = e.getChildNodes();
		int count = 0;
		for (int i = 0; i < node.getLength(); i++) {
			Node childnode = node.item(i);
			if (childnode instanceof Element) {
				Element childElement = (Element) childnode;
				if (childElement.getTagName().equals(tag)) {
					if (count == index)
						return childElement;
					count++;
				}// end if childElement
			}// end if childnode
		}// end for i
		return null;
	}

	/**
	 * get the Element Value
	 * @param e a root element; 
	 * @param tag a String tag name of the element
	 * @return a value of a tag from a nodelist
	 * */
	public String getElementValue(Element e, String tag) {
		NodeList list = e.getChildNodes();
		for (int j = 0; j < list.getLength(); j++) {
			Node elt = list.item(j);
			if (elt instanceof Element) {
				if (elt.getNodeName().equalsIgnoreCase(tag))
					return ((Text) elt.getFirstChild()).getData();
			}// end inst instanceof Element
		}// end for j
		return "";
	}

}/* end XMReader */