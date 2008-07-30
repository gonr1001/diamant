/**
 *
 * Title: XMLInputFile $Revision: 1.3 $  $Date: 2005-06-23 20:10:34 $
 * Description: XMLInputFile is a class used to create
 *              a Document org.w3c.dom.Document
 *              a typical use is :
 *              XMLInputFile xmlIF = new XMLInputFile();
 *              Document doc = xmlIF.createDocument(fileName);
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Description: XMLInputFile is a class used to create
 *              a Document org.w3c.dom.Document
 *              a typical use is :
 *              <p>XMLInputFile xmlIF = new XMLInputFile(); </p>
 *              <p>Document doc = xmlIF.createDocument(fileName); </p>
 */
public class XMLInputFile {
	private DocumentBuilder _builder;

	/***
	 * Construct a Internal DocumentBuilder
	 * 
	 */
	public XMLInputFile() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		_builder = factory.newDocumentBuilder();
	}

	/**
	 * create a Document org.w3c.dom.Document
	 * @param fileName a String which contains the file name
	 * @return a Document which is the XML file
	 * */
	public Document createDocument(String fileName) throws SAXException,
			FileNotFoundException, IOException {
		System.out.println("UFile Name in createDocument" + fileName);
		File file = new File(fileName);
		return _builder.parse(file);
	}

	
	/**
	 * create a Document org.w3c.dom.Document
	 * @param fileName a String which contains the file name
	 * @return a Document which is the XML file
	 * */
	public Document createDocument(InputStream fileName) throws SAXException,
			FileNotFoundException, IOException {
		System.out.println("UFile Name in createDocument" + fileName);
		//File file = new File(fileName);
		return _builder.parse(fileName);
	}
} /* end XMLInputFile */