/**
*
* Title: ReadXMLElement $Revision: 1.2 $  $Date: 2004-09-10 13:31:15 $
* Description: ReadXMLElement is a class used to
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
* @version $Revision: 1.2 $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/

package eLib.exit.xml.input;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;



/**
 * Description: ReadXMLElement is a class used to read elements from
 *              an XML file.
 */
public class ReadXMLElement {

/***
 * Construct a parser that can parse item lists
 * */
  public ReadXMLElement(){
  }

  /**
   * Parses an XML file containing an elements list
   * @param  doc Document DOM, 
   * @return a root Element containing all children elements in the XML file
   * */
  public Element getRootElement(Document doc){
      return doc.getDocumentElement();
  }

  /**
   * Obtains a child element from a DOM root element
   * @param e a root element; 
   * @param tag a String tag name of the element
   * @return an Element containing all children elements or items
   * */
  public static Element getDOMelement(Element e, String tag){
    //ArrayList items = new ArrayList();
    // get the item children
    NodeList children = e.getChildNodes();
    for (int i=0; i<children.getLength(); i++){
      Node childNode = children.item(i);
      if(childNode instanceof Element){
        Element childElement = (Element)childNode;
        if (childElement.getTagName().equals(tag)){
          return childElement;
        }// end if (childElement.getTagName().equals(ITEM1))
      }// end if (childNode instanceof Element)
    }// end for i
    return null;
  }
  /**
   * Obtains size of a nodeList
   * @param e a root element; 
   * @param tag a String tag name of the element
   *
   **/
  public int getSize(Element e, String tag){
    NodeList node = e.getChildNodes();
    int size=0;
  //  NodeList theChildren = e.getChildNodes();
    for (int i=0; i< node.getLength(); i++){
      Node childNode = node.item(i);
      if (childNode instanceof Element){
        Element childElement = (Element)childNode;
        if (childElement.getTagName().equals(tag)){
        	//theChildren = childElement.getChildNodes();
          size++;
        }// end if childElement
      }// end if childnode
    }// end for i
    return size;
  }


  /**
   * Obtains a node element from a DOM element
   * @param e a root element; 
   * @param tag a String tag name of the element
   * @param index element position
   **/
  public Element getElement(Element e, String tag, int index){
    NodeList node = e.getChildNodes();
    int count=0;
    for (int i=0; i< node.getLength(); i++){
      Node childnode = node.item(i);
      if (childnode instanceof Element){
        Element childElement = (Element)childnode;
        if (childElement.getTagName().equals(tag)){
          if (count== index)
            return childElement;
          count++;
        }// end if childElement
      }// end if childnode
    }// end for i
    return null;
  }

  /**
   * get the Node Value
   * @param e a root element; 
   * @param tag a String tag name of the element
   * @return a value of a tag from a nodelist
   * */
  public String getElementValue(Element e, String tag){
    NodeList list = e.getChildNodes();
    for (int j=0; j< list.getLength(); j++){
        Node elt = list.item(j);
        if (elt instanceof Element){
          if (elt.getNodeName().equalsIgnoreCase(tag))
            return ((Text)elt.getFirstChild()).getData();
        }// end inst instanceof Element
      }// end for j
    return "";
  }


}