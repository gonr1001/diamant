///**
// * Created on Mar 28, 2006
// * 
// * 
// * Title: DxPeriod.java 
// *
// * Copyright (c) 2001 by rgr.
// * All rights reserved.
// *
// *
// * This software is the confidential and proprietary information
// * of rgr. ("Confidential Information").  You
// * shall not disclose such Confidential Information and shall use
// * it only in accordance with the terms of the license agreement
// * you entered into with rgr.
// *
// * 
// * 
// */
//package dInternal.dTimeTable;
//
//import java.util.StringTokenizer;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//
//import dConstants.DConst;
//import dInternal.DResource;
//import dInternal.DSetOfResources;
//import dInternal.DValue;
//import dInternal.dData.StandardCollection;
//import dInternal.dOptimization.ConflictsAttach;
//import dInternal.dUtil.ArrayValue;
//import eLib.exit.xml.input.XMLReader;
//import eLib.exit.xml.output.XMLWriter;
//
///**
// * Ruben Gonzalez-Rubio
// * 
// * Description: DxPeriod is a class used to:
// * <p>
// * TODO:insert comments
// * <p>
// * 
// */
//public class DxPeriod {
//
//	private final int MINUTES = 60;
//
//	private int _nbStudentsConflicts;
//
//	private int _nbInstructorsConflicts = 0;
//
//	private int _nbRoomConflicts = 0;
//
//	private int[] _beginHour = { 8, 0 };// _beginHour[0]= hour; _beginHour[1]=
//										// minute
//
//	private int[] _endHour = { 9, 0 };// _endHour[0]= hour; _endHour[1]=
//										// minute
//
//	private int _priority;// 0= normal; 1= low; 2= null
//
//	private String _error = "";
//
//	/**
//	 * contains a resource where ID is the event which is place in the period,
//	 * and resource attach is a conflictsattach type
//	 */
//	private DSetOfResources _eventsInPeriod;
//
//	/**
//	 * Constructor
//	 */
//	public DxPeriod() {
//		super();
//		_eventsInPeriod = new StandardCollection();
//		_nbStudentsConflicts = 0;
//		_nbInstructorsConflicts = 0;
//		_nbRoomConflicts = 0;
//		_beginHour[0] = 8;
//		_beginHour[1] = 0;
//		_error = "";
//	}
//
//	/**
//	 * set the begin hour of the period
//	 * 
//	 * @param int
//	 *            the hour
//	 * @param int
//	 *            the minute
//	 */
//	public void setBeginHour(int hour, int minutes) {
//		_beginHour[0] = hour;
//		_beginHour[1] = minutes;
//	}
//
//	/**
//	 * set the priority of the periode
//	 * 
//	 * @param int
//	 *            the priority of the period
//	 */
//	public void setPriority(int prior) {
//		_priority = prior;
//	}
//
//	/**
//	 * get the begin hour of the period
//	 * 
//	 * @return int[2] the table of the begin hour. The in range 0 is the hour
//	 *         and the element in the range 1 is the minutes
//	 */
//	public int[] getBeginHour() {
//		return _beginHour;
//	}
//
//	/**
//	 * get the end hour of the period
//	 * 
//	 * @return int[2] the table of the begin hour. The in range 0 is the hour
//	 *         and the element in the range 1 is the minutes
//	 */
//	public int[] getEndHour() {
//		return _endHour;
//	}
//
//	/**
//	 * get number of students conflicts
//	 * 
//	 * @param int
//	 *            the number of students conflicts
//	 */
//	public int getNbStudConflict() {
//		return this._nbStudentsConflicts;
//	}
//
//	/**
//	 * add number of students conflicts
//	 * 
//	 * @param int
//	 *            the number of students conflicts
//	 */
//	public void addNbStudConflict(int conflict) {
//		this._nbStudentsConflicts += conflict;
//	}
//
//	/**
//	 * add number of rooms conflicts
//	 * 
//	 * @param int
//	 *            the number of rooms conflicts
//	 */
//	public void addNbRoomsConflict(int conflict) {
//		this._nbRoomConflicts += conflict;
//	}
//
//	/**
//	 * add number of instructors conflicts
//	 * 
//	 * @param int
//	 *            the number of instructors conflicts
//	 */
//	public void addNbInstructorsConflict(int conflict) {
//		this._nbInstructorsConflicts += conflict;
//	}
//
//	/**
//	 * remove number of instructors conflicts
//	 * 
//	 * @param int
//	 *            the number of instructors conflicts
//	 */
//	public void removeNbInstructorsConflict(int conflict) {
//		this._nbInstructorsConflicts -= conflict;
//	}
//
//	/**
//	 * remove number of students conflicts
//	 * 
//	 * @param int
//	 *            the number of students conflicts
//	 */
//	public void removeNbStudConflict(int conflict) {
//		this._nbStudentsConflicts -= conflict;
//	}
//
//	/**
//	 * remove number of rooms conflicts
//	 * 
//	 * @param int
//	 *            the number of rooms conflicts
//	 */
//	public void removeNbRoomsConflict(int conflict) {
//		this._nbRoomConflicts -= conflict;
//	}
//
//	/**
//	 * set number of instructors conflicts
//	 * 
//	 * @param int
//	 *            the number of instructors conflicts
//	 */
//	public void setNbInstructorsConflict(int conflict) {
//		this._nbInstructorsConflicts = conflict;
//	}
//
//	/**
//	 * set number of students conflicts
//	 * 
//	 * @param int
//	 *            the number of students conflicts
//	 */
//	public void setNbStudConflict(int conflict) {
//		this._nbStudentsConflicts = conflict;
//	}
//
//	/**
//	 * set number of rooms conflicts
//	 * 
//	 * @param int
//	 *            the number of rooms conflicts
//	 */
//	public void setNbRoomsConflict(int conflict) {
//		this._nbRoomConflicts = conflict;
//	}
//
//	/**
//	 * get number of instructors conflicts
//	 * 
//	 * @param int
//	 *            the number of instructors conflicts
//	 */
//	public int getNbInstConflict() {
//		return this._nbInstructorsConflicts;
//	}
//
//	/**
//	 * get number of rooms conflicts
//	 * 
//	 * @param int
//	 *            get number of rooms conflicts
//	 */
//	public int getNbRoomConflict() {
//		return this._nbRoomConflicts;
//	}
//
//	/**
//	 * get priority of the period
//	 * 
//	 * @param int
//	 *            the priority of the period
//	 */
//	public int getPriority() {
//		return this._priority;
//	}
//
//	/**
//	 * read a xml tag containing a period and build the resource
//	 * 
//	 * @param Element
//	 *            the root xml tag of a period
//	 */
//	public String readXMLtag(Element setPeriod) {
//		XMLReader list = new XMLReader();
//		// Period period = new Period();
//		String begin, end, prior;
//		begin = list.getElementValue(setPeriod, DConst.TTXML_BEGINTIME);
//		StringTokenizer time = new StringTokenizer(begin, ":");
//		end = list.getElementValue(setPeriod, DConst.TTXML_ENDTIME);
//		prior = list.getElementValue(setPeriod, DConst.TTXML_PRIORITY);
//		_beginHour[0] = Integer.parseInt(time.nextToken());
//		_beginHour[1] = Integer.parseInt(time.nextToken());
//		_priority = Integer.parseInt(prior);
//
//		try {
//			StringTokenizer timeend = new StringTokenizer(end, ":");
//			_endHour[0] = Integer.parseInt(timeend.nextToken());
//			_endHour[1] = Integer.parseInt(timeend.nextToken());
//		} catch (Exception e) {
//			_endHour[1] = (_beginHour[1] + 60) % MINUTES;//
//			_endHour[0] = _beginHour[0] + (_beginHour[1] + 60) / MINUTES;
//			// System.out.println(" Period -- no end time");
//			// e.printStackTrace();
//		}
//
//		if (begin == null || end == null || prior == null) {
//			_error = DConst.ERROR_XML;
//			return _error;
//		}
//		return _error;
//
//		// System.out.println(" Period properties -- begin:
//		// "+_beginHour[0]+"%"+_beginHour[1]+" end: "+end+" Priority:
//		// "+prior);//debug
//	}
//
//	/**
//	 * Contruct a xml element from this period
//	 * 
//	 * @param Document
//	 *            the root xml document
//	 * @Element the xml tag of this period
//	 */
//	public Element writeXMLtag(Document doc) {
//		XMLWriter xmlElt;
//		DConst.HourFormat.setMinimumIntegerDigits(2);
//		try {
//			xmlElt = new XMLWriter();
//			Element eltPer = xmlElt.createElement(doc, DConst.TTXML_TTPERIOD);
//			String time = DConst.HourFormat.format(_beginHour[0]) + ":"
//					+ DConst.HourFormat.format(_beginHour[1]);
//			Element child0 = xmlElt.createElement(doc, DConst.TTXML_BEGINTIME,
//					time);
//
//			String timeend = DConst.HourFormat.format(_endHour[0]) + ":"
//					+ DConst.HourFormat.format(_endHour[1]);
//			Element child1 = xmlElt.createElement(doc, DConst.TTXML_ENDTIME,
//					timeend);
//
//			Element child2 = xmlElt.createElement(doc, DConst.TTXML_PRIORITY,
//					Integer.toString(_priority));
//
//			eltPer = xmlElt.appendChildInElement(eltPer, child0);
//			eltPer = xmlElt.appendChildInElement(eltPer, child1);
//			eltPer = xmlElt.appendChildInElement(eltPer, child2);
//			return eltPer;
//		} catch (Exception e) {
//			System.out.println("Period: " + e);// debug
//			return null;
//		}
//	}
//
//	/**
//	 * return the number of events in this period
//	 */
//	public int getNumberOfEvents() {
//		return _eventsInPeriod.size();
//	}
//
//	/**
//	 * */
//	public DxPeriod clonePeriod() {
//		DxPeriod newPer = new DxPeriod();
//		newPer._nbStudentsConflicts = 0;
//		newPer._nbInstructorsConflicts = 0;
//		newPer._nbRoomConflicts = 0;
//		// TODO a revoir
//		// newPer._priority = this._priority;
//		// newPer._beginHour = this._beginHour;
//		return newPer;
//	}
//
//	/**
//	 * 
//	 * @return
//	 */
//	public DSetOfResources getEventsInPeriod() {
//		return _eventsInPeriod;
//	}
//
//	/**
//	 * Get conflicts between an event and the event placed in the period
//	 * 
//	 * @param String
//	 *            the event from which we need conflicts in period
//	 * @return SetOfRessources containing the conflicts as an attachment
//	 */
//	public DSetOfResources getConflictsEventsInPeriod(String event) {
//		DSetOfResources setOfConf = new StandardCollection();
//		int sizeIn = 0;
//		for (int i = 0; i < _eventsInPeriod.size(); i++) {
//			DResource eventInPeriod = _eventsInPeriod.getResourceAt(i);
//			String id = eventInPeriod.getID();
//			if (event.equalsIgnoreCase(eventInPeriod.getID())) {
//				setOfConf = ((ConflictsAttach) eventInPeriod.getAttach())
//						.getAllConflictsOfAnEvent(setOfConf);
//			} else {// else if
//					// (!event.equalsIgnoreCase(_eventsInPeriod.getResourceAt(i
//				sizeIn = setOfConf.size();
//				setOfConf = ((ConflictsAttach) eventInPeriod.getAttach())
//						.getAllConflictsOfAnEvent(setOfConf, event);
//				if (setOfConf.size() == sizeIn)
//					setOfConf.addResource(new DResource(id, new ArrayValue(3)),
//							1);
//			}// end else if
//				// (!event.equalsIgnoreCase(_eventsInPeriod.getResourceAt(i
//		}// end for (int i=0; i< _eventsInPeriod.size(); i++){
//
//		return builtSetOfRessources(setOfConf);
//	}
//
//	private DSetOfResources builtSetOfRessources(DSetOfResources setOfConf) {
//		DSetOfResources setOfRes = new StandardCollection();
//		for (int i = 0; i < setOfConf.size(); i++) {
//			ArrayValue array = (ArrayValue) setOfConf.getResourceAt(i)
//					.getAttach();
//			String id = setOfConf.getResourceAt(i).getID() + "  "
//					+ array.getIntArrayValue(0) + "  "
//					+ array.getIntArrayValue(1) + "  "
//					+ array.getIntArrayValue(2);
//			setOfRes.addResource(new DResource(id, new DValue()), 1);
//		}
//		return setOfRes;
//	}
//
//	/**
//	 * 
//	 */
//	public String toString() {
//		DConst.HourFormat.setMinimumIntegerDigits(2);
//		String str = DConst.HourFormat.format(_beginHour[0]) + ":"
//				+ DConst.HourFormat.format(_beginHour[1]) + " -- " + _priority;
//		return str;
//	}
//
//	/**
//	 * isEquals checks if this Period is equals to the Period gives in arg
//	 * 
//	 * @param per
//	 *            the Period arg
//	 * @return
//	 *            <p>
//	 *            true if this Period is equals to the Period gives in arg
//	 *            </p>
//	 *            false otherwise
//	 */
//	public boolean isEquals(Period per) {
//		if (_nbStudentsConflicts != per.getNbStudConflict())
//			return false;
//		if (_nbInstructorsConflicts != per.getNbInstConflict())
//			return false;
//		if (_nbRoomConflicts != per.getNbRoomConflict())
//			return false;
//		if (_beginHour[0] != per.getBeginHour()[0])
//			return false;
//		if (_beginHour[1] != per.getBeginHour()[1])
//			return false;
//		if (_priority != per.getPriority())
//			return false;
//
//		return true;
//	}
//
//	/**
//	 * 
//	 */
//	public void emptyEventsInPeriod() {
//		_eventsInPeriod = new StandardCollection();
//		_nbStudentsConflicts = 0;
//		_nbInstructorsConflicts = 0;
//		_nbRoomConflicts = 0;
//	}
//
//}
