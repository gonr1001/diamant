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
import eLib.exit.exception.DxException;
import dConstants.DConst;
import dInternal.DataExchange;
import dInternal.dData.DxAvailability;
import dInternal.dUtil.DXToolsMethods;


public class DxReadInstructorsdotDia implements DxInstructorsReader {
 	private DataExchange _deInstructors;

		private int _nDays, _nPeriods;
 /**
  * Used to report line where error was found
  */ 
		private long currentLine=0;

		public DxReadInstructorsdotDia(DataExchange de, int nDays, int nPeriods) {
			_deInstructors = de;
			_nDays = nDays;
			_nPeriods = nPeriods;
		}
		public DxReadInstructorsdotDia(DataExchange de, int nDays, int nPeriods,long line) {
			_deInstructors = de;
			_nDays = nDays;
			_nPeriods = nPeriods;
			currentLine=line;
		}

		/**
		 * 
		 */
		public DxSetOfInstructors readSetOfInstructors() throws DxException {
			StringTokenizer st = new StringTokenizer(_deInstructors.getContents(),
					DConst.CR_LF);
			String token;

			int numberOfInstructors = 0;
			int countInstructor = 0;
             String instID = "";
			DxSetOfInstructors dxsoiInst = new DxSetOfInstructors();
			DxAvailability dxaAvaTemp = new DxAvailability();
            
			// Get the total number 
			
			try {
				token = st.nextToken();
				currentLine++;
				numberOfInstructors = (new Integer(token.trim())).intValue();
			 } catch (NumberFormatException exc) {
				throw new DxException(DConst.INVALID_NUMBER_OF_INSTRUCTORS+exc.getMessage());
			}
			// As long as we dont reach the end of the string tokenizer
			// Reads all tokens in the file
			while (st.hasMoreElements()) {
			token = st.nextToken();
			currentLine++;
			// Get the instructor name
			boolean isnotavail = !isValidDayAvailability(token.substring(0, 1));
			if (token.trim().length() > DConst.MINIMUN_NAME
					&& isnotavail) {
				instID = token;
			} else
				throw new DxException(DConst.INVALID_INSTRUCTOR_NAME
						+ currentLine);
			// Build Availability
			dxaAvaTemp = new DxAvailability();
			for (int i = 0; st.hasMoreElements() && i < _nDays; i++) {
				token = st.nextToken();
				currentLine++;
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
							throw new DxException(
									DConst.INVALID_AVAILABILITY_AT
											+ currentLine);
						}
					}
					// After line is validated, we add it to the availability
					dxaAvaTemp.addDayAvailability(line);
				} else
					throw new DxException(DConst.INVALID_AVAILABILITY_AT+ currentLine);
			}// end for(int i=1;
			dxsoiInst.addInstructor(instID, dxaAvaTemp);
			countInstructor++;

		}// end while

            // Verifies if number of instructors indicated at the beginning of
			// the
			// file was valid
			if (countInstructor != numberOfInstructors) {
				throw new DxException(DConst.INVALID_NUMBER_OF_INSTRUCTORS);
			}

			return dxsoiInst;
		}

		private boolean isValidDayAvailability(String sDispo) {
			return (sDispo.equalsIgnoreCase("1"))
					|| (sDispo.equalsIgnoreCase("5"))
					|| (sDispo.equalsIgnoreCase("2"));
		}
	}
