/**
 * Created on May 4, 2006
 * 
 * 
 * Title: DxAvailability.java 
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
package dInternal.dData;

import java.util.StringTokenizer;
import java.util.Vector;

import dConstants.DConst;


/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxAvailability is a class used to:
 * <p>
 * Holds availability data and provides easy way of accessing informations.
 * DxAvailability don't make extensive verifications since, at this point, data
 * should have already been verified and correct.
 * <p>
 * 
 */

//TODO: int[][] should be removed
public class DxAvailability {

	private Vector<Vector<Integer>> _vDays;

	/**
	 * Constructor
	 * 
	 * @param nDays
	 *            Specify how many days of availability this object will
	 *            represent
	 */
	public DxAvailability() {
		_vDays = new Vector<Vector<Integer>>();
	}

	public DxAvailability(int[][] iMatrix) {
		_vDays = new Vector<Vector<Integer>>();
		for (int i = 0; i < iMatrix.length; i++) {
			Vector<Integer> vTemp = new Vector<Integer>();
			for (int j = 0; j < iMatrix[i].length; j++) {
				vTemp.add(j, new Integer(iMatrix[i][j]));
			}
			_vDays.add(i, vTemp);
		}
	}

	/**
	 * Set the availability for a whole day
	 * 
	 * @param nDay
	 *            The index of the day to be set (starts at 0)
	 * @param sAvailabilities
	 *            Different availabilities during a day, seperated by spaces
	 * @return bolean true if the day was in the range specified by constructor,
	 *         false in other cases
	 */
	public void addDayAvailability(String sAvailabilities) {
		// Create a tokenizer on a string that include availability
		StringTokenizer stAva = new StringTokenizer(sAvailabilities,
				DConst.SPACE);
		// Takes vector for the current day
		Vector<Integer> vCurrentDay = new Vector<Integer>();

		// Goes through tokens of a day and add availability to the
		// corresponding day vector
		while (stAva.hasMoreTokens()) {
			vCurrentDay.add(new Integer(stAva.nextToken()));
		}
		_vDays.add(vCurrentDay);
	}

	/**
	 * Set availability for a single period of a day
	 * 
	 * @param nDayIndex
	 *            Index of the day to be set (starts at 0)
	 * @param nPeriodIndex
	 *            Index of the period of the day to be set
	 * @return bolean true if the Day and Period were valid, false in other
	 *         cases
	 */
	public boolean setPeriodAvailability(int nDayIndex, int nPeriodIndex,
			int nAvailability) {
		if (isValidDay(nDayIndex)) {
			Vector<Integer> vTemp = _vDays.get(nDayIndex);
			if (nPeriodIndex >= 0 && nPeriodIndex < vTemp.size()) {
				vTemp.set(nPeriodIndex, new Integer(nAvailability));
				return true;
			}
		}
		return false;
	}

	public void setAvailability(int[][] newAva) {
		_vDays.removeAllElements();
		for (int i = 0; i < newAva.length; i++) {
			Vector<Integer> vTemp = new Vector<Integer>(newAva[i].length);
			for (int j = 0; j < newAva[i].length; j++) {
				vTemp.add(new Integer(newAva[i][j]));
			}
			_vDays.add(vTemp);
		}
	}

	/**
	 * Method that gives access to availibilities of a whole day through a
	 * vector of Integer
	 * 
	 * @param nDayIndex
	 *            Index of the day to be retreived
	 * @return Vector<Integer> The vector of Integer if the specified day was
	 *         valid, null otherwise
	 */
	public Vector<Integer> getDayAvailability(int nDayIndex) {
		if (isValidDay(nDayIndex))
			return _vDays.get(nDayIndex);
		return null;
	}

	/**
	 * Method granting acces to the availability for a certain period of a day
	 * 
	 * @param nDayIndex
	 *            Index of the day to be retreived
	 * @param nPeriodIndex
	 *            Index of the period to be retrived
	 * @return int The availability for the given period or -1 in case of
	 *         invalid day or period index
	 */
	public int getPeriodAvailability(int nDayIndex, int nPeriodIndex) {
		if (isValidDay(nDayIndex)) {
			Vector<Integer> vTemp = _vDays.get(nDayIndex);
			if (nPeriodIndex >= 0 && nPeriodIndex < vTemp.size()) {
				return vTemp.get(nPeriodIndex).intValue();
			}
		}
		return -1;
	}

	public int[][] getMatrixAvailability() {
		// Takes first day's number of period as a whole week constant
		int[][] iRet = new int[_vDays.size()][_vDays.get(0).size()];

		for (int i = 0; i < iRet.length; i++) {
			for (int j = 0; j < iRet[0].length; j++) {
				iRet[i][j] = this.getPeriodAvailability(i, j);
			}
		}
		return iRet;
	}

	/**
	 * Method giving the count of period during a certain day
	 * 
	 * @param nDayIndex
	 *            Index of the day to be retreived
	 * @return int The number of period in the specified day or -1 in case of
	 *         invalid day index
	 */
	public int getPeriodCount(int nDayIndex) {
		if (isValidDay(nDayIndex)) {
			return _vDays.get(nDayIndex).size();
		}
		return -1;
	}

	public int getDayCount() {
		return _vDays.size();
	}

	/**
	 * Return sites where instructor is already assigned in the given period
	 * 
	 * @param dayIndex
	 * @param periodIndex
	 * @param site
	 * @return
	 */
	public Vector<String> isAssignedInPeriod(int dayIndex, int periodIndex, Vector sites) {
		Vector<String> v = new Vector<String>();
		for (int i = 0; i < sites.size(); i++) {
			String site = (String) sites.get(i);
			String[][] matrix = getAssignAvailabilityTable(new Vector());
			if ((dayIndex < matrix.length))
				if (periodIndex < matrix[dayIndex].length)
					if (matrix[dayIndex][periodIndex].matches(".*" + site
							+ ".*"))
						v.add(site);
		} // end for (int i = 0; i< sites.size(); i++)
		return v;
	}

	public String toWrite(String sDaySeparator, String sPeriodSeparator) {
		StringBuffer sRet = new StringBuffer();

		for (int i = 0; i < _vDays.size(); i++) {
			Vector<Integer> vTemp = _vDays.get(i);
			for (int j = 0; j < vTemp.size(); j++) {
				sRet.append(vTemp.get(j).intValue());
				if (j < (vTemp.size() - 1)) {
					sRet.append(sPeriodSeparator);
				}
			}
			if (i < (_vDays.size() - 1)) {
				sRet.append(sDaySeparator);
			}
		}
		return sRet.toString();
	}

	public boolean isEqual(DxAvailability ava) {
		Vector<Integer> vTemp1;
		Vector<Integer> vTemp2;
		// If number of days is different, availabilities are different
		if (_vDays.size() != ava._vDays.size()) {
			return false;
		}
		// For every days in the availability
		for (int i = 0; i < _vDays.size(); i++) {
			vTemp1 = _vDays.get(i);
			vTemp2 = ava._vDays.get(i);
			// If number of periods differ for the same day, availabilites are
			// different
			if (vTemp1.size() != vTemp2.size()) {
				return false;
			}
			// For every period in an availability
			for (int j = 0; j < vTemp1.size(); j++) {
				// If availability for a same period is different,
				// availabilities are different
				if (vTemp1.get(j).intValue() != vTemp2.get(j).intValue()) {
					return false;
				}
			}
		}
		return true;
	}

	public void setAvailabilityOfAPeriod(int dayIndex, int perIndex,
			String currentSite) {
        
		System.out
				.println("DxAvailability.setAvailabilityOfAPeriod must be implemented");
		System.out
		.println(dayIndex+ " " + perIndex + " " + currentSite);

	}
	
	private boolean isValidDay(int nDayIndex) {
		return ((nDayIndex >= 0) && (nDayIndex < _vDays.size()));
	}
//	 private boolean isValidDayAvailability(String sDispo) {
//	        return (!sDispo.equalsIgnoreCase("1"))
//	                && (!sDispo.equalsIgnoreCase("5"))
//	                && (!sDispo.equalsIgnoreCase("2"));
//	    }
//	    public void parseAvailability(String sAvailabilities,
//				 long currentLine) throws DiaException {
//			// extract a line that gives availability of a day
//
//			StringTokenizer stDays = new StringTokenizer(sAvailabilities,
//					DConst.AVAILABILITY_DAY_SEPARATOR_ROOM);
//			String token=null;
//			DxAvailability dxaAvaTemp = new DxAvailability();
//			for (int i = 0; stDays.hasMoreElements() && i < _nDays; i++) {
//				 token = stDays.nextToken();
//				currentLine++;
//				// extract a line that gives availability of a day
//				String line = DXToolsMethods.getToken(token,
//						DConst.AVAILABILITY_SEPARATOR, 0);
//				StringTokenizer tokenDispo = new StringTokenizer(line);
//				// Verifies that number of period per day was correctly
//				// indicated
//				if (tokenDispo.countTokens() == _nPeriods) {
//					// Verifies that every availability element is valid
//					while (tokenDispo.hasMoreElements()) {
//						String dispo = tokenDispo.nextToken();
//						if (!isValidDayAvailability(dispo)) {
//							throw new DiaException(
//									DConst.INVALID_AVAILABILITY_AT
//											+ currentLine);
//						}
//					}
//					// After line is validated, we add it to the availability
//					dxaAvaTemp.addDayAvailability(line);
//				} else
//					throw new DiaException(DConst.INVALID_AVAILABILITY_AT+ currentLine);
//			}// end for(int i=1;
//		}
	/**
	 * 
	 * @return
	 */
	private String[][] getAssignAvailabilityTable(Vector vect) {
		String jour = (String) vect.get(0);
		StringTokenizer st = new StringTokenizer(jour);
		String[][] a = new String[vect.size()][st.countTokens()];
		int nbOfTokens = st.countTokens();
		for (int i = 0; i < vect.size(); i++) {
			jour = (String) vect.get(i);
			st = new StringTokenizer(jour);
			nbOfTokens = st.countTokens();
			for (int j = 0; j < nbOfTokens; j++) {
				a[i][j] = st.nextToken();
			} // end for j
		} // end for i
		return a;
	}
}
