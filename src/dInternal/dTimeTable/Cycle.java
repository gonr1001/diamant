/**
 *
 * Title: Cycle 
 * Description: Cycle
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

import java.util.StringTokenizer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import dConstants.DConst;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DObject;
import dInternal.dData.StandardCollection;
import dInternal.dUtil.DXToolsMethods;
import dInternal.DValue;
import eLib.exit.xml.input.XMLReader;
import eLib.exit.xml.output.XMLWriter;

public class Cycle extends DObject {

	private DSetOfResources _setOfDays;

	// private int _periodLength;
	private String _error = "";

	private int _currentDayIndex;

	private String _errorMessage = "XML file is corrupted";

	// private Day _currentDay;
	// private int _currentDayIndex=0;

	public Cycle() {
		_setOfDays = new StandardCollection();
		_currentDayIndex = 0;
	}

	/**
	 * 
	 */
	public void addDays(int nbDays) {
		int size = _setOfDays.size();
		DResource day = _setOfDays.getResourceAt(size - 1);
		String lastDayID = day.getID();
		int lastIndWeek = 0;
		for (int i = 0; i < TTStructure._weekTable.length; i++)
			if (TTStructure._weekTable[i].equalsIgnoreCase(lastDayID)) {
				lastIndWeek = i;
				break;
			}
		for (int i = size; i < (size + nbDays); i++) {
			lastIndWeek++;
			String dayID = TTStructure._weekTable[lastIndWeek
					% TTStructure.NUMBEROFACTIVESDAYS];
			_setOfDays.setCurrentKey(i + 1);
			_setOfDays.addResource(new DResource(dayID, ((Day) day.getAttach())
					.cloneDay()), 0);
		}
	}

	/**
	 * 
	 */
	public void removeDays(int nbDays) {
		int size = _setOfDays.size();
		// Day day= (Day)_setOfDays.getResourceAt(size-1).getAttach();
		for (int i = size; i > (size - nbDays); i--) {
			// String dayID=
			// TTStructure._weekTable[i%TTStructure.getNumberOfActiveDays()];
			// _setOfDays.setCurrentKey(i+1);
			// _setOfDays.addResource(new Resource(dayID,day),0);
			_setOfDays.removeResourceAt(i - 1);
		}
	}

	/**
	 * get the set of days
	 * 
	 * @return SetOfResources the set of days
	 */
	public DSetOfResources getSetOfDays() {
		return _setOfDays;
	}

	/**
	 * set the set of days
	 * 
	 * @param SetOfResources
	 *            the set of days
	 */
	public void setSetOfDays(DSetOfResources setOfDays) {
		_setOfDays = setOfDays;
	}

	/**
	 * get the number of days in a cycle
	 * 
	 * @param Cycle
	 *            the cycle where we want to find the number of days
	 * @return int the number of days
	 */
	public int getNumberOfDays() {
		return _setOfDays.size();
	}
	
	public int getNumberOfPeriods() {
		return  this.getNumberOfDays()
		* this.getMaxNumberOfPeriodsADay();
	}

	/**
	 * 
	 * @param dayIndex
	 * @return
	 */
	public Day getDayByIndex(int dayIndex) {
		if (dayIndex < _setOfDays.size())
			return (Day) _setOfDays.getResourceAt((dayIndex)).getAttach();
		return null;
	}

	/**
	 * */
	public Day getCurrentDay() {
		return getDayByIndex(_currentDayIndex);
	}

	/**
	 * */
	/*
	 * public Day getFistDay(){ return getDayByIndex(0) ; }
	 */

	/**
	 * */
	public int getCurrentDayIndex() {
		return _currentDayIndex;
	}

	/**
	 * 
	 * @param curDayIndex
	 */
	public void setCurrentDayIndex(int curDayIndex) {
		_currentDayIndex = curDayIndex;
	}

	/**
	 * 
	 * @param curDayIndex
	 * @param curSeqIndex
	 * @param curPerIndex
	 */
	public void setCurrentDaySeqPerIndex(int curDayIndex, int curSeqIndex,
			int curPerIndex) {
		_currentDayIndex = curDayIndex;
		getCurrentDay().setCurrentSequenceIndex(curSeqIndex);
		getCurrentDay().getCurrentSequence().setCurrentPeriodIndex(curPerIndex);
	}

	/**
	 * read a xml tag containing a set of days and build the resource
	 * 
	 * @param Element
	 *            the root xml tag of the set of days
	 */
	public String readXMLtag(Element setofDays) {
		XMLReader list = new XMLReader();
		String ID = "";
		String key = "";
		int size = list.getSize(setofDays, DConst.TTXML_TTDAY);
		if (size == 0) {
			_error = _errorMessage;
			return _error;
		}
		for (int i = 0; i < size; i++) {
			Day setOfSequences = new Day();
			Element day = list.getElement(setofDays, DConst.TTXML_TTDAY, i);
			ID = list.getElementValue(day, DConst.TTXML_DAYID);
			key = list.getElementValue(day, DConst.TTXML_DAYREF);
			Element sequences = list.getElement(day, DConst.TTXML_TTSEQUENCES,
					0);
			if (!setOfSequences.readXMLtag(sequences).equals("")) {
				_error = _errorMessage;
				return _error;
			}
			_setOfDays.setCurrentKey(Integer.parseInt(key));
			_setOfDays.addResource(new DResource(ID, setOfSequences), 0);
		}// end for (int i=0; i< size; i++)
		return _error;
	}

	/**
	 * read a xml tag containing a set of days and build the resource
	 * 
	 * @param Element
	 *            the root xml tag of the set of days
	 */
	public void readXMLTTTag(Element setofDays) throws Exception {
		XMLReader list = new XMLReader();
		String ID = "";
		String key = "";
		int size = list.getSize(setofDays, DConst.TTXML_TTDAY);
		// if (size == 0) {
		// _error = _errorMessage;
		// return _error;
		// }
		for (int i = 0; i < size; i++) {
			Day setOfSequences = new Day();
			Element day = list.getElement(setofDays, DConst.TTXML_TTDAY, i);
			ID = list.getElementValue(day, DConst.TTXML_DAYID);
			key = list.getElementValue(day, DConst.TTXML_DAYREF);
			Element sequences = list.getElement(day, DConst.TTXML_TTSEQUENCES,
					0);
			setOfSequences.readXMLTTTag(sequences);
			// if (!setOfSequences.readXMLtag(sequences).equals("")) {
			// _error = _errorMessage;
			// return _error;
			// }
			_setOfDays.setCurrentKey(Integer.parseInt(key));
			_setOfDays.addResource(new DResource(ID, setOfSequences), 0);
		}// end for (int i=0; i< size; i++)
		// return _error;
	}

	/**
	 * Contruct a xml element from the set of days
	 * 
	 * @param Document
	 *            the root xml document
	 * @Element the xml tag of the set of days
	 */
	public Element writeXMLtag(Document doc) {
		XMLWriter xmlElt;
		try {
			xmlElt = new XMLWriter();
			Element eltDays = xmlElt.createElement(doc, DConst.TTXML_TTDAYS);
			for (int i = 0; i < _setOfDays.size(); i++) {
				Element eltDay = xmlElt.createElement(doc, DConst.TTXML_TTDAY);
				Element day = ((Day) _setOfDays.getResourceAt(i).getAttach())
						.writeXMLtag(doc);
				Element dayID = xmlElt.createElement(doc, DConst.TTXML_DAYID,
						_setOfDays.getResourceAt(i).getID());
				Element dayKey = xmlElt.createElement(doc, DConst.TTXML_DAYREF,
						Integer.toString((int) _setOfDays.getResourceAt(i)
								.getKey()));
				eltDay = xmlElt.appendChildInElement(eltDay, day);
				eltDay = xmlElt.appendChildInElement(eltDay, dayID);
				eltDay = xmlElt.appendChildInElement(eltDay, dayKey);
				eltDays = xmlElt.appendChildInElement(eltDays, eltDay);
			}
			return eltDays;
		} catch (Exception e) {
			System.out.println("Cycle: " + e);// debug
			return null;
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getError() {
		return _error;
	}

	/**
	 * get the first period
	 * 
	 * @param Cycle
	 *            the cycle where we want to find the period
	 * @return Period the first period
	 */
	public Period getFirstPeriod() {
		// int maxPer=0;
		Day day = (Day) this._setOfDays.getResourceAt(0).getAttach();// cycle.getSetOfDays().getResource(1).getAttach();
		if (day != null) {
			Sequence seq = (Sequence) day.getSetOfSequences().getResourceAt(0)
					.getAttach();
			return (Period) seq.getSetOfPeriods().getResourceAt(0).getAttach();
		}
		return null;
	}

	/**
	 * get a day in a cycle
	 * 
	 * @param Cycle
	 *            the cycle where we want to find a day
	 * @param int
	 *            the day reference number
	 * @return Day the day or null if the day does not found
	 */
	public Day getDayByRefNo(int dayRefNo) {
		return (Day) this.getSetOfDays().getResource(dayRefNo).getAttach();
	}

	/**
	 * get the last period
	 * 
	 * @param Cycle
	 *            the cycle where we want to find the period
	 * @return Period the last period
	 */
	public Period getLastPeriod() {
		// int maxPer=0;
		Period lastPer = new Period();
		for (int i = 0; i < this.getNumberOfDays(); i++) {
			Day day = (Day) this.getSetOfDays().getResourceAt(i).getAttach();
			Sequence seq = (Sequence) day.getSetOfSequences().getResourceAt(
					getMaxNumberOfSeqs() - 1).getAttach();
			Period per = (Period) seq.getSetOfPeriods().getResourceAt(
					seq.getSetOfPeriods().size() - 1).getAttach();
			if (compareTabsHour(lastPer.getBeginHour(), per
					.getBeginHour()) == -1)
				lastPer = per;
			// return
			// (Period)seq.getSetOfPeriods().getResourceAt(seq.getSetOfPeriods().size()-1).getAttach();
		}// end for (int i=0; i< cycle.getNumberOfDays(); i++)
		return lastPer;
	}

	/**
	 * compare two hour tables of int. tab[0] is the hour and tab[1] is the
	 * minute
	 * 
	 * @param int[2]
	 *            tab1 the first table of integer
	 * @param int[2]
	 *            tab2 the second table of integer
	 * @return int "1" if tab1 is greater than tab2, "0" if tab1 and tab2 are
	 *         equals, "-1" if tab1 is smaller than tab2
	 */
	private int compareTabsHour(int[] tab1, int[] tab2) {
		if ((tab1.length == 2) && (tab2.length == 2)) {
			if (tab1[0] >= tab2[0]) {
				if (tab1[0] > tab2[0]) {
					return 1;
				}// else{// else if(tab1[0]>tab2[0])
				if (tab1[1] > tab2[1]) {
					return 1;
				}// end if(tab1[1]>tab2[1])
				if (tab1[1] == tab2[1]) {
					return 0;
				}// end if(tab1[1]==tab2[1])
				if (tab1[1] < tab2[1]) {
					return -1;
				}// end if(tab1[1]<tab2[1])
				// }// end else if(tab1[0]>tab2[0])
			}// end if(tab1[0]>tab2[0])
		}// end if((tab1.length==2) && (tab2.length==2))
		return -1;
	}
	/**
	 * get the max number of sequences in one day in a cycle
	 * 
	 * @param Cycle
	 *            the cycle where we want to find the max number of sequences
	 * @return int the max number of sequences in a day
	 */
	public int getMaxNumberOfSeqs() {
		int seq = 0;
		for (int i = 0; i < getSetOfDays().size(); i++) {
			Day day = (Day) getSetOfDays().getResourceAt(i).getAttach();
			if (seq < day.getSetOfSequences().size())
				seq = day.getSetOfSequences().size();
		}
		return seq;
	}

	/**
	 * get the hour of periods in one day in a cycle
	 * 
	 * @param Cycle
	 *            the cycle where we want to find the max number of sequences
	 * @return int the max number of periods in a day
	 */
	public String[] getHourOfPeriodsADay() {
		String[] time = new String[getMaxNumberOfPeriodsADay()
				+ getMaxNumberOfSeqs() - 1];

		// int maxPer=0;
		Day day = getCurrentDay();

		int inc = 0;
		for (int i = 0; i < day.getSetOfSequences().size(); i++) {
			Sequence seq = (Sequence) day.getSetOfSequences().getResourceAt(i)
					.getAttach();
			Period per = new Period();
			for (int j = 0; j < seq.getSetOfPeriods().size(); j++) {
				per = (Period) seq.getSetOfPeriods().getResourceAt(j)
						.getAttach();
				time[inc] = per.getBeginHour()[0] + ":" + per.getBeginHour()[1];
				inc++;
			}
		}
		return time;
	}

	/**
	 * get the max number of periods in one day in a cycle
	 * 
	 * @param Cycle
	 *            the cycle where we want to find the max number of sequences
	 * @return int the max number of periods in a day
	 */
	public int getMaxNumberOfPeriodsADay() {
		int maxPer = 0;
		for (int i = 0; i < getSetOfDays().size(); i++) {
			Day day = (Day) getSetOfDays().getResourceAt(i).getAttach();
			int inc = 0;
			for (int j = 0; j < day.getSetOfSequences().size(); j++) {
				Sequence seq = (Sequence) day.getSetOfSequences()
						.getResourceAt(j).getAttach();
				inc += seq.getSetOfPeriods().size();
			}
			if (maxPer < inc)
				maxPer = inc;
		}
		return maxPer;
	}

	/**
	 * get the number of periods before the day dKey in a cycle
	 * 
	 * @param dKey
	 *            the current day
	 * @return int the max number of periods in a day lgd: Correction du bug 94
	 */
	public int getBefNumberOfPeriodsADay(long dKey) {
		int befPer = 0;
		for (int i = 0; i < dKey - 1; i++) {
			Day day = (Day) getSetOfDays().getResourceAt(i).getAttach();
			for (int j = 0; j < day.getSetOfSequences().size(); j++) {
				Sequence seq = (Sequence) day.getSetOfSequences()
						.getResourceAt(j).getAttach();
				befPer += seq.getSetOfPeriods().size();
			}
		}
		return befPer;
	}

	/**
	 * get the max number of periods in a sequence in a cycle
	 * 
	 * @param Cycle
	 *            the cycle where we want to find the max number of sequences
	 * @return int the max number of periods in a day
	 */
	public int getMaxNumberOfPeriodsInASequence() {
		int maxPer = 0;
		for (int i = 0; i < getSetOfDays().size(); i++) {
			Day day = (Day) getSetOfDays().getResourceAt(i).getAttach();
			int inc = 0;
			for (int j = 0; j < day.getSetOfSequences().size(); j++) {
				Sequence seq = (Sequence) day.getSetOfSequences()
						.getResourceAt(j).getAttach();
				inc = seq.getSetOfPeriods().size();
				if (maxPer < inc)
					maxPer = inc;
			}// end for (int j=0; j< day.getSetOfSequences().size(); j++)
		}// end for(int i=0; i< getSetOfDays().size(); i++)
		return maxPer;
	}

	/**
	 * get a period
	 * 
	 * @param Cycle
	 *            the cycle where we want to find the period
	 * @param int
	 *            the day reference number where we want to find the period
	 * @param int
	 *            the sequence reference number where we want to find the period
	 * @param int
	 *            the index of the period int the sequence
	 * @return Period the period
	 */
	public Period getPeriodByPeriodKey(String str) {
		int dayIndex;
		int seqIndex;
		int perIndex;
		StringTokenizer st = new StringTokenizer(str, ".");
		dayIndex = Integer.parseInt(st.nextToken());
		seqIndex = Integer.parseInt(st.nextToken());
		perIndex = Integer.parseInt(st.nextToken());

		return getPeriodByIndex(dayIndex - 1, seqIndex - 1, perIndex - 1);
	}

	/**
	 * 
	 * @param dayIndex
	 * @param seqIndex
	 * @param perIndex
	 * @return
	 */
	public Period getPeriodByIndex(int dayIndex, int seqIndex, int perIndex) {
		Day day = (Day) getSetOfDays().getResourceAt(dayIndex).getAttach();
		if (day != null) {
			Sequence seq = (Sequence) day.getSetOfSequences().getResourceAt(
					seqIndex).getAttach();
			if (seq != null) {
				return (Period) seq.getSetOfPeriods().getResourceAt(perIndex)
						.getAttach();
			}
		}
		return null;
	}

	/**
	 * get a period
	 * 
	 * @param Cycle
	 *            the cycle where we want to find the period
	 * @param int
	 *            the day reference number where we want to find the period
	 * @param int
	 *            the sequence reference number where we want to find the period
	 * @param int
	 *            the index of the period int the sequence
	 * @return Period the period
	 */
	public Period getPeriodByKey(long dayKey, long seqKey, long perKey) {
		// System.out.println(dayKey+" " +seqKey+" " + perKey);
		Day day = (Day) getSetOfDays().getResource(dayKey).getAttach();
		if (day != null) {
			Sequence seq = (Sequence) day.getSetOfSequences().getResource(
					seqKey).getAttach();
			if (seq != null) {

				DResource rperiod = seq.getSetOfPeriods().getResource(perKey);
				if (rperiod != null)
					return (Period) rperiod.getAttach();

				// else
				// ;//System.out.println(dayKey+" " +seqKey+" " + perKey);

			}
		}
		return null;
	}

	/**
	 * get a period position in a day
	 * 
	 * @param Cycle
	 *            the cycle where we want to find the period
	 * @param int
	 *            the day reference number where we want to find the period
	 * @param int
	 *            the sequence reference number where we want to find the period
	 * @param int
	 *            the index of the period int the sequence
	 * @return int the period pesition
	 */
	public int getPeriodPositionInDay(long dayKey, long seqKey, long perKey) {
		Day day = (Day) getSetOfDays().getResource(dayKey).getAttach();
		int periodPos = -1;
		if (day != null) {
			Sequence seq = (Sequence) day.getSetOfSequences().getResource(
					seqKey).getAttach();
			if (seq != null) {
				periodPos = 0;
				for (int i = (int) seqKey - 1; i > 0; i--)
					periodPos += ((Sequence) day.getSetOfSequences()
							.getResource(i).getAttach()).getSetOfPeriods()
							.size();
				return periodPos + (int) perKey;
			}
		}
		return -1;
	}

	/**
	 * check adjacency of periods
	 * 
	 * @param long
	 *            the day reference number where we want to find the period
	 * @param long
	 *            the sequence reference number where we want to find the period
	 * @param long
	 *            the index of the begining period in the sequence
	 * @param int
	 *            the duration where we want to check adjacency of periods
	 * @return Period the period
	 */
	public boolean isPeriodContiguous(long dayKey, long seqKey,
			long beginperKey, int duration, int[] avoidPriority,
			boolean usePriority) {
		Day day = (Day) getSetOfDays().getResource(dayKey).getAttach();
		if (day != null) {
			Sequence seq = (Sequence) day.getSetOfSequences().getResource(
					seqKey).getAttach();
			if (seq != null) {
				int index = seq.getSetOfPeriods().getIndexOfResource(
						beginperKey);
				if ((index != -1)
						&& ((index + duration - 1) < seq.getSetOfPeriods()
								.size())) {
					for (int i = 0; i < seq.getSetOfPeriods().size(); i++) {
						if (usePriority) {
							for (int j = 0; j < avoidPriority.length; j++) {
								if (((Period) seq.getSetOfPeriods()
										.getResourceAt(i).getAttach())
										.getPriority() == avoidPriority[j])
									return false;
							}// end for (int j=0; j< avoidPriority.length;
							// j++)

						}// end if(usePriority)
					}
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * get a period
	 * 
	 * @param Sequence
	 *            the sequence where we want to find a period
	 * @param int
	 *            the period reference number in the sequence
	 * @return Period the period or null if period does not found
	 */
	/*
	 * public Period getPeriod(Sequence seq, int periodRefNo ){ return
	 * (Period)seq.getSetOfPeriods().getResource(
	 * Integer.toString(periodRefNo)).getAttach(); }
	 */

	/**
	 * get a period
	 * 
	 * @param int
	 *            [3] the day time index rank: 0= dayKey, 1= hour , 2= minute
	 * @return String the period complete key a.b.c where a= day key, b=
	 *         sequence key c= period key
	 */
	public String getPeriod(int[] dayTime) {
		int error = 1000;
		String key = "";
		Day day;
		if (_setOfDays.getResource(dayTime[0]) != null) {
			day = (Day) _setOfDays.getResource(dayTime[0]).getAttach();
			for (int i = 0; i < day.getSetOfSequences().size(); i++) {
				Sequence seq = (Sequence) day.getSetOfSequences()
						.getResourceAt(i).getAttach();
				for (int j = 0; j < seq.getSetOfPeriods().size(); j++) {
					Period per = (Period) seq.getSetOfPeriods()
							.getResourceAt(j).getAttach();
					int newError = Math
							.abs((per.getBeginHour()[0] - dayTime[1]) * 60
									+ per.getBeginHour()[1] - dayTime[2]);
					if (newError < error) {
						key = _setOfDays.getResource(dayTime[0]).getKey()
								+ "."
								+ day.getSetOfSequences().getResourceAt(i)
										.getKey()
								+ "."
								+ seq.getSetOfPeriods().getResourceAt(j)
										.getKey();
						error = newError;
					}
				}// for(int j=0; j< seq.getSetOfPeriods().size(); j++)
			}// end for (int i=0; i< day.getSetOfSequences().size(); i++)
		}// end if(day!=null)
		return key;
	}

	/**
	 * return the AttributsToDisplay matrix
	 * 
	 * @return
	 */
	public DisplayAttributs[][] getAttributesToDisplay() {
		DSetOfResources rowAtt = buildAttributsRowTodisplay();
		DisplayAttributs[][] matrixToDisplay = new DisplayAttributs[_setOfDays
				.size()][rowAtt.size() - 1];
		for (int i = 0; i < matrixToDisplay.length; i++) {
			for (int j = 0; j < matrixToDisplay[i].length; j++) {
				DisplayAttributs attrib = new DisplayAttributs();
				attrib.setHourToDisplay(rowAtt.getResourceAt(j).getID());
				attrib.setPeriodType(false);
				matrixToDisplay[i][j] = attrib;
			}
		}
		for (int i = 0; i < _setOfDays.size(); i++) {
			DResource day = _setOfDays.getResourceAt(i);
			for (int j = 0; j < ((Day) day.getAttach()).getSetOfSequences()
					.size(); j++) {
				DResource seq = ((Day) day.getAttach()).getSetOfSequences()
						.getResourceAt(j);
				for (int k = 0; k < ((Sequence) seq.getAttach())
						.getSetOfPeriods().size(); k++) {
					DResource per = ((Sequence) seq.getAttach())
							.getSetOfPeriods().getResourceAt(k);
					String periodKey = day.getKey() + "." + seq.getKey() + "."
							+ per.getKey() + ".";
					String hour = "00"
							+ ((Period) per.getAttach()).getBeginHour()[0];
					String minute = "00"
							+ ((Period) per.getAttach()).getBeginHour()[1];
					String beginHour = hour.substring(hour.length() - 2, hour
							.length())
							+ ":"
							+ minute.substring(minute.length() - 2, minute
									.length());
					String ehour = "00"
							+ ((Period) per.getAttach()).getEndHour()[0];
					String eminute = "00"
							+ ((Period) per.getAttach()).getEndHour()[1];
					String endHour = ehour.substring(ehour.length() - 2, ehour
							.length())
							+ ":"
							+ eminute.substring(eminute.length() - 2, eminute
									.length());
					int index = rowAtt.getIndexOfResource(beginHour);
					int eindex = rowAtt.getIndexOfResource(endHour);
					for (int l = index; l < eindex; l++) {
						DisplayAttributs attrib = new DisplayAttributs(
								periodKey, rowAtt.getResourceAt(l).getID(),
								((Period) per.getAttach()).getEventsInPeriod());
						attrib.setPeriodType(true);
						matrixToDisplay[i][l] = attrib;
					}
				}// end for(int k=0; k< seq.getSetOfPeriods().size(); k++)
			}// end for (int j=0; j< day.getSetOfSequences().size(); j++)
		}// end for(int i=0; i< _setOfDays.size(); i++)

		return matrixToDisplay;
	}

	/**
	 * 
	 * @param periodLength
	 * @return
	 */
	private DSetOfResources buildAttributsRowTodisplay() {
		DSetOfResources attrib = new StandardCollection();// rgr rgr
		for (int i = 0; i < _setOfDays.size(); i++) {
			Day day = (Day) _setOfDays.getResourceAt(i).getAttach();
			for (int j = 0; j < day.getSetOfSequences().size(); j++) {
				Sequence seq = (Sequence) day.getSetOfSequences()
						.getResourceAt(j).getAttach();
				for (int k = 0; k < seq.getSetOfPeriods().size(); k++) {
					DValue value = new DValue();
					value.setIntValue(1);
					Period per = (Period) seq.getSetOfPeriods()
							.getResourceAt(k).getAttach();
					String hour = "00" + per.getBeginHour()[0];
					String minute = "00" + per.getBeginHour()[1];
					String beginHour = hour.substring(hour.length() - 2, hour
							.length())
							+ ":"
							+ minute.substring(minute.length() - 2, minute
									.length()); // XXXX Pascal: A quoi sert cet
					// appel?
					attrib.addResource(new DResource(beginHour, value), 1);
				}// end for(int k=0; k< seq.getSetOfPeriods().size(); k++)
				if (j < day.getSetOfSequences().size() - 1) { // vrai sauf
					// pour la
					// derniere
					// iteration du
					// 'for' (ne
					// traite pas le
					// derniere
					// Sequence de
					// la journee)
					DValue value = new DValue();
					value.setIntValue(-1);
					Period per = (Period) seq.getSetOfPeriods().getResourceAt(
							seq.getSetOfPeriods().size() - 1).getAttach();
					String hour = "00" + per.getEndHour()[0];
					String minute = "00" + per.getEndHour()[1];
					String endHour = hour.substring(hour.length() - 2, hour
							.length())
							+ ":"
							+ minute.substring(minute.length() - 2, minute
									.length()); // XXXX Pascal: A quoi sert cet
					// appel?
					attrib.addResource(new DResource(endHour, value), 1);
				}
				seq = (Sequence) day.getSetOfSequences().getResourceAt( // traite
						// tjrs
						// la
						// derniere
						// Sequence
						// de la
						// journee
						day.getSetOfSequences().size() - 1).getAttach();
				Period per = (Period) seq.getSetOfPeriods().getResourceAt(
						seq.getSetOfPeriods().size() - 1).getAttach();
				DValue value = new DValue();
				value.setIntValue(1);
				String hour = "00" + per.getEndHour()[0];
				String minute = "00" + per.getEndHour()[1];
				String endHour = hour.substring(hour.length() - 2, hour
						.length())
						+ ":"
						+ minute
								.substring(minute.length() - 2, minute.length()); // XXXX
				// Pascal:
				// A
				// quoi
				// sert
				// cet
				// appel?
				attrib.addResource(new DResource(endHour, value), 1);
			}// end for (int j=0; j< day.getSetOfSequences().size(); j++)
		}// end for(int i=0; i< _setOfDays.size(); i++)
		// attrib.sortSetOfResourcesByKey();

		return attrib;
	}

	/**
	 * get a sequence in a day
	 * 
	 * @param Day
	 *            the day where we want to find a sequence
	 * @param String
	 *            the sequence ID (AM, PM, EM)
	 * @return Sequence the sequence or null if the sequence does not found
	 */
	public Sequence getSequence(Day day, String seqID) {
		return (Sequence) day.getSetOfSequences().getResource(seqID)
				.getAttach();
	}

	/**
	 * get a sequence in a day
	 * 
	 * @param Day
	 *            the day where we want to find a sequence
	 * @param String
	 *            the sequence ID (AM, PM, EM)
	 * @return Sequence the sequence or null if the sequence does not found
	 */
	public Sequence getSequence(Day day, int seqRef) {
		return (Sequence) day.getSetOfSequences().getResource(seqRef)
				.getAttach();
	}

	/**
	 * 
	 * @return int[0]= number of instructors conflicts, int[1]= rooms, int[2]=
	 *         students
	 */
	public int[] getTotalNumberOfConflicts() {
		int[] conf = { 0, 0, 0 };
		for (int i = 0; i < getSetOfDays().size(); i++) {
			Day day = (Day) getSetOfDays().getResourceAt(i).getAttach();
			for (int j = 0; j < day.getSetOfSequences().size(); j++) {
				Sequence seq = (Sequence) day.getSetOfSequences()
						.getResourceAt(j).getAttach();
				for (int k = 0; k < seq.getSetOfPeriods().size(); k++) {
					conf[0] += ((Period) seq.getSetOfPeriods().getResourceAt(k)
							.getAttach()).getNbInstConflict();
					conf[1] += ((Period) seq.getSetOfPeriods().getResourceAt(k)
							.getAttach()).getNbRoomConflict();
					conf[2] += ((Period) seq.getSetOfPeriods().getResourceAt(k)
							.getAttach()).getNbStudConflict();
				}// end for(int k=0; k< seq.getSetOfPeriods().size(); k++)
			}// end for(int j=0; j< day.getSetOfSequences().size(); j++)

		}// end for (int i=0; i< getSetOfDays().size(); i++)
		return conf;
	}

	/**
	 * 
	 */
	public void emptyAllEventsInPeriod() {
		for (int i = 0; i < getSetOfDays().size(); i++) {
			Day day = (Day) getSetOfDays().getResourceAt(i).getAttach();
			for (int j = 0; j < day.getSetOfSequences().size(); j++) {
				Sequence seq = (Sequence) day.getSetOfSequences()
						.getResourceAt(j).getAttach();
				for (int k = 0; k < seq.getSetOfPeriods().size(); k++) {
					((Period) seq.getSetOfPeriods().getResourceAt(k)
							.getAttach()).emptyEventsInPeriod();
				}// end for(int k=0; k< seq.getSetOfPeriods().size(); k++)
			}// end for(int j=0; j< day.getSetOfSequences().size(); j++)
		}// end for (int i=0; i< getSetOfDays().size(); i++)
	}

	/**
	 * 
	 */
	public void resetAllNumberOfConflicts() {
		for (int i = 0; i < getSetOfDays().size(); i++) {
			Day day = (Day) getSetOfDays().getResourceAt(i).getAttach();
			for (int j = 0; j < day.getSetOfSequences().size(); j++) {
				Sequence seq = (Sequence) day.getSetOfSequences()
						.getResourceAt(j).getAttach();
				for (int k = 0; k < seq.getSetOfPeriods().size(); k++) {
					((Period) seq.getSetOfPeriods().getResourceAt(k)
							.getAttach()).setNbInstructorsConflict(0);
					((Period) seq.getSetOfPeriods().getResourceAt(k)
							.getAttach()).setNbRoomsConflict(0);
					((Period) seq.getSetOfPeriods().getResourceAt(k)
							.getAttach()).setNbStudConflict(0);
				}// end for(int k=0; k< seq.getSetOfPeriods().size(); k++)
			}// end for(int j=0; j< day.getSetOfSequences().size(); j++)
		}// end for (int i=0; i< getSetOfDays().size(); i++)
	}

	/**
	 * return the period and increment _currentDayIndex
	 * 
	 * @return
	 */
	public Period getNextPeriod(int steps) {
		DValue dayValue = new DValue();
		Period period = getCurrentDay().getCurrentSequence().getCurrentPeriod();
		for (int i = 0; i < steps; i++) {
			dayValue.setIntValue(_currentDayIndex);
			// System.out.println("-> Day: "+_currentDayIndex);//debug
			period = ((Day) _setOfDays.getResourceAt(_currentDayIndex)
					.getAttach()).getNextPeriod(dayValue);
			_currentDayIndex = dayValue.getIntValue();
			if (_currentDayIndex >= _setOfDays.size()) {
				_currentDayIndex = 0;
			}
		}
		return period;
	}

	/**
	 * return the period and decrement _currentDayIndex
	 * 
	 * @return
	 */
	public Period getPreviousPeriod(){//int steps) {
		DValue dayValue = new DValue();
	//	steps += 0;
		Period period;// =
		// getCurrentDay().getCurrentSequence().getCurrentPeriod();
		// for (int i=steps; i> 0; i--){
		dayValue.setIntValue(_currentDayIndex);
		// System.out.println("Day: "+_currentDayIndex);//debug
		// System.out.println("-> Day: "+_currentDayIndex);//debug
		period = ((Day) _setOfDays.getResourceAt(_currentDayIndex).getAttach())
				.getPreviousPeriod(dayValue);

		if (dayValue.getIntValue() <= -1) {
			dayValue.setIntValue(_setOfDays.size() - 1);
		}
		if (_currentDayIndex != dayValue.getIntValue()) {
			_currentDayIndex = dayValue.getIntValue();
			getCurrentDay().setCurrentSequenceIndex(
					getCurrentDay().getSetOfSequences().size() - 1);
			getCurrentDay().getCurrentSequence().setCurrentPeriodIndex(
					getCurrentDay().getCurrentSequence().getSetOfPeriods()
							.size() - 1);
		}
		// }
		return period;
	}

	/**
	 * 
	 */
	public String toString() {
		String str = "";
		for (int i = 0; i < _setOfDays.size(); i++) {
			DResource rescD = _setOfDays.getResourceAt(i);
			str += ((Day) rescD.getAttach()).toString(rescD.getID());
		}
		return str;
	}

	/**
	 * isEquals checks if this Cycle is equals to the Cycle gives in arg
	 * 
	 * @param cycle
	 *            the Cycle arg
	 * @return
	 *            <p>
	 *            true if this Cycle is equals to the Cycle gives in arg
	 *            </p>
	 *            false otherwise
	 */
	public boolean isEquals(Cycle cycle) {
		for (int i = 0; i < _setOfDays.size(); i++) {
			DResource dayR = _setOfDays.getResourceAt(i);
			DResource dayCloneR = cycle.getSetOfDays().getResourceAt(i);
			if (!dayR.getID().equalsIgnoreCase(dayCloneR.getID()))
				return false;
			if (!dayR.getAttach().isEquals(dayCloneR.getAttach()))
				return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInternal.DObject#getSelectedField()
	 */
	public long getSelectedField() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean compareByField(int fieldIndex, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setField(int fieldIndex, String value) {
		// TODO Auto-generated method stub
		
	}

}
