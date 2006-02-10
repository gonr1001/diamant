/**
 *
 * Title: XMLTools 
 * Description: 
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
package dInternal.dUtil;

public class XMLTools {

	/**
	 * get a root document of an xml file
	 * @param fileName
	 * @return
	 
	 public final static Element getRootDocument(String fileName){
	 Element element;
	 //int numberfoElements=0;
	 String error="";//DConst.ERROR_XML_FILE;
	 try{
	 ReadXMLFile xmlFile = new ReadXMLFile();
	 Document  doc = xmlFile.getDocumentFile(fileName);
	 ReadXMLElement list= new ReadXMLElement();
	 element= list.getRootElement(doc);
	 }catch(Exception e){
	 System.out.println("Import file name :"+ e);
	 error+= e.toString();
	 return null;
	 }
	 return element;
	 }
	 */
	/**
	 * check if a tag exist in a xml element
	 * @param element
	 * @param tag
	 * @return
	 
	 public final static String tagError(Element element, String tag){
	 ReadXMLElement list= new ReadXMLElement();
	 String error="";
	 if(element==null)//{
	 return DConst.ERROR_XML_FILE;
	 //}else{
	 int size= list.getSize(element,tag);
	 if (size == 0){
	 error = DConst.ERROR_XML_FILE;
	 }
	 // }// end else if(element==null)

	 return error;

	 }
	 */
	/**
	 * return the number of occurence of an element repr�sent by its tag
	 * @param element
	 * @param tag
	 * @return
	 
	 public final static int tagSize(Element element, String tag){
	 ReadXMLElement list= new ReadXMLElement();
	 if (element==null)
	 return 0;
	 return list.getSize(element,tag);
	 }
	 */
}