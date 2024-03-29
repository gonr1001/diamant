/**
 *
 * Title: Day 
 * Description: 
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
 * 
 */

package dInternal.dTimeTable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import dConstants.DConst;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DObject;
import dInternal.DValue;
import dInternal.dData.StandardCollection;
import eLib.exit.xml.input.XMLReader;
import eLib.exit.xml.output.XMLWriter;

public class Day extends DObject {

	private DSetOfResources _setOfSequences;

	private int _currentSequenceIndex = 0;

	private String _error = "";

	private String _errorMessage = "XML file is corrupted";

	/**
	 * Constructor
	 */
	public Day() {
		_setOfSequences = new StandardCollection();
	}

	/**
	 * get the set of sequences
	 * 
	 * @return SetOfResources the set of sequences
	 */
	public DSetOfResources getSetOfSequences() {
		return _setOfSequences;
	}

	/**
	 * @param int
	 *            the index of the sequence
	 */
	/**
	 * @param int
	 *            the index of the sequence
	 */
	public Sequence getSequence(int sequenceIndex) {
		return (Sequence) _setOfSequences.getResourceAt((sequenceIndex))
				.getAttach();
	}

	/**
	 * */
	public Sequence getCurrentSequence() {
		return getSequence(_currentSequenceIndex);
	}

	/**
	 * */
	public int getCurrentSequenceIndex() {
		return _currentSequenceIndex;
	}

	/**
	 * */
	public void setCurrentSequenceIndex(int curSequenceIndex) {
		_currentSequenceIndex = curSequenceIndex;
	}

	/**
	 * set the set of sequences
	 * 
	 * @param SetOfResources
	 *            the set of sequences
	 */
	public void setSetOfSequences(DSetOfResources setOfSequences) {
		_setOfSequences = setOfSequences;
	}

	/**
	 * read a xml tag containing a set of sequences and build the resource
	 * 
	 * @param Element
	 *            the root xml tag of the set of sequences
	 */
	public String readXMLtag(Element setofSeqs) {
		XMLReader list = new XMLReader();
		String ID = "";
		int size = list.getSize(setofSeqs, DConst.TTXML_TTSEQUENCE);
		if (size == 0) {
			_error = _errorMessage;
			return _error;
		}
		for (int i = 0; i < size; i++) {
			Sequence setOfPeriods = new Sequence();
			Element sequence = list.getElement(setofSeqs,
					DConst.TTXML_TTSEQUENCE, i);
			ID = list.getElementValue(sequence, DConst.TTXML_SEQUENCEID);

			Element periods = list.getElement(sequence, DConst.TTXML_TTPERIODS,
					0);
			if (!setOfPeriods.readXMLtag(periods).equals("")) {
				_error = _errorMessage;
				return _error;
			}
			_setOfSequences.addResource(new DResource(ID, setOfPeriods), 0);
		}// end for (int i=0; i< size; i++)
		return _error;
	}
	
	/**
	 * read a xml tag containing a set of sequences and build the resource
	 * 
	 * @param Element
	 *            the root xml tag of the set of sequences
	 */
	public void readXMLTTTag(Element setofSeqs) throws Exception{
		XMLReader list = new XMLReader();
		String ID = "";
		int size = list.getSize(setofSeqs, DConst.TTXML_TTSEQUENCE);
//		if (size == 0) {
//			_error = _errorMessage;
//			return _error;
//		}
		for (int i = 0; i < size; i++) {
			Sequence setOfPeriods = new Sequence();
			Element sequence = list.getElement(setofSeqs,
					DConst.TTXML_TTSEQUENCE, i);
			ID = list.getElementValue(sequence, DConst.TTXML_SEQUENCEID);

			Element periods = list.getElement(sequence, DConst.TTXML_TTPERIODS,
					0);
			setOfPeriods.readXMLTTTag(periods);
//			if (!setOfPeriods.readXMLtag(periods).equals("")) {
//				_error = _errorMessage;
//				return _error;
//			}
			_setOfSequences.addResource(new DResource(ID, setOfPeriods), 0);
		}// end for (int i=0; i< size; i++)
//		return _error;
	}

	/**
	 * Contruct a xml element from the set of sequences
	 * 
	 * @param Document
	 *            the root xml document
	 * @Element the xml tag of the set of sequences
	 */
	public Element writeXMLtag(Document doc) {
		XMLWriter xmlElt;
		try {
			xmlElt = new XMLWriter();
			Element eltDay = xmlElt
					.createElement(doc, DConst.TTXML_TTSEQUENCES);
			for (int i = 0; i < _setOfSequences.size(); i++) {
				Element eltSeqs = xmlElt.createElement(doc,
						DConst.TTXML_TTSEQUENCE);
				Element sequence = ((Sequence) _setOfSequences.getResourceAt(i)
						.getAttach()).writeXMLtag(doc);
				Element sequenceID = xmlElt.createElement(doc,
						DConst.TTXML_SEQUENCEID, _setOfSequences.getResourceAt(
								i).getID());
				eltSeqs = xmlElt.appendChildInElement(eltSeqs, sequenceID);
				eltSeqs = xmlElt.appendChildInElement(eltSeqs, sequence);
				eltDay = xmlElt.appendChildInElement(eltDay, eltSeqs);
			}
			// eltDay=xmlElt.appendChildInElement(eltDay, sequence);
			return eltDay;
		} catch (Exception e) {
			System.out.println("Day: " + e);// debug
			return null;
		}
	}

	/**
	 * */
	public Day cloneDay() {
		Day newDay = new Day();
		newDay._currentSequenceIndex = this._currentSequenceIndex;
		for (int i = 0; i < this.getSetOfSequences().size(); i++) {
			String id = getSetOfSequences().getResourceAt(i).getID();
			int key = (int) getSetOfSequences().getResourceAt(i).getKey();
			Sequence seq = ((Sequence) this.getSetOfSequences()
					.getResourceAt(i).getAttach()).cloneSequence();
			newDay.getSetOfSequences().setCurrentKey(key);
			newDay.getSetOfSequences().addResource(new DResource(id, seq), 0);
		}// end for(int i=0; i< day.getSetOfSequences().size(); i++)

		return newDay;
	}

	public String getError() {
		return _error;
	}

	/**
	 * return the period and increment _currentSequenceIndex
	 * 
	 * @return
	 */
	public Period getNextPeriod(DValue dayValue) {
		DValue seqValue = new DValue();
		seqValue.setIntValue(_currentSequenceIndex);
		Period period = ((Sequence) _setOfSequences.getResourceAt(
				_currentSequenceIndex).getAttach()).getNextPeriod(seqValue);
		_currentSequenceIndex = seqValue.getIntValue();
		if (_currentSequenceIndex >= _setOfSequences.size()) {
			_currentSequenceIndex = 0;
			dayValue.setIntValue(dayValue.getIntValue() + 1);
		}// end if(_currentSequenceIndex>= _setOfSequences.size())
		return period;
	}

	/**
	 * return the previous period and decrement _currentSequenceIndex
	 * 
	 * @return
	 */
	public Period getPreviousPeriod(DValue dayValue) {
		// System.out.println("Sequence: "+_currentSequenceIndex);//debug
		DValue seqValue = new DValue();
		seqValue.setIntValue(_currentSequenceIndex);
		Period period = ((Sequence) _setOfSequences.getResourceAt(
				_currentSequenceIndex).getAttach()).getPreviousPeriod(seqValue);

		if (seqValue.getIntValue() <= -1) {
			seqValue.setIntValue(_setOfSequences.size() - 1);
			dayValue.setIntValue(dayValue.getIntValue() - 1);
			// getCurrentSequence().setCurrentPeriodIndex(getCurrentSequence().getSetOfPeriods().size()-1);
		}// end if(_currentSequenceIndex>= _setOfSequences.size())
		if (_currentSequenceIndex != seqValue.getIntValue()) {
			_currentSequenceIndex = seqValue.getIntValue();
			// setCurrentSequenceIndex(_setOfSequences.size()-1);
			getCurrentSequence().setCurrentPeriodIndex(
					getCurrentSequence().getSetOfPeriods().size() - 1);
		}
		return period;
	}

	/**
	 * 
	 */
	public String toString(String ID) {
		String str = "";
		for (int i = 0; i < _setOfSequences.size(); i++) {
			DResource rescD = _setOfSequences.getResourceAt(i);
			str += ((Sequence) rescD.getAttach()).toString(ID + "--"
					+ rescD.getID());
		}
		return str;
	}

	/**
	 * isEquals checks if this Day is equals to the Day gives in arg
	 * 
	 * @param day
	 *            the Day arg
	 * @return
	 *            <p>
	 *            true if this Day is equals to the Day gives in arg
	 *            </p>
	 *            false otherwise
	 */
	public boolean isEquals(Day day) {
		for (int i = 0; i < _setOfSequences.size(); i++) {
			DResource seqR = _setOfSequences.getResourceAt(i);
			DResource seqCloneR = day.getSetOfSequences().getResourceAt(i);
			if (!seqR.getID().equalsIgnoreCase(seqCloneR.getID()))
				return false;
			if (!seqR.getAttach().isEquals(seqCloneR.getAttach()))
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
