/**
 * Created on 2006-09-10
 * 
 * Title: DxReadInstructorsdotDia.java 
 * 
 *
 * Copyright (c) 2006 by rgr.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information
 * of rgr. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with rgr.
 *
 * @author HAROUNA Abdoul-Kader
 * @since JDK1.3
 */

package dInternal.dData.dInstructors;

import java.util.StringTokenizer;
import dConstants.DConst;
import dExceptions.DiaException;
import dInternal.DataExchange;
import dInternal.dData.DxAvailability;
import dInternal.dUtil.DXToolsMethods;

public class DxReadInstructorsdotDia implements DxInstructorsReader {

	private DataExchange _deInstructors;

	private int _nDays, _nPeriods;
	/**
	 * Used to report line where error was found
	 */
	private int _linesCounter = 0;

	public DxReadInstructorsdotDia(DataExchange de, int nDays, int nPeriods) {
		_deInstructors = de;
		_nDays = nDays;
		_nPeriods = nPeriods;
	}

	public DxReadInstructorsdotDia(DataExchange de, int nDays, int nPeriods,
			int line) {
		_deInstructors = de;
		_nDays = nDays;
		_nPeriods = nPeriods;
		_linesCounter = line;
	}

	/**
		 * 
		 */
	public DxSetOfInstructors readSetOfInstructors() throws DiaException {
		StringTokenizer st = new StringTokenizer(_deInstructors.getContents(),
				DConst.CR_LF);
		String token;

		int numberOfInstructors = 0;
		int countInstructor = 0;
		long numberOfInstructorsLine = 0;
		String instID = "";
		DxSetOfInstructors dxsoiInst = new DxSetOfInstructors();
		DxAvailability dxaAvaTemp = new DxAvailability();

		// Get the total number

		try {
			token = st.nextToken();
			_linesCounter++;
			numberOfInstructors = (new Integer(token.trim())).intValue();
			numberOfInstructorsLine = _linesCounter;
		} catch (NumberFormatException nfe) {
			throw new DiaException(DConst.INVALID_NUMBER_OF_INSTRUCTORS
					+ nfe.getMessage());
		}
		// As long as we do not reach the end of the string tokenizer
		// Reads all tokens in the file
		while (st.hasMoreElements()) {
			token = st.nextToken();
			_linesCounter++;
			// Get the instructor name
			boolean isnotavail = !isValidDayAvailability(token.substring(0, 1));
			if (token.trim().length() > DConst.MINIMUN_NAME && isnotavail) {
				instID = token;
			} else
				throw new DiaException(DConst.INVALID_INSTRUCTOR_NAME
						+ _linesCounter);
			// Build Availability
			dxaAvaTemp = new DxAvailability();
			for (int i = 0; st.hasMoreElements() && i < _nDays; i++) {
				token = st.nextToken();
				_linesCounter++;
				// extract a line that gives availability of a day
				String line = DXToolsMethods.getToken(token,
						DConst.AVAILABILITY_SEPARATOR, 0);
				StringTokenizer tokenDispo = new StringTokenizer(line);
				// Verifies that number of period per day was correctly
				// indicated
				if (tokenDispo.countTokens() == _nPeriods) {
					// Verifies that every availability element is valid
					while (tokenDispo.hasMoreElements()) {
						String dispo = tokenDispo.nextToken();
						if (!isValidDayAvailability(dispo)) {
							throw new DiaException(
									DConst.INVALID_AVAILABILITY_AT
											+ _linesCounter);
						}
					}
					// After line is validated, we add it to the availability
					dxaAvaTemp.addDayAvailability(line);
				} else
					throw new DiaException(DConst.INVALID_AVAILABILITY_AT
							+ _linesCounter);
			}// end for(int i=1;
			dxsoiInst.addInstructor(instID, dxaAvaTemp);
			countInstructor++;

		}// end while

		verifyNumberOfInstructors(numberOfInstructors, countInstructor,
				numberOfInstructorsLine);

		return dxsoiInst;
	}

	private void verifyNumberOfInstructors(int numberOfInstructors,
			int countInstructor, long numberOfInstructorsLine)
			throws DiaException {
		// Verify if number of instructors indicated at the beginning of
		// the
		// file was valid
		if (countInstructor != numberOfInstructors) {
			throw new DiaException(DConst.INVALID_NUMBER_OF_INSTRUCTORS
					+ " Il y a " + countInstructor + " a la place de "
					+ numberOfInstructors + " indique \n" + " sur la ligne "
					+ numberOfInstructorsLine);
		}
	}

	private boolean isValidDayAvailability(String sDispo) {
		return (sDispo.equalsIgnoreCase("1")) || (sDispo.equalsIgnoreCase("5"))
				|| (sDispo.equalsIgnoreCase("2"));
	}

	@Override
	public int getLines() {
		return _linesCounter;
	}
}
