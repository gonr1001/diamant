/**
*
* Title: WriteXMLFile $Revision: 1.2 $  $Date: 2004-09-10 13:31:20 $
* Description: WriteXMLFile is a class used to
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


package eLib.exit.xml.output;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import java.io.File;

public class WriteXMLFile {

 /**
   *
   * Constructor
   * 
   *
   */
	public WriteXMLFile() {
	}
/**
  *
  * Requires: a Document with the XML definitions.
  *           a String fileName
  *
  * <p>
  * Modifies: the file. will be written.
  *
  * <p>
  * Effect: the file will be written.
  * 
  * @param doc a Document XML
  * @param fileName a String
  */
	public static final void write(Document doc, String fileName) throws Exception {
		File file = new File(fileName);
		Transformer trans= TransformerFactory.newInstance().newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(file));
	}
} /* end WriteXMLFile */