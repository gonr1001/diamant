/**
 * Created on 26 nov. 2004
 *
 *
 * Class SetOfActivitiesInSites
 * Description: SetOfActivitiesInSites
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
 *
 */
package dInternal.dData.dActivities;

import java.util.StringTokenizer;

import dConstants.DConst;
import dExceptions.DiaException;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DataExchange;
import dInternal.dData.ByteArrayMsg;
import dInternal.dUtil.DXToolsMethods;

public class SetOfActivitiesInSites extends DSetOfResources {

	private String _error = "";

	private int _line = 1;

	private boolean _open;

	final static public int _COURSENAMELENGTH = 6;

	private int _ACTIVITYLENGTH = 11;

	private int _periodLength;

	/**
	 * Constructor
	 */
	public SetOfActivitiesInSites(boolean open, int periodLength) {
		super();
		_open = open;
		_periodLength = periodLength;
	}

	/**
	 * analysis activities data by a finished states machine
	 * 
	 * @param integer
	 *            the beginPosition (start position of the finished states
	 *            machine)
	 * @return boolean "true" if the analysis proceeded successfully and false
	 *         otherwise
	 */
	public boolean analyseTokens(DataExchange de, int beginPosition) throws DiaException{
		if (de.getHeader().equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
			return analyseTokens1dot6(de.getContents().getBytes(), beginPosition);
		} // else if (token.equalsIgnoreCase(DConst.FILE_VER_NAME1_7)) {
		return analyseTokens1dot5(de.getContents().getBytes(), beginPosition);
	}

	
	
	/**
	 * does the lexical analysis of activities data by an automaton
	 * 
	 * @param de DataExchange contains the data
	 *           
	 * @ param beginPosition int correspons to the start position in the stream
	 *
	 */
	public boolean newAnalyseTokens(DataExchange de, int beginPosition) throws DiaException{
		if (de.getHeader().equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
			return analyseTokens1dot6(de.getContents().getBytes(), beginPosition);
		} // else if (token.equalsIgnoreCase(DConst.FILE_VER_NAME1_7)) {
		return analyseTokens1dot5(de.getContents().getBytes(), beginPosition);
	}
	/**
	 * analyseTokens1_5 will analyse the tokens of files without any header like
	 * Diamant1.6
	 * 
	 * @param beginPosition
	 *            indicates from where start to read in the array
	 * 
	 * @return true
	 *         <p>
	 *         if no errors in _dataloaded
	 *         </p>
	 *         false otherwise
	 */
	private boolean analyseTokens1dot5(byte[] dataloaded, int beginPosition) throws DiaException{
		_error = "";
		if (!analyseTokensFirstVersion(dataloaded, beginPosition)) {// analyse STI data
			return false;
		} else if (_open) {// else if(!analyseSIGTokens(beginPosition))
			return analyseDeltaTokens1_5(dataloaded, beginPosition);// analyse
		}// end else if(!analyseSIGTokens(beginPosition))
		return true;
	}

	/**
	 * analyseTokens1_5 will analyse the tokens of files without any header like
	 * Diamant1.6
	 * 
	 * @param beginPosition
	 *            indicates from where start to read in the array
	 * 
	 * @return true
	 *         <p>
	 *         if no errors in _dataloaded
	 *         </p>
	 *         false otherwise
	 */
	private boolean analyseTokens1dot6(byte[] dataloaded, int beginPosition) throws DiaException {
		_error = "";
		StringBuffer newFile = new StringBuffer("");
		StringTokenizer st = new StringTokenizer(new String(dataloaded),
				DConst.CR_LF);
		st.nextToken();
		while (st.hasMoreTokens()) {
			newFile.append(st.nextToken() + DConst.CR_LF);
		}
		if (!analyseTokensFirstVersion(newFile.toString().getBytes(), beginPosition)) {// analyse
			// STI
			// data
//		if (!analyseSIGTokens(dataloaded, beginPosition)) {// analyse STI data
			return false;
		} else if (_open) {
			return analyseDeltaTokens1_6(dataloaded, beginPosition);// analyse
		}// end 

		return true;
	}

	/**
	 * analyse Delta activities data by a finished states machine
	 * 
	 * @param integer
	 *            the beginPosition (start position of the finished states
	 *            machine)
	 * @return boolean "true" if the analysis proceeded successfully and false
	 *         otherwise
	 */
	private boolean analyseDeltaTokens1_5(byte[] dataloaded, int beginPosition) throws DiaException {
		String token;

		StringTokenizer st = new StringTokenizer(new String(dataloaded),
				DConst.CR_LF);
		StringTokenizer stLine = null; // auxiliar StringTokenizer for reading
		// subStrings in a line
		// int state=0;
		int position = beginPosition;
		_line = 0;
		// String activityName="";
		String instructorName = "";
		int numberOfBlocs = 0;
		while (st.hasMoreElements()) {
			token = st.nextToken();
			_line++;
			if (_error.length() != 0)
				return false;
			switch (position) {
			case 0:// empty line
				position = 1;
				break;
			case 1:// activity name
				position = 2;
				break;
			case 2:// activity visibility
				position = 3;
				break;
			case 3:// number of activities
				position = 4;
				break;
			case 4:// teachers' names
				instructorName = token;
				position = 7;
				break;
			case 5:// empty line
				position = 6;
				break;
			case 6:// empty line
				position = 7;
				break;
			case 7:// number of blocs
				numberOfBlocs = Integer.parseInt(token.trim());
				if (DXToolsMethods.countTokens(instructorName, ";") != numberOfBlocs) {
					throw new DiaException(DConst.ACTI_TEXT13);
//					_error = DConst.ACTI_TEXT13
//							+ _line
//							+ ", I was in SetOfActivies class and in analyseDeltaTokens method ";
//					return false;
				}
				position = 8;
				break;
			case 8:// duration of blocs

				position = 9;
				break;
			case 9:// days and periods of blocs
				stLine = new StringTokenizer(token);
				if (numberOfBlocs != stLine.countTokens()) {
					_error = DConst.ACTI_TEXT5 + _line + " ActivityList";
					return false;
				}
				while (stLine.hasMoreElements()) {
					StringTokenizer stLine1;
					stLine1 = new StringTokenizer(stLine.nextToken(), ".");
					while (stLine1.hasMoreElements())
						_error = DXToolsMethods.isIntValue(stLine1.nextToken(),
								DConst.ACTI_TEXT8 + _line, "ActivityList");
					if (_error.length() != 0)
						return false;
				}
				position = 10;
				break;
			case 10:// fixed rooms

				position = 11;
				break;
			case 11:// Preferred rooms

				position = 12;
				break;
			case 12:// type of rooms

				position = 13;
				break;
			case 13:// idem

				position = 14;
				break;
			case 14:// pre-affected cours
				_error = analyseTokenPreaffectedRoom(token, numberOfBlocs, 1,
						_line);
				position = beginPosition;
				break;

			}// end switch (position)

		}// end while (st.hasMoreElements())

		return true;
	}

	/**
	 * analyse Delta activities data by a state machine
	 * 
	 * @param integer
	 *            the beginPosition (start position of the finished states
	 *            machine)
	 * @return boolean "true" if the analysis proceeded successfully and false
	 *         otherwise
	 */
	private boolean analyseDeltaTokens1_6(byte[] dataloaded, int beginPosition) {
		String token;

		//here start the work with the  activities
		StringTokenizer st = new StringTokenizer(new String(dataloaded),
				DConst.CR_LF);
		StringTokenizer stLine = null;
		int position = beginPosition;
		token = st.nextToken();
		_line = 0;
		String instructorName = "";
		int numberOfBlocs = 0;
		while (st.hasMoreElements()) {
			token = st.nextToken();
			_line++;
			if (_error.length() != 0)
				return false;
			switch (position) {
			case 0:// empty line
				position = 1;
				break;
			case 1:// activity name
				analyseDelTaTokenName1_6(token, _line);
				position = 2;
				break;
			case 2:// activity visibility
				position = 3;
				break;
			case 3:// number of activities
				position = 4;
				break;
			case 4:// teachers' names
				instructorName = token;
				position = 7;
				break;
			case 5:// empty line
				position = 6;
				break;
			case 6:// empty line
				position = 7;
				break;
			case 7:// number of blocs
				numberOfBlocs = Integer.parseInt(token.trim());
				if (DXToolsMethods.countTokens(instructorName, ";") != numberOfBlocs) {
					_error = DConst.ACTI_TEXT13
							+ _line
							+ ", I was in SetOfActivies class and in analyseDeltaTokens method ";
					return false;
				}
				position = 8;
				break;
			case 8:// duration of blocs

				position = 9;
				break;
			case 9:// days and periods of blocs
				stLine = new StringTokenizer(token);
				if (numberOfBlocs != stLine.countTokens()) {
					_error = DConst.ACTI_TEXT5 + _line + " ActivityList";
					return false;
				}
				while (stLine.hasMoreElements()) {
					StringTokenizer stLine1;
					stLine1 = new StringTokenizer(stLine.nextToken(), ".");
					while (stLine1.hasMoreElements())
						_error = DXToolsMethods.isIntValue(stLine1.nextToken(),
								DConst.ACTI_TEXT8 + _line, "ActivityList");
					if (_error.length() != 0)
						return false;
				}
				position = 10;
				break;
			case 10:// fixed rooms

				position = 11;
				break;
			case 11:// Preferred rooms

				position = 12;
				break;
			case 12:// type of rooms

				position = 13;
				break;
			case 13:// idem

				position = 14;
				break;
			case 14:// pre-affected cours
				_error = analyseTokenPreaffectedRoom(token, numberOfBlocs, 1,
						_line);
				position = beginPosition;
				break;

			}// end switch (position)

		}// end while (st.hasMoreElements())

		return true;
	}

	/**
	 * 
	 * @param str
	 * @param numberOfUnitys
	 * @param position
	 * @param line
	 * @return
	 */
	private String analyseTokenPreaffectedRoom(String str, int numberOfUnitys,
			int position, int line) {
		// check permanent token
		String permanentToken = DXToolsMethods.getToken(str, ";", position);
		StringTokenizer stLine = new StringTokenizer(permanentToken);
		if (numberOfUnitys != stLine.countTokens()) {
			return DConst.ACTI_TEXT12 + line + " ActivityList";
		}
		// check if the permanent value is belong 0 and 1
		while (stLine.hasMoreElements()) {
			String sousString = stLine.nextToken();
			String error = checkIfBelongsValues(sousString,
					"0 1", DConst.ACTI_TEXT12 + line, "ActivityList");
			if (error.length() != 0)
				return error;
		}
		return "";
	}

	/**
	 * 
	 * @param str
	 * @param line
	 * @return
	 */
	private void analyseDelTaTokenName1_6(String str, int line) {
		// activity name number of tokens
		if (DXToolsMethods.countTokens(str, " ") != DConst.NUMBER_OF_TOKEN_COURSE_LINE) {
			_error = DConst.ACTI_TEXT3 + line;
		}
		// first token
		String st = DXToolsMethods.getToken4Activitiy(str, " ", 0);
		if (isErrorEmpty()) {
			if (st.length() != DConst.SIZE_OF_COURSE_TOKEN)
				_error = DConst.ERR_ACTIVITY_NAME + line;  // TODO change
		}
		// 2nd token
		st = DXToolsMethods.getToken4Activitiy(str, " ", 1);
		if (isErrorEmpty()) {
			if (st.length() != DConst.SIZE_OF_GROUP_TOKEN)
				_error = DConst.ACTI_TEXT14 + line;
		}
		if (isErrorEmpty()) {
			if (!DXToolsMethods.isIntValue(st))
				_error = DConst.ACTI_TEXT14 + line;
		}
		// 3rd token
		st = DXToolsMethods.getToken4Activitiy(str, " ", 2);
		if (isErrorEmpty()) {
			if (st.length() != DConst.ACT_SITE_LENGTH)
				_error = DConst.ACTI_TEXT15 + line;
		}
		// 4th token
		st = DXToolsMethods.getToken4Activitiy(str, " ", 3);
		if (isErrorEmpty()) {
			if (st.length() > DConst.ACT_CAPACITY_LENGTH)
				_error = DConst.ACTI_TEXT16 + line;
		}
		if (isErrorEmpty()) {
			if (!DXToolsMethods.isIntValue(st))
				_error = DConst.ACTI_TEXT16 + line;
		}

	//	return "";
	}

	/**
	 * analyse SIG activities data by a finite states machine
	 * 
	 * @param integer
	 *            the beginPosition (start position of the finished states
	 *            machine)
	 * @return boolean "true" if the analysis proceeded successfully and false
	 *         otherwise
	 */
	private boolean analyseTokensFirstVersion(byte[] dataloaded, int beginPosition) throws DiaException {
		String methodName = "analyseTokensFirstVersion";
		String token;
		String sousString; 
		StringTokenizer st = new StringTokenizer(new String(dataloaded),
				DConst.CR_LF);
		StringTokenizer stLine = null; // auxiliar StringTokenizer for reading
		// subStrings in a line
		// int state=0;
		int position = beginPosition;
		if (!_open) {
			_line = 1;
		} else {
			_line = 0;
		}
		int numberOfUnities = 0;
		while (st.hasMoreElements()) {
			token = st.nextToken();
			_line++;
			if (_error.length() != 0)
				return false;
			switch (position) {
			case 0:// empty line
				position = 1;
				break;
			case 1:// activity name
				// activityName=token.trim();
				if (token.trim().length() < _ACTIVITYLENGTH) {
					String error = errorInActivityName(DConst.ERR_ACTIVITY_NAME, methodName);
					throw new DiaException(error);
//					_error = DConst.ACTI_TEXT1
//							+ _line
//							+ " in the activity file:"
//							+ "\n"
//							+ "I was in ActiviesList class and in analyseTokens method ";
//					return false;
				}
				position = 2;
				break;
			case 2:// activity visibility
				_error = checkIfBelongsValues(token, "0 1",
						DConst.ACTI_TEXT2 + _line, "ActivityList");
				if (_error.length() != 0)
					return false;
				_error = DXToolsMethods.isIntValue(token.trim(),
						DConst.ACTI_TEXT2 + _line, "ActivityList");
				if (_error.length() != 0)
					return false;
				position = 3;
				break;
			case 3:// number of activities
				_error = DXToolsMethods.isIntValue(token.trim(),
						DConst.ACTI_TEXT3 + _line, "ActivityList");
				if (_error.length() != 0)
					return false;
				position = 4;
				break;
			case 4:// teachers' names
				if (token.length() == 0) {
					_error = DConst.ACTI_TEXT4
							+ _line
							+ "in the activity file:"
							+ "\n"
							+ "I was in ActiviesList class and in analyseTokens method ";
					return false;
				}
				position = 7;
				if (!_open)
					_line += 2;
				break;
			case 5:// empty line
				position = 6;
				break;
			case 6:// empty line
				position = 7;
				break;
			case 7:// number of blocs
				_error = DXToolsMethods.isIntValue(token.trim(),
						DConst.ACTI_TEXT5 + _line, " ActivityList");
				if (_error.length() != 0)
					return false;
				numberOfUnities = Integer.parseInt(token.trim());
				position = 8;
				break;
			case 8:// duration of blocs
				stLine = new StringTokenizer(token);
				if (numberOfUnities != stLine.countTokens()) {
					throw new DiaException(DConst.ACTI_TEXT5
							+ _line
							+ " in the activity file:"
							+ "\n"
							+ "I was in ActiviesList class and in analyseTokens method ");
//					_error = DConst.ACTI_TEXT5
//							+ _line
//							+ " in the activity file:"
//							+ "\n"
//							+ "I was in ActiviesList class and in analyseTokens method ";
//					return false;
				}
				_error = checkIfLineIsEmpty(token,
						DConst.ACTI_TEXT6 + _line, "ActivityList");
				if (_error.length() != 0)
					return false;
				while (stLine.hasMoreElements()) {
					_error = DXToolsMethods.isIntValue(stLine.nextToken(),
							DConst.ACTI_TEXT7 + _line, "ActivityList");
					if (_error.length() != 0)
						return false;
				}
				position = 9;
				break;
			case 9:// days and periods of blocs
				stLine = new StringTokenizer(token);
				_error = checkIfLineIsEmpty(token,
						DConst.ACTI_TEXT6 + _line, "ActivityList");
				if (_error.length() != 0)
					return false;
				// int typeOfData= DXToolsMethods.countTokens(token,".");
				if (!_open) {
					if ((numberOfUnities * 2) != stLine.countTokens()) {
						_error = DConst.ACTI_TEXT5 + _line + " ActivityList";
						return false;
					}
					while (stLine.hasMoreElements()) {// rgr A problem in
						// tests allwhile is a
						// problem in real life
						// StringTokenizer stLine1;
						_error = DXToolsMethods.isIntValue(stLine.nextToken(),
								DConst.ACTI_TEXT8 + _line, "ActivityList");
						if (_error.length() != 0)
							return false;
					}// end while(stLine.hasMoreElements())
				}// end if(!_open)
				position = 10;
				break;
			case 10:// fixed rooms
				stLine = new StringTokenizer(token);
				_error = checkIfLineIsEmpty(token,
						DConst.ACTI_TEXT6 + _line, "ActivityList");
				if (_error.length() != 0)
					return false;
				while (stLine.hasMoreElements()) {
					sousString = stLine.nextToken();
					_error = checkIfBelongsValues(sousString,
							"0 1", DConst.ACTI_TEXT9 + _line, "ActivityList");
					if (_error.length() != 0)
						return false;
				}
				position = 11;
				break;
			case 11:// Preferred rooms
				stLine = new StringTokenizer(token);
				_error = checkIfLineIsEmpty(token,
						DConst.ACTI_TEXT6 + _line, "ActivityList");
				if (_error.length() != 0)
					return false;
				if (numberOfUnities != stLine.countTokens()) {
					_error = DConst.ACTI_TEXT10
							+ _line
							+ "in the activity file:"
							+ "\n"
							+ "I was in ActiviesList class and in analyseTokens method ";
					return false;
				}
				position = 12;
				break;
			case 12:// type of rooms
				stLine = new StringTokenizer(token);
				_error = checkIfLineIsEmpty(token,
						DConst.ACTI_TEXT6 + _line, "ActivityList");
				if (_error.length() != 0)
					return false;
				while (stLine.hasMoreElements()) {
					_error = DXToolsMethods.isIntValue(stLine.nextToken(),
							DConst.ACTI_TEXT11 + _line, "ActivityList");
					if (_error.length() != 0)
						return false;
				}
				position = 13;
				break;
			case 13:// idem
				stLine = new StringTokenizer(token);
				_error = checkIfLineIsEmpty(token,
						DConst.ACTI_TEXT6 + _line, "ActivityList");
				if (_error.length() != 0)
					return false;
				while (stLine.hasMoreElements()) {
					_error = DXToolsMethods.isIntValue(stLine.nextToken(),
							DConst.ACTI_TEXT11 + _line, "ActivityList");
					if (_error.length() != 0)
						return false;
				}
				position = 14;
				break;
			case 14:// pre-affected course

				_error = analyseTokenPreaffectedRoom(token, numberOfUnities, 0,
						_line);
				position = beginPosition;
				if (st.hasMoreElements())
					_line++;
				break;

			}// end switch (position)

		}// end while (st.hasMoreElements())

		return true;
	}

	private String errorInActivityName(String error, String methodName) {
		String message = error +  DConst.CR + DConst.ERR_ACTIVITY_LINE 
		+ _line + DConst.CR 
		+ DConst.ERR_ACTIVITY_FILE 
		+ DConst.CR 
		+ "In class :" + this.getClass().getSimpleName() 
		+ "In method " + methodName;
		return message;
	}

	public void buildSetOfResources(DataExchange de, int beginPosition) {
		if (de.getHeader().equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
			buildSetOfResources1_6(de.getContents().getBytes(), beginPosition);
		} else {
			buildSetOfResources1_5(de.getContents().getBytes(), beginPosition);
		}

	} // end analyseTokens

	/**
	 * build activitiesList from activities data by a finite state machine
	 * 
	 * @param integer
	 *            the beginPosition (start position of the finished states
	 *            machine)
	 * @return boolean "true" if the analysis proceeded successfully and false
	 *         otherwise
	 */
	public void buildSetOfResources1_5(byte[] dataloaded, int beginPosition) {
		String token;

		StringTokenizer st = new StringTokenizer(new String(dataloaded), "\r\n");

		int position = beginPosition;
		String course = "";
		while (st.hasMoreElements()) {
			token = st.nextToken();
			switch (position) {
			case 0:// empty line
				position = 1;
				break;
			case 1:// activity name
				// activityName= token;
				course = "";
				token += DConst.ACTIVITY_NAME_TOKEN_SEPARATOR
						+ DConst.ACTIVITY_STANDARD_SITE
						+ DConst.ACTIVITY_NAME_TOKEN_SEPARATOR
						+ DConst.ACTIVITY_STANDARD_CAPACITY;
				position = 2;
				break;
			case 2:// activity visibility

				position = 3;
				break;
			case 3:// number of activities
				position = 4;
				break;
			case 4:// teachers' names

				position = 7;
				break;
			case 5:// empty line
				position = 6;
				break;
			case 6:// empty line
				position = 7;
				break;
			case 7:// number of blocs
				position = 8;
				break;
			case 8:// duration of blocs
				position = 9;
				break;
			case 9:// days and periods of blocs

				position = 10;
				break;
			case 10:// fixed rooms

				position = 11;
				break;
			case 11:// Preferred rooms

				position = 12;
				break;
			case 12:// type of rooms

				position = 13;
				break;
			case 13:// idem

				position = 14;
				break;
			case 14:// activity is fixed or not
				DataExchange dEx = new ByteArrayMsg(DConst.FILE_VER_NAME1_5,
						course + token);
				String site = DConst.ACTIVITY_STANDARD_SITE;
				DResource actResc = getResource(site);

				if (actResc == null) {
					actResc = new DResource(site, new SetOfActivities(_open,
							_periodLength));
					addResource(actResc, 1);
				}

				SetOfActivities soa = (SetOfActivities) actResc.getAttach();
				soa.buildSetOfResources(dEx, 1);
				position = beginPosition;
				break;

			}// end switch (position)
			course += token + DConst.CR_LF;

		}// end while (st.hasMoreElements())

	}

	/**
	 * build activitiesList from activities data by a finite state machine
	 * 
	 * @param integer
	 *            the beginPosition (start position of the finished states
	 *            machine)
	 * @return boolean "true" if the analysis proceeded successfully and false
	 *         otherwise
	 */
	public void buildSetOfResources1_6(byte[] dataloaded, int beginPosition) {
		String token;
		StringTokenizer st = new StringTokenizer(new String(dataloaded), DConst.CR_LF);
		int position = beginPosition;
		String site = "", course = "";
		st.nextToken();// jump the first line

		while (st.hasMoreElements()) {
			token = st.nextToken();
			switch (position) {
			case 0:// empty line
				position = 1;
				break;
			case 1:// activity name
				// activityName= token;
				course = "";
				site = DXToolsMethods.getToken(token,
						DConst.ACTIVITY_TOKEN_SEPARATOR,
						DConst.ACTIVITY_SITE_TOKEN);
				position = 2;
				break;
			case 2:// activity visibility

				position = 3;
				break;
			case 3:// number of activities
				position = 4;
				break;
			case 4:// teachers' names

				position = 7;
				break;
			case 5:// empty line
				position = 6;
				break;
			case 6:// empty line
				position = 7;
				break;
			case 7:// number of blocs
				position = 8;
				break;
			case 8:// duration of blocs
				position = 9;
				break;
			case 9:// days and periods of blocs

				position = 10;
				break;
			case 10:// fixed rooms

				position = 11;
				break;
			case 11:// Preferred rooms

				position = 12;
				break;
			case 12:// type of rooms

				position = 13;
				break;
			case 13:// idem

				position = 14;
				break;
			case 14:// activity is fixed or not
				DataExchange dEx = new ByteArrayMsg(DConst.FILE_VER_NAME1_5,
						course + token);
				DResource actResc = getResource(site);

				if (actResc == null) {
					actResc = new DResource(site, new SetOfActivities(_open,
							_periodLength));
					addResource(actResc, 1);
				}

				SetOfActivities soa = (SetOfActivities) actResc.getAttach();
				soa.buildSetOfResources(dEx, 1);
				position = beginPosition;
				break;
			}// end switch (position)
			course += token + DConst.CR_LF;

		}// end while (st.hasMoreElements())
	}

	public String getError() {
		return _error;
	}

	/***
	 * */
	public int getLine() {
		return _line;
	}

	/**
	 * This object (which is already a string!) is itself returned.
	 * 
	 * @return the string itself
	 */
	public String toWrite() {
		String reslist = "";
		if (getSetOfResources().size() > 0) {
			DResource siteRsc;
			SetOfActivities soa;
			for (int i = 0; i < getSetOfResources().size() - 1; i++) {
				siteRsc = getSetOfResources().get(i);
				soa = (SetOfActivities) siteRsc.getAttach();
				reslist += soa.toWrite(siteRsc.getID()) + DConst.CR_LF;
			}
			siteRsc = getSetOfResources().get(getSetOfResources().size() - 1);
			soa = (SetOfActivities) siteRsc.getAttach();
			reslist += soa.toWrite(siteRsc.getID());
		}
		return reslist;
	}

	// /**
	// * Return the unity specified by the parameters
	// *
	// * @param actKey
	// * the activity key
	// * @param typeKey
	// * the type key
	// * @param secKey
	// * the section key
	// * @param unitKey
	// * the unity key
	// * @return The unity wanted
	// */
	//
	// public Unity getUnity(long actKey, long typeKey, long secKey, long
	// unitKey) {
	//
	// DResource a = getResource(actKey);
	// if (a != null) {
	// DResource t = ((Activity) a.getAttach()).getSetOfTypes()
	// .getResource(typeKey);
	// if (t != null) {
	// DResource s = ((Type) t.getAttach()).getSetOfSections()
	// .getResource(secKey);
	// if (s != null) {
	// DResource u = ((Section) s.getAttach()).getSetOfUnities()
	// .getResource(unitKey);
	// if (u != null)
	// return (Unity) u.getAttach();
	// }
	// }
	// }
	// return null;
	// }

	// /**
	// * Return the unity specified by the parameters
	// *
	// * @param actKey
	// * the activity ID
	// * @param typeKey
	// * the type ID
	// * @param secKey
	// * the section ID
	// * @param unitKey
	// * the unity ID
	// * @return The unity wanted
	// */
	//
	// public Unity getUnity(String actID, String typeID, String secID,
	// String unitID) {
	//
	// DResource a = getResource(actID);
	// if (a != null) {
	// DResource t = ((Activity) a.getAttach()).getSetOfTypes()
	// .getResource(typeID);
	// if (t != null) {
	// DResource s = ((Type) t.getAttach()).getSetOfSections()
	// .getResource(secID);
	// if (s != null) {
	// DResource u = ((Section) s.getAttach()).getSetOfUnities()
	// .getResource(unitID);
	// if (u != null)
	// return (Unity) u.getAttach();
	// }
	// }
	// }
	// return null;
	// }

	// /**
	// *
	// * @param actID
	// * @param typeID
	// * @param secID
	// * @return
	// */
	// public Section getSection(String actID, String typeID, String secID) {
	// DResource a = getResource(actID);
	// if (a != null) {
	// DResource t = ((Activity) a.getAttach()).getSetOfTypes()
	// .getResource(typeID);
	// if (t != null) {
	// DResource s = ((Type) t.getAttach()).getSetOfSections()
	// .getResource(secID);
	// if (s != null)
	// return (Section) s.getAttach();
	// }
	// }
	// return null;
	// }

	// /**
	// *
	// * @param actID
	// * @param typeID
	// * @return
	// */
	// public Type getType(String actID, String typeID) {
	// DResource a = getResource(actID);
	// if (a != null) {
	// DResource t = ((Activity) a.getAttach()).getSetOfTypes()
	// .getResource(typeID);
	// if (t != null) {
	// return (Type) t.getAttach();
	// }
	// }
	// return null;
	// }

	// /**
	// * Return the name of the unity specified by the parameters
	// *
	// * @param actKey
	// * the activity key
	// * @param typeKey
	// * the type key
	// * @param secKey
	// * the section key
	// * @param unitKey
	// * the unity key
	// * @return The name of the unity wanted
	// */
	// public String getUnityCompleteName(long actKey, long typeKey, long
	// secKey,
	// long unitKey) {
	// DResource a = getResource(actKey);
	// DResource t = ((Activity) a.getAttach()).getSetOfTypes().getResource(
	// typeKey);
	// DResource s = ((Type) t.getAttach()).getSetOfSections().getResource(
	// secKey);
	// DResource u = ((Section) s.getAttach()).getSetOfUnities().getResource(
	// unitKey);
	// return a.getID() + "." + t.getID() + "." + s.getID() + "." + u.getID()
	// + ".";
	// }

	// /**
	// *
	// * @param vect
	// * @return
	// */
	// public Vector getUnitiesNames(Vector vect) {
	// Vector result = new Vector();
	// for (int i = 0; i < vect.size(); i++) {
	// long actKey = Long.parseLong(DXToolsMethods.getToken(vect.get(i)
	// .toString(), ".", 0));
	// long typeKey = Long.parseLong(DXToolsMethods.getToken(vect.get(i)
	// .toString(), ".", 1));
	// long secKey = Long.parseLong(DXToolsMethods.getToken(vect.get(i)
	// .toString(), ".", 2));
	// long unitKey = Long.parseLong(DXToolsMethods.getToken(vect.get(i)
	// .toString(), ".", 3));
	// result.add(getUnityCompleteName(actKey, typeKey, secKey, unitKey));
	// }// end for (int i=0; i< vect.size(); i++)
	// return result;
	// }

	// /**
	// * Sets a field belonging a Unity
	// *
	// * @param actKey
	// * the activity key
	// * @param typeKey
	// * the type key
	// * @param secKey
	// * the section key
	// * @param unitKey
	// * the unity key
	// * @param fieldIndex
	// * The index identifaying the field
	// * @param fieldValue
	// * The value to be setted in the field
	// */
	// public void setUnityField(long actKey, long typeKey, long secKey,
	// long unitKey, int fieldIndex, String fieldValue) {
	// DResource a = getResource(actKey);
	// DResource t = ((Activity) a.getAttach()).getSetOfTypes().getResource(
	// typeKey);
	// DResource s = ((Type) t.getAttach()).getSetOfSections().getResource(
	// secKey);
	// DResource u = ((Section) s.getAttach()).getSetOfUnities().getResource(
	// unitKey);
	// u.getAttach().setField(fieldIndex, fieldValue);
	// }

	// /**
	// *
	// * @param actID
	// * @param typeID
	// * @param secID
	// * @param unitID
	// * @param fieldIndex
	// * @param fieldValue
	// */
	// public void setUnityField(String actID, String typeID, String secID,
	// String unitID, int fieldIndex, String fieldValue) {
	// DResource a = getResource(actID);
	// DResource t = ((Activity) a.getAttach()).getSetOfTypes().getResource(
	// typeID);
	// DResource s = ((Type) t.getAttach()).getSetOfSections().getResource(
	// secID);
	// DResource u = ((Section) s.getAttach()).getSetOfUnities().getResource(
	// unitID);
	// u.getAttach().setField(fieldIndex, fieldValue);
	// }

	/**
	 * check if there is an error detected
	 * 
	 * @return
	 */
	private boolean isErrorEmpty() {
		return _error.length() == 0;
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

	public void readSetOfActivities(DataExchange de, int i) throws DiaException {
		if (this.analyseTokens(de, i)) {
			this.buildSetOfResources(de, i);
		}
		
	}


	/**
	 * check if a String is not empty and exit software otherwise
	 * 
	 * @param String
	 *            the string value to check
	 * @param String
	 *            the error message to print
	 * @param String
	 *            the classe name where error has been detect
	 */
	private final String checkIfLineIsEmpty(String line, String message,
			String classList) {
		String error = "";
		if (line.length() == 0) {
			error = message + " in the activity file:" + "\n" + "I was in "
					+ classList + " class and in analyseTokens method ";
		}
		return error;
	}
	
	
	/**
	 * check if a String only contains a reference value and exit software
	 * otherwise
	 * 
	 * @param String
	 *            the string value to check
	 * @param String
	 *            the reference value to use
	 * @param String
	 *            the error message to print
	 * @param String
	 *            the classe name where error has been detect
	 */
	private final String checkIfBelongsValues(String line,
			String refValues, String message, String classList) {
		StringTokenizer ref = new StringTokenizer(refValues);
		StringTokenizer lineValue = new StringTokenizer(line);
		String lvalue;
		String error = "";
		int count = 0, refSize = ref.countTokens();
		while (lineValue.hasMoreElements()) {
			lvalue = lineValue.nextToken();
			while (ref.hasMoreElements()) {
				if (!lvalue.equals(ref.nextToken()))
					count++;
				if (count == refSize) {
					error = message + " in the activity file:" + "\n"
							+ "I was in " + classList
							+ " class and in analyseTokens method ";
				}// end if(count==refSize)
			}// end while(ref.hasMoreElements())
		}// end while(lineValue.hasMoreElements())
		return error;
	}
}