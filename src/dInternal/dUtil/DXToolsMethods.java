/**
 *
 * Title: DXToolsMethods 
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
package dInternal.dUtil;


import java.util.StringTokenizer;

public class DXToolsMethods {


	/**
	 * check if a String is an int value and exit software otherwise
	 * 
	 * @param String
	 *            the string value to check
	 * @param String
	 *            the error message to print
	 * @param String
	 *            the classe name where error has been detect
	 */
	public final static String isIntValue(String value, String message,
			String classList) {
		String error = "";
		try {
			Integer.parseInt(value.trim());
		} catch (NumberFormatException exc) {
			error = message + " in the activity file:" + "\n" + "I was in "
					+ classList + " class and in analyseTokens method ";
		}
		return error;
	}

	/**
	 * check if a String is an int value and exit software otherwise
	 * 
	 * @param String
	 *            the string value to check
	 */
	public final static boolean isIntValue(String value) {
		// String error="";
		try {
			Integer.parseInt(value.trim());
		} catch (NumberFormatException exc) {
			return false;
		}
		return true;
	}



	/**
	 * Convert STI Time and periods
	 * 
	 * @param int
	 *            the reference period
	 * @return int[2] the first element of the table is hour and the second
	 *         element is minute
	 */
	public final static int[] convertSTIPeriods(int period) {
		int[] time = { 0, 30 };
		time[0] = 7 + period;
		return time;
	}



	/**
	 * return a token from a stringtokenizer
	 * 
	 * @param str
	 * @param delimiter
	 * @param position
	 * @return theToken or an empty String if position >= nbTokens
	 */
	public final static String getToken(String str, String delimiter,
			int position) {
		StringTokenizer strToken = new StringTokenizer(str, delimiter);
		int nbTokens = strToken.countTokens();
		for (int i = 0; i < nbTokens; i++) {
			if (i == position)
				return strToken.nextToken();
			strToken.nextToken();
		}
		return "";
	}

	/**
	 * remove a token in a stringtokenizer
	 * 
	 * @param str
	 * @param delimiter
	 * @param position
	 * @return
	 */
	public final static String removeToken(String str, String delimiter,
			int position) {
		StringTokenizer strToken = new StringTokenizer(str, delimiter);
		String res = "";
		int inc = 0;
		while (strToken.hasMoreElements()) {
			if (inc != position)
				res += strToken.nextToken() + delimiter;
			else
				strToken.nextToken();
			inc++;
		}
		return res;
	}

	/**
	 * count the number of tokens
	 * 
	 * @return
	 */
	public final static int countTokens(String str, String delimiter) {
		StringTokenizer strToken = new StringTokenizer(str, delimiter);
		return strToken.countTokens();

	}




	public static String getToken4Activitiy(String activityName,
			String delimiter, int position) {
		StringTokenizer strToken = new StringTokenizer(activityName, delimiter);
		int nbTokens = strToken.countTokens();
		for (int i = 0; i < nbTokens; i++) {
			if (i == position)
				return strToken.nextToken();
			strToken.nextToken();
		}
		return "";
	}

}