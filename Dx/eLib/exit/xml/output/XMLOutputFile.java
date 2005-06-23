/**
 *
 * Title: XMLOutputFile $Revision: 1.3 $  $Date: 2005-06-23 20:10:34 $
 * Description: XMLOutputFile is a class used to create
 *              a Document org.w3c.dom.Document
 *              a typical use is :
 *              XMLInputFile xmlIF = new XMLInputFile();
 *              Document doc = xmlIF.createDocument(fileName);
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

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import java.io.File;

public class XMLOutputFile {

	/**
	 * XMLOutputFile used to call methods
	 *
	 */
	public XMLOutputFile() {
		//used to call methods
	}
	/**
	 * 
	 * Requires: a Document with the XML definitions. a String fileName
	 * 
	 * <p>
	 * Modifies: the file that will be written.
	 * 
	 * <p>
	 * Effect: the file is written.
	 * 
	 * @param doc
	 *            a Document XML
	 * @param fileName
	 *            a String
	 */
	public void write(Document doc, String fileName)
			throws Exception {
		File file = new File(fileName);
		Transformer trans = TransformerFactory.newInstance().newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(file));
	}
} /* end XMLOutputFile */