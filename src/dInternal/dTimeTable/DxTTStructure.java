/**
 * Created on 13-Dec-07
 * 
 * 
 * Title: DxTTStructue.java
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Observable;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.XMLEventAllocator;

import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler; // import org.xml.sax.InputSource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.sun.xml.stream.events.XMLEventAllocatorImpl;

public class DxTTStructure extends Observable {

	private static XMLEventAllocator allocator = null;

	private static String SAX_PARSER = "org.apache.xerces.parsers.SAXParser";

	private static String FILE = "file:." + File.separator;

	private static String DXTTSTRUCTURE_SCHEMA_2_dot_2 = File.separator
			+ "schemas" + File.separator + "DxTimetable.xsd";

	public static String XML_HEADER = "?xml version=\"1.0\" encoding=\"UTF-8\" ?";

	public static String VALIDATION = "http://xml.org/sax/features/validation";

	public static String VALIDATION_SCHEMA = "http://apache.org/xml/features/validation/schema";

	public static String VALIDATION_SCHEMA_FULL = "http://apache.org/xml/features/validation/schema-full-checking";

	public static String EXTERNAL_SCHEMA_LOCATION = "http://apache.org/xml/properties/schema/external-schemaLocation";

	public static String D_INC = "http://www.dInc.com "; 
	// last space in D_INC is important!!!
	public static String NO_NAMESPACE = "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation";

	/**
	 * it loads the time table structure
	 * 
	 * @param String
	 *            the xml file containing the timetable structure
	 * @return String the error message, empty if it does not found error
	 */

	public void loadTTSFromFile(String fileName) throws FileNotFoundException,
			MalformedURLException, IOException, SAXException,
			XMLStreamException {

		//validateFile(fileName);
		parse(fileName);
	}

	private void validateFile(String fileName) throws SAXException,
			SAXNotRecognizedException, SAXNotSupportedException, IOException {

		// Create instances needed for validate
		XMLReader reader = XMLReaderFactory.createXMLReader(SAX_PARSER);
		TTStructureSAXContentHandler ttsContentHandler = new TTStructureSAXContentHandler(
				this);
		ErrorHandler ttsErrorHandler = new TTStructureSAXErrorHandler();
		EntityResolver eResolver = new TTStructureEntitySolver();
		// Register handlers
		reader.setContentHandler(ttsContentHandler);
		reader.setErrorHandler(ttsErrorHandler);
		reader.setEntityResolver(eResolver);

		// Turn on validation
		reader.setFeature(VALIDATION, true);
		reader.setFeature(VALIDATION_SCHEMA, true);
		reader.setFeature(VALIDATION_SCHEMA_FULL, true);

		String schemaFileName = getSchemaFileName();
		System.out.println(schemaFileName);
		reader.setProperty(EXTERNAL_SCHEMA_LOCATION, D_INC + FILE
				+ schemaFileName);
		reader.setProperty(NO_NAMESPACE, FILE + schemaFileName);
		InputSource inputSource = new InputSource(fileName);
		reader.parse(inputSource);
	}

	private void parse(String fileName) throws FactoryConfigurationError,
			XMLStreamException, FileNotFoundException {
		// try {
		XMLInputFactory xmlif = XMLInputFactory.newInstance();
		System.out.println("FACTORY: " + xmlif);

		xmlif.setEventAllocator(new XMLEventAllocatorImpl());
		allocator = xmlif.getEventAllocator();
		XMLStreamReader xmlr = xmlif.createXMLStreamReader(fileName,
				new FileInputStream(fileName));

		int eventType = xmlr.getEventType();
		int state = 0;
		while (xmlr.hasNext()) {
			eventType = xmlr.next();
			// Get all "Book" elements as XMLEvent object
			switch (state) {
			case 0:
				break; // test start state 1
			case 1:
				break; // test cycle state 2
			case 2:
				break; // test days state 3
			case 3:
				break; // test day state 4
			case 4:
				break; // test seqs state 5
			case 5:
				break; // test period state 6
			case 6:
				break; // test in period 7
			case 7:
				break; // test end period 8
			case 8:
				break; // test end period 7 or end seq 9
			case 9:
				break; // test end day 10 or seq
			case 10:
				break; // test end cycle 11 or day 3
			case 11:
				break; // test en tt
			}
			if (eventType == XMLStreamConstants.START_ELEMENT) {// &&
				// xmlr.getLocalName().equals("Book")){
				// get immutable XMLEvent
				StartElement sEvent = getXMLEvent(xmlr).asStartElement();
				System.out.println("EVENT sE: " + sEvent.toString());
			}
			chars(xmlr, eventType);
			if (eventType == XMLStreamConstants.END_ELEMENT) {// &&
				// xmlr.getLocalName().equals("Book")){
				// get immutable XMLEvent
				EndElement eEvent = getXMLEvent(xmlr).asEndElement();
				System.out.println("EVENT eE: " + eEvent.toString());
			}

		}

		// } catch (XMLStreamException ex) {
		// ex.printStackTrace();
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
	}

	/**
	 * it produces the SchemaFileName which is in the package
	 * 
	 * @return String SchemaFileName
	 */

	protected String getSchemaFileName() {
		String fullClassName = DxTTStructure.class.getCanonicalName();
		String SimpleName = DxTTStructure.class.getSimpleName();
		fullClassName = fullClassName.replace('.', File.separatorChar);
		int i = fullClassName.indexOf(SimpleName);
		String str = fullClassName.substring(0, i - 1);
		return str + DXTTSTRUCTURE_SCHEMA_2_dot_2;
	}

	/**
	 * @param xmlr
	 * @param eventType
	 * @throws XMLStreamException
	 */
	private static void chars(XMLStreamReader xmlr, int eventType)
			throws XMLStreamException {
		if (eventType == XMLStreamConstants.CHARACTERS) {// &&
			// xmlr.getLocalName().equals("Book")){
			// get immutable XMLEvent
			Characters chars = getXMLEvent(xmlr).asCharacters();
			if (chars.toString() != "")
				System.out.println("EVENT c: " + chars);
		}
	}

	/**
	 * Get the immutable XMLEvent from given XMLStreamReader using
	 * XMLEventAllocator
	 */
	private static XMLEvent getXMLEvent(XMLStreamReader reader)
			throws XMLStreamException {
		return allocator.allocate(reader);
	}

	//
	// public int getNumberOfActiveDays() {
	// return _numberOfDays;
	// }
	//
	// public String[] getWeekTable() {
	// return _weekTable;
	// }
	//
	//
	// public int getPeriodLenght() {
	// return _periodLenght;
	// }
	//
	// public DSetOfResources getSetOfCycles() {
	// return _setOfCycles;
	// }
	//
	// public String toWrite() {
	// return "";
	// }
	//
	// public String getError() {
	// return _error;
	// }
	//
	// /**
	// * */
	// public int getCurrentCycleIndex() {
	// return _currentCycleIndex;
	// }
	//
	// public Cycle getCurrentCycle() {
	// return (Cycle) _setOfCycles.getResourceAt(_currentCycleIndex)
	// .getAttach();
	// }
	//
	// public DResource getCurrentCycleResource() {
	// return _setOfCycles.getResourceAt(_currentCycleIndex);
	// }
	//
	// public void setCurrentCycleIndex(int curCyc) {
	// _currentCycleIndex = curCyc;// _currentCycleIndex = curCyc;
	// }
	//
	// /**
	// * Create and save a standard TimeTable
	// *
	// * @param String
	// * the timetable file name
	// * @param int
	// * the number of cycles
	// * @param int
	// * the number of days in each cycle
	// * @return boolean the result of the operation
	// */
	// public boolean createDefaultTT(String fileName, int nbOfCycles, int
	// nbOfDays) {
	// XMLWriter wr;
	// try {
	// wr = new XMLWriter();
	// Document doc = wr.getNewDocument();
	// Element eltTT = wr.createElement(doc, ITEM2);
	// Element eltCycle;
	// Element eltDays;
	// Element eltDay;
	// Element eltSeqs;
	// Element eltSeq;
	// for (int cyc = 0; cyc < nbOfCycles; cyc++) {
	// eltCycle = wr.createElement(doc, ITEM2_subTag[0]); // XXXX
	// // Pascal:
	// // Magic
	// // numbers
	// eltDays = wr.createElement(doc, ITEM2_subTag[1]);
	// for (int day = 0; day < nbOfDays; day++) {
	// eltDay = wr.createElement(doc, ITEM2_subTag[2]);
	// eltSeqs = wr.createElement(doc, ITEM2_subTag[3]);
	//
	// // add AM periods
	// int[] beginT = { 8, 15 }; // XXXX Pascal: Magic numbers
	// eltSeq = CreateSeqPeriods(doc, "AM", 4, 60, beginT, 0);
	// eltSeqs = wr.appendChildInElement(eltSeqs, eltSeq);
	// // add PM periods
	// beginT[0] = 13;
	// beginT[1] = 30;
	// eltSeq = CreateSeqPeriods(doc, "PM", 5, 60, beginT, 0);
	// eltSeqs = wr.appendChildInElement(eltSeqs, eltSeq);
	// // add Evening periods
	// beginT[0] = 19;
	// beginT[1] = 00;
	// eltSeq = CreateSeqPeriods(doc, "EM", 3, 60, beginT, 1);
	// eltSeqs = wr.appendChildInElement(eltSeqs, eltSeq);
	//
	// // add sequences in a day
	// eltDay = wr.appendChildInElement(eltDay, eltSeqs);
	// Element childDay = wr.createElement(doc, ITEM2_subConst[2],
	// Integer.toString(day + 1));
	// String dayID = _weekTable[day % NUMBEROFACTIVESDAYS];
	// Element childDayID = wr.createElement(doc,
	// ITEM2_subConst[8], dayID);
	// eltDay = wr.appendChildInElement(eltDay, childDay);
	// eltDay = wr.appendChildInElement(eltDay, childDayID);
	// // eltDays= wr.appendChildInElement(eltDays, childDay);
	// eltDays = wr.appendChildInElement(eltDays, eltDay);
	// }// end for (day)
	// Element childCycle = wr.createElement(doc, ITEM2_subConst[0],
	// Integer.toString(cyc + 1));
	// eltCycle = wr.appendChildInElement(eltCycle, childCycle);
	// childCycle = wr.createElement(doc, ITEM2_subConst[1], Integer
	// .toString(60));
	// eltCycle = wr.appendChildInElement(eltCycle, childCycle);
	// eltCycle = wr.appendChildInElement(eltCycle, eltDays);
	// eltTT = wr.appendChildInElement(eltTT, eltCycle);
	// }// for (int cyc=0; cyc<3; cyc++)
	//
	// // create document and write in the file
	// doc = wr.buildDocument(doc, eltTT);
	// XMLOutputFile xmlOF = new XMLOutputFile();
	// xmlOF.write(doc, fileName);
	// return true;
	// } catch (Exception e) {
	// System.out.println("TTStructure: " + e);// debug
	// return false;
	// }
	// }// end of CreateStandardTT method
	//

	//
	// /**
	// * it load the time table structure
	// *
	// * @param String
	// * the xml file containing the timetable structure
	// * @return String the error message, empty if it does not found error
	// */
	//
	// public void loadTTStructFromFile(String fileName) throws Exception {
	// XMLInputFile xmlFile;
	// Element root;
	//
	// xmlFile = new XMLInputFile();
	// Document doc = xmlFile.createDocument(fileName);
	// XMLReader list = new XMLReader();
	// root = list.getRootElement(doc);
	// readXMLTTTag(root);
	//
	// } // loadTTStructFromFile
	//
	// /**
	// * it load the time table structure
	// *
	// * @param String
	// * the xml file containing the timetable structure
	// * @return String the error message, empty if it does not found error
	// */
	//
	// public String loadTTSFromString(String str) {
	// Element root;
	// try {
	//
	// DocumentBuilderFactory factory = DocumentBuilderFactory
	// .newInstance();
	// DocumentBuilder builder = factory.newDocumentBuilder();
	// ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes());
	// Document document = builder.parse(bais);
	// XMLReader list = new XMLReader();
	// root = list.getRootElement(document);
	// if (readXMLtag(root).length() != 0) {
	// _error = DConst.ERROR_XML_FILE;
	// // return _error;
	// }
	// } catch (Exception e) {
	// System.out.println("TTStructure 1 :" + e);
	// _error = e.toString();
	// }
	// return _error;
	// // return null; Cannot return null, causes error because caller verifies
	// // error length to determine if function caused an error. Could also
	// // modify callers to verify if return is null instead
	// }
	//
	// /**
	// * it set the time table structure
	// *
	// * @param Document
	// * doc the document containing the timetable structure
	// * @return String the error message, empty if it does not found error
	// */
	// public String setTTStructureDocument(Document doc) {
	// Element root;
	// try {
	// XMLReader list = new XMLReader();
	// root = list.getRootElement(doc);
	// if (readXMLtag(root).length() != 0) {
	// _error = DConst.ERROR_XML_FILE;
	// return _error;
	// }
	// } catch (Exception e) {
	// System.out.println("TTStructure 2:" + e);
	// _error = e.toString();
	// return e.toString();
	// }
	// return _error;
	// }
	//
	// /**
	// * it save the time table structure
	// *
	// * @param String
	// * the xml file where the timetable structure must be saved
	// * @return String the error message, empty if it does not found error
	// */
	// public String saveTTStructure(String fileName) {
	// XMLWriter wr;
	// try {
	// wr = new XMLWriter();
	// Document doc = wr.getNewDocument();
	// Element ttStruc = writeXMLtag(doc);
	// // create document and write in the file
	// doc = wr.buildDocument(doc, ttStruc);
	// XMLOutputFile xmlOF = new XMLOutputFile();
	// xmlOF.write(doc, fileName);
	// _modified = false;
	// return "";
	// } catch (Exception e) {
	// return e.toString();// debug
	// }
	//
	// }
	//
	// /**
	// * it get the time table structure
	// *
	// * @return Document doc the document containing the timetable structure
	// */
	// public Document getTTStructureDocument() {
	// XMLWriter wr;
	// try {
	// wr = new XMLWriter();
	// Document doc = wr.getNewDocument();
	// Element ttStruc = writeXMLtag(doc);
	// // create document and write in the file
	// doc = wr.buildDocument(doc, ttStruc);
	// return doc;
	// } catch (Exception e) {
	// return null;// debug
	// }
	//
	// }
	//
	// /**
	// * read a xml tag containing a set of cycle and build the resource
	// *
	// * @param Element
	// * the root xml tag of the set of cycle
	// */
	//
	// public String readXMLtag(Element setofCycles) {
	// XMLReader list = new XMLReader();
	// String ID = "";
	// int size = list.getSize(setofCycles, _TAG_TT_CYCLE);
	// if (size == 0) {
	// _error = DConst.ERROR_XML_FILE;
	// return _error;
	// }
	// for (int i = 0; i < size; i++) {
	// Cycle setOfdays = new Cycle();
	// Element cycle = list.getElement(setofCycles, _TAG_TT_CYCLE, i);
	// ID = list.getElementValue(cycle, _TAGITEM1);
	// _periodLenght = Integer.parseInt(list.getElementValue(cycle,
	// _TAGITEM2));
	// Element days = list.getElement(cycle, _TAGITEM3, 0);
	//
	// if (!setOfdays.readXMLtag(days).equals("")) {
	// _error = DConst.ERROR_XML_FILE;
	// return _error;
	// }
	// _numberOfDays = setOfdays.getNumberOfDays();
	// _setOfCycles.addResource(new DResource(ID, setOfdays), 0);
	// }// end for (int i=0; i< size; i++)
	// return _error;
	// }
	//
	// /**
	// * read a xml tag containing a set of cycle and build the resource
	// *
	// * @param Element
	// * the root xml tag of the set of cycle
	// */
	//
	// public void readXMLTTTag(Element setofCycles) throws Exception {
	// XMLReader list = new XMLReader();
	// String ID = "";
	// int size = list.getSize(setofCycles, _TAG_TT_CYCLE);
	// // if (size == 0) {
	// // _error = DConst.ERROR_XML_FILE;
	// // //return _error;
	// // }
	// for (int i = 0; i < size; i++) {
	// Cycle setOfdays = new Cycle();
	// Element cycle = list.getElement(setofCycles, _TAG_TT_CYCLE, i);
	// ID = list.getElementValue(cycle, _TAGITEM1);
	// _periodLenght = Integer.parseInt(list.getElementValue(cycle,
	// _TAGITEM2));
	//
	// Element days = list.getElement(cycle, _TAGITEM3, 0);
	// setOfdays.readXMLTTTag(days);
	// _numberOfDays = setOfdays.getNumberOfDays();
	// _setOfCycles.addResource(new DResource(ID, setOfdays), 0);
	// }// end for (int i=0; i< size; i++)
	// // return _error;
	// }
	//
	// /**
	// * Contruct a xml element from the set of cycles
	// *
	// * @param Document
	// * the root xml document
	// * @Element the xml tag of the set of cycles
	// */
	// public Element writeXMLtag(Document doc) {
	// XMLWriter xmlElt;
	// try {
	// xmlElt = new XMLWriter();
	// Element eltCycles = xmlElt.createElement(doc, TTStructure.ITEM2);
	// for (int i = 0; i < _setOfCycles.size(); i++) {
	// Element eltCycle = xmlElt.createElement(doc, _TAG_TT_CYCLE);
	// Element cycle = ((Cycle) _setOfCycles.getResourceAt(i)
	// .getAttach()).writeXMLtag(doc);
	// Element cycleID = xmlElt.createElement(doc, _TAGITEM1,
	// _setOfCycles.getResourceAt(i).getID());
	// Element cyclePLength = xmlElt.createElement(doc, _TAGITEM2,
	// Integer.toString(_periodLenght));
	// eltCycle = xmlElt.appendChildInElement(eltCycle, cycle);
	// eltCycle = xmlElt.appendChildInElement(eltCycle, cycleID);
	// eltCycle = xmlElt.appendChildInElement(eltCycle, cyclePLength);
	// eltCycles = xmlElt.appendChildInElement(eltCycles, eltCycle);
	// }
	// return eltCycles;
	// } catch (Exception e) {
	// System.out.println("SetOfCycle: " + e);// debug
	// return null;
	// }
	// }
	//
	// /**
	// *
	// * @param ID
	// * @return
	// */
	// public int findIndexInWeekTable(long key) {
	// DResource day = this.getCurrentCycle().getSetOfDays().getResource(key);
	// if (day != null) {
	// for (int i = 0; i < _weekTable.length; i++)
	// if (day.getID().equalsIgnoreCase(_weekTable[i]))
	// return i;
	// }
	// return -1;
	// }
	//
	// /**
	// * Create a sequence of periods
	// *
	// * @param Document
	// * the XML document where we are working
	// * @param String
	// * String the sequence ID (AM, PM or EM= evening)
	// * @param int
	// * the number of periods in the sequence
	// * @param int
	// * the length of each period in the sequence
	// * @param int[2]
	// * the begin time of the period. the first element of the table
	// * is the our, and the second is the minutes
	// * @param int
	// * the priority of each period
	// * @return Element the sequence element
	// */
	// private Element CreateSeqPeriods(Document doc, String seqID,
	// int nbOfPeriods, int periodLenght, int[] beginTime, int priority) {
	// // add PM periods
	// XMLWriter xmlElt;
	// try {
	// xmlElt = new XMLWriter();
	// Element eltSeq = xmlElt.createElement(doc, ITEM2_subTag[4]);
	// Element eltPers = xmlElt.createElement(doc, ITEM2_subTag[5]);
	// int hour = beginTime[0];
	// for (int i = 0; i < nbOfPeriods; i++) {
	// int mn = (beginTime[1] + periodLenght * i) % 60;//
	// hour = beginTime[0] + (beginTime[1] + periodLenght * i) / 60;
	// String time = hour + ":" + mn;
	// Element child0 = xmlElt.createElement(doc, ITEM2_subConst[5],
	// time);
	// int mn1 = (mn + periodLenght) % 60;//
	// int hour1 = hour + (mn + periodLenght) / 60;
	// time = hour1 + ":" + mn1;
	// Element child01 = xmlElt.createElement(doc, ITEM2_subConst[6],
	// time);
	// Element child1 = xmlElt.createElement(doc, ITEM2_subConst[4],
	// Integer.toString(priority));
	// Element child2 = xmlElt.createElement(doc, ITEM2_subConst[7],
	// Integer.toString(i + 1));//
	// Element eltPer = xmlElt.createElement(doc, ITEM2_subTag[6]);
	// eltPer = xmlElt.appendChildInElement(eltPer, child2);
	// eltPer = xmlElt.appendChildInElement(eltPer, child0);
	// eltPer = xmlElt.appendChildInElement(eltPer, child01);
	// eltPer = xmlElt.appendChildInElement(eltPer, child1);
	// eltPers = xmlElt.appendChildInElement(eltPers, eltPer);
	// }
	// Element childSeq = xmlElt.createElement(doc, ITEM2_subConst[3],
	// seqID);
	// eltSeq = xmlElt.appendChildInElement(eltSeq, childSeq);
	// eltSeq = xmlElt.appendChildInElement(eltSeq, eltPers);
	//
	// return eltSeq;
	// } catch (Exception e) {
	// System.out.println("TTStructure: " + e);// debug
	// return null;
	// }
	// }
	//
	// /**
	// * isEquals checks if this TTStructure is equals to the TTStructure gives
	// in
	// * arg
	// *
	// * @param tts
	// * the TTStructure arg
	// * @return
	// * <p>
	// * true if this TTStructure is equals to the TTStructure gives in
	// * arg
	// * </p>
	// * false otherwise
	// */
	// public boolean isEquals(TTStructure tts) {
	// for (int i = 0; i < _setOfCycles.size(); i++) {
	// DResource cycleR = _setOfCycles.getResourceAt(i);
	// DResource cycleCloneR = tts.getSetOfCycles().getResourceAt(i);
	// if (!cycleR.getID().equalsIgnoreCase(cycleCloneR.getID()))
	// return false;
	// if (!cycleR.getAttach().isEquals(cycleCloneR.getAttach()))
	// return false;
	// }
	// return true;
	// }
	//
	// /**
	// * cloneCurrentTTSruct
	// *
	// * @param dm
	// * @return TTStructure containing the values of the TTStructure in dm
	// */
	// public TTStructure cloneCurrentTTS() {
	// TTStructure ttStruct = new TTStructure();
	// ttStruct.setTTStructureDocument(getTTStructureDocument());
	// return ttStruct;
	// }
	//
	// public String[] getDayNames() {
	// Cycle cTemp = (Cycle) _setOfCycles.getResourceAt(_currentCycleIndex)
	// .getAttach();
	// String[] sReturn = new String[cTemp.getNumberOfDays()];
	// for (int i = 0; i < cTemp.getNumberOfDays(); i++) {
	// sReturn[i] = new String(cTemp.getSetOfDays().getResourceAt(i)
	// .getID());
	// }
	// return sReturn;
	// }
	//
	// public void changeInTTStructure(Object obj) {
	// this.setModified();
	// this.setChanged();
	// this.notifyObservers(obj);
	// this.clearChanged();
	// }
	//
	// public boolean isModified() {
	// return _modified;
	// }
	//
	// /**
	// *
	// * @return
	// */
	// private void setModified() {
	// _modified = true;
	// }
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("TTStructure");

		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println("hi!");
		String RESOURCES_FOLDER = "pref";
		DxTTStructure tts = new DxTTStructure();
		try {
			tts.loadTTSFromFile(RESOURCES_FOLDER + "/ntest.xml"); // "/StandardTTE.xml");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();

		}

		System.out.println(tts.toString());
		System.out.println("bye");
		System.exit(0);
	} // end main

} // end TTStructure
