/**
*
* Title: ReadXMLFile $Revision: 1.4 $  $Date: 2005-04-19 20:49:56 $
* Description: ReadXMLFile is a class used to
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
* @version $Revision: 1.4 $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/

package eLib.exit.xml.input;


import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 * Description: ReadXMLFile is a class used to read
 *              a XML file.
 */
// XXXX Pascal: Mauvais nom de classe.  Read est un verbe.
public class ReadXMLFile {
private DocumentBuilder _builder;

/***
 * Construct a parser that can parse item lists
 * */
  public ReadXMLFile() throws ParserConfigurationException{
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    _builder = factory.newDocumentBuilder();
  }

  /**
   * Parses an XML file containing a list of elements
   * INPUT: filename, the name of the file
   * OUTPUT: a root Element containing all children elements in the XML file
   * */
  public Document getDocumentFile (String filename) throws SAXException, IOException{
      File file = new File(filename);
      return _builder.parse(file);
  }

}