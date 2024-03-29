/* Generated by Together */

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

public class Sequence extends DObject {

	private DSetOfResources _setOfPeriods;

	private int _currentPeriodIndex = 0;

	private String _error = "";

	private String _errorMessage = "XML file is corrupted";

	/**
	 * Constructor
	 */
	public Sequence() {
		_setOfPeriods = new StandardCollection();
	}

	/**
	 * get the set of periods
	 * 
	 * @return SetOfResources the set of periods
	 */
	public DSetOfResources getSetOfPeriods() {
		return _setOfPeriods;
	}

	/**
	 * */
	public Period getPeriodByIndex(int periodIndex) {
		if (periodIndex < _setOfPeriods.size())
			return (Period) _setOfPeriods.getResourceAt((periodIndex))
					.getAttach();
		return null;
	}

	/**
	 * */
	public Period getCurrentPeriod() {
		return getPeriodByIndex(_currentPeriodIndex);
	}

	/**
	 * */
	public int getCurrentPeriodIndex() {
		return _currentPeriodIndex;
	}

	/**
	 * */
	public void setCurrentPeriodIndex(int curPeriodIndex) {
		_currentPeriodIndex = curPeriodIndex;
	}

	/**
	 * set the set of periods
	 * 
	 * @param SetOfResources
	 *            the set of periods
	 */
	public void setSetOfPeriods(DSetOfResources setOfPeriods) {
		_setOfPeriods = setOfPeriods;
	}

	/**
	 * read a xml tag containing a set of periods and build the resource
	 * 
	 * @param Element
	 *            the root xml tag of the set of periods
	 */
	public String readXMLtag(Element setofPers) {
		XMLReader list = new XMLReader();
		int size = list.getSize(setofPers, DConst.TTXML_TTPERIOD);
		if (size == 0) {
			_error = _errorMessage;
			return _error;
		}
		for (int i = 0; i < size; i++) {
			Period period = new Period();
			Element per = list.getElement(setofPers, DConst.TTXML_TTPERIOD, i);
			String periodID = list.getElementValue(per, DConst.TTXML_PERIODID);
			// System.out.println("period.readXMLtag(per) "+
			// period.readXMLtag(per));//debug
			if (!period.readXMLtag(per).equals("")) {
				_error = _errorMessage;
				return _error;
			}
			_setOfPeriods.addResource(new DResource(periodID, period), 0);
		}// end for (int i=0; i< size; i++)
		return _error;
	}

	/**
	 * read a xml tag containing a set of periods and build the resource
	 * 
	 * @param Element
	 *            the root xml tag of the set of periods
	 */
	public void readXMLTTTag(Element setofPers)throws Exception {
		XMLReader list = new XMLReader();
		int size = list.getSize(setofPers, DConst.TTXML_TTPERIOD);
//		if (size == 0) {
//			_error = _errorMessage;
//			return _error;
//		}
		for (int i = 0; i < size; i++) {
			Period period = new Period();
			Element per = list.getElement(setofPers, DConst.TTXML_TTPERIOD, i);
			String periodID = list.getElementValue(per, DConst.TTXML_PERIODID);
			period.readXMLTTTag(per);
//			if (!period.readXMLtag(per).equals("")) {
//				_error = _errorMessage;
//				return _error;
//			}
			_setOfPeriods.addResource(new DResource(periodID, period), 0);
		}// end for (int i=0; i< size; i++)
//		return _error;
	}
	/**
	 * Contruct a xml element from the set of periods
	 * 
	 * @param Document
	 *            the root xml document
	 * @Element the xml tag of the set of periods
	 */
	public Element writeXMLtag(Document doc) {
		XMLWriter xmlElt;
		try {
			xmlElt = new XMLWriter();
			Element eltPers = xmlElt.createElement(doc, DConst.TTXML_TTPERIODS);
			for (int i = 0; i < _setOfPeriods.size(); i++) {
				Element period = ((Period) _setOfPeriods.getResourceAt(i)
						.getAttach()).writeXMLtag(doc);
				Element periodID = xmlElt.createElement(doc,
						DConst.TTXML_PERIODID, _setOfPeriods.getResourceAt(i)
								.getID());
				period = xmlElt.appendChildInElement(period, periodID);
				eltPers = xmlElt.appendChildInElement(eltPers, period);
			}
			return eltPers;
		} catch (Exception e) {
			System.out.println("Sequence: " + e);// debug
			return null;
		}
	}

	/**
	 * */
	public Sequence cloneSequence() {
		Sequence newSeq = new Sequence();
		newSeq._currentPeriodIndex = this._currentPeriodIndex;
		for (int i = 0; i < this.getSetOfPeriods().size(); i++) {
			Period newPer = ((Period) this.getSetOfPeriods().getResourceAt(i)
					.getAttach()).clonePeriod();
			int position = (int) getSetOfPeriods().getResourceAt(i).getKey();
			newSeq.getSetOfPeriods().setCurrentKey(position);
			newSeq.getSetOfPeriods().addResource(
					new DResource(getSetOfPeriods().getResourceAt(i).getID(),
							newPer), 0);
		}
		return newSeq;
	}

	public String getError() {
		return _error;
	}

	/**
	 * return the next period and increment _currentPeriodIndex
	 * 
	 * @return
	 */
	public Period getNextPeriod(DValue seqVal) {
		Period period = (Period) _setOfPeriods.getResourceAt(
				_currentPeriodIndex++).getAttach();
		if (_currentPeriodIndex >= _setOfPeriods.size()) {
			_currentPeriodIndex = 0;
			seqVal.setIntValue(seqVal.getIntValue() + 1);
			// seqIndex++;
		}
		return period;
	}

	/**
	 * return the previous period and decrement _currentPeriodIndex
	 * 
	 * @return
	 */
	public Period getPreviousPeriod(DValue seqVal) {
		// System.out.println("Period: "+_currentPeriodIndex);//debug
		Period period = (Period) _setOfPeriods.getResourceAt(
				_currentPeriodIndex--).getAttach();
		if (_currentPeriodIndex <= -1) {
			_currentPeriodIndex = _setOfPeriods.size() - 1;
			seqVal.setIntValue(seqVal.getIntValue() - 1);
			// seqIndex++;
		}

		return period;
	}

	/**
	 * 
	 */
	public String toString(String ID) {
		String str = "";
		for (int i = 0; i < _setOfPeriods.size(); i++)
			str += ID + "--" + getPeriodByIndex(i).toString() + DConst.CR_LF;
		return str;
	}

	/**
	 * isEquals checks if this sequence is equals to the sequence gives in arg
	 * 
	 * @param seq
	 *            the Sequence arg
	 * @return
	 *            <p>
	 *            true if this sequence is equals to the sequence gives in arg
	 *            </p>
	 *            false otherwise
	 */
	public boolean isEquals(Sequence seq) {
		for (int i = 0; i < _setOfPeriods.size(); i++) {
			DResource perR = _setOfPeriods.getResourceAt(i);
			DResource perCloneR = seq.getSetOfPeriods().getResourceAt(i);
			if (!perR.getID().equalsIgnoreCase(perCloneR.getID()))
				return false;
			if (!perR.getAttach().isEquals(perCloneR.getAttach()))
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
