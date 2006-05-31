///**
// * Created on May 11, 2006
// * 
// * 
// * Title: DxRead1dot5.java 
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
//package dInternal.dData.dInstructors;
//
//import java.util.StringTokenizer;
//
//import dConstants.DConst;
//import dInternal.DataExchange;
//import dInternal.dData.DxAvailability;
//import dInternal.dUtil.DXToolsMethods;
//
///**
// * Ruben Gonzalez-Rubio
// * 
// * Description: DxRead1dot5 is a class used to:
// * <p>
// * Implements a reading behavior for a 1.5 version of Instructors data. In
// * format 1.5, number of days was fixed to 5 each contaning 14 periods. Valid
// * values for availability were 1 and 5 
// * TODO Implement
// * Throw to report errors instead of SetOfRessources error reporting
// * <p>
// * 
// */
//public class DxReadInstructors1dot5 implements DxReadInstructorsBehavior {
//
//	private int _days;
//
//	private int _periods;
//
//	public DxReadInstructors1dot5(int days, int periods) {
//		_days = days;
//		_periods = periods;
//	}
//
//	public boolean analyseTokens(DataExchange de) {
//		StringTokenizer st = new StringTokenizer(de.getContents(), DConst.CR_LF);
//		String token;
//
//		int state = 0;
//		int stateDispo = 1;
//		int numberOfInstructors = 0;
//		int countInstructor = 0;
//
//		// Used to report line where error was found
//		int currentLine = 0;
//
//		// As long as we dont reach the end of the string tokenizer
//		// Reads all tokens in the file
//		while (st.hasMoreElements()) {
//			token = st.nextToken();
//			currentLine++;
//
//			// Finite state machine
//			switch (state) {
//			// Line indicating the number of instructors in the file
//			case 0:
//				try {
//					numberOfInstructors = (new Integer(token.trim()))
//							.intValue();
//				} catch (NumberFormatException exc) {
//					// _error = DConst.INST_TEXT1 + "\n" + DConst.INST_TEXT6;
//					return false;
//				}
//				state = 1;
//				break;
//
//			// Line containing the name of the instructor
//			case 1:
//				if (token.length() == 0) {
//					// _error = DConst.INST_TEXT2 + line + DConst.INST_TEXT5
//					// + "\n" + DConst.INST_TEXT6;
//					return false;
//				}
//				state = 2;
//				stateDispo = 1;
//				countInstructor++;
//				break;
//
//			// Lines containing the availability
//			case 2:
//				// extract a line that gives availability of a day
//				String line = DXToolsMethods.getToken(token,
//						DConst.AVAILABILITY_SEPARATOR, 0);
//				StringTokenizer tokenDispo = new StringTokenizer(line);
//				if (tokenDispo.countTokens() != _periods) {
//					// _error = DConst.INST_TEXT3 + line + DConst.INST_TEXT5
//					// + "\n" + DConst.INST_TEXT6;
//					return false;
//				}
//				// traitement de la description de la disponibilité
//				while (tokenDispo.hasMoreElements()) {
//					String dispo = tokenDispo.nextToken();
//					if ((!dispo.equalsIgnoreCase("1"))
//							&& (!dispo.equalsIgnoreCase("5"))
//							&& (!dispo.equalsIgnoreCase("2"))) {
//						// _error = DConst.INST_TEXT4 + line + DConst.INST_TEXT5
//						// + "\n" + DConst.INST_TEXT6;
//						return false;
//					}
//				}
//				stateDispo++;
//				if (stateDispo > _days)
//					state = 1;
//				break;
//			}
//		}
//
//		// Verify that the number of instructor annouced at the beginning of the
//		// file
//		// match the actual number of instructors included
//		if (countInstructor != numberOfInstructors) {
//			// _error = DConst.INST_TEXT1 + "\n" + DConst.INST_TEXT6;
//			return false;
//		}
//		return true;
//	}
//
//	public DxSetOfInstructors buildSetOfInstructors(DataExchange de) {
//		StringTokenizer st = new StringTokenizer(de.getContents(), DConst.CR_LF);
//		String token;
//
//		String instID = "";
//		int state = 0;
//		int stateDispo = 1;
//
//		DxSetOfInstructors dxsoiInst = new DxSetOfInstructors();
//		// To avoir errors: might not have been initialized
//		DxAvailability dxaAvaTemp = new DxAvailability();
//
//		// For each instructors in the DataExchange object
//		while (st.hasMoreElements()) {
//			token = st.nextToken();
//
//			// Finished state machine
//			switch (state) {
//			// Number of instructors in the file
//			case 0:
//				state = 1;
//				break;
//
//			// instructor name
//			case 1:
//				instID = token;
//				state = 2;
//				stateDispo = 1;
//
//				dxaAvaTemp = new DxAvailability();
//				break;
//
//			// instructor availabilities
//			case 2:
//				// extract first part of the line that gives availability
//				String firstPart = DXToolsMethods.getToken(token,
//						DConst.AVAILABILITY_SEPARATOR, 0);
//
//				dxaAvaTemp.addDayAvailability(firstPart);
//
//				stateDispo++;
//				if (stateDispo > _days) {
//					dxsoiInst.addInstructor(instID, dxaAvaTemp);
//					state = 1;
//				}
//				break;
//			}
//		}
//		//sorted by name
////		dxsoiInst.sortInstructors();
//		return dxsoiInst;
//	}
//
//}
